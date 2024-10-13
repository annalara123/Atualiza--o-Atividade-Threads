import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final int TOTAL_THREADS = 20;
    public static final AtomicInteger sum = new AtomicInteger(0);
    public static final AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws IOException, InterruptedException {

        Path path = Paths.get("src/calibration.txt").toAbsolutePath();
        List<String> calibrations = Files.readAllLines(path);

        final int INTERVALO = Math.floorDiv(calibrations.size(), TOTAL_THREADS);
        CountDownLatch latch = new CountDownLatch(TOTAL_THREADS);

        ExecutorService executor = Executors.newFixedThreadPool(TOTAL_THREADS);

        long tempoInicial = System.currentTimeMillis();

        try {
            for (int i = 0; i < TOTAL_THREADS; i++) {
                int start = INTERVALO * i;
                int end = Math.min(start + INTERVALO, calibrations.size());

                if (start < end) {
                    Tarefa tarefa = new Tarefa(calibrations, start, end, latch);
                    executor.submit(tarefa);
                } else {
                    latch.countDown();
                }
            }

            latch.await();
        } finally {
            executor.shutdown();
        }

        System.out.println("A soma dos valores é: " + sum.get());
        System.out.println("Total de linhas: " + count.get());

        long tempoFinal = System.currentTimeMillis();
        System.out.printf("%.3f ms%n", (tempoFinal - tempoInicial) / 1000d);
    }
}
