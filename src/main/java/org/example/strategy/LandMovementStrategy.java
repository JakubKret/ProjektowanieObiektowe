package org.example.strategy;

import org.example.model.Entity;
import org.example.model.Tile;
import java.util.Random;

public class LandMovementStrategy implements MovementStrategy {
    private final Random rand = new Random();

    @Override
    public void move(Entity entity, Tile[][] board, int width, int height) {
        int posX = entity.getPosX();
        int posY = entity.getPosY();

        int dx = rand.nextInt(3) - 1; // -1, 0, 1
        int dy = rand.nextInt(3) - 1; // -1, 0, 1

        int newX = posX + dx;
        int newY = posY + dy;

        //obsluga granicy mapy
        if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
            if (board[newX][newY].isLand()) {
                entity.setPos(newX, newY);
            }
        }
    }
}