package org.example.model;

public class EntityFactory {

    public static Human createHuman(int x, int y) {
        return new Human(x, y);
    }

    public static Animal createRat(int x, int y) {
        return new Rat(x, y);
    }

    public static Animal createBat(int x, int y) {
        return new Bat(x, y);
    }
}