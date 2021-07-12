package com.web02.web.dto;

import com.web02.domain.comments.Comments;
import com.web02.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CommentsRequestDto {

    private String content;
    private String author;
    private Posts posts_id;

    @Builder
    public CommentsRequestDto(String content, String author, Posts posts_id){
        this.content=content;
        this.author=author;
        this.posts_id=posts_id;
    }

    public Comments toEntity(){
        return Comments.builder()
                .content(content)
                .author(author)
                .build();
    }
}
