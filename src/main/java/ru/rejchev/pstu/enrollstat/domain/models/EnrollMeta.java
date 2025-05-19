package ru.rejchev.pstu.enrollstat.domain.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollSource;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollMeta;

import java.io.Serializable;

@Getter
@Entity
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "enrolls_metas")
public class EnrollMeta implements IEnrollMeta, Serializable {

    public static EnrollMeta create() {
        return new EnrollMeta();
    }

    public static EnrollMeta create(final IEnrollSource value) {
        return create().setSource(EnrollSource.create(value));
    }

    public static EnrollMeta create(IEnrollMeta value) {
        return create()
                .setId(value.getId())
                .setSource(EnrollSource.create(value.getSource(IEnrollSource.class)))
                .setEduFormat(value.getEduFormat())
                .setBranch(value.getBranch())
                .setLevel(value.getLevel())
                .setSpecialization(value.getSpecialization())
                .setAdmission(value.getAdmission())
                .setCategory(value.getCategory())
                .setLastModified(value.getLastModified())
                .setTotal(value.getTotal())
                .setEnrolled(value.getEnrolled())
                .setCompetitionGroup(value.getCompetitionGroup());
    }

    @Id
    @UuidGenerator
    String id;

    @ManyToOne
    @JoinColumn(name = "source_id", unique = true, nullable = false)
    EnrollSource source;

    // очная / заочная / очно-заочная
    @Column(name="edu_format", nullable = false)
    String eduFormat = "";

    // филиал
    @Column(nullable = false)
    String branch = "";

    // бакалавр / маг ....
    @Column(nullable = false)
    String level = "";

    // направление подготовки / специализация
    @Column(nullable = false)
    String specialization = "";

    // основания: бюджетная основа / полное возмещение затрат ...
    @Column(nullable = false)
    String admission = "";

    // категория приема: на общих основаниях / ...
    @Column(nullable = false)
    String category = "";

    // Конкурсная группа
    @Column(name = "competition_group", nullable = false, length = 512)
    String competitionGroup = "";

    @Column(name = "last_modified", nullable = false)
    String lastModified = "";

    // зачислено
    @Column(nullable = false)
    int total = 0;

    // всего мест
    @Column(nullable = false)
    int enrolled = 0;

    public EnrollMeta setId(String value) {
        this.id = value;
        return this;
    }

    public EnrollMeta setSource(EnrollSource value) {
        this.source = value;
        return this;
    }

    public EnrollMeta setEduFormat(String value) {
        this.eduFormat = value;
        return this;
    }

    public EnrollMeta setBranch(String value) {
        this.branch = value;
        return this;
    }

    public EnrollMeta setLevel(String value) {
        this.level = value;
        return this;
    }

    public EnrollMeta setSpecialization(String value) {
        this.specialization = value;
        return this;
    }

    public EnrollMeta setAdmission(String value) {
        this.admission = value;
        return this;
    }

    public EnrollMeta setCategory(String value) {
        this.category = value;
        return this;
    }

    public EnrollMeta setCompetitionGroup(String value) {
        this.competitionGroup = value;
        return this;
    }

    public EnrollMeta setTotal(int value) {
        this.total = value;
        return this;
    }

    public EnrollMeta setEnrolled(int value) {
        this.enrolled = value;
        return this;
    }

    public EnrollMeta setLastModified(String value) {
        this.lastModified = value;
        return this;
    }

    @Override
    public <T extends IEnrollSource> T getSource(Class<T> clazz) {
        return ((getSource() != null) ? getSource().cast(clazz) : null);
    }
}
