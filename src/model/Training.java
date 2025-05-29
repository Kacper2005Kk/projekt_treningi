package model;

/**
 * Klasa reprezentująca pojedynczy trening sportowy.
 * Przechowuje dane takie jak typ treningu, dystans, czas trwania oraz datę.
 * Używana w aplikacji do zarządzania listą treningów.
 */
public class Training {
    private String type;      // Typ treningu, np. "Bieganie", "Rower"
    private double distance;  // Dystans w kilometrach
    private int duration;     // Czas trwania w minutach
    private String date;      // Data treningu w formacie "YYYY-MM-DD"

    /**
     * Konstruktor klasy Training – tworzy nowy trening na podstawie podanych danych.
     */
    public Training(String type, double distance, int duration, String date) {
        this.type = type;
        this.distance = distance;
        this.duration = duration;
        this.date = date;
    }

    // Gettery – umożliwiają dostęp do poszczególnych pól obiektu treningu
    public String getType() {
        return type;
    }

    public double getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public String getDate() {
        return date;
    }

    /**
     * Zwraca dane treningu w formacie CSV, np. "Bieganie,5.0,30,2025-05-12".
     * Używane podczas zapisu do pliku.
     */
    @Override
    public String toString() {
        return type + "," + distance + "," + duration + "," + date;
    }

    /**
     * Zwraca dane treningu w formacie czytelnym dla użytkownika,
     * np. "Bieganie     |   5.00 km |   30 min | 2025-05-12".
     * Używane podczas wyświetlania w konsoli.
     */
    public String toDisplayString() {
        return String.format("%-12s | %6.2f km | %4d min | %s", type, distance, duration, date);
    }
}
