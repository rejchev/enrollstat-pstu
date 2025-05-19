package ru.rejchev.pstu.enrollstat.pollers;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollSource;
import ru.rejchev.pstu.enrollstat.domain.dtos.EnrollSourceDto;
import ru.rejchev.pstu.enrollstat.domain.models.EnrollSource;
import ru.rejchev.pstu.enrollstat.events.EnrollDocumentPollerEventPublisher;
import ru.rejchev.pstu.enrollstat.services.base.IEnrollSourceService;
import ru.rejchev.pstu.enrollstat.services.base.IPstuScrapperService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Getter(AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnrollDocumentsPoller {

    private static final Logger logger = LoggerFactory.getLogger(EnrollDocumentsPoller.class);

    private static final int BeginningYear = 2022;

    IPstuScrapperService pstuScrapperService;

    EnrollDocumentPollerEventPublisher enrollEventPublisher;

    IEnrollSourceService enrollSourceService;

    Map<IEnrollSource, List<String>> enrollDocuments = new HashMap<>();

    @Autowired
    public EnrollDocumentsPoller(
            IPstuScrapperService pstuScrapperService,
            IEnrollSourceService enrollSourceService,
            EnrollDocumentPollerEventPublisher enrollEventPublisher) {
        this.pstuScrapperService = pstuScrapperService;
        this.enrollSourceService = enrollSourceService;
        this.enrollEventPublisher = enrollEventPublisher;
    }

    @Async
    @Scheduled(initialDelay = 15000, fixedRateString = "PT01H")
    void poll() {
        logger.info("EnrollDocumentsPoller::poll()");

        IntStream.range(BeginningYear, LocalDateTime.now().getYear() + 1).parallel().forEach(
                year -> getPstuScrapperService().scrapEnrollDocuments(year, this::pollerHandler, year)
        );

        getEnrollEventPublisher().publishEvent(this, enrollDocuments);

        getEnrollDocuments().clear();
    }

    private void pollerHandler(Elements data, String err, Object any) {
        if((err != null && !err.isEmpty()) || data == null || data.isEmpty())
            return;

        final int year = (Integer) any;

        final Collection<EnrollSourceDto> existingEnrollSources = getEnrollSourceService()
                .getAllByYear(year);

        logger.info("EnrollDocumentsPoller::handle({})", year);

        data.forEach(x -> {
            final String ref = x.attr("href").replace(" ", "%20");
            Optional<EnrollSourceDto> dto;
            if((dto = existingEnrollSources.stream().filter(y -> y.getPath().equals(ref)).findFirst()).isEmpty())
                existingEnrollSources.add((dto = Optional.of(
                        getEnrollSourceService().update(null, EnrollSource.create().setPath(ref).setYear(year))
                )).get());

            getPstuScrapperService().scrapEnroll(ref, this::enrollPollerHandler, dto.get());
        });
    }

    private void enrollPollerHandler(Elements data, String err, Object any) {
        if((err != null && !err.isEmpty()) || data == null || data.isEmpty() || !(any instanceof EnrollSourceDto source))
            return;

        getEnrollDocuments().put(source, data.stream().map(Element::text).collect(Collectors.toList()));
    }
}
