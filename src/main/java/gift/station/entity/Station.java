package gift.station.entity;

import gift.line.entity.Line;
import jakarta.persistence.*;

@Entity // (1) 현재 클래스를 테이블과 매핑
@Table(name = "station") // (2) 테이블 이름과 매핑, name 생략 시 객체(클래스) 이름으로 자동 생성
public class Station {
    @Id // (3) primary key에 해당하는 필드에 해당
    @GeneratedValue(strategy = GenerationType.IDENTITY) // (4) primary key 생성 전략
    private Long id;

    @Column(name = "name", nullable = false) // (5) 생략 시 필드에 해당하는 이름으로 자동 생성, 'nullable = false'는 NOT NULL을 의미
    private String name;

    // (1) 다대일(N:1)
    // optional = false: line_id가 null이 될 수 없음. (LineRepositoryTest.test8() 실패, station을 영속화하기 전에 line 할당이 필요하기 때문)
    @ManyToOne// (optional = false) -> 아래 JoinColumn(nullable = false)와 같은 의미
    @JoinColumn(name = "line_id") // (2) join하는 칼럼명 지정
    private Line line; // (3) 영속성 entity와 매핑

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

    public Line getLine() {
        return line;
    }

    public void setLine(final Line line) {
        this.line = line;
        if (line != null) {
            line.getStations().add(this);
        }
    }
}
