package gr.hua.dit.fittrack.core.service.impl;

import gr.hua.dit.fittrack.core.model.*;
import gr.hua.dit.fittrack.core.repository.AppointmentRepository;
import gr.hua.dit.fittrack.core.repository.TrainerAvailabilityRepository;
import gr.hua.dit.fittrack.core.service.AppointmentService;
import gr.hua.dit.fittrack.core.service.NotificationService;
import gr.hua.dit.fittrack.core.service.PersonDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final TrainerAvailabilityRepository availabilityRepository;
    private final NotificationService notificationService;
    private final PersonDataService personDataService;

    private static final int MAX_ACTIVE_APPOINTMENTS_PER_USER = 5;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  TrainerAvailabilityRepository availabilityRepository,
                                  NotificationService notificationService,
                                  PersonDataService personDataService) {
        this.appointmentRepository = appointmentRepository;
        this.availabilityRepository = availabilityRepository;
        this.notificationService = notificationService;
        this.personDataService = personDataService;
    }

    @Override
    public Appointment createAppointment(
            Long customerId,
            Long trainerId,
            LocalDateTime start,
            LocalDateTime end
    ) {

        Person customer = personDataService.findPersonEntityById(customerId);
        Person trainer = personDataService.findPersonEntityById(trainerId);

        if (customer.getType() != PersonType.CUSTOMER) {
            throw new IllegalArgumentException("Only customers can book appointments");
        }
        if (trainer.getType() != PersonType.TRAINER) {
            throw new IllegalArgumentException("Appointment must be with a trainer");
        }

        if (start.isBefore(LocalDateTime.now()) || !start.isBefore(end)) {
            throw new IllegalArgumentException("Invalid appointment time");
        }

        long activeCount = appointmentRepository.countByCustomerAndStatusIn(
                customer,
                List.of(AppointmentStatus.PENDING, AppointmentStatus.APPROVED)
        );

        if (activeCount >= MAX_ACTIVE_APPOINTMENTS_PER_USER) {
            throw new IllegalStateException("Max active appointments reached");
        }

        boolean overlap = appointmentRepository
                .existsByTrainerAndStartDateTimeLessThanAndEndDateTimeGreaterThan(
                        trainer, end, start
                );

        if (overlap) {
            throw new IllegalStateException("Trainer has overlapping appointment");
        }

        boolean available = availabilityRepository
                .existsByTrainerAndStartTimeLessThanAndEndTimeGreaterThanAndStatus(
                        trainer, end, start, AvailabilityStatus.AVAILABLE
                );

        if (!available) {
            throw new IllegalStateException("Trainer not available");
        }

        Appointment appointment = new Appointment(
                trainer,
                customer,
                start,
                end,
                AppointmentStatus.PENDING
        );

        return appointmentRepository.save(appointment);
    }


    @Override
    @Transactional(readOnly = true)
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with id: " + id));
    }

    @Override
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with id: " + id));
        appointmentRepository.delete(appointment);
    }


    @Override
    public List<Appointment> getApprovedAppointmentsForTrainer(Long trainerId) {
        Person trainer = personDataService.findPersonEntityById(trainerId);

        if (trainer.getType() != PersonType.TRAINER) {
            throw new SecurityException("Not a trainer");
        }

        return appointmentRepository.findByTrainerAndStatus(
                trainer,
                AppointmentStatus.APPROVED
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Appointment> getAppointmentsForTrainer(Long trainerId) {

        Person trainer = personDataService.findPersonEntityById(trainerId);

        if (trainer.getType() != PersonType.TRAINER) {
            throw new SecurityException("Not a trainer");
        }

        return appointmentRepository.findByTrainer(trainer);
    }

    @Override
    public void approveAppointment(Long appointmentId, Long trainerId) {

        Appointment appointment = getAppointmentById(appointmentId);
        Person trainer = personDataService.findPersonEntityById(trainerId);

        if (!appointment.getTrainer().getId().equals(trainerId)) {
            throw new SecurityException("Trainer does not own this appointment");
        }

        if (appointment.getStatus() != AppointmentStatus.PENDING) {
            throw new IllegalStateException("Only pending appointments can be approved");
        }

        appointment.setStatus(AppointmentStatus.APPROVED);
        appointmentRepository.save(appointment);

        notificationService.notifyAppointmentApproved(appointment);
    }

    @Override
    public void cancelAppointment(Long appointmentId, Long customerId) {

        Appointment appointment = getAppointmentById(appointmentId);
        Person customer = personDataService.findPersonEntityById(customerId);

        if (customer.getType() != PersonType.CUSTOMER) {
            throw new IllegalArgumentException("Only customer can cancel appointment");
        }

        if (!appointment.getCustomer().getId().equals(customerId)) {
            throw new SecurityException("Customer does not own this appointment");
        }

        if (appointment.getStatus() == AppointmentStatus.CANCELLED ||
                appointment.getStatus() == AppointmentStatus.REJECTED) {
            throw new IllegalStateException("Appointment cannot be cancelled");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);

        notificationService.notifyAppointmentCancelled(appointment);
    }

    @Override
    public void rejectAppointment(Long appointmentId, Long trainerId) {

        Appointment appointment = getAppointmentById(appointmentId);
        Person trainer = personDataService.findPersonEntityById(trainerId);

        if (trainer.getType() != PersonType.TRAINER) {
            throw new SecurityException("Only trainers can reject appointments");
        }

        if (!appointment.getTrainer().getId().equals(trainerId)) {
            throw new SecurityException("Trainer does not own this appointment");
        }

        if (appointment.getStatus() != AppointmentStatus.PENDING) {
            throw new IllegalStateException("Only pending appointments can be rejected");
        }

        appointment.setStatus(AppointmentStatus.REJECTED);
        appointmentRepository.save(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Appointment> getAppointmentsForCustomer(Long customerId) {

        Person customer = personDataService.findPersonEntityById(customerId);

        if (customer.getType() != PersonType.CUSTOMER) {
            throw new SecurityException("Not a customer");
        }

        return appointmentRepository.findByCustomer(customer);
    }
}
