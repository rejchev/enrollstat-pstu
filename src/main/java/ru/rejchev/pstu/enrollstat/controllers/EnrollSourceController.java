package ru.rejchev.pstu.enrollstat.controllers;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rejchev.pstu.enrollstat.domain.dtos.EnrollDto;
import ru.rejchev.pstu.enrollstat.domain.dtos.EnrollMetaDto;
import ru.rejchev.pstu.enrollstat.domain.dtos.EnrollSourceDto;
import ru.rejchev.pstu.enrollstat.domain.models.EnrollSource;
import ru.rejchev.pstu.enrollstat.services.base.IEnrollMetaService;
import ru.rejchev.pstu.enrollstat.services.base.IEnrollService;
import ru.rejchev.pstu.enrollstat.services.base.IEnrollSourceService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@Getter(AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnrollSourceController {

    private final static Logger logger = LoggerFactory.getLogger(EnrollSourceController.class);

    IEnrollSourceService enrollSourceService;

    IEnrollMetaService enrollMetaService;

    IEnrollService enrollService;

    @Autowired
    public EnrollSourceController(IEnrollSourceService enrollSourceService,
                                  IEnrollMetaService enrollMetaService,
                                  IEnrollService enrollService) {
        this.enrollSourceService = enrollSourceService;
        this.enrollMetaService = enrollMetaService;
        this.enrollService = enrollService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/enrollsources", produces = "application/json")
    public ResponseEntity<Collection<EnrollSourceDto>> getAll(final Optional<Integer> year) {
        logger.info("EnrollController::getAll()");

        return year.map(integer -> ResponseEntity.ofNullable(getEnrollSourceService().getAllByYear(integer)))
                .orElseGet(() -> ResponseEntity.ofNullable(getEnrollSourceService().getAll()));

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/enrollsources/{id}", produces = "application/json")
    public ResponseEntity<Optional<EnrollSourceDto>> getById(final @PathVariable String id) {
        return ResponseEntity.ofNullable(getEnrollSourceService().getById(id));
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/enrollsources/{id}/meta", produces = "application/json")
    public ResponseEntity<Optional<EnrollMetaDto>> getSourceMeta(final @PathVariable String id) {
        return ResponseEntity.ofNullable(getEnrollMetaService().getBySource(EnrollSource.create().setId(id)));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/enrollsources/{id}/enrolls", produces = "application/json")
    public ResponseEntity<Collection<EnrollDto>> getSourceEnrolls(final @PathVariable String id) {
        return ResponseEntity.ofNullable(getEnrollSourceService().getById(id)
                .map(x -> getEnrollService().getAllBySource(x))
                .orElse(List.of()));
    }
}
