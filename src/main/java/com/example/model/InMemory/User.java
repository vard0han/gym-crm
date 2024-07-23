package com.example.model.InMemory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    @JsonProperty("isActive")
    private boolean active;

}
