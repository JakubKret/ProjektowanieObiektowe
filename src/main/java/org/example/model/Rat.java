package org.example.model;

import org.example.strategy.LandMovementStrategy;

public class Rat extends Animal {
    public Rat(int x, int y) {
        super(x, y, new LandMovementStrategy());
    }
}