package com.personal.searchsavvy.repository;
import com.personal.searchsavvy.entity.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface BlogRepository extends JpaRepository<Blogs, Integer> {
    @Query(value = "SELECT * FROM tfidf_scores where term = ? and field_type = ? and blog_id = ?", nativeQuery = true)
    List<Object[]> checkTermExistence(String term, String fieldType, Integer blogId);
}
