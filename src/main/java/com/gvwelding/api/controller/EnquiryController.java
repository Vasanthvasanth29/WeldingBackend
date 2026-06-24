package com.gvwelding.api.controller;

import com.gvwelding.api.dto.EnquiryRequest;
import com.gvwelding.api.service.EnquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Simple CORS for now, can be restricted in WebConfig
public class EnquiryController {

    private final EnquiryService enquiryService;

    @PostMapping("/enquiry")
    public ResponseEntity<Map<String, Object>> submitEnquiry(@Valid @RequestBody EnquiryRequest request) {
        enquiryService.processEnquiry(request);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Enquiry submitted successfully");

        return ResponseEntity.ok(response);
    }
}
