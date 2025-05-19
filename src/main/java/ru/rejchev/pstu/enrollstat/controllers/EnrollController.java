package ru.rejchev.pstu.enrollstat.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rejchev.pstu.enrollstat.domain.dtos.EnrollDto;
import ru.rejchev.pstu.enrollstat.services.base.IEnrollService;

import java.util.Collection;
import java.util.Optional;

@RestController
@Getter(AccessLevel.PRIVATE)
//@AllArgsConstructor(onConstructor_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnrollController {

    IEnrollService enrollService;

    @Autowired
    public EnrollController(IEnrollService enrollService) {
        this.enrollService = enrollService;
    }

    @GetMapping("/enrolls")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Collection<EnrollDto>> all() {
        return ResponseEntity.ofNullable(getEnrollService().getAll());
    }

    @GetMapping("/enrolls/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Optional<EnrollDto>> byId(final @PathVariable String id) {
        return ResponseEntity.ofNullable(getEnrollService().getBy(id));
    }

    @GetMapping("/enrolls?year={year}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Collection<EnrollDto>> allByYear(final @PathVariable Integer year) {
        return ResponseEntity.ofNullable(getEnrollService().getAllByYear(year));
    }

    @GetMapping("/enrolls?order={oid}")
    public ResponseEntity<Collection<EnrollDto>> allByOrder(final @PathVariable String oid) {
        return ResponseEntity.ofNullable(getEnrollService().getAllByOrder(oid));
    }
}
