package com.gvwelding.api.service;

import com.gvwelding.api.dto.EnquiryRequest;
import com.gvwelding.api.entity.Enquiry;
import com.gvwelding.api.repository.EnquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnquiryService {

    private final EnquiryRepository repository;
    private final EmailService emailService;

    @Transactional
    public void processEnquiry(EnquiryRequest request) {
        log.info("Processing new enquiry from: {}", request.getName());

        // 1. Save to database (Bonus requirement)
        Enquiry enquiry = Enquiry.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .service(request.getService())
                .message(request.getMessage())
                .build();
        repository.save(enquiry);

        // 2. Send emails
        try {
            emailService.sendEnquiryToAdmin(request);
            emailService.sendAutoResponseToUser(request);
        } catch (Exception e) {
            log.error("Failed to send emails for enquiry from {}: {}", request.getName(), e.getMessage());
            // We don't throw exception here to ensure the user gets a success response 
            // if the enquiry was at least saved to the DB. 
            // Or you might want to fail the whole transaction? 
            // In many production cases, email sending is async or failure is logged.
        }
    }
}
