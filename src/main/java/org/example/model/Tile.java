package org.example.model;

import org.example.state.DeadState;
import org.example.state.InfectedState;
import java.util.ArrayList;
import java.util.List;

public class Tile {
    private boolean isAirport = false;
    private ArrayList<Plane> planes = new ArrayList<>();
    private boolean isLand = false;
    private final int posX, posY;

    private List<Entity> entities = new ArrayList<>();

    public Tile(int rgbVal, int x, int y) {
        if (rgbVal <= 100) { isLand = true; }
        this.posX = x;
        this.posY = y;
    }

    public void setAirport() { isAirport = true; }
    public void addEntity(Entity e) { entities.add(e); }
    public void removeEntity(Entity e) { entities.remove(e); }

    public List<Entity> getEntities() { return entities; }
    public List<Human> getHumans() {
        List<Human> humans = new ArrayList<>();
        for(Entity e : entities) if(e instanceof Human) humans.add((Human)e);
        return humans;
    }
    public ArrayList<Plane> getPlanes() { return planes; }
    public boolean isLand() { return isLand; }
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }

    public int getTileRGBColor(int maxPeoplePerTile) {
        if (!planes.isEmpty()) return 255 << 16 | 255 << 8 | 0; // Żółty samolot

        if (!isLand) {
            for(Entity e : entities) if(e instanceof Bat) return 165 << 16 | 42 << 8 | 42;
            return (0 << 16) | (0 << 8) | 255; // Niebieska woda
        }

        if (entities.isEmpty()) return (0 << 16) | (255 << 8) | 0; // Zielony ląd

        int totalHumans = 0, infected = 0, dead = 0, rats = 0, bats = 0;
        for (Entity e : entities) {
            if (e instanceof Human) {
                totalHumans++;
                if (e.getHealthState() instanceof DeadState) dead++;
                else if (e.getHealthState() instanceof InfectedState) infected++;
            } else if (e instanceof Rat) rats++;
            else if (e instanceof Bat) bats++;
        }

        if (dead > 0 && dead == totalHumans && totalHumans > 0) return 0 << 16 | 0 << 8 | 0; // Czarny

        if (totalHumans > 0) {
            if (infected == 0) {
                int value = (int)(256 - (totalHumans * 256.0 / (2 * maxPeoplePerTile)));
                return (value << 16) | (value << 8) | value;
            } else {
                double ratio = Math.min((double) infected / (totalHumans - dead), 1.0);
                return ((int)(ratio * 255) << 16) | (0 << 8) | 0; // Odcienie czerwieni
            }
        }

        if (bats >= rats && bats > 0) return 165 << 16 | 42 << 8 | 42; // Brązowy (Nietoperze)
        if (rats > 0) return 128 << 16 | 0 << 8 | 128; // Fioletowy (Szczury)

        return (0 << 16) | (255 << 8) | 0; // Zabezpieczenie (Zielony)
    }
}