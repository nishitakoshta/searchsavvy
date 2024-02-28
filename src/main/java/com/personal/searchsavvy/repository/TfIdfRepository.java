package com.personal.searchsavvy.repository;
import com.personal.searchsavvy.entity.TfIdfScores;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TfIdfRepository extends JpaRepository<TfIdfScores, Integer> {
}
