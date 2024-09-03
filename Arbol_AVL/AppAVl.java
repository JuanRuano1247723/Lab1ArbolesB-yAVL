import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AppAVl {

    private String filePath;
    private ArbolAVL arbol;
    private PerformanceTesterAVL tester;
    private PerformanceTesterConsoleAVL testerc;

    public AppAVl(String filePath) {
        this.filePath = filePath;
        this.arbol = new ArbolAVL();
        this.tester = new PerformanceTesterAVL(arbol);
        this.testerc = new PerformanceTesterConsoleAVL(arbol);

        readAndProcessFile();
        testerc.showResults();
    }

    private void readAndProcessFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processLine(String line) {
        if (line.startsWith("Insert:")) {
            handleInsert(line.substring(7).trim());
        } else if (line.startsWith("Delete:")) {
            handleDelete(line.substring(7).trim());
        } else if (line.startsWith("Search:")) {
            handleSearch(line.substring(7).trim());
        } else {
            System.out.println("Unknown operation: " + line);
        }
    }

    private void handleInsert(String data) {
        String[] parts = data.replace("{", "").replace("}", "").split(",");
        int id = Integer.parseInt(parts[0].split(":")[1].trim());
        String nombre = parts[1].split(":")[1].trim().replace("\"", "");

        tester.measureInsert(id, nombre);
        testerc.measureInsert(id, nombre);
    }

    private void handleDelete(String data) {
        int id = Integer.parseInt(data.replace("{", "").replace("}", "").split(":")[1].trim());

        tester.measureDelete(id);
        testerc.measureDelete(id);
    }

    private void handleSearch(String data) {
        int id = Integer.parseInt(data.replace("{", "").replace("}", "").split(":")[1].trim());

        tester.measureSearch(id);
        testerc.measureSearch(id);
    }
}
