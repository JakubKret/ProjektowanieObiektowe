package org.example.model;

import org.example.strategy.MovementStrategy;

public abstract class Animal extends Entity {
    public Animal(int x, int y, MovementStrategy strategy) {
        super(x, y, strategy);
    }
}