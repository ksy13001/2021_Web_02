package com.web02.service;

import com.web02.domain.comments.Comments;
import com.web02.domain.comments.CommentsRepository;
import com.web02.domain.posts.PostsRepository;
import com.web02.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentsService {
    private  final PostsRepository  postsRepository;
    private final CommentsRepository commentsRepository;

    @Transactional
    public Long saveComments(CommentsRequestDto commentsDto){
        return commentsRepository.save(commentsDto.toEntity()).getId();
    }

    @Transactional
    public void delete (Long id) {
        Comments comments=commentsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
        commentsRepository.delete(comments);
    }


    @Transactional(readOnly = true) //readOnly >> 트랜잭션 범위를 유지하며 조회 속도 개선// 등록, 수정, 삭제 기능 없는 메소드에사용
    public List<CommentsListResponseDto> findAllDesc() {
        return commentsRepository.findAllDesc().stream()
                .map(CommentsListResponseDto::new) //= map(posts-> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }
}
