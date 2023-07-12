package com.acima.genesiscreditservice.controller;

import com.acima.genesiscreditservice.entities.ContactInfo;
import com.acima.genesiscreditservice.services.ContactInfoService;
import com.acima.genesiscreditservice.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Contact info controller.
 *
 * @author LavKumar
 * @author Md Salman
 */
@RestController
@RequestMapping("api/contact-info")
public class ContactInfoController {

    private final ContactInfoService contactInfoService;

    /**
     * Instantiates a new Contact info controller.
     *
     * @param contactInfoService the contact info service
     */
    public ContactInfoController(ContactInfoService contactInfoService) {
        this.contactInfoService = contactInfoService;
    }

    /**
     * Add contact info response entity.
     *
     * @param contactInfo the contact info
     * @param request     the request
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<Object> addContactInfo(@Valid @RequestBody ContactInfo contactInfo, HttpServletRequest request) {
        return contactInfoService.addContactInfo(contactInfo, request);
    }

    /**
     * Find contact info by id contact info.
     *
     * @param id the id
     * @return the contact info
     */
    @GetMapping("/contactInfoById/{id}")
    public ContactInfo findContactInfoById(@PathVariable int id) {
        return contactInfoService.getContactInfoById(id);
    }

    /**
     * Update contact info contact info.
     *
     * @param contactInfo the contact info
     * @return the contact info
     */
    @PutMapping("/updateContactInfo")
    public ContactInfo updateContactInfo(@RequestBody ContactInfo contactInfo) {
        return contactInfoService.updateContactInfo(contactInfo);
    }

}
