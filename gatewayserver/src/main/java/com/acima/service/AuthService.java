package com.acima.service;

import com.acima.constants.ApiConstants;
import com.acima.exceptions.exception.GenericException;
import com.acima.model.AuthResponse;
import com.acima.model.QRCodeDataRequest;
import com.acima.model.QRCodeResponse;
import com.acima.utils.JwtUtil;
import io.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The type Auth service.
 *
 * @author LavKumar
 */
@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final JwtUtil jwtUtil;
    private final WebClient webClient;
    @Value("${genesis.credit.service.baseUrl}")
    private String genesisCreditServiceBaseUrl;

    /**
     * Instantiates a new Auth service.
     *
     * @param jwtUtil   the jwt util
     * @param webClient the web client
     */
    public AuthService(JwtUtil jwtUtil, WebClient webClient) {
        this.jwtUtil = jwtUtil;
        this.webClient = webClient;
    }

    /**
     * Authenticate the user request.
     *
     * @param qrCodeDataRequest the qr code data request
     * @return the object of AuthResponse
     */
    public Mono<ResponseEntity<Object>> authenticate(QRCodeDataRequest qrCodeDataRequest) {
        AtomicReference<AuthResponse> authResponse = new AtomicReference<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Mono<QRCodeResponse> qrCodeResponseMono = webClient
                    .post()
                    .uri(genesisCreditServiceBaseUrl.concat(ApiConstants.GENESIS_QR_CODE_URI))
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .body(BodyInserters.fromValue(qrCodeDataRequest))
                    .retrieve()
                    .bodyToMono(QRCodeResponse.class);

            Mono<AtomicReference<AuthResponse>> map = qrCodeResponseMono
                    //.onErrorReturn(null)
                    .doOnError(throwable -> logger.error("Error :: {}", throwable.toString()))
                    .onErrorResume(throwable -> throwable instanceof ReadTimeoutException
                                    || throwable instanceof ConnectException || throwable instanceof WebClientRequestException,
                            t -> Mono.error(new GenericException(HttpStatus.BAD_REQUEST.value(), t.getMessage())))
                    .onErrorContinue(
                            (throwable, o) -> {
                                if (throwable instanceof ReadTimeoutException || throwable instanceof ConnectException
                                        || throwable instanceof WebClientRequestException) {
                                    throw new GenericException(HttpStatus.BAD_REQUEST.value(), throwable.getMessage());
                                }
                            })
                    .map(response -> {
                        String token = jwtUtil.generateToken(response.getRequestId());
                        //QRCodeResponse qrCodeResponse = new QRCodeResponse(response.getRequestId(), response.getLocationId(), response.getMerchantName());
                        authResponse.set(new AuthResponse(token, response));
                        return authResponse;
                    });
            return Mono.just(ResponseEntity.ok(map));

        }
        catch (Exception e) {
            throw new GenericException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
