package gift.member.controller;

import gift.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcClient jdbcClient;

    private RestClient client = RestClient.builder().build();

    @BeforeEach // 모든 테스트가 진행되기 전에 먼저 실행
    void setUp() {
        jdbcClient.sql("DELETE FROM member").update();
    }

    @Test
    void 존재하는_아이디로_조회하면_200이_반환된다() { // E2E test
        var insertUrl = "http://localhost:" + port + "/api/members";
        client.post()
                .uri(insertUrl)
                .retrieve()
                .toBodilessEntity();

        var getUrl = "http://localhost:" + port + "/api/members/1";
        var response = client.get()
                .uri(getUrl)
                .retrieve()
                .toEntity(Member.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("park");
    }

    @Test
    void 존재하지_않는_아이디로_조회하면_404가_반환된다() {
        var url = "http://localhost:" + port + "/api/members/2";
        assertThatExceptionOfType(HttpClientErrorException.NotFound.class)
                .isThrownBy(
                        () ->
                                client.get()
                                        .uri(url)
                                        .retrieve()
                                        .toEntity(Void.class)
                );
    }
}
