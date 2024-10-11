package com.code.BTS.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private String id;
    private String email;
    private String username;
    private String password;
}
