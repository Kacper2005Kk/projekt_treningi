package app;

import model.Training; // Klasa reprezentująca pojedynczy trening (typ, dystans, czas, data)
import service.TrainingManager; // Klasa zarządzająca listą treningów (dodawanie, usuwanie, sortowanie itp.)
import util.InputHelper; // Klasa pomocnicza do odczytu danych od użytkownika (zabezpieczenia i walidacja)

import java.io.*; // Obsługa operacji wejścia/wyjścia (np. zapis/odczyt pliku)
import java.util.ArrayList;
import java.util.List;

/**
 * Główna klasa aplikacji do zarządzania treningami.
 * Obsługuje menu, zapis i odczyt danych z pliku oraz wszystkie interakcje z użytkownikiem.
 */
public class TrainingApp {
    private static final String FILE_NAME = "trainings.csv"; // Nazwa pliku do zapisu/wczytywania danych
    private static TrainingManager manager = new TrainingManager(); // Obiekt zarządzający treningami

    // Liczniki operacji wykonywanych w czasie działania programu
    private static int addCount = 0;
    private static int searchCount = 0;
    private static int editCount = 0;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis(); // Pomiar czasu działania programu

        loadTrainingsFromFile(); // Wczytanie danych z pliku CSV do pamięci

        boolean running = true;
        while (running) {
            printMenu(); // Wyświetlenie opcji dla użytkownika
            String choice = InputHelper.readLine(""); // Pobranie wyboru użytkownika

            // Obsługa menu na podstawie wyboru
            switch (choice) {
                case "1": addTraining(); break; // Dodaj nowy trening
                case "2": displayTrainings(manager.getAllTrainings()); break; // Wyświetl wszystkie treningi
                case "3": manager.sortByDate(); displayTrainings(manager.getAllTrainings()); break; // Sortowanie po dacie
                case "4": manager.sortByDuration(); displayTrainings(manager.getAllTrainings()); break; // Sortowanie po czasie trwania
                case "5": searchByType(); break; // Wyszukiwanie treningów po typie
                case "6": editTraining(); break; // Edycja wybranego treningu
                case "7": deleteTraining(); break; // Usuwanie treningu
                case "8": showStatistics(); break; // Wyświetlenie statystyk (suma km, średni czas)
                case "9": 
                    saveTrainingsToFile(); // Zapis danych do pliku
                    running = false; // Zakończenie działania programu
                    long endTime = System.currentTimeMillis();
                    System.out.printf("Czas działania programu: %.2f sekundy\n",
                            (endTime - startTime) / 1000.0); // Wyświetlenie czasu działania
                    showOperationCounts(); // Wyświetlenie liczby wykonanych operacji
                    System.out.println("Zapisano dane. Kończę program."); 
                    break;
                default: System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }

    // Wyświetla menu główne
    private static void printMenu() {
        System.out.println("\n=== Opcje ===");
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

    // Dodaje nowy trening na podstawie danych od użytkownika
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

    // Wyświetla listę treningów w formie tabeli
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

    // Wyszukiwanie treningów po typie (np. bieganie, rower)
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

    // Edycja istniejącego treningu na liście
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

    // Usunięcie treningu z listy
    private static void deleteTraining() {
        displayTrainings(manager.getAllTrainings());
        int index = InputHelper.readPositiveInt("Podaj numer treningu do usunięcia: ") - 1;
        if (manager.deleteTraining(index)) {
            System.out.println("Usunięto trening.");
        } else {
            System.out.println("Nieprawidłowy numer.");
        }
    }

    // Pokazuje statystyki: łączny dystans i średni czas treningów
    private static void showStatistics() {
        System.out.printf("Suma kilometrów: %.2f km\n", manager.totalDistance());
        System.out.printf("Średni czas treningu: %.2f minut\n", manager.averageDuration());
    }

    // Wyświetla liczbę dodanych, edytowanych i wyszukanych treningów
    private static void showOperationCounts() {
        System.out.println("\n=== Liczba wykonanych operacji ===");
        System.out.printf("Dodano treningów: %d\n", addCount);
        System.out.printf("Wyszukano treningów: %d\n", searchCount);
        System.out.printf("Edytowano treningów: %d\n", editCount);
    }

    // Wczytuje treningi z pliku CSV przy uruchomieniu programu
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

    // Zapisuje treningi do pliku CSV przy zakończeniu działania programu
    private static void saveTrainingsToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Training t : manager.getAllTrainings()) {
                pw.println(t.toString()); // W toString() trening formatuje się do CSV
            }
        } catch (IOException e) {
            System.out.println("Błąd zapisu do pliku: " + e.getMessage());
        }
    }
}