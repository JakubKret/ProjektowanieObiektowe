package org.example.model;

import org.example.strategy.UniversalMovementStrategy;

public class Bat extends Animal {
    public Bat(int x, int y) {
        super(x, y, new UniversalMovementStrategy());
    }
}