package com.insoftar.apiusers.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.insoftar.apiusers.persistence.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByIdCard(String idCard);

    Optional<User> findByEmail(String email);
}
