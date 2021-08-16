package com.web02.service;

import com.web02.domain.posts.Posts;
import com.web02.domain.posts.PostsRepository;
import com.web02.web.dto.PostsResponseDto;
import com.web02.web.dto.PostsUpdateRequestDto;
import com.web02.web.dto.PostsListResponseDto;
import com.web02.web.dto.PostsSaveRequestDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor    //생성자 자동생성
@Service
public class PostsService {
    private final PostsRepository postsRepository;

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

    @Transactional(readOnly = true) //readOnly >> 트랜잭션 범위를 유지하며 조회 속도 개선// 등록, 수정, 삭제 기능 없는 메소드에사용
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) //= map(posts-> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostsListResponseDto> searchPosts(String keyword){
        List<Posts> posts = postsRepository.findByTitleContaining(keyword);
        List<PostsListResponseDto> postsListResponseDto =new ArrayList<>();

        if(posts.isEmpty()){    //검색결과 없음
            return postsListResponseDto;
        }
        for(Posts post: posts){ //객체 차례대로 삽입
            postsListResponseDto.add(this.toDto(post));
        }
        return postsListResponseDto;
    }

    private PostsListResponseDto toDto(Posts post){
        return PostsListResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getAuthor())
                .count(post.getCount())
                .build();
    }
}