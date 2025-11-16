package com.polytechnique.fleetman.service;

import com.polytechnique.fleetman.dto.notification.NotificationCreateDTO;
import com.polytechnique.fleetman.dto.notification.NotificationDTO;
import com.polytechnique.fleetman.entity.NotificationEntity;
import com.polytechnique.fleetman.entity.UserEntity;
import com.polytechnique.fleetman.exception.ResourceNotFoundException;
import com.polytechnique.fleetman.repository.NotificationRepository;
import com.polytechnique.fleetman.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Transactional
    public NotificationDTO createNotification(NotificationCreateDTO notificationCreateDTO) {
        UserEntity user = userRepository.findById(notificationCreateDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        NotificationEntity notification = new NotificationEntity();
        notification.setNotificationSubject(notificationCreateDTO.getNotificationSubject());
        notification.setNotificationContent(notificationCreateDTO.getNotificationContent());
        notification.setNotificationDateTime(LocalDateTime.now());
        notification.setNotificationState(false);
        notification.setUser(user);

        NotificationEntity savedNotification = notificationRepository.save(notification);
        return convertToDTO(savedNotification);
    }

    @Transactional(readOnly = true)
    public NotificationDTO getNotificationById(Long notificationId) {
        NotificationEntity notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification non trouvée"));
        return convertToDTO(notification);
    }

    @Transactional(readOnly = true)
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NotificationDTO> getNotificationsByUserId(Long userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        return notificationRepository.findByUser_UserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NotificationDTO> getUnreadNotificationsByUserId(Long userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        return notificationRepository.findByUser_UserIdAndNotificationState(userId, false).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long countUnreadNotifications(Long userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        return notificationRepository.countByUser_UserIdAndNotificationState(userId, false);
    }

    @Transactional
    public NotificationDTO markAsRead(Long notificationId) {
        NotificationEntity notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification non trouvée"));

        notification.setNotificationState(true);
        NotificationEntity updatedNotification = notificationRepository.save(notification);
        return convertToDTO(updatedNotification);
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        List<NotificationEntity> notifications = notificationRepository
                .findByUser_UserIdAndNotificationState(userId, false);

        notifications.forEach(notification -> notification.setNotificationState(true));
        notificationRepository.saveAll(notifications);
    }

    @Transactional
    public void deleteNotification(Long notificationId) {
        if (!notificationRepository.existsById(notificationId)) {
            throw new ResourceNotFoundException("Notification non trouvée");
        }
        notificationRepository.deleteById(notificationId);
    }

    private NotificationDTO convertToDTO(NotificationEntity notification) {
        return new NotificationDTO(
                notification.getNotificationId(),
                notification.getNotificationSubject(),
                notification.getNotificationContent(),
                notification.getNotificationDateTime(),
                notification.getNotificationState(),
                notification.getUser().getUserId()
        );
    }
}
