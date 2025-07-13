package gift;

import gift.member.service.MemberService;
import gift.wish.controller.WishController;
import org.springframework.context.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

//@Sql("")
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @MockitoBean
    private MemberService memberService;

    @DirtiesContext // 재활용을 피할 수 있게 해준다. (서로 다른 주소값 부여) -> sql 리셋 등을 따로 진행할 필요가 없다..!
    @Test
    void test1() {
        System.out.println("Test11");
        System.out.println(this);
        System.out.println(applicationContext);
    }

    @DirtiesContext
    @Test
    void test2() {
        System.out.println("Test22");
        System.out.println(this);
        System.out.println(applicationContext);
    }
}
