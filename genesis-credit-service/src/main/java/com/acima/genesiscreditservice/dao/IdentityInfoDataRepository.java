package com.acima.genesiscreditservice.dao;

import com.acima.genesiscreditservice.entities.IdentityInfoData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface Identity info data repository.
 * @author MuhammadSalman
 */
public interface IdentityInfoDataRepository extends JpaRepository<IdentityInfoData,Integer> {
    /**
     * Find by request id optional.
     * @param requestId the request id
     * @return the optional
     */
    Optional<IdentityInfoData> findByRequestId(String requestId);
}
