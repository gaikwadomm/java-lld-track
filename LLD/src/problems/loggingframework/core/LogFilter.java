package problems.loggingframework.core;

public interface LogFilter {
    boolean shouldLog(LogMessage message);

    void setLevel(LogLevel level);

    LogLevel getLevel();    
}
