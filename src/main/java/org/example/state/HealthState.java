package org.example.state;

import org.example.model.Entity;

public interface HealthState {
    void handleExposure(Entity context, double contagiousness);
    void handleTreatment(Entity context, double healRate, double virusContagiousness);
    void passTurn(Entity context, double deathRate);
    boolean canInfectOthers();
}