package com.personal.searchsavvy.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "tfidf_scores")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TfIdfScores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "term")
    private String term;
    @Column(name = "score")
    private Double score;
    @Column(name = "field_type")
    private String fieldType;
    @Column(name = "blog_id")
    private Integer blogId;
}
