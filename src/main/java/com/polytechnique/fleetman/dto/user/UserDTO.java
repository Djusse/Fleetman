package com.polytechnique.fleetman.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPhoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
