package com.acima.filters;

import com.acima.exceptions.exception.GenericException;
import io.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

import java.net.ConnectException;

@Configuration
public class ResponseFilter {

    final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

    @Autowired
    FilterUtils filterUtils;

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> chain.filter(exchange)
                .doOnError(throwable -> logger.error("Error :: {}", throwable.toString()))
                .onErrorResume(throwable -> throwable instanceof ReadTimeoutException
                                || throwable instanceof ConnectException
                                || throwable instanceof WebClientRequestException
                                || throwable instanceof Exception,
                        t -> Mono.error(new GenericException(HttpStatus.BAD_REQUEST.value(), t.getMessage())))
                .onErrorContinue(
                        (throwable, o) -> {
                            if (throwable instanceof ReadTimeoutException || throwable instanceof ConnectException
                                    || throwable instanceof WebClientRequestException || throwable instanceof Exception) {
                                throw new GenericException(HttpStatus.BAD_REQUEST.value(), throwable.getMessage());
                            }
                        })
                .then(Mono.fromRunnable(() -> {
                    HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                    String correlationId = filterUtils.getCorrelationId(requestHeaders);
                    logger.debug("Adding the correlation id to the outbound headers. {}", correlationId);
                    exchange.getResponse().getHeaders().add(FilterUtils.CORRELATION_ID, correlationId);
                    logger.debug("Completing outgoing request for {}.", exchange.getRequest().getURI());
                }));
    }
}
