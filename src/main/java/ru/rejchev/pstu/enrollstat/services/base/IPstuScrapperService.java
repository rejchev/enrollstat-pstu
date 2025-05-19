package ru.rejchev.pstu.enrollstat.services.base;

import ru.rejchev.pstu.enrollstat.domain.base.IScrapperHandler;

public interface IPstuScrapperService {
    boolean scrapEnrollDocuments(int year, IScrapperHandler handler, Object any);

    boolean scrapEnroll(String enrollPath, IScrapperHandler handler, Object any);
}
