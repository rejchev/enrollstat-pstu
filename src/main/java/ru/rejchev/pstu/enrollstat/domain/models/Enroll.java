package ru.rejchev.pstu.enrollstat.domain.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;
import ru.rejchev.pstu.enrollstat.domain.base.IEnroll;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollMeta;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollSource;

import java.io.Serializable;

@Getter
@Entity
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "enrolls")
public class Enroll implements IEnroll, Serializable {

    public static Enroll create() {
        return new Enroll();
    }

    public static Enroll create(final IEnroll value) {
        return create()
                .setId(value.getId())
                .setSource(EnrollSource.create(value.getSource(IEnrollSource.class)))
                .setEid(value.getEid())
                .setOrder(value.getOrder())
                .setGroup(value.getGroup())
                .setTotalPoints(value.getTotalPoints());
    }

    @Id
    @UuidGenerator
    String id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "source_id", nullable = false)
    EnrollSource source;

    @Column(nullable = false)
    String eid;

    @Column(name="order_id", nullable = false)
    String order;

    @Column(name = "edu_group", nullable = false)
    String group;

    @Column(name = "total_points", nullable = false)
    int totalPoints;

    public Enroll setId(final String value) {
        this.id = value;
        return this;
    }

    public Enroll setSource(final EnrollSource value) {
        this.source = value;
        return this;
    }

    public Enroll setEid(final String value) {
        this.eid = value;
        return this;
    }

    public Enroll setOrder(final String value) {
        this.order = value;
        return this;
    }

    public Enroll setGroup(final String value) {
        this.group = value;
        return this;
    }

    public Enroll setTotalPoints(final int value) {
        this.totalPoints = value;
        return this;
    }

    @Override
    public <T extends IEnrollSource> T getSource(Class<T> clazz) {
        return ((getSource() != null) ? getSource().cast(clazz) : null);
    }
}
