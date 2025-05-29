package util;

import java.util.Scanner;

/**
 * Klasa pomocnicza do pobierania danych od użytkownika.
 * Zawiera metody do wczytywania tekstu, liczb całkowitych i zmiennoprzecinkowych z walidacją.
 */
public class InputHelper {
    // Obiekt Scanner używany do odczytu danych z konsoli
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Wczytuje od użytkownika liczbę zmiennoprzecinkową większą lub równą 0.
     * W razie błędu (nieprawidłowy format lub liczba ujemna), prosi o ponowne podanie wartości.
     * prompt komunikat dla użytkownika
     *  poprawna liczba typu double
     */
    public static double readPositiveDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Błąd: wartość musi być nieujemna.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Błąd: podano nieprawidłową liczbę.");
            }
        }
    }

    /**
     * Wczytuje od użytkownika liczbę całkowitą większą lub równą 0.
     * W razie błędu (nieprawidłowy format lub liczba ujemna), prosi o ponowne podanie wartości.
     *  prompt komunikat dla użytkownika
     *  poprawna liczba typu int
     */
    public static int readPositiveInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Błąd: wartość musi być nieujemna.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Błąd: podano nieprawidłową liczbę.");
            }
        }
    }

    /**
     * Wczytuje linię tekstu od użytkownika.
     * prompt komunikat dla użytkownika
     *  wprowadzony tekst
     */
    public static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}