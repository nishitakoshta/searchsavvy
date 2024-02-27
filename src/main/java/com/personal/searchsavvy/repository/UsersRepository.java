package com.personal.searchsavvy.repository;
import com.personal.searchsavvy.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UsersRepository extends JpaRepository<Users, Integer> {
}
