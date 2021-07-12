package com.web02.domain.comments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web02.domain.BaseTimeEntity;
import com.web02.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comments extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="COMMENT_ID")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private String author;

    @ManyToOne
    @JoinColumn(name = "POSTS_ID")
    private Posts post_id;


    @Builder
    public Comments(Long id, String content,String author,Posts post_id){
        this.id=id;
        this.content=content;
        this.author=author;
        this.post_id=post_id;
    }

}
