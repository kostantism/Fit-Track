package gr.hua.dit.fittrack.core.port.impl;

import gr.hua.dit.fittrack.config.RestApiClientConfig;
import gr.hua.dit.fittrack.core.port.EmailNotificationPort;
import gr.hua.dit.fittrack.core.port.impl.dto.SendEmailRequest;
import gr.hua.dit.fittrack.core.port.impl.dto.SendEmailResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailNotificationPortImpl implements EmailNotificationPort {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(EmailNotificationPortImpl.class);

    private final RestTemplate restTemplate;

    public EmailNotificationPortImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean sendEmail(String toEmail, String subject, String content) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        SendEmailRequest body =
                new SendEmailRequest(toEmail, subject, content);

        HttpEntity<SendEmailRequest> entity =
                new HttpEntity<>(body, headers);

        String url = RestApiClientConfig.EMAIL_BASE_URL + "/api/v1/email";

        ResponseEntity<SendEmailResult> response =
                restTemplate.postForEntity(
                        url,
                        entity,
                        SendEmailResult.class
                );

        if (response.getStatusCode().is2xxSuccessful()
                && response.getBody() != null) {
            return response.getBody().sent();
        }

        throw new RuntimeException("Email service failed: " + response.getStatusCode());
    }
}


