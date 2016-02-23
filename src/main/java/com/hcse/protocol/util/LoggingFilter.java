package com.hcse.protocol.util;

import org.apache.mina.filter.logging.LogLevel;

public class LoggingFilter extends org.apache.mina.filter.logging.LoggingFilter {

    public LoggingFilter() {
        setExceptionCaughtLogLevel(LogLevel.WARN);
        setMessageReceivedLogLevel(LogLevel.DEBUG);
        setMessageSentLogLevel(LogLevel.DEBUG);
        setSessionClosedLogLevel(LogLevel.DEBUG);
        setSessionCreatedLogLevel(LogLevel.DEBUG);
        setSessionIdleLogLevel(LogLevel.DEBUG);
        setSessionOpenedLogLevel(LogLevel.DEBUG);
    }
}
