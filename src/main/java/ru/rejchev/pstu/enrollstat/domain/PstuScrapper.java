package ru.rejchev.pstu.enrollstat.domain;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import ru.rejchev.pstu.enrollstat.domain.base.IScrapperHandler;


@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class PstuScrapper {
    public static final String PstuBase = "https://pstu.ru";
    public static final String BaseAgent = "";

    public static PstuScrapper create() {
        return new PstuScrapper(PstuBase, BaseAgent);
    }

    public PstuScrapper(final String base) {
        this(base, PstuBase);
    }

    public PstuScrapper() {
        this(PstuBase, BaseAgent);
    }

    String baseURL;

    String agent;

    public boolean scrap(final String endpoint, final String cssQuery, final IScrapperHandler handler, Object any) {
        if(handler == null)
            return false;

        final Connection connection = Jsoup.connect(getBaseURL() + endpoint)
                .userAgent(getAgent());

        String err = null;
        Elements elements = null;

        try {
            elements = connection.get().select(cssQuery);
        }
        catch (final Exception e) {
            err = e.getMessage();
        }

        handler.handle(elements, err, any);
        return true;
    }
}
