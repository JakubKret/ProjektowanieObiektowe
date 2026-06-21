package org.example.model;

public interface BoardBuilder {
    void buildTerrain();
    void buildPopulation();
    void buildIslandsAndAirports();
    void buildPlanes();
    Board getResult();
}