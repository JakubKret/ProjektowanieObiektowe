package org.example.model;

import java.util.ArrayList;

public class Island {
    private ArrayList<Tile> islandLand;
    private Tile airport;

    public Island(ArrayList<Tile> islandTiles) {
        this.islandLand = islandTiles;
    }

    public void setAirport(Tile tile) {
        airport = tile;
    }

    public ArrayList<Tile> getIslandLand() {
        return this.islandLand;
    }

    public Tile getAirport() {
        return airport;
    }
}