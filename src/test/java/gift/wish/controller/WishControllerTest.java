package gift.wish.controller;

import gift.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = WishController.class)
public class WishControllerTest {

    @Autowired
    private MockMvc mockMvc; // 임시로 만든 fake WAS를 띄우게 된다.

    @MockitoBean // MemberService가 없는 빈 자리를 가짜 객체로 채워줄 수 있다.
    private MemberService memberService;

    @Test
    void test1() throws Exception {
        // given
        // stubbing: given절에 대한 행위를 재정의하는 것 (아래 두 줄이 같은 의미)
        given(memberService.member()).willReturn("hydrationn");
//        when(memberService.member()).thenReturn("hydrationn");

        // when
        var actual = mockMvc
                .perform(get("/api/wishes"))
                .andDo(print())
                .andReturn()
                .getResponse()
                ;

        // then
        assertThat(actual.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actual.getContentAsString()).isEqualTo("hydrationn");
    }
}
