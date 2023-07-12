package com.acima.genesiscreditservice.dao;

import com.acima.genesiscreditservice.entities.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface Contact info repository.
 * @author Md Salman
 * @author LavKumar
 */
public interface ContactInfoRepository extends JpaRepository<ContactInfo,Integer> {
    /**
     * Find byfirst name contact info.
     *
     * @param name the name
     * @return the contact info
     */
    ContactInfo findByfirstName(String name);

    /**
     * @author Lav Kumar
     * Find by request id optional.
     *
     * @param requestId the request id
     * @return the optional
     */
    Optional<ContactInfo> findByRequestId(String requestId);
}