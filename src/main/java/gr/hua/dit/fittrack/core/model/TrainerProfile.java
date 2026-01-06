package gr.hua.dit.fittrack.core.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trainer_profiles")
public class TrainerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "trainer_id", unique = true, nullable = false)
    private Person trainer;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private String area;

    @Column(length = 2000)
    private String bio;

    protected TrainerProfile() {
    }

    public TrainerProfile(
            Person trainer,
            String specialization,
            String area,
            String bio
    ) {
        if (trainer.getType() != PersonType.TRAINER) {
            throw new IllegalArgumentException("TrainerProfile can be created only for TRAINER");
        }
        this.trainer = trainer;
        this.specialization = specialization;
        this.area = area;
        this.bio = bio;
    }


    public Long getId() {
        return id;
    }

    public Person getTrainer() {
        return trainer;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getArea() {
        return area;
    }

    public String getBio() {
        return bio;
    }
}
