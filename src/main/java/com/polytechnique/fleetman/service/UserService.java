package com.polytechnique.fleetman.service;

import com.polytechnique.fleetman.dto.user.UserCreateDTO;
import com.polytechnique.fleetman.dto.user.UserDTO;
import com.polytechnique.fleetman.dto.user.UserPasswordUpdateDTO;
import com.polytechnique.fleetman.dto.user.UserUpdateDTO;
import com.polytechnique.fleetman.entity.UserEntity;
import com.polytechnique.fleetman.exception.InvalidPasswordException;
import com.polytechnique.fleetman.exception.ResourceAlreadyExistsException;
import com.polytechnique.fleetman.exception.ResourceNotFoundException;
import com.polytechnique.fleetman.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        if (userRepository.existsByUserEmail(userCreateDTO.getUserEmail())) {
            throw new ResourceAlreadyExistsException("Email déjà utilisé");
        }

        UserEntity user = new UserEntity();
        user.setUserName(userCreateDTO.getUserName());
        user.setUserPassword(passwordEncoder.encode(userCreateDTO.getUserPassword()));
        user.setUserEmail(userCreateDTO.getUserEmail());
        user.setUserPhoneNumber(userCreateDTO.getUserPhoneNumber());

        UserEntity savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        return convertToDTO(user);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        UserEntity user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        return convertToDTO(user);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO updateUser(Long userId, UserUpdateDTO userUpdateDTO) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        if (userUpdateDTO.getUserName() != null) {
            user.setUserName(userUpdateDTO.getUserName());
        }
        if (userUpdateDTO.getUserEmail() != null) {
            if (!user.getUserEmail().equals(userUpdateDTO.getUserEmail())
                    && userRepository.existsByUserEmail(userUpdateDTO.getUserEmail())) {
                throw new ResourceAlreadyExistsException("Email déjà utilisé");
            }
            user.setUserEmail(userUpdateDTO.getUserEmail());
        }
        if (userUpdateDTO.getUserPhoneNumber() != null) {
            user.setUserPhoneNumber(userUpdateDTO.getUserPhoneNumber());
        }

        UserEntity updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Utilisateur non trouvé");
        }
        userRepository.deleteById(userId);
    }

    private UserDTO convertToDTO(UserEntity user) {
        return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getUserEmail(),
                user.getUserPhoneNumber(),
                "",
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    @Transactional
    public void updateUserPassword(Long userId, UserPasswordUpdateDTO passwordUpdateDTO) {
        // ✅ CORRECTION
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        // Vérifier que l'ancien mot de passe est correct
        if (!passwordEncoder.matches(passwordUpdateDTO.getOldPassword(), user.getUserPassword())) {
            throw new InvalidPasswordException("L'ancien mot de passe est incorrect");
        }

        // Vérifier que les nouveaux mots de passe correspondent
        if (!passwordUpdateDTO.getNewPassword().equals(passwordUpdateDTO.getConfirmPassword())) {
            throw new InvalidPasswordException("Les nouveaux mots de passe ne correspondent pas");
        }

        // Vérifier que le nouveau mot de passe est différent de l'ancien
        if (passwordEncoder.matches(passwordUpdateDTO.getNewPassword(), user.getUserPassword())) {
            throw new InvalidPasswordException("Le nouveau mot de passe doit être différent de l'ancien");
        }

        // Hasher et sauvegarder le nouveau mot de passe
        String hashedPassword = passwordEncoder.encode(passwordUpdateDTO.getNewPassword());
        user.setUserPassword(hashedPassword);

        userRepository.save(user);
    }

    // Méthode alternative pour l'admin (sans vérification de l'ancien mot de passe)
    public void resetUserPassword(Long userId, String newPassword) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + userId));

        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setUserPassword(hashedPassword);

        userRepository.save(user);
    }

    /**
     * Vérifie si le mot de passe fourni correspond au mot de passe de l'utilisateur
     */
    public boolean verifyPassword(Long userId, String password) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + userId));

        return passwordEncoder.matches(password, user.getUserPassword());
    }
}
