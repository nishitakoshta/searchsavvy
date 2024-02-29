package com.personal.searchsavvy.controller;
import com.personal.searchsavvy.dto.BlogDTO;
import com.personal.searchsavvy.dto.PaginatedBlogResultDTO;
import com.personal.searchsavvy.service.BlogService;
import com.personal.searchsavvy.service.TfIdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("api/v1/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TfIdfService tfIdfService;
    @PostMapping
    public ResponseEntity<?> createBlog(@RequestBody BlogDTO blogDTO, @RequestParam int userId) {
        try {
            BlogDTO responseDto = blogService.addBlog(blogDTO,userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception e) {
            throw e;
        }
    }
    @PutMapping("/{blogId}")
    public ResponseEntity<?> updateBlog(@RequestBody BlogDTO blogDTO, @RequestParam int blogId){
        try {
            BlogDTO responseDTO = blogService.updateBlog(blogDTO, blogId);
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
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "query", required = false) String query, @RequestParam(defaultValue = "0") int pageIndex) {
        try {
            PaginatedBlogResultDTO matchingStories = tfIdfService.getMatchingBlog(query, pageIndex);
            if (query == null || query.trim().isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Invalid query");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            if (matchingStories.getSearchResult().isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Invalid page index or no result found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.status(HttpStatus.OK).body(matchingStories);
        }catch (Exception e){
            throw e;
        }
    }
}
