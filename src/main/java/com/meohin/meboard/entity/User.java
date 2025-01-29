package com.meohin.meboard.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String created_at;

    private String updated_at;

    private String deleted_at;

    private String like_count;

    private String bookmark_count;

    @OneToMany(mappedBy = "author")
    private List<Post> posts;
}
