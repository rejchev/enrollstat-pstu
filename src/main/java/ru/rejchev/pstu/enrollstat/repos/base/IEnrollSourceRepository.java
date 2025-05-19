package ru.rejchev.pstu.enrollstat.repos.base;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rejchev.pstu.enrollstat.domain.models.EnrollSource;

import java.util.Collection;
import java.util.Optional;

public interface IEnrollSourceRepository extends JpaRepository<EnrollSource, String> {

    Collection<EnrollSource> findByYear(int year);

    Optional<EnrollSource> findByPath(String path);
}
