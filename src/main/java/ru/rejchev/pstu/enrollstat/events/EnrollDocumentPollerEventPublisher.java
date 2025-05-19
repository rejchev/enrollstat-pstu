package ru.rejchev.pstu.enrollstat.events;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollSource;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
@Getter(AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnrollDocumentPollerEventPublisher {

    ApplicationEventPublisher publisher;

    @Autowired
    public EnrollDocumentPollerEventPublisher(final ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishEvent(Object source, final Map<IEnrollSource, List<String>> vals) {
        getPublisher().publishEvent(EnrollDocumentPollerEvent.create(source, vals));
    }
}
