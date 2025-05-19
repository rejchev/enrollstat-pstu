package ru.rejchev.pstu.enrollstat.events;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollSource;

import java.util.List;
import java.util.Map;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnrollDocumentPollerEvent extends ApplicationEvent {

    public static EnrollDocumentPollerEvent create(Object obj, final Map<IEnrollSource, List<String>> vals) {
           return new EnrollDocumentPollerEvent(obj, vals);
    }

    Map<IEnrollSource, List<String>> data;

    public EnrollDocumentPollerEvent(Object source, final Map<IEnrollSource, List<String>> data) {
        super(source);
        this.data = data;
    }
}
