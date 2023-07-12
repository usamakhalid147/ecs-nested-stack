package com.acima.genesiscreditservice.utils;

import com.acima.genesiscreditservice.model.ErrorResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * The type Genesis utils.
 *
 * @author LavKumar
 */
public class GenesisUtils {

    /**
     * Gets error response.
     *
     * @param message     the message
     * @param description the description
     * @return the error response
     */
    public static ErrorResponse getErrorResponse(String message, Object description) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                message, description);
    }

    /**
     * Is valid input boolean.
     *
     * @param text  the text
     * @param regex the regex
     * @return the boolean
     */
    public static boolean isValidInput(String text, String regex) {
        if (StringUtils.isNotBlank(text) && StringUtils.isNotBlank(regex))
            return Pattern.matches(regex, text);
        return false;
    }

    /**
     * Get local date from string date local date.
     *
     * @param dateString the date string
     * @return the local date
     */
    public static LocalDate getLocalDateFromStringDate(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

}
