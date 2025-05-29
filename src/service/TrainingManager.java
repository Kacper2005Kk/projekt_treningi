package service;

import model.Training;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Klasa zarządzająca listą treningów.
 * Odpowiada za operacje CRUD (Create, Read, Update, Delete), sortowanie oraz statystyki.
 */
public class TrainingManager {
    // Lista przechowująca wszystkie treningi
    private List<Training> trainings = new ArrayList<>();

    /**
     * Dodaje nowy trening do listy.
     *  t trening do dodania
     */
    public void addTraining(Training t) {
        trainings.add(t);
    }

    /**
     * Zwraca kopię listy wszystkich treningów.
     *  lista treningów
     */
    public List<Training> getAllTrainings() {
        return new ArrayList<>(trainings); // Zwracamy kopię, by nie dało się zmodyfikować oryginału z zewnątrz
    }

    /**
     * Usuwa trening po indeksie.
     * index indeks treningu do usunięcia
     *  true jeśli usunięto, false jeśli indeks nieprawidłowy
     */
    public boolean deleteTraining(int index) {
        if (index >= 0 && index < trainings.size()) {
            trainings.remove(index);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Aktualizuje trening pod wskazanym indeksem.
     *  index indeks treningu do edycji
     *  newTraining nowy obiekt treningu
     *  true jeśli edytowano, false jeśli indeks nieprawidłowy
     */
    public boolean updateTraining(int index, Training newTraining) {
        if (index >= 0 && index < trainings.size()) {
            trainings.set(index, newTraining);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sortuje listę treningów według daty (rosnąco).
     */
    public void sortByDate() {
        trainings.sort(Comparator.comparing(Training::getDate));
    }

    /**
     * Sortuje listę treningów według czasu trwania (rosnąco).
     */
    public void sortByDuration() {
        trainings.sort(Comparator.comparingInt(Training::getDuration));
    }

    /**
     * Oblicza sumę przebytych kilometrów ze wszystkich treningów.
     * return łączny dystans
     */
    public double totalDistance() {
        double sum = 0;
        for (Training t : trainings) {
            sum += t.getDistance();
        }
        return sum;
    }

    /**
     * Oblicza średni czas trwania treningu.
     * return średnia liczba minut
     */
    public double averageDuration() {
        if (trainings.isEmpty()) {
            return 0;
        }
        int total = 0;
        for (Training t : trainings) {
            total += t.getDuration();
        }
        return (double) total / trainings.size();
    }

    /**
     * Zwraca liczbę zapisanych treningów.
     * return rozmiar listy
     */
    public int size() {
        return trainings.size();
    }

    /**
     * Zwraca trening z podanego indeksu, lub null jeśli indeks nieprawidłowy.
     *  index indeks treningu
     * return obiekt Training lub null
     */
    public Training get(int index) {
        if (index >= 0 && index < trainings.size()) {
            return trainings.get(index);
        } else {
            return null;
        }
    }

    /**
     * Wyszukuje treningi po typie (np. "Bieganie", "Rower").
     *  type szukany typ treningu (ignoruje wielkość liter)
     * return lista pasujących treningów
     */
    public List<Training> searchByType(String type) {
        List<Training> result = new ArrayList<>();
        for (Training t : trainings) {
            if (t.getType().equalsIgnoreCase(type)) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Ustawia całą listę treningów (np. podczas wczytywania z pliku).
     *  list nowa lista treningów
     */
    public void setTrainings(List<Training> list) {
        trainings = list;
    }
}