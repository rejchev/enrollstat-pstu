package ru.rejchev.pstu.enrollstat.domain.base;

public interface IEnrollSource extends ICastable<IEnrollSource> {

    String getId();

    String getPath();

    int getYear();
}
