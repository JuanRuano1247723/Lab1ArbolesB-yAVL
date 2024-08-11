import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PerformanceTesterConsole {
    private static final String OUTPUT_FILE = "performance_results3.txt";
    private ArbolBM arbol;

    private Map<String, List<Long>> operationTimes;

    public PerformanceTesterConsole(ArbolBM arbol) {
        this.arbol = arbol;
        this.operationTimes = new HashMap<>();
        operationTimes.put("Insert", new ArrayList<>());
        operationTimes.put("Delete", new ArrayList<>());
        operationTimes.put("Search", new ArrayList<>());
    }

    public void measureInsert(int id, String nombre) {
        long startTime = System.nanoTime();
        arbol.insert(id, nombre);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        operationTimes.get("Insert").add(duration);
        logResult(id, "Insert", duration);
    }

    public void measureDelete(int id) {
        long startTime = System.nanoTime();
        arbol.deleteB(id);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        operationTimes.get("Delete").add(duration);
        logResult(id, "Delete", duration);
    }

    public void measureSearch(int id) {
        long startTime = System.nanoTime();
        arbol.searchB(id);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        operationTimes.get("Search").add(duration);
        logResult(id, "Search", duration);
    }

    public void showResults() {
        showTop10();
        showAverageTimes();
        showTotalTimes();
    }

    private void showTop10() {
        System.out.println("Top 10 Times for Each Operation:");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE, true))) {
            writer.write("Top 10 Times for Each Operation:\n");
            for (String operation : operationTimes.keySet()) {
                List<Long> times = operationTimes.get(operation);
                times.sort(Collections.reverseOrder());
                List<Long> top10 = times.size() > 10 ? times.subList(0, 10) : times;

                System.out.println(operation + ": " + top10);
                writer.write(operation + ": " + top10 + "\n");
            }
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAverageTimes() {
        System.out.println("Average Times for Each Operation:");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE, true))) {
            writer.write("Average Times for Each Operation:\n");
            for (String operation : operationTimes.keySet()) {
                List<Long> times = operationTimes.get(operation);
                long sum = times.stream().mapToLong(Long::longValue).sum();
                double average = times.size() > 0 ? (double) sum / times.size() : 0;

                System.out.printf("%s: %.2f ns\n", operation, average);
                writer.write(String.format("%s: %.2f ns\n", operation, average));
            }
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showTotalTimes() {
        System.out.println("Total Times for Each Operation:");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE, true))) {
            writer.write("Total Times for Each Operation:\n");
            for (String operation : operationTimes.keySet()) {
                List<Long> times = operationTimes.get(operation);
                long total = times.stream().mapToLong(Long::longValue).sum();

                System.out.printf("%s: %d ns\n", operation, total);
                writer.write(String.format("%s: %d ns\n", operation, total));
            }
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
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