package ru.rejchev.pstu.enrollstat.domain.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollSource;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollMeta;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnrollMetaDto implements IEnrollMeta, Serializable {

    public static EnrollMetaDto create() {
        return new EnrollMetaDto();
    }

    public static EnrollMetaDto create(final IEnrollMeta value) {
        return create()
                .setId(value.getId())
                .setSource(EnrollSourceDto.create(value.getSource(IEnrollSource.class)))
                .setBranch(value.getBranch())
                .setAdmission(value.getAdmission())
                .setCategory(value.getCategory())
                .setSpecialization(value.getSpecialization())
                .setCompetitionGroup(value.getCompetitionGroup())
                .setEduFormat(value.getEduFormat())
                .setEnrolled(value.getEnrolled())
                .setLastModified(value.getLastModified())
                .setTotal(value.getTotal())
                .setLevel(value.getLevel());
    }

    String id;

    EnrollSourceDto source;

    String eduFormat;

    String branch;

    String level;

    String specialization;

    String admission;

    String category;

    String competitionGroup;

    String lastModified;

    int total;

    int enrolled = 0;

    public EnrollMetaDto setId(String value) {
        this.id = value;
        return this;
    }

    public EnrollMetaDto setSource(EnrollSourceDto value) {
        this.source = value;
        return this;
    }

    public EnrollMetaDto setEduFormat(String value) {
        this.eduFormat = value;
        return this;
    }

    public EnrollMetaDto setBranch(String value) {
        this.branch = value;
        return this;
    }

    public EnrollMetaDto setLevel(String value) {
        this.level = value;
        return this;
    }

    public EnrollMetaDto setSpecialization(String value) {
        this.specialization = value;
        return this;
    }

    public EnrollMetaDto setAdmission(String value) {
        this.admission = value;
        return this;
    }

    public EnrollMetaDto setCategory(String value) {
        this.category = value;
        return this;
    }

    public EnrollMetaDto setCompetitionGroup(String value) {
        this.competitionGroup = value;
        return this;
    }

    public EnrollMetaDto setTotal(int value) {
        this.total = value;
        return this;
    }

    public EnrollMetaDto setEnrolled(int value) {
        this.enrolled = value;
        return this;
    }

    public EnrollMetaDto setLastModified(String value) {
        this.lastModified = value;
        return this;
    }

    @Override
    public <T extends IEnrollSource> T getSource(Class<T> clazz) {
        return ((getSource() != null) ? getSource().cast(clazz) : null);
    }
}
