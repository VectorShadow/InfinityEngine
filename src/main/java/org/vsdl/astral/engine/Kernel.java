package org.vsdl.astral.engine;

import org.vsdl.astral.util.AALogger;
import org.vsdl.astral.util.LogLevel;

import static org.vsdl.astral.engine.TestDriver.KERNEL_PROCESS_ID;

public class Kernel extends Thread {

    private static final int TURN = 125;

    private static Kernel instance;

    private static AALogger logger;

    private int turnCounter = 0;

    private boolean isRunning = false;

    private Kernel() {}

    public static Kernel getInstance() {
        if (instance == null) {
            instance = new Kernel();
            logger = AALogger.getLogger();
            logger.initialize();
            logger.mapOriginID(KERNEL_PROCESS_ID, "Kernel");
        }
        return instance;
    }

    @Override
    public void run() {
        startUp();
    }

    private void startUp() {
        isRunning = true;
        logger.log(KERNEL_PROCESS_ID, LogLevel.INFO, "Starting kernel...");
        loop();
    }

    private void executeTurn() {
        long now = System.currentTimeMillis();
        long next = now + TURN;
        //todo - stuff!
        ++turnCounter;
        now = System.currentTimeMillis();
        long timeout = next - now;
        if (timeout > 0) {
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                logger.log(KERNEL_PROCESS_ID, LogLevel.CORE_FAIL, "Interrupted thread during turn execution:" + e);
                System.exit(-1);
            }
        } else {
            logger.log(
                    KERNEL_PROCESS_ID,
                    LogLevel.WARN,
                    "Turn " + turnCounter + " execution exceeded 125ms: " + (-timeout));
        }
    }

    private void loop() {
        do {
            executeTurn();
        } while (isRunning);
        shutdown();
    }

    private void shutdown() {
        //todo - stuff?
        logger.log(
                KERNEL_PROCESS_ID,
                LogLevel.INFO,
                "Shutdown successfully after " + turnCounter + " turns.");
        logger.close();
        System.exit(0);
    }

    public void terminate() {
        isRunning = false;
    }
}
