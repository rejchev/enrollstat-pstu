package ru.rejchev.pstu.enrollstat.repos.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rejchev.pstu.enrollstat.domain.dtos.EnrollDto;
import ru.rejchev.pstu.enrollstat.domain.models.Enroll;
import ru.rejchev.pstu.enrollstat.domain.models.EnrollSource;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface IEnrollRepository extends JpaRepository<Enroll, String> {
    Collection<Enroll> findAllByOrder(String order);

    Collection<Enroll> findAllBySource(EnrollSource source);

    Collection<Enroll> findAllBySource_Year(int sourceYear);

    Optional<Enroll> findByEid(String eid);
}
