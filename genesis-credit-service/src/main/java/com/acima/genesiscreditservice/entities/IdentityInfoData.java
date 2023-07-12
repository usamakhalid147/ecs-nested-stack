package com.acima.genesiscreditservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The type Identity info data.
 * @author Salman mustafa
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "identity_info_data")
public class IdentityInfoData {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;
    private String requestId;
    private String merchantName;
    private String breadcrumbHeader;
    private String dateOfBirth;
    private String ssn;
    private String confirmSsn;


    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;
}