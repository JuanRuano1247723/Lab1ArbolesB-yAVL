import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        String filePath = "operaciones.txt";
        ArbolAVL arbol = new ArbolAVL(2);
        PerformanceTester tester = new PerformanceTester(arbol);
        readAndProcessFile(filePath, tester);
    }

    private static void readAndProcessFile(String filePath, PerformanceTester tester) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                processLine(line, tester);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processLine(String line, PerformanceTester tester) {
        if (line.startsWith("Insert:")) {
            handleInsert(line.substring(7).trim(), tester);
        } else if (line.startsWith("Delete:")) {
            handleDelete(line.substring(7).trim(), tester);
        } else if (line.startsWith("Search:")) {
            handleSearch(line.substring(7).trim(), tester);
        } else {
            System.out.println("Unknown operation: " + line);
        }
    }

    private static void handleInsert(String data, PerformanceTester tester) {
        String[] parts = data.replace("{", "").replace("}", "").split(",");
        int id = Integer.parseInt(parts[0].split(":")[1].trim());
        String nombre = parts[1].split(":")[1].trim().replace("\"", "");

        tester.measureInsert(id, nombre);
        System.out.println("Inserting: ID = " + id + ", Nombre = " + nombre);
    }

    private static void handleDelete(String data, PerformanceTester tester) {
        int id = Integer.parseInt(data.replace("{", "").replace("}", "").split(":")[1].trim());

        tester.measureDelete(id);
        System.out.println("Deleting: ID = " + id);
    }

    private static void handleSearch(String data, PerformanceTester tester) {
        int id = Integer.parseInt(data.replace("{", "").replace("}", "").split(":")[1].trim());

        tester.measureSearch(id);
        System.out.println("Searching: ID = " + id);
    }
}
