package com.acima.genesiscreditservice.services;

import com.acima.genesiscreditservice.constants.GenesisConstants;
import com.acima.genesiscreditservice.dao.ContactInfoRepository;
import com.acima.genesiscreditservice.dao.IncomeInfoDataRepository;
import com.acima.genesiscreditservice.entities.IncomeInfoData;
import com.acima.genesiscreditservice.exceptions.GenericException;
import com.acima.genesiscreditservice.utils.GenesisUtils;
import com.acima.genesiscreditservice.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * The type Income info data service.
 *
 * @author Salman mustafa
 * @author LavKumar
 */
@Service
public class IncomeInfoDataService {
    private final IncomeInfoDataRepository incomeInfoDataRepository;
    private final ContactInfoRepository contactInfoRepository;
    private final JwtUtil jwtUtil;
    private final Validator validator;
    private final MessageSource messageSource;

    /**
     * Instantiates a new Income info data service.
     *
     * @param incomeInfoDataRepository the income info data repository
     * @param contactInfoRepository    the contact info repository
     * @param jwtUtil                  the jwt util
     * @param validator                the validator
     * @param messageSource            the message source
     */
    public IncomeInfoDataService(IncomeInfoDataRepository incomeInfoDataRepository, ContactInfoRepository contactInfoRepository, JwtUtil jwtUtil, @Qualifier("customValidator") Validator validator, MessageSource messageSource) {
        this.incomeInfoDataRepository = incomeInfoDataRepository;
        this.contactInfoRepository = contactInfoRepository;
        this.jwtUtil = jwtUtil;
        this.validator = validator;
        this.messageSource = messageSource;
    }

    /**
     * Save income info data income info data.
     *
     * @param request the request
     * @return the income info data
     */
    public IncomeInfoData saveIncomeInfoData(IncomeInfoData request) {
        return incomeInfoDataRepository.save(request);
    }

    /**
     * Process income info data response entity.
     *
     * @param incomeInfoData the income info data
     * @param servletRequest the servlet request
     * @return the response entity
     * @author LavKumar
     */
    public ResponseEntity<Object> processIncomeInfoData(IncomeInfoData incomeInfoData, HttpServletRequest servletRequest) {

        // checking validation on request
        Set<ConstraintViolation<IncomeInfoData>> violations = validator.validate(incomeInfoData);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        // validating required field input based on paymentFrequency
        validateInputsBasedOnPaymentFrequency(incomeInfoData);

        if (jwtUtil.isRequestValid(servletRequest, incomeInfoData.getRequestId().strip())) {

            // checking contact info is present or not, if exists then proceed else throw exception
            if (contactInfoRepository.findByRequestId(incomeInfoData.getRequestId().strip()).isPresent()) {

                // checking income info is already present or not, if yes then throw exception else proceed
                if (!incomeInfoDataRepository.findByRequestId(incomeInfoData.getRequestId().strip()).isPresent()) {
                    return ResponseEntity.ok(saveIncomeInfoData(incomeInfoData));
                } else {
                    throw new GenericException(HttpStatus.BAD_REQUEST.value(), "You have already saved income information for this request");
                }
            } else {
                throw new GenericException(HttpStatus.BAD_REQUEST.value(), "First you must fill the contact information");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(GenesisUtils.getErrorResponse("Please provide valid requestId", "Invalid requestId"));
    }

    /**
     * Validate inputs based on payment frequency.
     *
     * @param incomeInfoData the income info data
     */
    private void validateInputsBasedOnPaymentFrequency(IncomeInfoData incomeInfoData) {
        switch (incomeInfoData.getPaymentFrequency().strip()) {
            case GenesisConstants.WEEKLY -> {
                if (!GenesisUtils.isValidInput(incomeInfoData.getPaymentDay().strip(), GenesisConstants.DAY_REGEX)) {
                    throw new GenericException(HttpStatus.BAD_REQUEST.value(), messageSource.getMessage("paymentDay.error.message", null, null));
                }
            }

            case GenesisConstants.EVERY_OTHER_WEEK -> {
                if (!GenesisUtils.isValidInput(incomeInfoData.getPaymentDay().strip(), GenesisConstants.DAY_REGEX)) {
                    throw new GenericException(HttpStatus.BAD_REQUEST.value(), messageSource.getMessage("paymentDay.error.message", null, null));
                }
                if (!StringUtils.isNotBlank(incomeInfoData.getNormalPayDayByWeek().strip())) {
                    throw new GenericException(HttpStatus.BAD_REQUEST.value(), messageSource.getMessage("normalPayDayByWeek.error.message", null, null));
                }
            }

            case GenesisConstants.TWICE_A_MONTH -> {
                if (!StringUtils.isNotBlank(incomeInfoData.getNextPayDaysOfMonthDate1().strip())
                        || !StringUtils.isNotBlank(incomeInfoData.getNextPayDaysOfMonthDate2().strip())) {
                    throw new GenericException(HttpStatus.BAD_REQUEST.value(), messageSource.getMessage("nextPayDaysOfMonthDate.error.message", null, null));
                }
            }

            case GenesisConstants.DAY_OF_MONTH -> {
                if (!StringUtils.isNotBlank(incomeInfoData.getPaymentDayOfMonth().strip())) {
                    throw new GenericException(HttpStatus.BAD_REQUEST.value(), messageSource.getMessage("paymentDayOfMonth.error.message", null, null));
                }
            }

            case GenesisConstants.WEEK_DAY_OF_MONTH -> {
                if (!StringUtils.isNotBlank(incomeInfoData.getPaymentWeekOfMonth().strip())) {
                    throw new GenericException(HttpStatus.BAD_REQUEST.value(), messageSource.getMessage("paymentWeekOfMonth.error.message", null, null));
                }
            }
            default ->
                    throw new GenericException(HttpStatus.BAD_REQUEST.value(), messageSource.getMessage("paymentFrequency.error.message", null, null));
        }
    }

}
