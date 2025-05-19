package ru.rejchev.pstu.enrollstat.repos.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rejchev.pstu.enrollstat.domain.models.Enroll;
import ru.rejchev.pstu.enrollstat.domain.models.EnrollSource;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface IEnrollRepository extends JpaRepository<Enroll, String> {
    @Transactional(readOnly = true)
    Collection<Enroll> findAllByOrder(String order);

    @Transactional(readOnly = true)
    Collection<Enroll> findAllBySource(EnrollSource source);

    @Transactional(readOnly = true)
    Collection<Enroll> findAllBySource_Year(int sourceYear);

    @Transactional(readOnly = true)
    Optional<Enroll> findByEid(String eid);
}
