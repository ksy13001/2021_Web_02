package com.web02.service;

import com.web02.domain.comments.Comments;
import com.web02.domain.posts.Posts;
import com.web02.domain.posts.PostsRepository;
import com.web02.web.dto.PostsResponseDto;
import com.web02.web.dto.PostsUpdateRequestDto;
import com.web02.web.dto.PostsListDto;
import com.web02.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.PrimitiveCharacterArrayNClobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor    //생성자 자동생성
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private static final int PAGE_COUNT=5;  // 페이지 개수
    private static final int PAGE_MAX_POSTS=5;  //한페이지 안의 게시글 개수

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional  //delete 는 JPA에서 지원함
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListDto::new) //= map(posts-> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Posts> search(String keyword) {
        List<Posts> postsList = postsRepository.findByTitleContaining(keyword);
        return postsList;
    }

    @Transactional
    public int updateView(Long id) {
        return postsRepository.updateViews(id);
    }

    @Transactional
    public Page<Posts> getPostList(Pageable pageable){
        return postsRepository.findAll(pageable);
    }
}
