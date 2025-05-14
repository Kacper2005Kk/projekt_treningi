package service;



import model.Training;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Klasa zarządzająca listą treningów (CRUD, sortowanie, statystyki).
 */
public class TrainingManager {
    private List<Training> trainings = new ArrayList<>();

    /**
     * Dodaje nowy trening do listy.
     * @param t trening do dodania
     */
    public void addTraining(Training t) {
        trainings.add(t);
    }

    /**
     * Zwraca listę wszystkich treningów.
     * @return lista treningów
     */
    public List<Training> getAllTrainings() {
        return new ArrayList<>(trainings);
    }

    public boolean deleteTraining(int index) {
        if (index >= 0 && index < trainings.size()) {
            trainings.remove(index);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateTraining(int index, Training newTraining) {
        if (index >= 0 && index < trainings.size()) {
            trainings.set(index, newTraining);
            return true;
        } else {
            return false;
        }
    }

    public void sortByDate() {
        trainings.sort(Comparator.comparing(Training::getDate));
    }

    public void sortByDuration() {
        trainings.sort(Comparator.comparingInt(Training::getDuration));
    }

    public double totalDistance() {
        double sum = 0;
        for (Training t : trainings) {
            sum += t.getDistance();
        }
        return sum;
    }

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

    public int size() {
        return trainings.size();
    }

    public Training get(int index) {
        if (index >= 0 && index < trainings.size()) {
            return trainings.get(index);
        } else {
            return null;
        }
    }

    public List<Training> searchByType(String type) {
        List<Training> result = new ArrayList<>();
        for (Training t : trainings) {
            if (t.getType().equalsIgnoreCase(type)) {
                result.add(t);
            }
        }
        return result;
    }

    public void setTrainings(List<Training> list) {
        trainings = list;
    }
}
