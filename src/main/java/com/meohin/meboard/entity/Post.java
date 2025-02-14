package com.meohin.meboard.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "제목을 입력하세요.")
    private String title;

    @NotEmpty(message = "내용을 입력하세요.")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private String category;

    private String tag;

    @Column(name = "view_count", columnDefinition = "integer default 0")
    private int viewCount;

    @Column(name = "like_count", columnDefinition = "integer default 0")
    private int likeCount;

    @Column(name = "reply_count", columnDefinition = "integer default 0")
    private int replyCount;

    @Column(name = "share_count", columnDefinition = "integer default 0")
    private int shareCount;

    private int rating;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private SiteUser author;

    @OneToMany(mappedBy = "post")
    private List<Reply> replies;
}
