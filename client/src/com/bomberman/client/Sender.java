package com.bomberman.client;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.bomberman.common.utils.EngineUtils.CLIENT_SENDER_DELAY;
import static java.lang.Thread.sleep;

public class Sender extends Thread {
    private String packet;
    private final AtomicBoolean isRunning;

    private final PrintWriter outputStream;

    public Sender(PrintWriter outputStream) {
        this.outputStream = outputStream;
        isRunning = new AtomicBoolean(true);
    }

    @Override
    public void run() {
        while(isRunning.get()) {
            try {
                if(packet != null) {
                    outputStream.println(packet);
                    packet = null;
                }
                sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    synchronized public void stopThread() {
        isRunning.set(false);
    }

    public void sendPacket(String packet) {
        this.packet = packet;
    }

    synchronized public boolean isRunning() {
        return isRunning.get();
    }
}
