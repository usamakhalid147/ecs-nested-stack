package com.acima.genesiscreditservice.controller;

import com.acima.genesiscreditservice.entities.IdentityInfoData;
import com.acima.genesiscreditservice.services.IdentityInfoDataService;
import com.acima.genesiscreditservice.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Identity info controller.
 * @author Salman mustafa
 */
@RestController
@RequestMapping("/api/identity-info")
public class IdentityInfoController {

    private final IdentityInfoDataService identityInfoDataService;
    private final JwtUtil jwtUtil;

    /**
     * Instantiates a new Identity info controller.
     * @param identityInfoDataService the identity info data service
     * @param jwtUtil the jwt util
     */
    public IdentityInfoController(IdentityInfoDataService identityInfoDataService, JwtUtil jwtUtil) {
        this.identityInfoDataService = identityInfoDataService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Test response entity.
     * @param requestId the request id
     * @param request   the request
     * @return the response entity
     */
    @GetMapping("/test")
    public ResponseEntity<String> test(@RequestParam("requestId") String requestId, HttpServletRequest request){

        if (jwtUtil.isRequestValid(request, requestId)){
            return ResponseEntity.ok("Genesis Credit Card Service: IdentityInfoController");
        }
        return ResponseEntity.badRequest().body("Bad Request");
    }

    /**
     * Process Identity info data response entity.
     * @param identityInfoData the identity Info Data
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<Object> processIdentityInfoData(@RequestBody IdentityInfoData  identityInfoData, HttpServletRequest servletRequest) {
        return ResponseEntity.ok(identityInfoDataService.processIdentityInfoData(identityInfoData));
    }
}
