package org.example.main;

import org.example.model.Board;
import org.example.model.SimulationConfig;
import org.example.observer.ConsoleStatsLogger;
import org.example.ui.ConfigScreen;
import org.example.ui.SimulationWindow;

public class Plague {
    public static void main(String[] args) {
        ConfigScreen.ConfigListener listener = (speed, contagiousness, deathRate, healRate, delay, density, scale, maxPeople, maxAnimals, iter) -> {

            SimulationConfig.initialize(speed, contagiousness, deathRate, healRate, delay, density, scale, maxPeople, maxAnimals, iter);

            Board board = new Board();

            SimulationWindow window = new SimulationWindow(board);
            window.display();

            board.addObserver(window);
            board.addObserver(new ConsoleStatsLogger());

            board.patientZero();

            new Thread(() -> {
                try {
                    while (true) {
                        Thread.sleep(SimulationConfig.getInstance().speed);
                        board.tick();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        };

        javax.swing.SwingUtilities.invokeLater(() -> new ConfigScreen(listener).setVisible(true));
    }
}