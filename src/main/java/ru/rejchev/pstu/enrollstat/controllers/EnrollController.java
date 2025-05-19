package ru.rejchev.pstu.enrollstat.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnrollController {

    private final static Logger logger = LoggerFactory.getLogger(EnrollController.class);

    IEnrollService enrollService;

    @Autowired
    public EnrollController(IEnrollService enrollService) {
        this.enrollService = enrollService;
    }

    @GetMapping({"/enrolls"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Collection<EnrollDto>> all(
            final Optional<Integer> year,
            final Optional<String> order
    ) {
        logger.info("EnrollController::all()");
        if(year.isPresent())
            return ResponseEntity.ofNullable(getEnrollService().getAllByYear(year.get()));

        if(order.isPresent())
            return ResponseEntity.ofNullable(getEnrollService().getAllByOrder(order.get()));

        return ResponseEntity.ofNullable(getEnrollService().getAll());
    }

    @GetMapping("/enrolls/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Optional<EnrollDto>> byId(final @PathVariable String id) {
        return ResponseEntity.ofNullable(getEnrollService().getBy(id));
    }


}
