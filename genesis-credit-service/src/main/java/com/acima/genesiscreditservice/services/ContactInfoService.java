package com.acima.genesiscreditservice.services;

import com.acima.genesiscreditservice.dao.ContactInfoRepository;
import com.acima.genesiscreditservice.entities.ContactInfo;
import com.acima.genesiscreditservice.exceptions.GenericException;
import com.acima.genesiscreditservice.utils.GenesisUtils;
import com.acima.genesiscreditservice.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * The type Contact info service.
 *
 * @author LavKumar
 * @author Md Salman
 */
@Service
public class ContactInfoService {

    private final ContactInfoRepository contactInfoRepository;
    private final JwtUtil jwtUtil;

    /**
     * Instantiates a new Contact info service.
     *
     * @param contactInfoRepository the contact info repository
     * @param jwtUtil               the jwt util
     */
    public ContactInfoService(ContactInfoRepository contactInfoRepository, JwtUtil jwtUtil) {
        this.contactInfoRepository = contactInfoRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Gets contacts.
     *
     * @return the contacts
     */
    public List<ContactInfo> getContacts() {
        return contactInfoRepository.findAll();
    }

    /**
     * Save contact info contact info.
     *
     * @param contactInfo the contact info
     * @return the contact info
     */
    public ContactInfo saveContactInfo(ContactInfo contactInfo) {
        return contactInfoRepository.save(contactInfo);
    }

    /**
     * Save contacts info list.
     *
     * @param contactInfo the contact info
     * @return the list
     */
    public List<ContactInfo> saveContactsInfo(List<ContactInfo> contactInfo) {
        return contactInfoRepository.saveAll(contactInfo);
    }


    /**
     * Gets contact info by id.
     *
     * @param id the id
     * @return the contact info by id
     */
    public ContactInfo getContactInfoById(int id) {
        return contactInfoRepository.findById(id).orElse(null);
    }



//    public ContactInfo getContactInfoByName(String name) {
//        return contactInfoRepository.findByfirstName(name);
//    }


    /**
     * Update contact info contact info.
     *
     * @param contactInfo the contact info
     * @return the contact info
     */
    public ContactInfo updateContactInfo(ContactInfo contactInfo) {
        ContactInfo existingContactInfo = contactInfoRepository.findById(contactInfo.getId()).orElse(null);
        existingContactInfo.setId(contactInfo.getId());
        existingContactInfo.setFirstName(contactInfo.getFirstName());
        existingContactInfo.setLastName(contactInfo.getLastName());
        existingContactInfo.setMobilePhone(contactInfo.getMobilePhone());
        existingContactInfo.setEmail(contactInfo.getEmail());
        existingContactInfo.setAddress(contactInfo.getAddress());
        existingContactInfo.setStatus(contactInfo.getStatus());
        
        return contactInfoRepository.save(existingContactInfo);
    }

    /**
     * Delete contact info string.
     *
     * @param id the id
     * @return the string
     */
    public String deleteContactInfo(int id) {
        contactInfoRepository.deleteById(id);
        return "removed !! " + id;
    }

    /**
     * Add contact info.
     * @author LavKumar
     *
     * @param contactInfo the contact info
     * @param request     the request
     * @return the response entity
     */
    public ResponseEntity<Object> addContactInfo(@Valid @RequestBody ContactInfo contactInfo, HttpServletRequest request) {
        try{
            if (jwtUtil.isRequestValid(request, contactInfo.getRequestId().strip())){
                if (!contactInfoRepository.findByRequestId(contactInfo.getRequestId().strip()).isPresent()){
                    if (Boolean.TRUE.equals(contactInfo.getStatus())){
                        return ResponseEntity.ok(saveContactInfo(contactInfo));
                    }else {
                        throw new GenericException(HttpStatus.BAD_REQUEST.value(), "Please accept terms and conditions");
                    }
                }else {
                    throw new GenericException(HttpStatus.BAD_REQUEST.value(), "You have already saved contact information for this request");
                }
            }
        }catch(Exception e){
            throw new GenericException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(GenesisUtils.getErrorResponse("Please provide valid requestId","Invalid requestId"));
    }


}
