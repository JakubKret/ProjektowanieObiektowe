package org.example;

import java.util.ArrayList;

public class Island {

    ////[    VARIABLES    ]\\\\
    private ArrayList<Tile> islandLand;
    private Tile airport;

    ////[    METHODS    ]\\\\

    /**
     * Island constructor
     * @param islandTiles List of Land Tiles
     */
    public Island(ArrayList<Tile> islandTiles)
    {
        this.islandLand = islandTiles;
    }

    ////[    SETTERS    ]\\\\

    /**
     * Sets an airport
     * @param tile Tile which becomes the airport
     */
    public void setAirport(Tile tile){//Tile tile
        airport =tile;
    }

    ////[    GETTERS    ]\\\\

    /**
     * Gets all Island Tiles
     * @return List of land Tiles of the Island
     */
    public ArrayList<Tile> getIslandLand(){
        return this.islandLand;
    }

    /**
     * Gets the airport
     * @return airport Tile
     */
    public /*int[]*/Tile getAirport(){
        return airport;
    }
}
