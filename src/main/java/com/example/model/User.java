package com.example.model;

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
    private boolean isActive;
}
