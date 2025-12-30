package gr.hua.dit.fittrack.core.port.impl.dto;

public record SendEmailRequest(
        String to,
        String subject,
        String content
) {}
