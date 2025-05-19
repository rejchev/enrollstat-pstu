package ru.rejchev.pstu.enrollstat.services;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollSource;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollMeta;
import ru.rejchev.pstu.enrollstat.domain.dtos.EnrollMetaDto;
import ru.rejchev.pstu.enrollstat.domain.models.EnrollSource;
import ru.rejchev.pstu.enrollstat.domain.models.EnrollMeta;
import ru.rejchev.pstu.enrollstat.repos.base.IEnrollMetaRepository;
import ru.rejchev.pstu.enrollstat.services.base.IEnrollMetaService;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Getter(AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnrollMetaService implements IEnrollMetaService {

    private static final Logger logger = LoggerFactory.getLogger(EnrollMetaService.class);

    IEnrollMetaRepository repos;

    @Autowired
    public EnrollMetaService(IEnrollMetaRepository repos) {
        this.repos = repos;
    }

    @Override
    public Optional<EnrollMetaDto> getById(String id) {
        return getRepos().findById(id).map(EnrollMetaDto::create);
    }

    @Override
    public Optional<EnrollMetaDto> getBySource(IEnrollSource value) {
        return getRepos().findBySource(EnrollSource.create(value)).map(EnrollMetaDto::create);
    }

    @Override
    public Collection<EnrollMetaDto> getByGroup(String groupId) {
        return getRepos().findAllByCompetitionGroup(groupId).stream().map(EnrollMetaDto::create).collect(Collectors.toSet());
    }

    @Override
    public Collection<EnrollMetaDto> getByYear(int year) {
        return getRepos().findAllBySource_Year(year).stream()
                .map(EnrollMetaDto::create)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<EnrollMetaDto> getAll() {
        return getRepos().findAll().stream()
                .map(EnrollMetaDto::create)
                .collect(Collectors.toList());
    }

    @Override
    public EnrollMetaDto create(IEnrollMeta value) {
        try {
           return EnrollMetaDto.create(getRepos().save(EnrollMeta.create(value)));
        } catch (Exception e) {
            logger.error("create(): {}", e.getMessage());
        }

        return null;
    }

    @Override
    public EnrollMetaDto update(String id, IEnrollMeta value) {
        return create(EnrollMeta.create(value).setId(id));
    }

    @Override
    public void delete(String id) {
        getRepos().deleteById(id);
    }

    @Override
    public EnrollMetaDto getOrCreate(IEnrollMeta value) {
        return getBySource(value.getSource(IEnrollSource.class))
                .map(EnrollMetaDto::create)
                .orElseGet(() -> create(value));
//        return null;
    }

    public boolean isSame(final IEnrollMeta a, final IEnrollMeta b) {
        return Objects.equals(a.getSource(IEnrollSource.class).getPath(), b.getSource(IEnrollSource.class).getPath());
    }

}
