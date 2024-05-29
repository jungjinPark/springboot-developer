package com.example.springbootdeveloper.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // 테스트용 애플리케이션 컨텍스트 생성
@AutoConfigureMockMvc // MockMvc 인스턴스를 주입받을 수 있음
class TestControllerTest {

    @Autowired
    protected MockMvc mockMvc; // MockMvc 인스턴스 주입

    @Autowired
    private WebApplicationContext context; // WebApplicationContext 인스턴스 주입

    @Autowired
    private MemberRepository memberRepository; // MemberRepository 인스턴스 주입

    @BeforeEach
    public void mockMvcSetUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build(); // MockMvc 인스턴스 생성
    }

    @AfterEach
    public void cleanUp() {
        memberRepository.deleteAll(); // 모든 데이터 삭제
    }

    @DisplayName("getAllMembers 테스트")
    @Test
    public void getAllMembers() throws Exception {
        // given
        final String url = "/test"; // URL
        Member saveMember = memberRepository.save(new Member(1L, "홍길동")); // 데이터 저장

        // when
        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)); // 요청

        // then
        result.andExpect(status().isOk()) // HTTP 상태 코드 200
                .andExpect(jsonPath("$[0].id").value(saveMember.getId())) // id 값 비교
                .andExpect(jsonPath("$[0].name").value(saveMember.getName())); // name 값 비교
    }

}