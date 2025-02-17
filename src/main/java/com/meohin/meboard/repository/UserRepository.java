package com.meohin.meboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meohin.meboard.entity.SiteUser;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
    Optional<SiteUser> findByUsername(String username);
}
