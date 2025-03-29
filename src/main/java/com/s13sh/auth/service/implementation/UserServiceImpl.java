package com.s13sh.auth.service.implementation;

import com.s13sh.auth.dto.UserRequestDTO;
import com.s13sh.auth.dto.UserResponseDTO;
import com.s13sh.auth.entity.Role;
import com.s13sh.auth.entity.User;
import com.s13sh.auth.exception.AlreadyExistsException;
import com.s13sh.auth.repository.UserRepository;
import com.s13sh.auth.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO){
        if (!userRepository.existsByUsername(userRequestDTO.getUsername())) {
            User user = new User(
                    null,
                    userRequestDTO.getUsername(),
                    passwordEncoder.encode(userRequestDTO.getPassword()),
                    Role.valueOf(userRequestDTO.getRole())
            );
           userRepository.save(user);
           return new UserResponseDTO(user.getId(),user.getUsername(),user.getRole().name());
        }
        throw new AlreadyExistsException("Username " + userRequestDTO.getUsername() + " Exists");
    }

    @Override
    public List<UserResponseDTO> fetchUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty())
            throw new NoSuchElementException("No users found");

        return users.stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getUsername(), user.getRole().name()))
                .toList();
    }

    @Override
    public UserResponseDTO fetchUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with ID " + id + " not found"));
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getRole().name());
    }

    @Override
    public UserResponseDTO fetchUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User with username '" + username + "' not found"));
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getRole().name());
    }

    @PostConstruct
    public void initAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User(
                    null,
                    "admin",
                    passwordEncoder.encode("admin"),
                    Role.ADMIN
            );
            userRepository.save(admin);
        }
    }
}
