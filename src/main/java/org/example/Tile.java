package org.example;

import java.util.ArrayList;
import java.util.List;

public class Tile {

    ////[    VARIABLES    ]\\\\

    boolean isAirport=false;
    private ArrayList<Plane> planes =  new ArrayList<>();
    public boolean isLand=false;
    private int landModifier = 100;
    private List<Human> humans = new ArrayList<Human>();
    public List<Animal> animals = new ArrayList<Animal>();
    int posX,posY;

    ////[    CONSTRUCTORS    ]\\\\

    /**
     * Tile constructor, decides if the Tile should be Land
     * @param rgbVal Shade of grey value from Simplex noise, based on it decides whether the Tile is Land or Water
     * @param x Tile x position
     * @param y Tile y position
     */
    public Tile(int rgbVal,int x,int y){
      if (rgbVal <= landModifier)
      {
          isLand = true;
      }
      this.posX=x;
      this.posY=y;
    }

    /**
     * Default constructor, sets the Tile to water
     */
    public Tile(){
        isLand = false;
    }

    ////[    SETTERS    ]\\\\

    /**
     * creates an Airport on the Tile
     */
    public void setAirport(){isAirport = true;}

    ////[    GETTERS    ]\\\\

    /**
     * Gets people from the Tile
     * @return List of Human instances from the Tile
     */
    public List<Human> getPeople(){
        return humans;
    }

    /**
     * Gets animals from the Tile
     * @return List of Animal instances from the Tile
     */
    public List<Animal> getAnimals(){
        return animals;
    }
    /**
     * Gets planes from the Tile
     * @return List of Plane instances from the Tile
     */
    public ArrayList<Plane> getPlanes(){
        return planes;
    }
}
