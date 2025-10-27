package com.polytechnique.fleetman.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long notificationId;
    private String notificationSubject;
    private String notificationContent;
    private LocalDateTime notificationDateTime;
    private Boolean notificationState;
    private Long userId;
}
