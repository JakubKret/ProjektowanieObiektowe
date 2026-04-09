package org.example.state;

import org.example.model.Entity;

public class DeadState implements HealthState {
    @Override
    public void handleExposure(Entity context, double contagiousness) {}
    @Override
    public void handleTreatment(Entity context, double healRate, double virusContagiousness) {}
    @Override
    public void passTurn(Entity context, double deathRate) {}
    @Override
    public boolean canInfectOthers() { return false; }
}