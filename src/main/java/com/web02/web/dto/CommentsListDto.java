package com.web02.web.dto;


import com.web02.domain.comments.Comments;
import com.web02.domain.posts.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentsListDto {
    private Long commentId;
    private String author;
    private String content;
    private Posts post;
    private LocalDateTime modifiedDate;
    private Long count;

    public CommentsListDto(Comments entity){
        this.commentId=entity.getCommentId();
        this.author=entity.getAuthor();
        this.content=entity.getContent();
        this.post=entity.getPost();
        this.modifiedDate=entity.getModifiedDate();
    }


}
