package com.meohin.meboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meohin.meboard.entity.SiteUser;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
}
