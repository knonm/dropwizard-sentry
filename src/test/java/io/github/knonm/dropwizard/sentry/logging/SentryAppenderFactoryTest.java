package io.github.knonm.dropwizard.sentry.logging;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import io.dropwizard.logging.async.AsyncLoggingEventAppenderFactory;
import io.dropwizard.logging.filter.ThresholdLevelFilterFactory;
import io.dropwizard.logging.layout.DropwizardLayoutFactory;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class SentryAppenderFactoryTest {

    private final LoggerContext context = new LoggerContext();
    private final DropwizardLayoutFactory layoutFactory = new DropwizardLayoutFactory();
    private final ThresholdLevelFilterFactory levelFilterFactory = new ThresholdLevelFilterFactory();
    private final AsyncLoggingEventAppenderFactory asyncAppenderFactory = new AsyncLoggingEventAppenderFactory();

    @Test(expected = NullPointerException.class)
    public void buildSentryAppenderShouldFailWithNullContext() {
        new SentryAppenderFactory().build(null, "", null, levelFilterFactory, asyncAppenderFactory);
    }

    @Test
    public void buildSentryAppenderShouldWorkWithValidConfiguration() {
        SentryAppenderFactory factory = new SentryAppenderFactory();

        Appender<ILoggingEvent> appender
                = factory.build(context, "", layoutFactory, levelFilterFactory, asyncAppenderFactory);

        assertThat(appender, instanceOf(AsyncAppender.class));
    }

}
