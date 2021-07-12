package com.web02.web;


import com.web02.service.CommentsService;
import com.web02.service.PostsService;
import com.web02.web.dto.CommentsRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentsController {

    private final PostsService postService;
    private final CommentsService commentsService;

    @PostMapping("/api/v1/comments/{posts_id}")
    public Long save(@RequestBody CommentsRequestDto commentsDto){
         return commentsService.saveComments(commentsDto);
    }

    @DeleteMapping("/api/v1/comments/{id}")
    public Long delete(@PathVariable Long id) {
        commentsService.delete(id);
        return id;
    }
}

