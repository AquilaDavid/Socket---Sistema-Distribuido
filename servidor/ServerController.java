package servidor;

import java.util.concurrent.atomic.AtomicBoolean;

public class ServerController {
    private final AtomicBoolean running = new AtomicBoolean(true);

    public boolean isRunning() {
        return running.get();
    }

    public void stop() {
        running.set(false);
    }
}