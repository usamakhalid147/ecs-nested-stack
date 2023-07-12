package com.acima.genesiscreditservice.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Application config.
 * @author LavKumar
 */
@Configuration
public class ApplicationConfig {

    /**
     * Get validator validator.
     *
     * @return the validator
     */
    @Bean(name = "customValidator")
    public Validator getValidator(){
        return Validation.buildDefaultValidatorFactory()
                .getValidator();
    }

}
