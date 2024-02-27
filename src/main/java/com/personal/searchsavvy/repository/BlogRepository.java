package com.personal.searchsavvy.repository;
import com.personal.searchsavvy.entity.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BlogRepository extends JpaRepository<Blogs, Integer> {
}
