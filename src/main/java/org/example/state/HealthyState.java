package org.example.state;

import org.example.model.Entity;
import java.util.Random;

public class HealthyState implements HealthState {
    private final Random rand = new Random();

    @Override
    public void handleExposure(Entity context, double contagiousness) {
        if (contagiousness + rand.nextDouble() > context.getImmunity() + rand.nextDouble()) {
            context.changeState(new InfectedState());
        }
    }

    @Override
    public void handleTreatment(Entity context, double healRate, double virusContagiousness) { }

    @Override
    public void passTurn(Entity context, double deathRate) { }

    @Override
    public boolean canInfectOthers() { return false; }
}