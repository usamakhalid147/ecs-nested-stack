package com.acima.genesiscreditservice.dao;

import com.acima.genesiscreditservice.entities.IncomeInfoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IncomeInfoDataRepository extends JpaRepository<IncomeInfoData,Integer> {
    Optional<IncomeInfoData> findByRequestId(String requestId);
}
