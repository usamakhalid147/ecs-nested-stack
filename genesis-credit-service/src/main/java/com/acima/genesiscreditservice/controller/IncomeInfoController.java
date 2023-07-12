package com.acima.genesiscreditservice.controller;

import com.acima.genesiscreditservice.entities.IncomeInfoData;
import com.acima.genesiscreditservice.services.IncomeInfoDataService;
import com.acima.genesiscreditservice.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * The type Income info controller.
 *
 * @author Salman mustafa
 */
@RestController
@RequestMapping("/api/income-info")
public class IncomeInfoController {
    private final IncomeInfoDataService incomeInfoDataService;
    private final JwtUtil jwtUtil;
    private final Validator validator;

    /**
     * Instantiates a new Income info controller.
     *
     * @param incomeInfoDataService the income info data service
     * @param jwtUtil               the jwt util
     * @param validator             the validator
     */
    public IncomeInfoController(IncomeInfoDataService incomeInfoDataService, JwtUtil jwtUtil, @Qualifier("customValidator") Validator validator) {
        this.incomeInfoDataService = incomeInfoDataService;
        this.jwtUtil = jwtUtil;
        this.validator = validator;
    }

    /**
     * Test response entity.
     *
     * @param requestId the request id
     * @param request   the request
     * @return the response entity
     */
    @GetMapping("/test")
    public ResponseEntity<String> test(@RequestParam("requestId") String requestId, HttpServletRequest request) {

        if (jwtUtil.isRequestValid(request, requestId)) {
            return ResponseEntity.ok("Genesis Credit Card Service: IncomeInfoController");
        }
        return ResponseEntity.badRequest().body("Bad Request");
    }

    /**
     * Process income info data response entity.
     *
     * @param incomeInfoData the income info data
     * @param servletRequest the servlet request
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<Object> processIncomeInfoData(@RequestBody IncomeInfoData incomeInfoData, HttpServletRequest servletRequest) {
        return incomeInfoDataService.processIncomeInfoData(incomeInfoData,servletRequest);
    }

}
