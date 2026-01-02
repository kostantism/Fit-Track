package gr.hua.dit.fittrack.core.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "training_session")
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Συνδέεται με Appointment
    @OneToOne(optional = false)
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;

    // 👤 Trainer
    @ManyToOne(optional = false)
    @JoinColumn(name = "trainer_id", nullable = false)
    private Person trainer;

    // 👤 Customer
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Person customer;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private TrainingSessionStatus status;

    @Column(name = "notes", length = 1000)
    private String notes;

    @Column(name = "training_plan", length = 2000)
    private String trainingPlan;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    // 🔹 Constructors
    public TrainingSession() {
    }

//    public TrainingSession(Appointment appointment,
//                           Person trainer,
//                           Person customer,
//                           LocalDateTime startTime,
//                           LocalDateTime endTime,
//                           TrainingSessionStatus status) {
//        this.appointment = appointment;
//        this.trainer = trainer;
//        this.customer = customer;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.status = status;
//    }

    public TrainingSession(Long id, Appointment appointment, Person trainer, Person customer, LocalDateTime startTime, LocalDateTime endTime, TrainingSessionStatus status, String notes, String trainingPlan, Instant createdAt) {
        this.id = id;
        this.appointment = appointment;
        this.trainer = trainer;
        this.customer = customer;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.notes = notes;
        this.trainingPlan = trainingPlan;
        this.createdAt = createdAt;
    }

    // 🔹 Getters & Setters

    public Long getId() {
        return id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Person getTrainer() {
        return trainer;
    }

    public void setTrainer(Person trainer) {
        this.trainer = trainer;
    }

    public Person getCustomer() {
        return customer;
    }

    public void setCustomer(Person customer) {
        this.customer = customer;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public TrainingSessionStatus getStatus() {
        return status;
    }

    public void setStatus(TrainingSessionStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getTrainingPlan() {
        return trainingPlan;
    }

    public void setTrainingPlan(String trainingPlan) {
        this.trainingPlan = trainingPlan;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

//package gr.hua.dit.fittrack.core.model;
//
//import jakarta.persistence.*;
//import org.hibernate.annotations.CreationTimestamp;
//
//import java.time.Instant;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "training_session")
//public class TrainingSession {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // 🔗 Συνδέεται με Appointment
//    @OneToOne(optional = false)
//    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
//    private Appointment appointment;
//
//    // 👤 Trainer
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "trainer_id", nullable = false)
//    private Person trainer;
//
//    // 👤 Customer
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "customer_id", nullable = false)
//    private Person customer;
//
//    @Column(name = "start_time", nullable = false)
//    private LocalDateTime startTime;
//
//    @Column(name = "end_time", nullable = false)
//    private LocalDateTime endTime;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "status", nullable = false, length = 20)
//    private TrainingSessionStatus status;
//
//    @Column(name = "notes", length = 1000)
//    private String notes;
//
//    @CreationTimestamp
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private Instant createdAt;
//
//    // 🔹 Constructors
//    public TrainingSession() {
//    }
//
//    public TrainingSession(Appointment appointment,
//                           Person trainer,
//                           Person customer,
//                           LocalDateTime startTime,
//                           LocalDateTime endTime,
//                           TrainingSessionStatus status) {
//        this.appointment = appointment;
//        this.trainer = trainer;
//        this.customer = customer;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.status = status;
//    }
//
//    // 🔹 Getters & Setters
//
//    public Long getId() {
//        return id;
//    }
//
//    public Appointment getAppointment() {
//        return appointment;
//    }
//
//    public void setAppointment(Appointment appointment) {
//        this.appointment = appointment;
//    }
//
//    public Person getTrainer() {
//        return trainer;
//    }
//
//    public void setTrainer(Person trainer) {
//        this.trainer = trainer;
//    }
//
//    public Person getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Person customer) {
//        this.customer = customer;
//    }
//
//    public LocalDateTime getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(LocalDateTime startTime) {
//        this.startTime = startTime;
//    }
//
//    public LocalDateTime getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(LocalDateTime endTime) {
//        this.endTime = endTime;
//    }
//
//    public TrainingSessionStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(TrainingSessionStatus status) {
//        this.status = status;
//    }
//
//    public String getNotes() {
//        return notes;
//    }
//
//    public void setNotes(String notes) {
//        this.notes = notes;
//    }
//
//    public Instant getCreatedAt() {
//        return createdAt;
//    }
//
//
//}
