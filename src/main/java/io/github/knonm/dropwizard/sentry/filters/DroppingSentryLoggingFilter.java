package io.github.knonm.dropwizard.sentry.filters;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class DroppingSentryLoggingFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event.getLoggerName().startsWith("io.sentry")) {
            return FilterReply.DENY;
        } else {
            return FilterReply.NEUTRAL;
        }
    }
}
