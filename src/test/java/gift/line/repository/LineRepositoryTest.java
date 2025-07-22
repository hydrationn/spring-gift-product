package gift.line.repository;

import gift.line.entity.Line;
import gift.station.entity.Station;
import gift.station.repository.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) // 테스트 코드에 생성자 주입 가능
@DataJpaTest
class LineRepositoryTest {
    @Autowired
    private final LineRepository lines;

    @Autowired
    private final StationRepository stations;

    @Autowired
    private TestEntityManager entityManager;

    LineRepositoryTest(LineRepository lines, StationRepository stations) {
        this.lines = lines;
        this.stations = stations;
    }

    @Test
    void test1() {
        var station = new Station("잠실역");
        station.setLine(lines.save(new Line("2호선"))); // 연관관계와 매핑하는 entity끼리는 항상 영속 상태로 만들어야 한다..!
        var actual = stations.save(station);
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getLine()).isNotNull();
    }

    @Test
    void test2() {
        var station = new Station("잠실역");
//        station.setLine(new Line("2호선")); // (ERROR) save the transient instance before flushing -> By 쓰기 지연 때문
        station.setLine(lines.save(new Line("2호선")));
        stations.save(station);
        var actual = stations.findByName("잠실역").get();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getLine()).isNotNull();
    }

    @Sql(scripts = "/data.sql") // src/test/resources/data.sql 실행
    @Test
    void test3() {
        var actual = stations.findByName("교대역").get();
        assertThat(actual.getLine()).isNotNull();
        assertThat(actual.getLine().getName()).isEqualTo("3호선");
    }

    @Sql(scripts = "/data.sql")
    @Test
    void test4() {
        var station = stations.findByName("교대역").get();
        station.setLine(lines.save(new Line("2호선")));
//        stations.flush();
        flushAndClear();
        assertThat(station.getLine().getName()).isEqualTo("2호선");
    }

    @Sql(scripts = "/data.sql")
    @Test
    void test6() {
        var station = stations.findByName("교대역").get();
        station.setLine(null); // 연관관계만 제거, Line을 삭제하지는 않음.
        flushAndClear();
    }

    @Sql(scripts = "/data.sql")
    @Test
    void test7() { // name 기반으로 line(actual)을 획득하고, 이를 기반으로 station들을 불러온다.
        var actual = lines.findByName("3호선").get();
        assertThat(actual.getStations()).hasSize(1);
    }

    @Test
    void test8() {
        var line = lines.save(new Line("2호선"));
        line.addStation(stations.save(new Station("잠실역")));
        flushAndClear();
    }

    @Test
    void test9() { // test8에 대한 정석 코드
        var line = new Line("2호선");
        line.addStation(new Station("잠실역"));
        lines.save(line);
    }

    @Test
    void test10() {
        var line = lines.save(new Line("2호선"));
        var stationList = stations.findByLine(line);
        if(stationList.size() < 20)
            line.addStation(new Station("잠실역"));
    }

    private void flushAndClear() {
        entityManager.flush();
        entityManager.clear(); // 영속성 컨텍스트에 존재하던 모든 entity를 날려버림
    }
}
