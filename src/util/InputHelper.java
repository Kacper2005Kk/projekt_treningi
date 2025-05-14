package util;

import java.util.Scanner;

/**
 * Klasa pomocnicza do pobierania danych od użytkownika.
 */
public class InputHelper {
    private static Scanner scanner = new Scanner(System.in);

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

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}