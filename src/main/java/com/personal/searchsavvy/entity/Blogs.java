package com.personal.searchsavvy.entity;
import com.personal.searchsavvy.enums.CategoryEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Entity
@Table(name = "blogs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private int blogId;
    @Column(name = "blog_title", nullable = false, length = 1000)
    private String blogTitle;
    @Column(name = "blog_category", nullable = false)
    private Integer blogCategory;
    @Column(name = "created_on", columnDefinition = "TIMESTAMP DEFAULT '2024-01-22 00:00:01'")
    @CreationTimestamp
    private LocalDateTime createdOn;
    @Column(name = "updated_on", columnDefinition = "TIMESTAMP DEFAULT '2024-01-22 00:00:01'")
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    @Column(name = "tag_line", nullable = false,length = 1000)
    private String tagLine;
    @Column(name = "blog_content", nullable = false,length = 4000)
    private String blogContent;
    @Column(name = "thumbnail")
    private String thumbnail;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;
}
