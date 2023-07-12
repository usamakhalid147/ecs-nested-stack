package com.acima.controller;

import com.acima.exceptions.exception.GenericException;
import com.acima.model.QRCodeDataRequest;
import com.acima.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * The type Auth controller.
 *
 * @author LavKumar
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Instantiates a new Auth controller.
     *
     * @param authService the auth service
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Authenticate user request.
     *
     * @param qrCodeDataRequest the qr code data request
     * @return the object of AuthResponse
     */
    @PostMapping("/login")
    public Mono<ResponseEntity<Object>> authenticate(@Valid @RequestBody QRCodeDataRequest qrCodeDataRequest) {
        try{
            return authService.authenticate(qrCodeDataRequest);
        }catch (Exception e){
            throw new GenericException(400, e.getMessage());
        }
    }

}
