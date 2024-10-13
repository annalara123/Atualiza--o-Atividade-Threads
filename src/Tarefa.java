import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Tarefa implements Runnable {
    private final List<String> calibrations;
    private final int start;
    private final int end;
    private final CountDownLatch latch;

    public Tarefa(List<String> calibrations, int start, int end, CountDownLatch latch) {
        this.calibrations = calibrations;
        this.start = start;
        this.end = end;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            for (int i = start; i < end; i++) {
                Main.sum.getAndAdd(Calibration.value(calibrations.get(i)));
                Main.count.getAndIncrement();
            }
        } finally {
            latch.countDown();
        }
    }
}
