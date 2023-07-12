package com.acima.genesiscreditservice.entities;

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

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contact_info")
public class ContactInfo {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank(message = "{requestId.error.message}")
    private String requestId;

    @NotBlank(message = "{breadcrumbHeader.error.message}")
    private String breadcrumbHeader;

    @NotBlank(message = "{firstName.error.message}")
    private String firstName;

    @NotBlank(message = "{lastName.error.message}")
    private String lastName;

    @NotBlank(message = "{phone.error.message}")
    @Size(min = 9, max = 9, message = "{phone.error.message}")
    @Pattern(regexp = "(^$|[0-9]{9})", message = "{phone.error.message}")
    private String mobilePhone;

    @NotEmpty(message = "{email.error.message}")
    @Email(message = "{email.error.message}")
    private String email;

    @NotBlank(message = "{address.error.message}")
    private String address;

    @NotBlank(message = "{city.error.message}")
    private String city;

    @NotBlank(message = "{state.error.message}")
    private String state;

    @NotBlank(message = "{zipcode.error.message}")
    @Size(min = 5, max = 5, message = "{zipcode.error.message}")
    @Pattern(regexp = "(^$|[0-9]{5})", message = "{zipcode.error.message}")
    private String zipcode;

    private String aptSteUnit;

    @NotNull(message = "{status.error.message}")
    private Boolean status;

    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;

}
