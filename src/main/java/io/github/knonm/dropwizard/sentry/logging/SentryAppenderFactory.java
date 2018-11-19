package io.github.knonm.dropwizard.sentry.logging;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.filter.Filter;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.dropwizard.logging.AbstractAppenderFactory;
import io.dropwizard.logging.async.AsyncAppenderFactory;
import io.dropwizard.logging.filter.LevelFilterFactory;
import io.dropwizard.logging.layout.LayoutFactory;
import io.sentry.logback.SentryAppender;
import io.github.knonm.dropwizard.sentry.filters.DroppingSentryLoggingFilter;

import static com.google.common.base.Preconditions.checkNotNull;

@JsonTypeName("sentry")
public class SentryAppenderFactory extends AbstractAppenderFactory<ILoggingEvent> {

    public static final String APPENDER_NAME = "dropwizard-sentry";

    @Override
    public Appender<ILoggingEvent> build(LoggerContext context,
            String applicationName,
            LayoutFactory<ILoggingEvent> layoutFactory,
            LevelFilterFactory<ILoggingEvent> levelFilterFactory,
            AsyncAppenderFactory<ILoggingEvent> asyncAppenderFactory) {
        checkNotNull(context);

        final SentryAppender appender = new SentryAppender();
        appender.setName(APPENDER_NAME);
        appender.setContext(context);

        appender.addFilter(levelFilterFactory.build(threshold));
        getFilterFactories().stream().forEach(f -> appender.addFilter(f.build()));
        appender.start();

        final Appender<ILoggingEvent> asyncAppender = wrapAsync(appender, asyncAppenderFactory, context);
        addDroppingRavenLoggingFilter(asyncAppender);

        return asyncAppender;
    }

    private void addDroppingRavenLoggingFilter(Appender<ILoggingEvent> appender) {
        final Filter<ILoggingEvent> filter = new DroppingSentryLoggingFilter();
        filter.start();
        appender.addFilter(filter);
    }

}
