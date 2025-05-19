package ru.rejchev.pstu.enrollstat.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.rejchev.pstu.enrollstat.domain.base.IEnroll;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollSource;
import ru.rejchev.pstu.enrollstat.domain.dtos.EnrollDto;
import ru.rejchev.pstu.enrollstat.domain.models.Enroll;
import ru.rejchev.pstu.enrollstat.domain.models.EnrollSource;
import ru.rejchev.pstu.enrollstat.repos.base.IEnrollRepository;
import ru.rejchev.pstu.enrollstat.services.base.IEnrollService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnrollService implements IEnrollService {

    IEnrollRepository repos;

    @Override
    public Optional<EnrollDto> getBy(String id) {
        return getRepos().findById(id).map(EnrollDto::create);
    }

    @Override
    public Optional<EnrollDto> getByEid(String eid) {
        return getRepos().findByEid(eid).map(EnrollDto::create);
    }

    @Override
    public Collection<EnrollDto> getAll() {
        return getRepos().findAll().stream().map(EnrollDto::create).collect(Collectors.toList());
    }

    @Override
    public Collection<EnrollDto> getAllByOrder(String orderId) {
        return getRepos().findAllByOrder(orderId).stream().map(EnrollDto::create).collect(Collectors.toList());
    }

    @Override
    public EnrollDto create(IEnroll value) {
        return EnrollDto.create(getRepos().save(Enroll.create(value)));
    }

    @Override
    public EnrollDto update(final String id, final IEnroll value) {
        return create(Enroll.create(value).setId(id));
    }

    @Override
    public void delete(String id) {
        getRepos().deleteById(id);
    }

    @Override
    public Collection<EnrollDto> getAllByYear(int year) {
        return getRepos().findAllBySource_Year(year).stream().map(EnrollDto::create).collect(Collectors.toList());
    }

    @Override
    public Collection<EnrollDto> getAllBySource(IEnrollSource source) {
        return getRepos().findAllBySource(EnrollSource.create(source)).stream().map(EnrollDto::create).collect(Collectors.toList());
    }

    @Override
    public EnrollDto getOrCreate(IEnroll value) {
        return getByEid(value.getEid()).orElseGet(() -> create(value));
    }
}
