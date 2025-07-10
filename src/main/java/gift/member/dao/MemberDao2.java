package gift.member.dao;

import gift.member.entity.Member;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository // 데이터 관련 모든 예외는 DataAccessException으로 추상화해준다.
public class MemberDao2 {
    private final JdbcClient jdbcClient;

    public MemberDao2(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void insertMember(Member member) {
        var sql = "insert into member(id, name, age, email) values (:id, :name, :age, :email);";
        // ':' 사용 시 순서를 굳이 안 맞춰도 될 뿐만 아니라, 잘못 끼워넣는 실수를 방지할 수 있다.
        jdbcClient.sql(sql)
                .param("id", member.id())
                .param("name", member.name())
                .param("age", member.age())
                .param("email", member.email())
                .update();
    }

    public Optional<Member> selectMember(Long id) {
        var sql = "select id, name, age, email from member where id = :id";
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(getMemberRowMapper())
                .optional();
    }

    private static RowMapper<Member> getMemberRowMapper() {
        return (rs, rowNum) -> {
            var id = rs.getLong("id");
            var name = rs.getString("name");
            var age = rs.getInt("age");
            var email = rs.getString("email");
            return new Member(id, name, age, email);
        };

    }
}
