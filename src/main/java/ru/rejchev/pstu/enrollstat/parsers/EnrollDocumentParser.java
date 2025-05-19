package ru.rejchev.pstu.enrollstat.parsers;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.rejchev.pstu.enrollstat.domain.EnrollMetaParserToolRecord;
import ru.rejchev.pstu.enrollstat.domain.base.IEnrollSource;
import ru.rejchev.pstu.enrollstat.domain.dtos.EnrollDto;
import ru.rejchev.pstu.enrollstat.domain.dtos.EnrollMetaDto;
import ru.rejchev.pstu.enrollstat.domain.dtos.EnrollSourceDto;
import ru.rejchev.pstu.enrollstat.events.EnrollDocumentPollerEvent;
import ru.rejchev.pstu.enrollstat.services.base.IEnrollMetaService;
import ru.rejchev.pstu.enrollstat.services.base.IEnrollService;
import ru.rejchev.pstu.enrollstat.services.base.IEnrollSourceService;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Getter(AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnrollDocumentParser {

    @Getter
    private enum EnrollDocumentRowType {
        kLastModified (2),
        kEduFormat(3),
        kBranch(4),
        kLevel(5),
        kSpecialization(6),
        kAdmission(7),
        kCategory(8),
        kGroup(9),
        kTotal(10);

        final int pos;

        EnrollDocumentRowType(int i) {
            this.pos = i;
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(EnrollDocumentParser.class);

    private static final int MinimalDocumentRowCount = 11;

    private final Map<EnrollDocumentRowType, EnrollMetaParserToolRecord> EnrollMetaMap = Map.of(
            EnrollDocumentRowType.kLastModified,
            EnrollMetaParserToolRecord.create(
                    Pattern.compile("(?<date>(?<day>\\d{1,2})\\.(?<month>(1[0-2]|0[1-9]))\\.(20[2-9][2-9]))[ .А-ЯЁа-яё-]+(?<time>([01][0-9]|2[0-3]|[1-9]):([0-5][0-9]):([0-5][0-9]))"),
                    this::extractLastModified
            ),

            EnrollDocumentRowType.kEduFormat,
            EnrollMetaParserToolRecord.create(
                    Pattern.compile("- (?<format>[ А-ЯЁа-яё-]+)"),
                    this::extractEduFormat
            ),


            EnrollDocumentRowType.kBranch,
            EnrollMetaParserToolRecord.create(
                    Pattern.compile("- (?<format>[ А-ЯЁа-яё-]+)"),
                    this::extractEduFormat
            ),

            EnrollDocumentRowType.kLevel,
            EnrollMetaParserToolRecord.create(
                    Pattern.compile("- (?<format>[ А-ЯЁа-яё-]+)"),
                    this::extractEduFormat
            ),

            EnrollDocumentRowType.kSpecialization,
            EnrollMetaParserToolRecord.create(
                    Pattern.compile("- (?<format>[ А-ЯЁа-яё-]+)"),
                    this::extractEduFormat
            ),

            EnrollDocumentRowType.kAdmission,
            EnrollMetaParserToolRecord.create(
                    Pattern.compile("- (?<format>[ А-ЯЁа-яё-]+)"),
                    this::extractEduFormat
            ),

            EnrollDocumentRowType.kCategory,
            EnrollMetaParserToolRecord.create(
                    Pattern.compile("- (?<format>[ А-ЯЁа-яё-]+)"),
                    this::extractEduFormat
            ),

            EnrollDocumentRowType.kGroup,
            EnrollMetaParserToolRecord.create(
                    Pattern.compile("- ?(?<format>.+)"),
                    this::extractEduFormat
            ),

            EnrollDocumentRowType.kTotal,
            EnrollMetaParserToolRecord.create(
                    Pattern.compile(": (?<total>\\d+)[ .А-Яа-яеЁ]+: (?<enrolled>[+-]?\\d+)"),
                    this::extractEduFormatEx
            )
    );

    IEnrollMetaService enrollMetaService;
    IEnrollService enrollService;
    IEnrollSourceService enrollSourceService;

    @Autowired
    public EnrollDocumentParser(
            IEnrollMetaService enrollMetaService,
            IEnrollService enrollService,
            IEnrollSourceService enrollSourceService) {
        this.enrollMetaService = enrollMetaService;
        this.enrollService = enrollService;
        this.enrollSourceService = enrollSourceService;
    }

    @EventListener(value = EnrollDocumentPollerEvent.class)
    void onEvent(EnrollDocumentPollerEvent event) {
        if(event.getData().isEmpty())
            return;

        event.getData().entrySet().parallelStream().forEach((x) ->
                handle(x.getKey(), getEnrollMetaService().getBySource(x.getKey()), x.getValue())
        );
    }

    @Async
    void handle(IEnrollSource source, Optional<EnrollMetaDto> meta, final List<String> document) {

        if(document.size() < MinimalDocumentRowCount)
            return;

        final EnrollMetaDto dto = extractEnrollMeta(document)
                .setSource(EnrollSourceDto.create(source));

        String id = null;
        if(meta.isPresent())
            id = meta.get().getId();

        if(id != null && Objects.equals(meta.get().getLastModified(), dto.getLastModified()))
            return;

        getEnrollMetaService().update(id, dto);

        final Collection<EnrollDto> enrolls = getEnrollService()
                .getAllBySource(source);

        document.stream().skip(MinimalDocumentRowCount).forEach(x -> {
            if(x.isEmpty())
                return;

            if(x.startsWith("№ Ун"))
                return;

            int idx;
            if((idx = x.indexOf(" ")) == -1)
                return;

            Optional<EnrollDto> enrollDto;
            if((enrollDto = extractEnroll(x.substring(idx + 1))).isEmpty())
                return;

            final Optional<String> existingEnrollId = enrolls.stream()
                    .filter(e -> e.getEid().equals(enrollDto.get().getEid()))
                    .map(e -> e.getSource().getId())
                    .findFirst();

            getEnrollService().update(existingEnrollId.orElse(null), enrollDto.get().setSource(EnrollSourceDto.create(source)));
        });
    }

    protected Optional<EnrollDto> extractEnroll(String buf) {
        final EnrollDto dto = EnrollDto.create();

        String buf2;
        int idx, idx2;
        Matcher matcher;
        final Pattern groupPattern = Pattern.compile("(?<group>([А-Я1-9Ё])+ ?-(\\d){2}-([0-9]{1,2})([а-я]+))");

        if((idx = buf.indexOf("№")) == -1 || (idx2 = buf.lastIndexOf("от")) == -1)
            return Optional.empty();

        dto.setOrder(buf.substring(idx + 1, idx2).trim());

        buf = buf.substring(0, idx) + buf.substring(idx2).trim();

        if(!(matcher = groupPattern.matcher(buf)).find())
            dto.setGroup("");

        else  {
            dto.setGroup(tryGet(matcher, "group").replaceAll(" ", ""));
            buf = buf.substring(0, matcher.start());
        }


        final String[] parts = buf.split(" ");

        if(parts[0].contains("-"))
            buf2 = parts[0] + " " + parts[1];

        else buf2 = parts[0];

        dto.setEid(buf2);

        dto.setTotalPoints(0);

        logger.debug("dto: {}", dto);

        return Optional.of(dto);
    }

    protected EnrollMetaDto extractEnrollMeta(final List<String> document) {
        final EnrollMetaDto meta = EnrollMetaDto.create();

        EnrollMetaMap.forEach((x, y) -> {
            final String result = y.extractor().extract(document.get(x.getPos()), y.pattern());

            if(result.isEmpty())
                return;

            switch (x) {
                case kLastModified: meta.setLastModified(result);
                break;
                case kEduFormat: meta.setEduFormat(result);
                break;
                case kBranch: meta.setBranch(result);
                break;
                case kLevel: meta.setLevel(result);
                break;
                case kSpecialization: meta.setSpecialization(result);
                break;
                case kAdmission: meta.setAdmission(result);
                break;
                case kCategory: meta.setCategory(result);
                break;
                case kGroup: meta.setCompetitionGroup(result);
                break;
                case kTotal: {
                    final int pos = result.indexOf(' ');

                    if(pos == -1)
                        return;

                    try {
                        meta.setEnrolled(Integer.parseInt(result.substring(pos + 1)));
                        meta.setTotal(Integer.parseInt(result.substring(0, pos)));
                    } catch (NumberFormatException ignored) {}
                }
            }
        });

        return meta;
    }

    protected String extractLastModified(final String val, final Pattern pattern) {
        Matcher matcher;
        if(!(matcher = pattern.matcher(val)).find())
            return "";

        final String date = tryGet(matcher, "date");
        final String time = tryGet(matcher, "time");

        return date + "_" + time;
    }

    protected String extractEduFormat(final String val, final Pattern pattern) {
        Matcher matcher;
        if(!(matcher = pattern.matcher(val)).find())
            return "";

        return tryGet(matcher, "format");
    }

    protected String extractEduFormatEx(final String val, final Pattern pattern) {
        Matcher matcher;
        if(!(matcher = pattern.matcher(val)).find())
            return "";

        final String total = tryGet(matcher, "total");
        final String enrolled = tryGet(matcher, "enrolled");

        if(total.isEmpty() || enrolled.isEmpty())
            return "";

        return total + " " + enrolled;
    }

    protected String tryGet(final Matcher matcher, final String group) {
        try {
            String buf;
            return (buf = matcher.group(group)) == null ? "" : buf;
        } catch (IllegalStateException | IllegalArgumentException ignored) {}

        return "";
    }

}
