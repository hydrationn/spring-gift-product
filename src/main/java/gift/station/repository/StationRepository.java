package gift.station.repository;

import gift.line.entity.Line;
import gift.station.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StationRepository extends JpaRepository<Station, Long> {
    Optional<Station> findByName(String name);

    List<Station> findByLine(Line line);
}
