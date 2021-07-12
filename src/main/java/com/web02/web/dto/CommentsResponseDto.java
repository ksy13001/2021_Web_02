package com.web02.web.dto;

import com.web02.domain.comments.Comments;
import com.web02.domain.posts.Posts;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentsResponseDto {

    private Long id;
    private String content;
    private String author;
    private Posts posts_id;
    private LocalDateTime modifiedDate;

    public CommentsResponseDto(Comments entity){
        this.id= entity.getId();
        this.content=entity.getContent();
        this.author=entity.getAuthor();
        this.posts_id=entity.getPost_id();
        this.modifiedDate=entity.getModifiedDate();
    }
}
