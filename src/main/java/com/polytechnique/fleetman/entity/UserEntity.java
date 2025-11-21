package com.polytechnique.fleetman.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "\"user\"") // Guillemets pour le mot réservé
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column( nullable = false, length = 100)
    private String userName;

    @Column( nullable = false, length = 255)
    private String userPassword;

    @Column( nullable = false, unique = true, length = 150)
    private String userEmail;

    @Column( nullable = false, length = 20)
    private String userPhoneNumber;

    @Column(name = "token", length = 500)
    private String token;

    @CreationTimestamp
    @Column( nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column( nullable = false)
    private LocalDateTime updatedAt;

    // Relation One-to-Many avec Notification
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationEntity> notifications;
}