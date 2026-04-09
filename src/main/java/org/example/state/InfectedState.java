package org.example.state;

import org.example.model.Entity;
import java.util.Random;

public class InfectedState implements HealthState {
    private final Random rand = new Random();

    @Override
    public void handleExposure(Entity context, double contagiousness) { }

    @Override
    public void handleTreatment(Entity context, double healRate, double virusContagiousness) {
        context.increaseHealth(healRate);
        if (virusContagiousness + rand.nextDouble() < context.getImmunity() + healRate) {
            context.changeState(new ImmuneState());
        }
    }

    @Override
    public void passTurn(Entity context, double deathRate) {
        double damage = deathRate - (context.getImmunity() / 2);
        if (damage <= 0.01) damage = 0.01;
        context.decreaseHealth(damage);

        if (context.getHealth() <= 0) {
            context.changeState(new DeadState());
        }
    }

    @Override
    public boolean canInfectOthers() { return true; }
}