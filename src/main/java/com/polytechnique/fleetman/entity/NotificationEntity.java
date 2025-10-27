package com.polytechnique.fleetman.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(nullable = false, length = 200)
    private String notificationSubject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String notificationContent;

    @Column(nullable = false)
    private LocalDateTime notificationDateTime = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean notificationState = false;

    // Relation Many-to-One avec User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_notification_user"))
    private UserEntity user;
}
