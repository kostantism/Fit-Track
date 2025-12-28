package gr.hua.dit.fittrack.core.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "progress_entry")
public class ProgressEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    // =====================
    // Relations
    // =====================

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Person customer;

    // =====================
    // Progress data
    // =====================

    @Column(nullable = false)
    private LocalDate entryDate;

    @Column
    private Double weightKg;

    @Column
    private Integer runTimeSeconds;

    @Column(length = 500)
    private String notes;

    // =====================
    // Metadata
    // =====================

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected ProgressEntry() {}

    public ProgressEntry(
            Person customer,
            LocalDate entryDate,
            Double weightKg,
            Integer runTimeSeconds,
            String notes
    ) {
        this.customer = customer;
        this.entryDate = entryDate;
        this.weightKg = weightKg;
        this.runTimeSeconds = runTimeSeconds;
        this.notes = notes;
    }

    // Getters only (immutable-ish domain)
    public Long getId() { return id; }
    public Person getCustomer() { return customer; }
    public LocalDate getEntryDate() { return entryDate; }
    public Double getWeightKg() { return weightKg; }
    public Integer getRunTimeSeconds() { return runTimeSeconds; }
    public String getNotes() { return notes; }
    public Instant getCreatedAt() { return createdAt; }
}
