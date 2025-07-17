package gift.station.entity;

import jakarta.persistence.*;

@Entity // (1) 현재 클래스를 테이블과 매핑
@Table(name = "station") // (2) 테이블 이름과 매핑, name 생략 시 객체(클래스) 이름으로 자동 생성
public class Station {
    @Id // (3) primary key에 해당하는 필드에 해당
    @GeneratedValue(strategy = GenerationType.IDENTITY) // (4) primary key 생성 전략
    private Long id;

    @Column(name = "name", nullable = false) // (5) 생략 시 필드에 해당하는 이름으로 자동 생성, 'nullable = false'는 NOT NULL을 의미
    private String name;

    protected Station() { // (6) 파라미터가 없는 생성자 필요 !
    }

    public Station(String name) {
        this(null, name);
    }

    public Station(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
