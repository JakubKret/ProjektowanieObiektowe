package org.example.strategy;

import org.example.model.Entity;
import org.example.model.Tile;

public interface MovementStrategy {
    void move(Entity entity, Tile[][] board, int width, int height);
}