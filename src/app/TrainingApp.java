package app;

import model.Training;
import service.TrainingManager;
import util.InputHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TrainingApp {
    private static final String FILE_NAME = "trainings.csv";
    private static TrainingManager manager = new TrainingManager();

    private static int addCount = 0;
    private static int searchCount = 0;
    private static int editCount = 0;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        loadTrainingsFromFile();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = InputHelper.readLine("");

            switch (choice) {
                case "1": addTraining(); break;
                case "2": displayTrainings(manager.getAllTrainings()); break;
                case "3": manager.sortByDate(); displayTrainings(manager.getAllTrainings()); break;
                case "4": manager.sortByDuration(); displayTrainings(manager.getAllTrainings()); break;
                case "5": searchByType(); break;
                case "6": editTraining(); break;
                case "7": deleteTraining(); break;
                case "8": showStatistics(); break;
                case "9": 
                    saveTrainingsToFile(); 
                    running = false;
                    long endTime = System.currentTimeMillis();
                    System.out.printf("Czas działania programu: %.2f sekundy\n",
                            (endTime - startTime) / 1000.0);
                    showOperationCounts();
                    System.out.println("Zapisano dane. Kończę program."); 
                    break;
                default: System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1. Dodaj trening");
        System.out.println("2. Wyświetl wszystkie treningi");
        System.out.println("3. Posortuj treningi po dacie");
        System.out.println("4. Posortuj treningi po czasie trwania");
        System.out.println("5. Szukaj treningów po typie");
        System.out.println("6. Edytuj trening");
        System.out.println("7. Usuń trening");
        System.out.println("8. Wyświetl statystyki");
        System.out.println("9. Zapisz i zakończ");
        System.out.print("Wybierz opcję: ");
    }

    private static void addTraining() {
        String type = InputHelper.readLine("Typ treningu: ");
        double distance = InputHelper.readPositiveDouble("Dystans (km): ");
        int duration = InputHelper.readPositiveInt("Czas (min): ");
        String date = InputHelper.readLine("Data (YYYY-MM-DD): ");

        Training t = new Training(type, distance, duration, date);
        manager.addTraining(t);
        addCount++;
        System.out.println("Dodano trening.");
    }

    private static void displayTrainings(List<Training> list) {
        if (list.isEmpty()) {
            System.out.println("Brak treningów.");
        } else {
            System.out.printf("%-3s %-12s | %6s | %6s | %s\n", "Lp", "Typ", "Dystans", "Czas", "Data");
            int i = 1;
            for (Training t : list) {
                System.out.printf("%-3d %s\n", i, t.toDisplayString());
                i++;
            }
        }
    }

    private static void searchByType() {
        String type = InputHelper.readLine("Podaj typ treningu do wyszukania: ");
        List<Training> result = manager.searchByType(type);
        displayTrainings(result);
        if (result.isEmpty()) {
            System.out.println("Brak wyników dla podanego typu.");
        } else {
            System.out.printf("Znaleziono %d trening(ów).\n", result.size());
        }
        searchCount++;
    }

    private static void editTraining() {
        displayTrainings(manager.getAllTrainings());
        int index = InputHelper.readPositiveInt("Podaj numer treningu do edycji: ") - 1;
        if (manager.get(index) != null) {
            System.out.println("Wprowadź nowe dane:");
            String type = InputHelper.readLine("Typ treningu: ");
            double distance = InputHelper.readPositiveDouble("Dystans (km): ");
            int duration = InputHelper.readPositiveInt("Czas (min): ");
            String date = InputHelper.readLine("Data (YYYY-MM-DD): ");
            Training newT = new Training(type, distance, duration, date);
            manager.updateTraining(index, newT);
            editCount++;
            System.out.println("Trening zaktualizowany.");
        } else {
            System.out.println("Nieprawidłowy numer.");
        }
    }

    private static void deleteTraining() {
        displayTrainings(manager.getAllTrainings());
        int index = InputHelper.readPositiveInt("Podaj numer treningu do usunięcia: ") - 1;
        if (manager.deleteTraining(index)) {
            System.out.println("Usunięto trening.");
        } else {
            System.out.println("Nieprawidłowy numer.");
        }
    }

    private static void showStatistics() {
        System.out.printf("Suma kilometrów: %.2f km\n", manager.totalDistance());
        System.out.printf("Średni czas treningu: %.2f minut\n", manager.averageDuration());
    }

    private static void showOperationCounts() {
        System.out.println("\n=== Liczba wykonanych operacji ===");
        System.out.printf("Dodano treningów: %d\n", addCount);
        System.out.printf("Wyszukano treningów: %d\n", searchCount);
        System.out.printf("Edytowano treningów: %d\n", editCount);
    }

    private static void loadTrainingsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("Brak pliku danych. Rozpoczynamy z pustą listą.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<Training> list = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String type = parts[0];
                    double distance = Double.parseDouble(parts[1]);
                    int duration = Integer.parseInt(parts[2]);
                    String date = parts[3];
                    list.add(new Training(type, distance, duration, date));
                }
            }
            manager.setTrainings(list);
            System.out.println("Wczytano treningi z pliku.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Błąd podczas wczytywania pliku: " + e.getMessage());
        }
    }

    private static void saveTrainingsToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Training t : manager.getAllTrainings()) {
                pw.println(t.toString());
            }
        } catch (IOException e) {
            System.out.println("Błąd zapisu do pliku: " + e.getMessage());
        }
    }
}