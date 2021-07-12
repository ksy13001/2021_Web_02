package com.web02.web.dto;


import com.web02.domain.comments.Comments;
import com.web02.domain.posts.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentsListResponseDto {
    private Long id;
    private String author;
    private String content;
    private Posts posts_id;
    private LocalDateTime modifiedDate;

    public CommentsListResponseDto(Comments entity){
        this.id=entity.getId();
        this.author=entity.getAuthor();
        this.content=entity.getContent();
        this.posts_id=entity.getPost_id();
        this.modifiedDate=entity.getModifiedDate();
    }
}
