package com.personal.searchsavvy.service.impl;
import com.personal.searchsavvy.dto.BlogDTO;
import com.personal.searchsavvy.entity.Blogs;
import com.personal.searchsavvy.entity.Users;
import com.personal.searchsavvy.enums.CategoryEnum;
import com.personal.searchsavvy.repository.BlogRepository;
import com.personal.searchsavvy.repository.UsersRepository;
import com.personal.searchsavvy.service.BlogService;
import com.personal.searchsavvy.service.TfIdfService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private TfIdfService tfIdfService;

    @Override
    public BlogDTO addBlog(BlogDTO blogDTO, int userId){
        try{
            Blogs blog = new Blogs();
            blog.setBlogTitle(blogDTO.getBlogTitle());
            blog.setBlogCategory(blogDTO.getBlogCategory().ordinal());
            blog.setTagLine(blogDTO.getTagLine());
            blog.setBlogContent(blogDTO.getBlogContent());
            blog.setThumbnail(blogDTO.getThumbnail());
            blog.setCreatedOn(LocalDateTime.now());
            blog.setUpdatedOn(LocalDateTime.now());
            List<Blogs> blogList = blogRepository.findAll();
            List<String> blogContent = blogList.stream()
                    .map(Blogs::getBlogContent)
                    .toList();
            List<String> tagLineList = blogList.stream()
                    .map(Blogs::getTagLine)
                    .toList();
            List<String> blogTitle = blogList.stream()
                    .map(Blogs::getBlogTitle)
                    .toList();
            List<String> blogCategory = blogList.stream()
                    .map(blogs -> String.valueOf(CategoryEnum.values()[blog.getBlogCategory()]))
                    .toList();
            Users user = usersRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with user id "+userId));
            blog.setUser(user);
            blogRepository.save(blog);
            Map<String, Double> blogContentScore = tfIdfService.calculateTFIDF(blog.getBlogContent(), blogContent != null ? blogContent : Collections.singletonList(blogDTO.getBlogContent()));
            tfIdfService.saveTFIDFScores(blogContentScore, "content", blog.getBlogId());
            Map<String, Double> blogTagLineScore = tfIdfService.calculateTFIDF(blog.getTagLine(), tagLineList != null ? tagLineList : Collections.singletonList(blogDTO.getTagLine()));
            tfIdfService.saveTFIDFScores(blogTagLineScore, "tagLine", blog.getBlogId());
            Map<String, Double> blogTitleScore = tfIdfService.calculateTFIDF(blog.getBlogTitle(), blogTitle != null ? blogTitle : Collections.singletonList(blogDTO.getBlogTitle()));
            tfIdfService.saveTFIDFScores(blogTitleScore, "title", blog.getBlogId());
            Map<String, Double> blogCategoryScore = tfIdfService.calculateTFIDF(String.valueOf(blogDTO.getBlogCategory()), blogCategory != null ? blogCategory : Collections.singletonList(String.valueOf(blogDTO.getBlogCategory().ordinal())));
            tfIdfService.saveTFIDFScores(blogCategoryScore, "category", blog.getBlogId());
            return BlogDTO.builder()
                    .blogId(blog.getBlogId())
                    .blogTitle(blog.getBlogTitle())
                    .blogCategory(CategoryEnum.values()[blog.getBlogCategory()])
                    .tagLine(blog.getTagLine())
                    .blogContent(blog.getBlogContent())
                    .thumbnail(blog.getThumbnail())
                    .createdOn(blog.getCreatedOn())
                    .updatedOn(blog.getUpdatedOn())
                    .build();
        }catch (Exception e){
            throw e;
        }
    }
    @Override
    public BlogDTO updateBlog(BlogDTO blogDTO, int blogId){
        try{
            Blogs blog = blogRepository.findById(blogId)
                            .orElseThrow(()-> new EntityNotFoundException("Blog not found with blog id "+blogId));
            blog.setBlogTitle(blogDTO.getBlogTitle());
            blog.setBlogCategory(blogDTO.getBlogCategory().ordinal());
            blog.setTagLine(blogDTO.getTagLine());
            blog.setBlogContent(blogDTO.getBlogContent());
            blog.setThumbnail(blogDTO.getThumbnail());
            blog.setUpdatedOn(LocalDateTime.now());
            blog.setUser(blog.getUser());
            blogRepository.save(blog);
            return BlogDTO.builder()
                    .blogId(blog.getBlogId())
                    .blogTitle(blog.getBlogTitle())
                    .blogCategory(CategoryEnum.values()[blog.getBlogCategory()])
                    .tagLine(blog.getTagLine())
                    .blogContent(blog.getBlogContent())
                    .thumbnail(blog.getThumbnail())
                    .createdOn(blog.getCreatedOn())
                    .updatedOn(blog.getUpdatedOn())
                    .build();
        }catch (Exception e){
            throw e;
        }
    }
    @Override
    public List<BlogDTO> getAllBlogs(){
        try {
            List<Blogs> blogs = blogRepository.findAll();
            return blogs.stream()
                    .map(blog -> BlogDTO.builder()
                            .blogId(blog.getBlogId())
                            .blogTitle(blog.getBlogTitle())
                            .blogCategory(CategoryEnum.values()[blog.getBlogCategory()])
                            .tagLine(blog.getTagLine())
                            .blogContent(blog.getBlogContent())
                            .thumbnail(blog.getThumbnail())
                            .createdOn(blog.getCreatedOn())
                            .updatedOn(blog.getUpdatedOn())
                            .build())
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw e;
        }
    }
    @Override
    public BlogDTO getBlogById(int blogId){
        try{
            Blogs blog = blogRepository.findById(blogId)
                    .orElseThrow(() -> new EntityNotFoundException("Blog not found with blog id "+blogId));
            return BlogDTO.builder()
                            .blogId(blog.getBlogId())
                            .blogTitle(blog.getBlogTitle())
                            .blogCategory(CategoryEnum.values()[blog.getBlogCategory()])
                            .tagLine(blog.getTagLine())
                            .blogContent(blog.getBlogContent())
                            .thumbnail(blog.getThumbnail())
                            .createdOn(blog.getCreatedOn())
                            .updatedOn(blog.getUpdatedOn())
                            .build();
        }catch (Exception e){
            throw e;
        }
    }
}
