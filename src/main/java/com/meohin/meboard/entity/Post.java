package com.meohin.meboard.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private LocalDateTime deleted_at;

    private String category;

    private String tag;

    private Long view_count;

    private Long like_count;

    private Long reply_count;

    private Long share_count;

    private Long bookmark_count;

    private int rating;

    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany(mappedBy = "post_id")
    private List<Reply> replies;
}
