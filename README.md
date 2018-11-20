# Dropwizard Sentry

[![Build Status](https://travis-ci.org/knonm/dropwizard-sentry.svg?branch=master)](https://travis-ci.org/knonm/dropwizard-sentry)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.knonm/dropwizard-sentry/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.knonm/dropwizard-sentry)
[![Javadocs](https://www.javadoc.io/badge/io.github.knonm/dropwizard-sentry.svg)](https://www.javadoc.io/doc/io.github.knonm/dropwizard-sentry)

Dropwizard integration for error logging to [Sentry](https://sentry.io). This is a simpler version where you don't need to configure DSN and other options on the application's YAML. Useful when using other logging libs besides Logback (Dropwizard's logging backend). Forked from dhatim's [dropwizard-sentry](https://github.com/dhatim/dropwizard-sentry).

## Usage

Dropwizard Sentry provides an `AppenderFactory` which is automatically registered in Dropwizard and will send errors to Sentry.

### Logging startup errors

In order to log startup errors (i.e. before the `SentryAppenderFactory` has been properly initialized), the Dropwizard application has to run the `SentryBootstrap.bootstrap()` in its `main` method and set a custom `UncaughtExceptionHandler` for the main thread.

```java
public static void main(String[] args) throws Exception {
    SentryBootstrap.bootstrap();
    Thread.currentThread().setUncaughtExceptionHandler(UncaughtExceptionHandlers.systemExit());

    new MyDropwizardApplication().run(args);
}
```

### Configuration

Include the `sentry` appender in your application's YAML configuration:

```yaml
appenders:
  - type: sentry
    threshold: ERROR
```

| Setting | Default | Description | Example Value |
|---|---|---|---|
| `threshold` | ALL | The log level to configure to send to Sentry | `ERROR` |

Other options, like DSN and Sample Rate, should be configured as described on [Sentry documentation](https://docs.sentry.io/clients/java/config/).

## Maven Artifacts

This project is available in the [Central Repository](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22io.github.knonm%22%20AND%20a%3A%22dropwizard-sentry%22). To add it to your project simply add the following dependency to your POM:

```xml
<dependency>
  <groupId>io.github.knonm</groupId>
  <artifactId>dropwizard-sentry</artifactId>
  <version>2.1.8</version>
</dependency>
```

## Support

Please file bug reports and feature requests in [GitHub issues](https://github.com/knonm/dropwizard-sentry/issues).
