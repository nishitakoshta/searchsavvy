package com.personal.searchsavvy.service;
import com.personal.searchsavvy.dto.BlogDTO;

import java.util.List;
public interface BlogService {
    BlogDTO addBlog(BlogDTO blogDTO, int userId);
    BlogDTO updateBlog(BlogDTO blogDTO, int blogId, int userId);
    List<BlogDTO> getAllBlogs();
    BlogDTO getBlogById(int blogId);
}
