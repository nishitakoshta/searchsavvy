package com.personal.searchsavvy.controller;
import com.personal.searchsavvy.dto.BlogDTO;
import com.personal.searchsavvy.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/v1/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @PostMapping
    public ResponseEntity<?> createBlog(@RequestBody BlogDTO blogDTO, @RequestParam int userId) {
        try {
            BlogDTO responseDto = blogService.addBlog(blogDTO,userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception e) {
            throw e;
        }
    }
    @PutMapping
    public ResponseEntity<?> updateBlog(@RequestBody BlogDTO blogDTO,@RequestParam int blogId,  @RequestParam int userId){
        try {
            BlogDTO responseDTO = blogService.updateBlog(blogDTO, blogId, userId);
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        }catch (Exception e){
            throw e;
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllBlogs(){
        try {
            List<BlogDTO> userList = blogService.getAllBlogs();
            return ResponseEntity.status(HttpStatus.OK).body(userList);
        }catch (Exception e){
            throw e;
        }
    }
    @GetMapping("/{blogId}")
    public ResponseEntity<?> getBlogById(@RequestParam int blogId){
        try {
            BlogDTO responseDTO = blogService.getBlogById(blogId);
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        }catch (Exception e){
            throw e;
        }
    }
}
