package ru.rejchev.pstu.enrollstat.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.rejchev.pstu.enrollstat.domain.PstuScrapper;
import ru.rejchev.pstu.enrollstat.domain.base.IScrapperHandler;
import ru.rejchev.pstu.enrollstat.services.base.IPstuScrapperService;

@Service
@Getter(AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PstuScrapperService implements IPstuScrapperService {

    private static final Logger logger = LoggerFactory.getLogger(PstuScrapperService.class);

    public static final String PstuEnrollEndpointPattern = "/enrollee/stat{y}/list{y}";

    public PstuScrapperService() {}

    public boolean scrapEnrollDocuments(int year, IScrapperHandler handler, Object any) {

        logger.info("pattern: {}", PstuEnrollEndpointPattern.replaceAll("[{]y[}]", "" + year));

        return PstuScrapper.create().scrap(
                PstuEnrollEndpointPattern.replaceAll("[{]y[}]", "" + year),
                "div > div > div > ul > li > a[href^=/files/file/Abitur/{y}]".replaceAll("[{]y[}]", "" + year),
                handler, any
        );
    }

    @Override
    public boolean scrapEnroll(String enrollPath, IScrapperHandler handler, Object any) {
        return PstuScrapper.create().scrap(
                enrollPath,
                "table > tbody > tr",
                handler, any
        );
    }
}
