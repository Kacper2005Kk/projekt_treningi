package model;

/**
 * Klasa reprezentująca pojedynczy trening sportowy.
 */
public class Training {
    private String type;
    private double distance; // w kilometrach
    private int duration;    // w minutach
    private String date;     // format np. "2025-05-12"

    public Training(String type, double distance, int duration, String date) {
        this.type = type;
        this.distance = distance;
        this.duration = duration;
        this.date = date;
    }

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

    @Override
    public String toString() {
        return type + "," + distance + "," + duration + "," + date;
    }

    public String toDisplayString() {
        return String.format("%-12s | %6.2f km | %4d min | %s", type, distance, duration, date);
    }
}
