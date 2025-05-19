package ru.rejchev.pstu.enrollstat.domain.base;

public interface IEnrollMeta extends ICastable<IEnrollMeta> {
    String getId();

    String getEduFormat();

    String getBranch();

    String getLevel();

    String getSpecialization();

    String getAdmission();

    String getCategory();

    String getCompetitionGroup();

    String getLastModified();

    int getEnrolled();

    int getTotal();

    <T extends IEnrollSource> T getSource(Class<T> clazz);
}
