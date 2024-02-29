package com.personal.searchsavvy.service.impl;
import com.personal.searchsavvy.dto.UserDTO;
import com.personal.searchsavvy.entity.Users;
import com.personal.searchsavvy.repository.UsersRepository;
import com.personal.searchsavvy.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;
    @Override
    @Transactional
    public UserDTO addUser(UserDTO userDTO) {
        try {
            Users user = new Users();
            user.setUserName(userDTO.getUserName());
            user.setUserAge(userDTO.getUserAge());
            user.setUserMobile(userDTO.getUserMobile());
            user.setUserEmail(userDTO.getUserEmail());
            user.setCreatedOn(LocalDateTime.now());
            user.setUpdatedOn(LocalDateTime.now());
            usersRepository.save(user);
            return UserDTO.builder()
                    .userId(user.getUserId())
                    .userName(user.getUserName())
                    .userAge(user.getUserAge())
                    .userMobile(user.getUserMobile())
                    .userEmail(user.getUserEmail())
                    .createdOn(user.getCreatedOn())
                    .updatedOn(user.getUpdatedOn())
                    .build();
        } catch (Exception ex) {
            throw ex;
        }
    }
    @Override
    public List<UserDTO> getAllUsers() {
        try {
            List<Users> userList = usersRepository.findAll();
            List<UserDTO> userDTOList = userList.stream()
                    .map(user -> UserDTO.builder()
                            .userId(user.getUserId())
                            .userName(user.getUserName())
                            .userAge(user.getUserAge())
                            .userMobile(user.getUserMobile())
                            .userEmail(user.getUserEmail())
                            .createdOn(user.getCreatedOn())
                            .updatedOn(user.getUpdatedOn())
                            .build())
                    .collect(Collectors.toList());
            return userDTOList;
        } catch (Exception e) {
            throw e;
        }
    }
    @Override
    public UserDTO getUserById(int userId) {
        try {
            Users user = usersRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with user id " + userId));
            return UserDTO.builder()
                    .userId(userId)
                    .userName(user.getUserName())
                    .userAge(user.getUserAge())
                    .userMobile(user.getUserMobile())
                    .userEmail(user.getUserEmail())
                    .createdOn(user.getCreatedOn())
                    .updatedOn(user.getUpdatedOn())
                    .build();
        } catch (Exception e) {
            throw e;
        }
    }
    @Override
    public UserDTO updateUser(UserDTO userDTO, int userId) {
        try {
            Users user = usersRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with user id " + userId));
            user.setUserName(userDTO.getUserName());
            user.setUserAge(userDTO.getUserAge());
            user.setUserMobile(userDTO.getUserMobile());
            user.setUserEmail(userDTO.getUserEmail());
            user.setUpdatedOn(LocalDateTime.now());
            usersRepository.save(user);
            return UserDTO.builder()
                    .userId(user.getUserId())
                    .userName(user.getUserName())
                    .userAge(user.getUserAge())
                    .userMobile(user.getUserMobile())
                    .userEmail(user.getUserEmail())
                    .createdOn(user.getCreatedOn())
                    .updatedOn(user.getUpdatedOn())
                    .build();
        } catch (Exception e) {
            throw e;
        }
    }
}
