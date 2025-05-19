package ru.rejchev.pstu.enrollstat.repos.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rejchev.pstu.enrollstat.domain.models.EnrollSource;
import ru.rejchev.pstu.enrollstat.domain.models.EnrollMeta;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface IEnrollMetaRepository extends JpaRepository<EnrollMeta, String> {

    @Transactional(readOnly = true)
    Collection<EnrollMeta> findAllByCompetitionGroup(String competitionGroup);

    @Transactional(readOnly = true)
    Collection<EnrollMeta> findAllBySource_Year(int sourceYear);

    @Transactional(readOnly = true)
    Optional<EnrollMeta> findBySource(EnrollSource source);
}
