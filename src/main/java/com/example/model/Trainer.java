package com.example.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "trainer_table")
public class Trainer extends AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "specialization", nullable = false)
    private TrainingType specialization;

    @OneToMany(mappedBy = "trainer")
    private Set<Training> trainings = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "trainee_trainer",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "trainee_id")
    )
    private Set<Trainee> trainees = new HashSet<>();
}
