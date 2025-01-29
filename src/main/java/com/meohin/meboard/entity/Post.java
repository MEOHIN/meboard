package com.meohin.meboard.entity;

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

    private String created_at;

    private String updated_at;

    private String deleted_at;

    private String category;

    private String tag;

    private String view_count;

    private String like_count;

    private String reply_count;

    private String share_count;

    private String bookmark_count;

    private String rating;

    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany(mappedBy = "post_id")
    private List<Reply> replies;
}
