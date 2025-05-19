package ru.rejchev.pstu.enrollstat.services.base;

import ru.rejchev.pstu.enrollstat.domain.base.IEnroll;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollSource;
import ru.rejchev.pstu.enrollstat.domain.dtos.EnrollDto;
import ru.rejchev.pstu.enrollstat.domain.models.Enroll;

import java.util.Collection;
import java.util.Optional;

public interface IEnrollService {

    Optional<EnrollDto> getBy(String id);

    Optional<EnrollDto> getByEid(String eid);

    Collection<EnrollDto> getAll();

    Collection<EnrollDto> getAllByOrder(String orderId);
    Collection<EnrollDto> getAllByYear(int year);
    Collection<EnrollDto> getAllBySource(IEnrollSource source);

    EnrollDto create(IEnroll value);
    EnrollDto update(final String id, final IEnroll value);

    void delete(String id);


    EnrollDto getOrCreate(IEnroll value);
}
