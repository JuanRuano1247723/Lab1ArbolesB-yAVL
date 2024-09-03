import java.util.Scanner;


public class MasterApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Probar árboles");
            System.out.println("2. Salir");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Ingrese el grado par los árboles B, B+ y B*: ");
                    int grado = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Ingrese la dirección del archivo para leer: ");
                    String filePath = scanner.nextLine();
                    new AppAVl(filePath);
                    new AppB(filePath, grado);
                    new AppBM(filePath, grado);
                    new AppBAste(filePath, grado);
                    break;
                case 2:
                    System.out.println("Saliendo...");
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
            }
        }

        scanner.close();
    
    }
}    

