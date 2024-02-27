package com.personal.searchsavvy.service;
import com.personal.searchsavvy.dto.UserDTO;

import java.util.List;
public interface UserService {
    UserDTO addUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(int userId);
    UserDTO updateUser(UserDTO userDTO, int userId);
}
