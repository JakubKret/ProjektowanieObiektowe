package org.example.model;

public class SimulationDirector {
    private BoardBuilder builder;

    public SimulationDirector(BoardBuilder builder) {
        this.builder = builder;
    }

    public Board constructStandardBoard() {
        builder.buildTerrain();
        builder.buildPopulation();
        builder.buildIslandsAndAirports();
        builder.buildPlanes();
        return builder.getResult();
    }
}