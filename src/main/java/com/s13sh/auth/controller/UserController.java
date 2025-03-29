package com.s13sh.auth.controller;

import com.s13sh.auth.dto.UserRequestDTO;
import com.s13sh.auth.dto.UserResponseDTO;
import com.s13sh.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameAlreadyBoundException;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserRequestDTO userRequestDTO){
        return new ResponseEntity<>(userService.saveUser(userRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> fetchUsers(){
        return new ResponseEntity<>(userService.fetchUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> fetchUserById(@PathVariable long id){
        return new ResponseEntity<>(userService.fetchUserById(id), HttpStatus.OK);
    }

    @GetMapping("/users/username/{username}")
    public ResponseEntity<UserResponseDTO> fetchUserByUsername(@PathVariable String username){
        return new ResponseEntity<>(userService.fetchUserByUsername(username), HttpStatus.OK);
    }
}
