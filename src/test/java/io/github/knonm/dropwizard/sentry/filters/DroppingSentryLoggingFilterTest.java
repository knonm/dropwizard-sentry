package io.github.knonm.dropwizard.sentry.filters;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.spi.FilterReply;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class DroppingSentryLoggingFilterTest {

    @Test
    public void verifyFilterDeniesSentryLoggers() {
        final DroppingSentryLoggingFilter filter = new DroppingSentryLoggingFilter();
        filter.start();

        final LoggingEvent evt = new LoggingEvent();
        evt.setLoggerName("io.sentry.logback");
        assertEquals(FilterReply.DENY, filter.decide(evt));
    }

    @Test
    public void verifyFilterAllowsNonSentryLoggers() {
        final DroppingSentryLoggingFilter filter = new DroppingSentryLoggingFilter();
        filter.start();

        final LoggingEvent evt = new LoggingEvent();
        evt.setLoggerName("org.dhatim.sentry");
        assertEquals(FilterReply.NEUTRAL, filter.decide(evt));
    }
}
