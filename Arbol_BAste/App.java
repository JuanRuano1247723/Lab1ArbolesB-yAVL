import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        String filePath = "new 3.txt";
        ArbolAste arbol = new ArbolAste(2);
        PerformanceTester tester = new PerformanceTester(arbol);
        PerformanceTesterConsole testerc = new PerformanceTesterConsole(arbol);

        readAndProcessFile(filePath, tester,testerc);
        testerc.showResults();
    }

    private static void readAndProcessFile(String filePath, PerformanceTester tester, PerformanceTesterConsole testerc) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                processLine(line, tester, testerc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processLine(String line, PerformanceTester tester, PerformanceTesterConsole testerc) {
        if (line.startsWith("Insert:")) {
            handleInsert(line.substring(7).trim(), tester, testerc);
        } else if (line.startsWith("Delete:")) {
            handleDelete(line.substring(7).trim(), tester, testerc);
        } else if (line.startsWith("Search:")) {
            handleSearch(line.substring(7).trim(), tester, testerc);
        } else {
            System.out.println("Unknown operation: " + line);
        }
    }

    private static void handleInsert(String data, PerformanceTester tester, PerformanceTesterConsole testerc) {
        String[] parts = data.replace("{", "").replace("}", "").split(",");
        int id = Integer.parseInt(parts[0].split(":")[1].trim());
        String nombre = parts[1].split(":")[1].trim().replace("\"", "");

        tester.measureInsert(id, nombre);
        testerc.measureInsert(id, nombre);
        System.out.println("Inserting: ID = " + id + ", Nombre = " + nombre);
    }

    private static void handleDelete(String data, PerformanceTester tester, PerformanceTesterConsole testerc) {
        int id = Integer.parseInt(data.replace("{", "").replace("}", "").split(":")[1].trim());

        tester.measureDelete(id);
        testerc.measureDelete(id);
        System.out.println("Deleting: ID = " + id);
    }

    private static void handleSearch(String data, PerformanceTester tester, PerformanceTesterConsole testerc) {
        int id = Integer.parseInt(data.replace("{", "").replace("}", "").split(":")[1].trim());

        tester.measureSearch(id);
        testerc.measureSearch(id);
        System.out.println("Searching: ID = " + id);
    }
}
