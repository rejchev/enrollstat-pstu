package ru.rejchev.pstu.enrollstat.domain.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.rejchev.pstu.enrollstat.domain.base.IEnroll;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollMeta;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollSource;
import ru.rejchev.pstu.enrollstat.domain.models.EnrollSource;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnrollDto implements IEnroll, Serializable {

    public static EnrollDto create() {
        return new EnrollDto();
    }

    public static EnrollDto create(final IEnroll value) {
        return create()
                .setId(value.getId())
                .setSource(EnrollSourceDto.create(value.getSource(IEnrollSource.class)))
                .setEid(value.getEid())
                .setOrder(value.getOrder())
                .setGroup(value.getGroup())
                .setTotalPoints(value.getTotalPoints());
    }

    String id;

    EnrollSourceDto source;

    String eid;

    String order;

    String group;

    int totalPoints;

    public EnrollDto setId(final String value) {
        this.id = value;
        return this;
    }

    public EnrollDto setSource(final EnrollSourceDto value) {
        this.source = value;
        return this;
    }

    public EnrollDto setEid(final String value) {
        this.eid = value;
        return this;
    }

    public EnrollDto setOrder(final String value) {
        this.order = value;
        return this;
    }

    public EnrollDto setGroup(final String value) {
        this.group = value;
        return this;
    }

    public EnrollDto setTotalPoints(final int value) {
        this.totalPoints = value;
        return this;
    }

    @Override
    public <T extends IEnrollSource> T getSource(Class<T> clazz) {
        return ((getSource() != null) ? getSource().cast(clazz) : null);
    }
}
