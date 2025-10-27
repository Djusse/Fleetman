package com.polytechnique.fleetman.controller;

import com.polytechnique.fleetman.dto.user.UserCreateDTO;
import com.polytechnique.fleetman.dto.user.UserDTO;
import com.polytechnique.fleetman.dto.user.UserPasswordUpdateDTO;
import com.polytechnique.fleetman.dto.user.UserUpdateDTO;
import com.polytechnique.fleetman.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        UserDTO createdUser = userService.createUser(userCreateDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        UserDTO updatedUser = userService.updateUser(userId, userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Modifier le mot de passe de l'utilisateur (nécessite l'ancien mot de passe)
     */
    @PatchMapping("/{userId}/password")
    public ResponseEntity<Void> updateUserPassword(
            @PathVariable Long userId,
            @Valid @RequestBody UserPasswordUpdateDTO passwordUpdateDTO) {

        userService.updateUserPassword(userId, passwordUpdateDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Réinitialiser le mot de passe (pour admin, sans ancien mot de passe)
     */
    @PatchMapping("/{userId}/reset-password")
    public ResponseEntity<Void> resetUserPassword(
            @PathVariable Long userId,
            @Valid @RequestBody @NotBlank String newPassword) {

        userService.resetUserPassword(userId, newPassword);
        return ResponseEntity.ok().build();
    }

    /**
     * Vérifier si l'ancien mot de passe est correct
     */
    @PostMapping("/{userId}/verify-password")
    public ResponseEntity<Boolean> verifyPassword(
            @PathVariable Long userId,
            @RequestBody @NotBlank String password) {

        boolean isValid = userService.verifyPassword(userId, password);
        return ResponseEntity.ok(isValid);
    }
}
