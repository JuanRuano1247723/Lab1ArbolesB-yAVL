import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PerformanceTester {
    private static final String OUTPUT_FILE = "performance_results.txt";
    private ArbolBM arbol;

    public PerformanceTester(ArbolBM arbol) {
        this.arbol = arbol;
    }

    public void measureInsert(int id, String nombre) {
        long startTime = System.nanoTime();
        arbol.insert(id, nombre);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        logResult(id, "Insert", duration);
    }

    public void measureDelete(int id) {
        long startTime = System.nanoTime();
        arbol.deleteB(id);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        logResult(id, "Delete", duration);
    }

    public void measureSearch(int id) {
        long startTime = System.nanoTime();
        arbol.searchB(id);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        logResult(id, "Search", duration);
    }

    private void logResult(int id, String action, long duration) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String timeStamp = dtf.format(now);

        String logEntry = String.format("%s,%d,%s,%d%n", timeStamp, id, action, duration);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE, true))) {
            writer.write(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}