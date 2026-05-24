package org.example.model;

public class SimulationConfig {
    private static SimulationConfig instance;

    public final int speed;
    public final double contagiousness;
    public final double deathRate;
    public final double healRate;
    public final int delay;
    public final double density;
    public final double scale;
    public final int maxPeoplePerTile;
    public final int maxAnimalsPerTile;
    public int iter;

    private SimulationConfig(int speed, double contagiousness, double deathRate, double healRate,
                             int delay, double density, double scale, int maxPeople, int maxAnimals, int iter) {
        this.speed = speed;
        this.contagiousness = contagiousness / 100.0;
        this.deathRate = deathRate / 100.0;
        this.healRate = healRate / 100.0;
        this.delay = delay;
        this.density = density / 100.0;
        this.scale = scale / 10.0;
        this.maxPeoplePerTile = maxPeople;
        this.maxAnimalsPerTile = maxAnimals;
        this.iter = iter;
    }

    public static void initialize(int speed, double contagiousness, double deathRate, double healRate,
                                  int delay, double density, double scale, int maxPeople, int maxAnimals, int iter) {
        if (instance == null) {
            instance = new SimulationConfig(speed, contagiousness, deathRate, healRate, delay, density, scale, maxPeople, maxAnimals, iter);
        }
    }

    public static SimulationConfig getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Konfiguracja nie została zainicjowana!");
        }
        return instance;
    }
}