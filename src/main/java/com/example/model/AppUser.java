package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "User_table")
@Inheritance(strategy = InheritanceType.JOINED)
public class AppUser {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    @JsonProperty("isActive")
    private boolean active;
    @Id
    @GeneratedValue
    private Long id;
}
