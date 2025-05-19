package ru.rejchev.pstu.enrollstat.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollSource;
import ru.rejchev.pstu.enrollstat.domain.dtos.EnrollSourceDto;
import ru.rejchev.pstu.enrollstat.domain.models.EnrollSource;
import ru.rejchev.pstu.enrollstat.repos.base.IEnrollSourceRepository;
import ru.rejchev.pstu.enrollstat.services.base.IEnrollSourceService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Getter(AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnrollSourceService implements IEnrollSourceService {

    IEnrollSourceRepository repository;

    @Autowired
    public EnrollSourceService(IEnrollSourceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<EnrollSourceDto> getById(final String id) {
        return getRepository().findById(id).map(EnrollSourceDto::create);
    }

    @Override
    public Optional<EnrollSourceDto> getByPath(final String val) {
        return getRepository().findByPath(val).map(EnrollSourceDto::create);
    }

    @Override
    public Collection<EnrollSourceDto> getAll() {
        return getRepository().findAll().stream().map(EnrollSourceDto::create).collect(Collectors.toSet());
    }

    @Override
    public Collection<EnrollSourceDto> getAllByYear(int year) {
        return getRepository().findByYear(year).stream().map(EnrollSourceDto::create).collect(Collectors.toSet());
    }

    @Override
    public EnrollSourceDto update(String id, IEnrollSource value) {
        return create(EnrollSource.create(value).setId(id));
    }

    @Override
    public EnrollSourceDto create(IEnrollSource value) {
        return EnrollSourceDto.create(getRepository().save(EnrollSource.create(value)));
    }

    @Override
    public void delete(String id) {
        getRepository().deleteById(id);
    }

    @Override
    public void deleteAll() {
        getRepository().deleteAll();
    }
}
