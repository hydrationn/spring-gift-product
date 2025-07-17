package gift.station.repository;

import gift.station.entity.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StationRepositoryTest {
    @Autowired
    private StationRepository stations;

    @Transactional
    @Test
    void save() {
        Station station = new Station("잠실역");
        var actual = stations.save(station);
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo("잠실역");
    }

    @Test
    void findByName() {
        var actual1 = stations.findByName("잠실역");
        assertThat(actual1).isEmpty();
        stations.save(new Station("잠실역"));
        var actual2 = stations.findByName("잠실역");
        assertThat(actual2).isNotEmpty();
    }

    @Test
    void identity() { // 동일성 보장 test
        var station1 = stations.save(new Station("잠실역"));
        var station2 = stations.findByName("잠실역").get();
        assertThat(station1).isSameAs(station2);
    }

    @Test
    void lazy() { // database에 저장(insert)하지 않고, 먼저 쓰기 지연 저장소에 저장
        var station = stations.save(new Station("잠실역"));
        var actual = stations.save(station);
        assertThat(actual).isNotNull();
//        stations.flush(); // 강제 호출하기에 좋은 방법은 아님.
//        stations.findByName("잠실역").get(); // 자연스럽게 flush()
    }

    @Test
    void update() {
        var station1 = stations.save(new Station("잠실역"));
        station1.changeName("몽촌토성역"); // Dirty check
//        station1.changeName("잠실역"); // 만약에 이렇게 되면 update가 진행되지 않음. 처음 스냅샷과 상태가 동일하기 때문
        var station2 = stations.findByName("몽촌토성역"); // flush()
        assertThat(station2).isNotEmpty();
    }
}
