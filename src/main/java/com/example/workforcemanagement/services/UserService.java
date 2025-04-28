package com.example.workforcemanagement.services;

import com.example.workforcemanagement.dto.CreateUserDTO;
import com.example.workforcemanagement.dto.UpdatePasswordDTO;
import com.example.workforcemanagement.dto.UserDTO;
import com.example.workforcemanagement.entities.User;
import com.example.workforcemanagement.exceptions.UserException;
import com.example.workforcemanagement.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

//    public List<User> findUsersByName(String userName) {
//        return userRepository.findByName(userName);
//    }

    public List<UserDTO> findUsersByName(String userName) {
        return userRepository.findByName(userName).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
    }
    public UserDTO getUserDTOById(Long userId) {
        return mapToResponse(getUserById(userId));
    }


//    public List<User> createUser(List<User> users) {
//        return userRepository.saveAll(users);
//    }

    public UserDTO createUser(CreateUserDTO createUserDTO) {
        if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new UserException("Email already in use");
        }
        User user = mapToEntity(createUserDTO);
        String hashed = passwordEncoder.encode(createUserDTO.getPassword());
        user.setPassword(hashed);
        return mapToResponse(userRepository.save(user));
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserDTO getUserByEmail(String email) {
        if (!userRepository.existsByEmail(email.toLowerCase())) throw new UserException("User with specified email address was not found");
        return mapToResponse(userRepository.findByEmail(email.toLowerCase()));

    }
//this is old code
//    public boolean authenticateUser(String email, String password) {
//        User user = userRepository.findByEmail(email);
//        if (user != null && user.getPassword().equals(password)) {
//            return true;
//
//        }
//        return false;
//    }

    public UserDTO updateUser(Long userId, UserDTO dto) {
        User user = getUserById(userId);

        if (dto.getName() != null) {
            user.setName(dto.getName());
        }

        if (dto.getEmail() != null && !dto.getEmail().equalsIgnoreCase(user.getEmail())) {
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new UserException("Email already in use");
            }

            user.setEmail(dto.getEmail().toLowerCase());
        }

        return mapToResponse(userRepository.save(user));
    }

    public void updatePassword(Long userId, UpdatePasswordDTO dto) {
        User user = getUserById(userId);
        String hashed = passwordEncoder.encode(dto.getPassword());
        user.setPassword(hashed);
        userRepository.save(user);
    }

    public User mapToEntity(CreateUserDTO dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail().toLowerCase())
                .password(dto.getPassword())
                .build();
    }

    public UserDTO mapToResponse(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public void deleteUsersByIds(List<Long> ids) {
        ids.forEach(this::deleteUserById);
    } // -ciscenje baze


}
