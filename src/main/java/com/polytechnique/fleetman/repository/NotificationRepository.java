package com.polytechnique.fleetman.repository;


import com.polytechnique.fleetman.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByUser_UserId(Long userId);
    List<NotificationEntity> findByUser_UserIdAndNotificationState(Long userId, Boolean state);
    long countByUser_UserIdAndNotificationState(Long userId, Boolean state);
}