package com.personal.searchsavvy.controller;
import com.personal.searchsavvy.dto.UserDTO;
import com.personal.searchsavvy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO responseDto = userService.addUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception e) {
            throw e;
        }
    }
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO, @RequestParam int userId){
        try {
            UserDTO responseDTO = userService.updateUser(userDTO, userId);
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        }catch (Exception e){
            throw e;
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllUser(){
        try {
            List<UserDTO> userList = userService.getAllUsers();
            return ResponseEntity.status(HttpStatus.OK).body(userList);
        }catch (Exception e){
            throw e;
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@RequestParam int userId){
        try {
            UserDTO responseDTO = userService.getUserById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        }catch (Exception e){
            throw e;
        }
    }
}
