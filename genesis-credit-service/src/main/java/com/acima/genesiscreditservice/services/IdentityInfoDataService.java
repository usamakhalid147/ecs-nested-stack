package com.acima.genesiscreditservice.services;
import com.acima.genesiscreditservice.dao.IdentityInfoDataRepository;
import com.acima.genesiscreditservice.entities.IdentityInfoData;
import org.springframework.stereotype.Service;

/**
 * The type Identity info data service.
 * @author Salman mustafa
 */
@Service
public class IdentityInfoDataService {
    private final IdentityInfoDataRepository identityInfoDataRepository;

    /**
     * Instantiates a new Identity info data service.
     * @param identityInfoDataRepository the Identity info data repository
     */

    public IdentityInfoDataService(IdentityInfoDataRepository identityInfoDataRepository) {
        this.identityInfoDataRepository = identityInfoDataRepository;
    }

    /**
     * Save Identity info data Identity info data.
     * @param request the request
     * @return the Identity info data
     */
    public IdentityInfoData processIdentityInfoData(IdentityInfoData request){
        return identityInfoDataRepository.save(request);
    }
}
