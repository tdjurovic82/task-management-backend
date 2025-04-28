package com.example.workforcemanagement.controllers;

import com.example.workforcemanagement.dto.CreateUserDTO;
import com.example.workforcemanagement.dto.UpdatePasswordDTO;
import com.example.workforcemanagement.dto.UserDTO;
import com.example.workforcemanagement.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Validated
@RequestMapping("/users")

public class UserController {


    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUserDTOById(id));
    }


    @GetMapping
    public List<UserDTO> getAllUsers() {

        return userService.getAllUsers();

    }
    @GetMapping("/by-email")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam @Email String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping
    public UserDTO createUser(@RequestBody CreateUserDTO createUserDTO) {
        return userService.createUser(createUserDTO);
    }

    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {

        UserDTO userDTO = userService.getUserDTOById(userId);

        userService.deleteUserById(userDTO.getId());
        return ResponseEntity.ok( userDTO.getName() + " deleted");


    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> deleteUsers(@RequestBody List<Long> ids) {
        userService.deleteUsersByIds(ids);
        return ResponseEntity.ok("Users deleted: " + ids.size());
    } //ciscenje baze

    @PatchMapping("/{id}/password")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody @Valid UpdatePasswordDTO dto) {

        userService.updatePassword(id, dto);
        return ResponseEntity.ok("Password updated successfully.");
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        UserDTO updated = userService.updateUser(id,dto);
        return ResponseEntity.ok(updated);


    }




}
