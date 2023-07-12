package com.acima.genesiscreditservice.controller;

import com.acima.genesiscreditservice.model.QRCodeDataRequest;
import com.acima.genesiscreditservice.services.QRCodeDataService;
import com.acima.genesiscreditservice.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credit")
public class CreditController {

    private final QRCodeDataService qrCodeDataService;
    private final JwtUtil jwtUtil;

    public CreditController(QRCodeDataService qrCodeDataService, JwtUtil jwtUtil) {
        this.qrCodeDataService = qrCodeDataService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(@RequestParam("requestId") String requestId, HttpServletRequest request){

        if (jwtUtil.isRequestValid(request, requestId)){
            return ResponseEntity.ok("Genesis Credit Card Service");
        }
        return ResponseEntity.badRequest().body("Bad Request");
    }

    @GetMapping("/{orderId}")
    public String checkOrderStatus(@PathVariable String orderId) {
//        return service.checkOrderStatus(orderId);
        return (orderId);

    }

    @PostMapping("/process-qr-code-data")
    public ResponseEntity<Object> processQRCodeData(@RequestBody QRCodeDataRequest request){
        return ResponseEntity.ok(qrCodeDataService.saveQRCodeData(request));
    }
}