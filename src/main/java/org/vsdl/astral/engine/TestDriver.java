package org.vsdl.astral.engine;

import org.vsdl.astral.util.AALogger;

public class TestDriver {

    public static final int KERNEL_PROCESS_ID = 1;

    public static void main(String[] args) throws InterruptedException {
        Kernel kernel = Kernel.getInstance();
        kernel.start();
        Thread.sleep(1000);
        kernel.terminate();
    }
}
