package ru.rejchev.pstu.enrollstat.domain.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnrollDocumentMetaDto {

    public static EnrollDocumentMetaDto create(int year, String path) {
        return new EnrollDocumentMetaDto(year, path);
    }

    int year;

    String path;

    public EnrollDocumentMetaDto(int year, String path) {
        this.year = year;
        this.path = path;
    }

    public EnrollDocumentMetaDto setYear(int value) {
        year = value;
        return this;
    }

    public EnrollDocumentMetaDto setPath(String value) {
        path = value;
        return this;
    }
}
