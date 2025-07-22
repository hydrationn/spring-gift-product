package gift.line.entity;

import gift.station.entity.Station;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "line")
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // 일대다(1:N)
    // mappedBy = 관계에 대한 필드가 어디(Line)에 저장되어 있는지 알려줌
    // FetchType.LAZY: stations를 호출하지 않으면 조회 X / FetchType.EAGER: stations를 호출하지 않아도 조회 O
    @OneToMany(
            mappedBy = "line",
            cascade = CascadeType.PERSIST, // 저장할 때만 함께 영속화
            fetch = FetchType.LAZY
    )
    private List<Station> stations = new ArrayList<>();

    protected Line () {}

    public Line(String name) {
        this.name = name;
    }

    public Line(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addStation(Station station) {
        // 일대다(N:1)로 설정 시 장점 중 하나, 로직을 내부에서 관리할 수 있음.
        // (+ 현재는 add가 두 번 수행되어 방어 로직을 작성할 필요가 있음. )
        if(stations.size() < 20) {
            stations.add(station); // 객체 그래프 탐색
            station.setLine(this); // 연관관계 매핑
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Station> getStations() {
        return stations;
    }
}

