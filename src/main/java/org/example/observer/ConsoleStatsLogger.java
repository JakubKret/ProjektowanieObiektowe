package org.example.observer;

import org.example.model.Board;
import org.example.model.Entity;
import org.example.model.Human;
import org.example.state.DeadState;
import org.example.state.InfectedState;

public class ConsoleStatsLogger implements SimulationObserver {

    @Override
    public void onSimulationTick(Board board) {
        int healthy = 0, dead = 0, infected = 0;
        int humanCount = 0;

        for (Entity e : board.getPopulation()) {
            if (e instanceof Human) {
                humanCount++;
                if (e.getHealthState() instanceof DeadState) dead++;
                else if (e.getHealthState() instanceof InfectedState) infected++;
                else healthy++;
            }
        }

        if (humanCount == 0) return;

        System.out.println("________________________________________");
        System.out.println("Healthy: " + (int) (healthy * 100.0 / humanCount) + "%");
        System.out.println("Infected: " + (int) (infected * 100.0 / humanCount) + "%");
        System.out.println("Dead: " + (int) (dead * 100.0 / humanCount) + "%");
    }
}