package com.personal.searchsavvy.repository;
import com.personal.searchsavvy.entity.TfIdfScores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface TfIdfRepository extends JpaRepository<TfIdfScores, Integer> {
    @Query(value = "SELECT term FROM tfidf_scores where term LIKE LOWER(CONCAT('%', :term, '%'))", nativeQuery = true)
    List<String> getMatchingStrings(@Param("term") String term);
    @Query(value = "SELECT blog_id, sum(score) FROM tfidf_scores where term LIKE LOWER(CONCAT('%', :term, '%')) group by blog_id", nativeQuery = true)
    List<Object[]> getMatchingStringsByTrigram(@Param("term") String term);
    @Query(value = "SELECT * FROM blogs WHERE blog_id IN :ids", nativeQuery = true)
    List<Object[]> blogsByIds(@Param("ids") List<Integer> ids);
}
