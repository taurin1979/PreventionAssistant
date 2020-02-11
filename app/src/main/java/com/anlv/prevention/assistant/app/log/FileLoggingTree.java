package com.anlv.prevention.assistant.app.log;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import timber.log.Timber;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2018/5/29
 *     desc   : 日志文件输出格式。
 * </pre>
 */
public class FileLoggingTree extends Timber.DebugTree {

    private static Logger mLogger = LoggerFactory.getLogger(FileLoggingTree.class);

    @Override
    protected String createStackElementTag(@NotNull StackTraceElement element) {
        return String.format("[%s]-%s:%s(%s)",
                element.getFileName(),
                super.createStackElementTag(element),
                element.getMethodName(),
                element.getLineNumber());
    }

    @Override
    protected void log(int priority, String tag, @NotNull String message, Throwable t) {
        if (priority > Log.VERBOSE) {
            String logMessage = tag + ": " + message;
            switch (priority) {
                case Log.DEBUG:
                    mLogger.debug(logMessage);
                    break;
                case Log.INFO:
                    mLogger.info(logMessage);
                    break;
                case Log.WARN:
                    mLogger.warn(logMessage);
                    break;
                case Log.ERROR:
                    mLogger.error(logMessage);
                    break;
            }
        }
    }
}
