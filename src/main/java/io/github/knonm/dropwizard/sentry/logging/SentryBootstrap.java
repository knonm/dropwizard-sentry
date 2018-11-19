package io.github.knonm.dropwizard.sentry.logging;

import ch.qos.logback.classic.Logger;
import io.dropwizard.logging.async.AsyncLoggingEventAppenderFactory;
import io.dropwizard.logging.filter.ThresholdLevelFilterFactory;
import io.dropwizard.logging.layout.DropwizardLayoutFactory;
import java.util.Optional;
import java.util.Set;
import static org.slf4j.Logger.ROOT_LOGGER_NAME;
import org.slf4j.LoggerFactory;

/**
 * A class adding a configured
 * {@link io.sentry.logback.SentryAppender} to the root logger.
 */
public final class SentryBootstrap {

    private SentryBootstrap() {
        /* No instance methods */ }

    /**
     * Bootstrap the SLF4J root logger with a configured
     * {@link io.sentry.logback.SentryAppender}.
     */
    public static void bootstrap() {
        bootstrap(true);
    }

    /**
     * Bootstrap the SLF4J root logger with a configured
     * {@link io.sentry.logback.SentryAppender}.
     *
     * @param cleanRootLogger If true, detach and stop all other appenders from
     * the root logger
     */
    public static void bootstrap(boolean cleanRootLogger) {
        final SentryAppenderFactory factory = new SentryAppenderFactory();

        registerAppender(cleanRootLogger, factory);
    }

    private static void registerAppender(boolean cleanRootLogger,
            SentryAppenderFactory factory) {
        final Logger root = (Logger) LoggerFactory.getLogger(ROOT_LOGGER_NAME);

        if (cleanRootLogger) {
            root.detachAndStopAllAppenders();
        }

        final ThresholdLevelFilterFactory levelFilterFactory = new ThresholdLevelFilterFactory();
        final DropwizardLayoutFactory layoutFactory = new DropwizardLayoutFactory();
        final AsyncLoggingEventAppenderFactory asyncAppenderFactory
                = new AsyncLoggingEventAppenderFactory();
        root.addAppender(factory.build(root.getLoggerContext(), SentryAppenderFactory.APPENDER_NAME, layoutFactory, levelFilterFactory,
                asyncAppenderFactory));
    }
}
