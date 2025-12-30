package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.model.Appointment;
import gr.hua.dit.fittrack.core.model.Person;

public interface NotificationService {

    void notifyAppointmentApproved(Appointment appointment);

    void notifyAppointmentCancelled(Appointment appointment);

    void notifyUserRegistered(Person person);


}
