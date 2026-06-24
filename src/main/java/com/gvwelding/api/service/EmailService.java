package com.gvwelding.api.service;

import com.gvwelding.api.dto.EnquiryRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${business.email.receiver}")
    private String businessEmail;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEnquiryToAdmin(EnquiryRequest request) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(businessEmail);
        helper.setSubject("NEW ENQUIRY - GV Welding Works");

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        String content = "<h3>New Enquiry Received</h3>" +
                "<p><b>Name:</b> " + request.getName() + "</p>" +
                "<p><b>Phone:</b> " + request.getPhone() + "</p>" +
                "<p><b>Email:</b> " + request.getEmail() + "</p>" +
                "<p><b>Service:</b> " + request.getService() + "</p>" +
                "<p><b>Message:</b> " + request.getMessage() + "</p>" +
                "<p><b>Submission Time:</b> " + time + "</p>";

        helper.setText(content, true);
        mailSender.send(message);
        log.info("Enquiry email sent to admin: {}", businessEmail);
    }

    public void sendAutoResponseToUser(EnquiryRequest request) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(request.getEmail());
        helper.setSubject("GV Welding Works - Request Received");

        String content = "<div style='font-family: Arial, sans-serif; line-height: 1.6;'>" +
                "<h2>Hello " + request.getName() + ",</h2>" +
                "<p><b>Tamil:</b><br>" +
                "உங்கள் கோரிக்கை வெற்றிகரமாக பெறப்பட்டது.<br>" +
                "எங்கள் குழு விரைவில் உங்களை தொடர்பு கொள்வார்கள்.<br>" +
                "நீங்கள் நேரடியாகவும் எங்களை தொடர்பு கொள்ளலாம்.</p>" +
                "<p><b>English:</b><br>" +
                "Your request has been successfully submitted.<br>" +
                "Our engineering team will contact you shortly.<br>" +
                "You may also contact us directly.</p>" +
                "<br>" +
                "<p><b>Business Phone:</b> +91 6374942172<br>" +
                "<b>Business Email:</b> vasanthvasanth1945@gmail.com</p>" +
                "<br>" +
                "<p>Best Regards,<br><b>GV Welding Works Team</b></p>" +
                "</div>";

        helper.setText(content, true);
        mailSender.send(message);
        log.info("Auto-response email sent to user: {}", request.getEmail());
    }
}
