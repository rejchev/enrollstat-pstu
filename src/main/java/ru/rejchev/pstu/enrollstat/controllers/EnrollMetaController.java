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
import ru.rejchev.pstu.enrollstat.domain.dtos.EnrollMetaDto;
import ru.rejchev.pstu.enrollstat.domain.dtos.EnrollSourceDto;
import ru.rejchev.pstu.enrollstat.services.base.IEnrollMetaService;
import ru.rejchev.pstu.enrollstat.services.base.IEnrollSourceService;

import java.util.Collection;
import java.util.Optional;

@RestController
@Getter(AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnrollMetaController {

    private final static Logger logger = LoggerFactory.getLogger(EnrollSourceController.class);

    IEnrollMetaService enrollMetaService;
    IEnrollSourceService enrollSourceService;

    @Autowired
    public EnrollMetaController(IEnrollMetaService enrollMetaService, IEnrollSourceService enrollSourceService) {
        this.enrollMetaService = enrollMetaService;
        this.enrollSourceService = enrollSourceService;
    }

    @GetMapping({"/enrollmetas"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Collection<EnrollMetaDto>> getAll() {
        logger.info("EnrollController::getAll()");
        return ResponseEntity.ofNullable(getEnrollMetaService().getAll());
    }

    @GetMapping("/enrollmetas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Optional<EnrollMetaDto>> getById(final @PathVariable String id) {
        return ResponseEntity.ofNullable(getEnrollMetaService().getById(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/enrollmetas/{id}/source", produces = "application/json")
    public ResponseEntity<Optional<EnrollSourceDto>> getSourceMeta(final @PathVariable String id) {
        return ResponseEntity.ofNullable(getEnrollMetaService().getById(id).map(EnrollMetaDto::getSource));
    }


}
