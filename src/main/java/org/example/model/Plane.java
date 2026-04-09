package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.example.state.DeadState;

public class Plane {
    private int posX, posY;
    int[] move = new int[2];
    ArrayList<Island> islands;
    private boolean isMoving = false;
    public List<Human> humans = new ArrayList<Human>();
    Tile[][] board;
    int x;
    int y;

    public Plane(int posX, int posY, ArrayList<Island> islands, Tile[][] board) {
        this.posX = posX;
        this.posY = posY;
        this.islands = islands;
        this.board = board;
        calculateMove();
    }

    private void calculateMove() {
        if (!isMoving && !board[posX][posY].getHumans().isEmpty()) { isMoving = true; }
        if (isMoving) {
            Random random = new Random();
            int r = random.nextInt(islands.size());
            while (islands.get(r).getAirport().getPosX() == this.posX && islands.get(r).getAirport().getPosY() == this.posY) {
                r = random.nextInt(islands.size());
            }
            x = islands.get(r).getAirport().getPosX();
            y = islands.get(r).getAirport().getPosY();

            for (Human human : board[posX][posY].getHumans()) {
                if (!(human.getHealthState() instanceof DeadState) && !human.getIsAfterFlight() && !human.getIsOnPlane()) {
                    human.setIsOnPlane(true);
                    this.humans.add(human);
                }
            }
            for (Human human : this.humans) {
                board[posX][posY].removeEntity(human);
            }
            move[0] = (x - posX);
            move[1] = (y - posY);
        }
    }

    public void Move() {
        if (!isMoving && !board[posX][posY].getHumans().isEmpty()) { isMoving = true; }
        if (!humans.isEmpty()) { isMoving = true; }
        if (isMoving) {
            board[posX][posY].getPlanes().remove(this);
            posX = x;
            posY = y;
            if (posX == x && posY == y) {
                for (Human human : humans) {
                    human.setIsAfterFlight(true);
                    human.setIsOnPlane(false);
                    board[posX][posY].addEntity(human);
                }
                isMoving = false;
                humans.clear();
                calculateMove();
            }
            board[posX][posY].getPlanes().add(this);
        }
    }

    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
}