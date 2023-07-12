package com.acima.genesiscreditservice.utils;

import com.acima.genesiscreditservice.dao.QRCodeDataRepository;
import com.acima.genesiscreditservice.entities.QRCodeData;
import com.acima.genesiscreditservice.exceptions.GenericException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.Optional;

/**
 * The type Jwt util.
 *
 * @author LavKumar
 */
@Component
public class JwtUtil {

    private final QRCodeDataRepository qrCodeDataRepository;

    /**
     * Instantiates a new Jwt util.
     *
     * @param qrCodeDataRepository the qr code data repository
     */
    public JwtUtil(QRCodeDataRepository qrCodeDataRepository) {
        this.qrCodeDataRepository = qrCodeDataRepository;
    }

    /**
     * Parse jwt string.
     *
     * @param request the request
     * @return the string
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    /**
     * Is token expired boolean.
     *
     * @param exp the exp
     * @return the boolean
     */
    private Boolean isTokenExpired(Long exp) {
        long expirationTime = exp;
        long currentTime = System.currentTimeMillis() / 1000;
        return currentTime >= expirationTime;
    }

    /**
     * Get request id string.
     *
     * @param token the token
     * @return the string
     */
    private String getSubjectFromToken(String token){
        try{
            String[] tokens = token.split("\\.");
            String claims = new String(Base64.getDecoder().decode(tokens[1]));
            JsonNode obj = new ObjectMapper().readTree(claims);
            if (!isTokenExpired(obj.get("exp").asLong())){
                return obj.get("sub").asText();
            }else {
                throw new GenericException(HttpStatus.BAD_REQUEST.value(), "Token has been expired. please login again");
            }

        }catch (Exception e){
            throw new GenericException(HttpStatus.BAD_REQUEST.value(), "Invalid Token");
        }
    }

    /**
     * Is token valid boolean.
     *
     * @param request the request
     * @return the boolean
     */
    public boolean isTokenValid(HttpServletRequest request){
        return getQRCodeDataDetails(request).isPresent();
    }

    /**
     * Is request valid boolean.
     *
     * @param request   the request
     * @param requestId the request id
     * @return the boolean
     */
    public boolean isRequestValid(HttpServletRequest request, String requestId){
        return getQRCodeDataDetails(request).get().getRequestId().equals(requestId);
    }

    /**
     * Get qr code data details optional.
     *
     * @param request the request
     * @return the optional
     */
    private Optional<QRCodeData> getQRCodeDataDetails(HttpServletRequest request){
        String subjectFromToken = getSubjectFromToken(parseJwt(request));
        return qrCodeDataRepository.findByRequestId(subjectFromToken);
    }

}
