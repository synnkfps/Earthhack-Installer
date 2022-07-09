package impl;

import impl.util.ThreadUtil;

import java.util.concurrent.ExecutorService;

public interface GlobalExecutor {
    ExecutorService EXECUTOR = ThreadUtil.newDaemonCachedThreadPool();
    ExecutorService FIXED_EXECUTOR = ThreadUtil.newFixedThreadPool((int)((double)Runtime.getRuntime().availableProcessors() / 1.5D));
}
