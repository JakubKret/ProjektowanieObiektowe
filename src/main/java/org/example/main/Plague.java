package org.example.main;

import org.example.model.Board;
import org.example.ui.ConfigScreen;
import org.example.ui.SimulationWindow;

public class Plague {
    public static void main(String[] args) {
        ConfigScreen.ConfigListener listener = new ConfigScreen.ConfigListener() {
            @Override
            public void onConfigSelected(int speed, double contagiousness, double deathRate, double healRate,
                                         int delay, double density, double scale,
                                         int maxPeople, int maxAnimals, int iter) {

                Board board = new Board(speed, contagiousness, deathRate, healRate, delay, density, scale, maxPeople, maxAnimals, iter);


                SimulationWindow window = new SimulationWindow(board);
                window.display();

                board.patientZero();

                new Thread(() -> {
                    try {
                        while (true) {
                            Thread.sleep(board.speed);

                            board.tick();
                            window.updateGraphics();
                            board.showStats();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        };

        javax.swing.SwingUtilities.invokeLater(() -> new ConfigScreen(listener).setVisible(true));
    }
}