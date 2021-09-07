package com.web02.domain.posts;

import com.web02.domain.BaseTimeEntity;
import com.web02.domain.comments.Comments;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
//setter x
@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="postId")
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @Column
    private int views;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comments> comments;

    @Column
    private int commentsCnt;

    @Builder
    public Posts(Long id,String title, String content, String author,int views,List<Comments> comments, int commentsCnt) {
        this.id=id;
        this.title  = title;
        this.content= content;
        this.author = author;
        this.views=views;
        this.comments=comments;
        this.commentsCnt=commentsCnt;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }



}