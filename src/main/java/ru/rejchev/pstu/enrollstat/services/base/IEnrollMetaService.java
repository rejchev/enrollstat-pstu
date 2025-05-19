package ru.rejchev.pstu.enrollstat.services.base;

import ru.rejchev.pstu.enrollstat.domain.base.IEnrollMeta;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollSource;
import ru.rejchev.pstu.enrollstat.domain.dtos.EnrollMetaDto;
import ru.rejchev.pstu.enrollstat.domain.models.EnrollMeta;

import java.util.Collection;
import java.util.Optional;

public interface IEnrollMetaService {
    Optional<EnrollMetaDto> getById(String id);
    Optional<EnrollMetaDto> getBySource(final IEnrollSource value);

    Collection<EnrollMetaDto> getByGroup(String groupId);
    Collection<EnrollMetaDto> getByYear(int year);
    Collection<EnrollMetaDto> getAll();

    EnrollMetaDto create(IEnrollMeta value);
    EnrollMetaDto update(final String id, IEnrollMeta value);

    void delete(String id);

    EnrollMetaDto getOrCreate(IEnrollMeta value);

    boolean isSame(final IEnrollMeta a, final IEnrollMeta b);
}
