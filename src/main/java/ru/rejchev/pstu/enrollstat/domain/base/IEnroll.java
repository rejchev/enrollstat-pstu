package ru.rejchev.pstu.enrollstat.domain.base;

public interface IEnroll extends ICastable<IEnroll> {

    // local unique id
    String getId();

    // unique educator id
    String getEid();

    // номер приказа
    String getOrder();

    // группа
    String getGroup();

    // всего баллов
    int getTotalPoints();

    <T extends IEnrollSource> T getSource(Class<T> clazz);
}
