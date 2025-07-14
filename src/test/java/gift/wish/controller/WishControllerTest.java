package gift.wish.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.member.dto.CreateWishRequest;
import gift.member.dto.CreateWishResponse;
import gift.member.service.MemberService;
import gift.wish.service.WishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = WishController.class)
public class WishControllerTest {

    @Autowired
    private MockMvc mockMvc; // 임시로 만든 fake WAS를 띄우게 된다.

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean // MemberService가 없는 빈 자리를 가짜 객체로 채워줄 수 있다.
    private MemberService memberService;

    @MockitoBean
    private WishService wishService;

    /*@Test
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
    }*/

    @Test
    void test2() throws Exception {
        // given
        var token = "valid_token";
        // 방법 1
        var content = "{\"productId\":1, \"quantity\":1}";

        given(wishService.create(any(), any())).willReturn(new CreateWishResponse(1L, 1L, 1L));

        // when
        var actual = createWish(token, content);

        // then
        assertThat(actual.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actual.getHeader(HttpHeaders.LOCATION)).isNotBlank();
        var response = objectMapper.readValue(actual.getContentAsString(), CreateWishResponse.class);
        assertThat(response.productId()).isEqualTo(1L);
        assertThat(response.memberId()).isEqualTo(1L);
    }

    @Test
    void test3() throws Exception {
        // given
        var token = "token";
        // 방법 2
        var request = new CreateWishRequest(1L, 1);
        var content = objectMapper.writeValueAsString(request);

        given(wishService.create(any(), any())).willReturn(new CreateWishResponse(1L, 1L, 1L));

        // when
        var actual = createWish(token, content);

        // then
        assertThat(actual.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actual.getHeader(HttpHeaders.LOCATION)).isNotBlank();
        var response = objectMapper.readValue(actual.getContentAsString(), CreateWishResponse.class);
        assertThat(response.productId()).isEqualTo(1L);
        assertThat(response.memberId()).isEqualTo(1L);
    }

    private MockHttpServletResponse createWish(String token, String content) throws Exception {
        return mockMvc.perform(
                        post("/api/wishes")
                                .contentType(MediaType.APPLICATION_JSON) // RequestBody의 type이 어떤 형식으로 되어있는가
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .content(content)
                )
                .andDo(print())
                .andReturn()
                .getResponse()
                ;
    }
}
