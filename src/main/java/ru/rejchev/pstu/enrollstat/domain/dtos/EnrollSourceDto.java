package ru.rejchev.pstu.enrollstat.domain.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollSource;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnrollSourceDto implements IEnrollSource, Serializable {

    public static EnrollSourceDto create() {
        return new EnrollSourceDto();
    }

    public static EnrollSourceDto create(IEnrollSource value) {
        return create()
                .setId(value.getId())
                .setPath(value.getPath())
                .setYear(value.getYear());
    }

    String id;

    String path;

    int year;

    public EnrollSourceDto setId(final String value) {
        this.id = value;
        return this;
    }

    public EnrollSourceDto setPath(final String value) {
        this.path = value;
        return this;
    }

    public EnrollSourceDto setYear(final int value) {
        this.year = value;
        return this;
    }
}
