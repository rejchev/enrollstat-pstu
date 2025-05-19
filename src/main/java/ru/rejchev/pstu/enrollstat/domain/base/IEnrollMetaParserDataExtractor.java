package ru.rejchev.pstu.enrollstat.domain.base;

import java.util.regex.Pattern;

@FunctionalInterface
public interface IEnrollMetaParserDataExtractor {
    String extract(final String val, final Pattern pattern);
}
