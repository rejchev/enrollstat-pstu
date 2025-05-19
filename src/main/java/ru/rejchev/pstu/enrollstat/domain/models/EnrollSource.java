package ru.rejchev.pstu.enrollstat.domain.models;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollSource;

import java.io.Serializable;

@Getter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "enrolls_sources")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnrollSource implements IEnrollSource, Serializable {

    public static EnrollSource create() {
        return new EnrollSource();
    }

    public static EnrollSource create(IEnrollSource value) {
        return create()
                .setId(value.getId())
                .setPath(value.getPath())
                .setYear(value.getYear());
    }

    @Id
    @UuidGenerator
    String id;

    @Column(nullable = false, unique = true)
    String path;

    @Column(nullable = false)
    int year;

    public EnrollSource setId(final String value) {
        this.id = value;
        return this;
    }

    public EnrollSource setPath(final String value) {
        this.path = value;
        return this;
    }

    public EnrollSource setYear(final int value) {
        this.year = value;
        return this;
    }
}
