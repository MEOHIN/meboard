package com.meohin.meboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meohin.meboard.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
