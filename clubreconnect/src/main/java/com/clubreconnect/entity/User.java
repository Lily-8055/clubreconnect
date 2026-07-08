package com.clubreconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    private String role; // ROLE_USER, ROLE_ADMIN

    private Integer graduationYear;

    @Column(length = 1000)
    private String bio;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Membership> memberships;
}
