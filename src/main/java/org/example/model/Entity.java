package org.example.model;

import org.example.state.HealthState;
import org.example.state.HealthyState;
import org.example.strategy.MovementStrategy;
import java.util.Random;

public abstract class Entity {
    protected int posX, posY;
    protected double health = 40.0;
    protected double immunity;

    protected HealthState healthState;
    protected MovementStrategy movementStrategy;

    public Entity(int x, int y, MovementStrategy movementStrategy) {
        this.posX = x;
        this.posY = y;
        this.movementStrategy = movementStrategy;
        this.healthState = new HealthyState();
        this.immunity = new Random().nextDouble();
    }

    public void move(Tile[][] board, int width, int height) {
        movementStrategy.move(this, board, width, height);
    }

    public void getExposed(double contagiousness) { healthState.handleExposure(this, contagiousness); }
    public void receiveTreatment(double healRate, double contagiousness) { healthState.handleTreatment(this, healRate, contagiousness); }
    public void passTurn(double deathRate) { healthState.passTurn(this, deathRate); }

    public void changeState(HealthState newState) { this.healthState = newState; }
    public void decreaseHealth(double amount) { this.health -= amount; }
    public void increaseHealth(double amount) { this.health += amount; }
    public double getHealth() { return health; }
    public double getImmunity() { return immunity; }
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public void setPos(int x, int y) { this.posX = x; this.posY = y; }
    public HealthState getHealthState() { return healthState; }
}