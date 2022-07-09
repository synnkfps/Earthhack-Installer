package impl.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class ThreadUtil {
    public static final ThreadFactory FACTORY = newDaemonThreadFactoryBuilder().setNameFormat("3arthh4ck-Thread-%d").build();

    public static ScheduledExecutorService newDaemonScheduledExecutor(String name) {
        ThreadFactoryBuilder factory = newDaemonThreadFactoryBuilder();
        factory.setNameFormat("3arthh4ck-" + name + "-%d");
        return Executors.newSingleThreadScheduledExecutor(factory.build());
    }

    public static ExecutorService newDaemonCachedThreadPool() {
        return Executors.newCachedThreadPool(FACTORY);
    }

    public static ExecutorService newFixedThreadPool(int size) {
        ThreadFactoryBuilder factory = newDaemonThreadFactoryBuilder();
        factory.setNameFormat("3arthh4ck-Fixed-%d");
        return Executors.newFixedThreadPool(Math.max(size, 1), factory.build());
    }

    public static ThreadFactoryBuilder newDaemonThreadFactoryBuilder() {
        ThreadFactoryBuilder factory = new ThreadFactoryBuilder();
        factory.setDaemon(true);
        return factory;
    }
}
