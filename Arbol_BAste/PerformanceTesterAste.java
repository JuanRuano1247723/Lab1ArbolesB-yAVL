import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class PerformanceTesterAste {
    private static final String OUTPUT_FILE = "performance_results_logs(new3).txt";
    private ArbolAste arbol;

    public PerformanceTesterAste(ArbolAste arbol) {
        this.arbol = arbol;
    }

    public void measureInsert(int id, String nombre) {
        LocalDateTime startTime = LocalDateTime.now();
        long startNano = System.nanoTime();
        
        arbol.insert(id, nombre);
        
        long endNano = System.nanoTime();
        LocalDateTime endTime = LocalDateTime.now();
        long duration = endNano - startNano;
        logInsertResult(startTime, endTime, duration, id, nombre);
    }

    public void measureDelete(int id) {
        LocalDateTime startTime = LocalDateTime.now();
        long startNano = System.nanoTime();
        
        NodeAste foundNode = arbol.searchB(id);
        boolean found = foundNode != null;
        String name = "";
        if (found) {
        for (int i = 0; i < foundNode.getNumKeys(); i++) {
                if (foundNode.getId(i) == id) {
                    name = foundNode.getValor(i);
                    break;
                }
            }
        }
        
        arbol.deleteB(id);
        
        long endNano = System.nanoTime();
        LocalDateTime endTime = LocalDateTime.now();
        long duration = endNano - startNano;
        logDeleteResult(startTime, endTime, duration, id, found, name);
    }

    public void measureSearch(int id) {
        LocalDateTime startTime = LocalDateTime.now();
        long startNano = System.nanoTime();
        
        NodeAste foundNode = arbol.searchB(id);
        boolean found = foundNode != null;
        String name = "";
        if (found) {
            for (int i = 0; i < foundNode.getNumKeys(); i++) {
                if (foundNode.getId(i) == id) {
                    name = foundNode.getValor(i);
                    break;
                }
            }
        }
        
        long endNano = System.nanoTime();
        LocalDateTime endTime = LocalDateTime.now();
        long duration = endNano - startNano;
        logSearchResult(startTime, endTime, duration, id, found, name);
    }

    private void logInsertResult(LocalDateTime startTime, LocalDateTime endTime, long duration, int id, String nombre) {
        String startTimeFormatted = formatDateTime(startTime);
        String endTimeFormatted = formatDateTime(endTime);
        
        String logEntry = String.format("Insert: %s, %s, %d ns, ID: %d, Nombre: %s%n",
                startTimeFormatted, endTimeFormatted, duration, id, nombre);

        writeLog(logEntry);
    }

    private void logDeleteResult(LocalDateTime startTime, LocalDateTime endTime, long duration, int id, boolean found, String nombre) {
        String startTimeFormatted = formatDateTime(startTime);
        String endTimeFormatted = formatDateTime(endTime);
        
        String logEntry = String.format("Delete: %s, %s, %d ns, ID: %d, Encontrado: %s, Nombre: %s%n",
                startTimeFormatted, endTimeFormatted, duration, id, found ? "Sí" : "No", nombre);

        writeLog(logEntry);
    }

    private void logSearchResult(LocalDateTime startTime, LocalDateTime endTime, long duration, int id, boolean found, String nombre) {
        String startTimeFormatted = formatDateTime(startTime);
        String endTimeFormatted = formatDateTime(endTime);
        
        String logEntry = String.format("Search: %s, %s, %d ns, ID: %d, Encontrado: %s, Nombre: %s%n",
                startTimeFormatted, endTimeFormatted, duration, id, found ? "Sí" : "No", nombre);

        writeLog(logEntry);
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dtf.format(dateTime);
    }

    private void writeLog(String logEntry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE, true))) {
            writer.write(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}