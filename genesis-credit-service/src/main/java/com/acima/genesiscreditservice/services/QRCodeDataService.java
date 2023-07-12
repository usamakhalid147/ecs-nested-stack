package com.acima.genesiscreditservice.services;

import com.acima.genesiscreditservice.dao.QRCodeDataRepository;
import com.acima.genesiscreditservice.entities.QRCodeData;
import com.acima.genesiscreditservice.model.QRCodeDataRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The type Qr code data service.
 * @author Md Salman
 * @author LavKumar
 */
@Service
public class QRCodeDataService {

    private final QRCodeDataRepository qrCodeDataRepository;

    /**
     * Instantiates a new Qr code data service.
     *
     * @param qrCodeDataRepository the qr code data repository
     */
    public QRCodeDataService(QRCodeDataRepository qrCodeDataRepository) {
        this.qrCodeDataRepository = qrCodeDataRepository;
    }

    /**
     * Save qr code data qr code data.
     *
     * @param request the request
     * @return the qr code data
     */
    public QRCodeData saveQRCodeData(QRCodeDataRequest request){
        return qrCodeDataRepository.save(new QRCodeData(request.getLocationGuid(), request.getUtmSource(),
                request.getUtmMedium(), request.getUtmCampaign(), request.getUtmContent(),
                request.getUtmTerm(), UUID.randomUUID()+"-"+System.currentTimeMillis(), request.getUtmGmid()));
    }
}
