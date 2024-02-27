package com.personal.searchsavvy.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
    @Column(name = "user_name", nullable = false)
    private String userName;
    @Column(name = "user_age", nullable = false)
    private short userAge;
    @Column(name = "user_mobile", unique = true, nullable = false)
    private String userMobile;
    @Column(name = "user_email", nullable = false)
    private String userEmail;
    @Column(name = "created_on", columnDefinition = "TIMESTAMP DEFAULT '2024-01-22 00:00:01'")
    @CreationTimestamp
    private LocalDateTime createdOn;
    @Column(name = "updated_on", columnDefinition = "TIMESTAMP DEFAULT '2024-01-22 00:00:01'")
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Blogs> blogs;
}
