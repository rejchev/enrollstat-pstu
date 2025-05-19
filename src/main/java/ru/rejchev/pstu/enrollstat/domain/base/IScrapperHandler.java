package ru.rejchev.pstu.enrollstat.domain.base;

import org.jsoup.select.Elements;

public interface IScrapperHandler {
    void handle(final Elements elements, final String err, Object any);
}
