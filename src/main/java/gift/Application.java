package gift;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import member.entity.Member;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
        var connection = getConnection();
        createMemberTable(connection);
        var member = new Member(1L, "박수화", 20, "test@gmail.com");
        insertMember(connection, member);
    }

    public static void createMemberTable(Connection connection) throws SQLException {
        var sql = """
                create table member (
                    id bigint,
                    name varchar(100),
                    age int,
                    email varchar(255),
                    primary key(id)
                )
                """;
        var statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
    }

    public static void insertMember(Connection connection, Member member) throws Exception {
        var sql = "insert into member(id, name, age, email) values (?, ?, ?, ?);";
        var statement = connection.prepareStatement(sql);
        statement.setLong(1, member.id());
        statement.setString(2, member.name());
        statement.setInt(3, member.age());
        statement.setString(4, member.email());
        statement.execute();
        statement.close();
    }

    public static Connection getConnection() throws Exception {
        var url = "jdbc:h2:mem:test";
        var user = "sa";
        var password = "";
        return DriverManager.getConnection(url, user, password);
    }
}
