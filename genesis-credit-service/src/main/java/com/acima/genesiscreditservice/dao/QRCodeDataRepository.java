package com.acima.genesiscreditservice.dao;

import com.acima.genesiscreditservice.entities.QRCodeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QRCodeDataRepository extends JpaRepository<QRCodeData,Integer> {

    Optional<QRCodeData> findByRequestId(String requestId);
}
