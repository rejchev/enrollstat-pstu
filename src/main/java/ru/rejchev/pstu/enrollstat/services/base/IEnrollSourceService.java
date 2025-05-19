package ru.rejchev.pstu.enrollstat.services.base;

import ru.rejchev.pstu.enrollstat.domain.base.IEnrollSource;
import ru.rejchev.pstu.enrollstat.domain.dtos.EnrollSourceDto;

import java.util.Collection;
import java.util.Optional;

public interface IEnrollSourceService {

    Optional<EnrollSourceDto> getById(final String id);
    Optional<EnrollSourceDto> getByPath(final String path);

    Collection<EnrollSourceDto> getAll();
    Collection<EnrollSourceDto> getAllByYear(final int year);

    EnrollSourceDto update(final String id, final IEnrollSource value);
    EnrollSourceDto create(final IEnrollSource value);

    void delete(final String id);
    void deleteAll();
}
