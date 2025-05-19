package ru.rejchev.pstu.enrollstat.repos.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rejchev.pstu.enrollstat.domain.models.EnrollSource;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface IEnrollSourceRepository extends JpaRepository<EnrollSource, String> {

    @Transactional(readOnly = true)
    Collection<EnrollSource> findByYear(int year);

    @Transactional(readOnly = true)
    Optional<EnrollSource> findByPath(String path);
}
