package com.s13sh.auth.service;

import com.s13sh.auth.dto.UserRequestDTO;
import com.s13sh.auth.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO saveUser(UserRequestDTO user);

    List<UserResponseDTO> fetchUsers();

    UserResponseDTO fetchUserById(long id);

    UserResponseDTO fetchUserByUsername(String username);
}
