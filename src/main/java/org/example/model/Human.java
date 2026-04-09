package org.example.model;

import org.example.strategy.LandMovementStrategy;

public class Human extends Entity {
    private boolean isOnPlane = false;
    private boolean isAfterFlight = false;

    public Human(int x, int y) {
        super(x, y, new LandMovementStrategy());
    }

    public boolean getIsOnPlane() { return isOnPlane; }
    public void setIsOnPlane(boolean onPlane) { this.isOnPlane = onPlane; }
    public boolean getIsAfterFlight() { return isAfterFlight; }
    public void setIsAfterFlight(boolean afterFlight) { this.isAfterFlight = afterFlight; }
}