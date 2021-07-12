package com.web02.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web02.domain.comments.Comments;
import com.web02.domain.comments.CommentsRepository;
import com.web02.web.dto.CommentsRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentsController {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }
    @After
    public void tearDown() throws Exception {
        commentsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER")//USER 권한부여
    public void 댓글등록() throws Exception {
        //given


        String content="content1234";
        String author="author134";

        CommentsRequestDto commentsDto = CommentsRequestDto.builder()
                .content(content)
                .author(author)
                .build();

        Long postsId=commentsDto.toEntity().getId();

        String url = "http://localhost:" + port + "/api/v1/comments/"+postsId;

        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(commentsDto)))
                .andExpect(status().isOk());

        //then
        List<Comments> all = commentsRepository.findAll();
        assertThat(all.get(0).getAuthor()).isEqualTo(author);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

}
