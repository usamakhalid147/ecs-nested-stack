package com.acima.genesiscreditservice.entities;

import com.acima.genesiscreditservice.constants.GenesisConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The type Income info data.
 * @author Salman mustafa
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "income_info_data")
public class IncomeInfoData {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;

    @NotBlank(message = "{requestId.error.message}")
    private String requestId;

    @NotBlank(message = "{merchantName.error.message}")
    private String merchantName;

    @NotBlank(message = "{breadcrumbHeader.error.message}")
    private String breadcrumbHeader;

    @NotBlank(message = "{incomeSource.error.message}")
    @Pattern(regexp = GenesisConstants.INCOME_SOURCE_REGEX,message = "{incomeSource.error.message}")
    private String incomeSource;

    @NotBlank(message = "{paymentMode.error.message}")
    @Pattern(regexp = GenesisConstants.PAYMENT_MODE_REGEX,message = "{paymentMode.error.message}")
    private String paymentMode;

    @NotNull(message = "{monthlyIncome.error.message}")
    @Min(value = 1,message = "{monthlyIncome.error.message}")
    private Integer monthlyIncome;

    @NotBlank(message = "{paymentFrequency.error.message}")
    @Pattern(regexp = GenesisConstants.PAYMENT_FREQUENCY_REGEX,message = "{paymentFrequency.error.message}")
    private String paymentFrequency;

    private String paymentDay;
    private String normalPayDayByWeek;
    private String nextPayDaysOfMonthDate1;
    private String nextPayDaysOfMonthDate2;
    private String paymentDayOfMonth;
    private String paymentWeekOfMonth;

    @NotBlank(message = "{localDate.error.message}")
    @Pattern(regexp = GenesisConstants.LOCAL_DATE_REGEX,message = "{localDate.error.message}")
    private String nextPayDateFirst;

    @NotBlank(message = "{localDate.error.message}")
    @Pattern(regexp = GenesisConstants.LOCAL_DATE_REGEX,message = "{localDate.error.message}")
    private String nextPayDateSecond;

    @NotBlank(message = "{localDate.error.message}")
    @Pattern(regexp = GenesisConstants.LOCAL_DATE_REGEX,message = "{localDate.error.message}")
    private String nextPayDateThird;

    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;
}
