package ru.rejchev.pstu.enrollstat.domain;

import ru.rejchev.pstu.enrollstat.domain.base.IEnrollMetaParserDataExtractor;

import java.util.regex.Pattern;


public record EnrollMetaParserToolRecord(Pattern pattern, IEnrollMetaParserDataExtractor extractor) {

    public static EnrollMetaParserToolRecord create(Pattern pattern, IEnrollMetaParserDataExtractor extractor) {
        return new EnrollMetaParserToolRecord(pattern, extractor);
    }

}
