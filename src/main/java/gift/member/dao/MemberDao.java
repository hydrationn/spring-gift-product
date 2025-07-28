package gift.member.dao;

import gift.member.entity.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
    private final JdbcTemplate jdbcTemplate; // template 안에서 connection이 관리되어 connection 필요 X

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertMember(Member member) {
        var sql = "insert into member(id, name, age, email) values (?, ?, ?, ?);";
        jdbcTemplate.update(sql, member.getId(), member.getName(), member.getAge(), member.getEmail()); // database에 변화 (값 수정)
    }
}
