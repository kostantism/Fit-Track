package gr.hua.dit.fittrack.core.service.impl;

import gr.hua.dit.fittrack.core.model.Appointment;
import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.port.EmailNotificationPort;
import gr.hua.dit.fittrack.core.port.SmsNotificationPort;
import gr.hua.dit.fittrack.core.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final SmsNotificationPort smsPort;
    private final EmailNotificationPort emailPort;

    public NotificationServiceImpl(
            SmsNotificationPort smsPort,
            EmailNotificationPort emailPort
    ) {
        this.smsPort = smsPort;
        this.emailPort = emailPort;
    }

    @Override
    public void notifyAppointmentApproved(Appointment appointment) {

        String phone = appointment.getCustomer().getMobilePhoneNumber();
        String email = appointment.getCustomer().getEmailAddress();

        String message = "Your appointment with trainer "
                + appointment.getTrainer().getLastName()
                + " has been approved!";

        try {
            smsPort.sendSms(phone, message);
        } catch (Exception ex) {
            LOGGER.error("Failed to send SMS", ex);
        }

        try {
            emailPort.sendEmail(
                    email,
                    "Appointment approved",
                    message
            );
        } catch (Exception ex) {
            LOGGER.warn("Email service unavailable (handled externally)");
        }
    }

    @Override
    public void notifyAppointmentCancelled(Appointment appointment) {

        String phone = appointment.getCustomer().getMobilePhoneNumber();
        String email = appointment.getCustomer().getEmailAddress();

        String message = "Your appointment with trainer "
                + appointment.getTrainer().getLastName()
                + " has been cancelled.";

        try {
            smsPort.sendSms(phone, message);
        } catch (Exception ex) {
            LOGGER.error("Failed to send cancellation SMS", ex);
        }

        try {
            emailPort.sendEmail(
                    email,
                    "Appointment cancelled",
                    message
            );
        } catch (Exception ex) {
            LOGGER.warn("Email service unavailable (handled externally)");
        }
    }

    @Override
    public void notifyUserRegistered(Person person) {

        String phone = person.getMobilePhoneNumber();
        String email = person.getEmailAddress();

        String message = "Welcome to FitTrack, "
                + person.getFirstName()
                + "! Your registration was successful.";

        try {
            smsPort.sendSms(phone, message);
        } catch (Exception ex) {
            LOGGER.error("Failed to send registration SMS", ex);
        }

        try {
            emailPort.sendEmail(
                    email,
                    "Successfully registered",
                    message
            );
        } catch (Exception ex) {
            LOGGER.warn("Email service unavailable (handled externally)");
        }

    }


}
