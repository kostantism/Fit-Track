package gr.hua.dit.fittrack.core.port;

/**
 * Port to external service for sending email notifications.
 */
public interface EmailNotificationPort {
    boolean sendEmail(final String toEmail, final String subject, final String content);
}
