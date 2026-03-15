package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class Plague {
    public static void main(String[] args) {
        ConfigScreen.ConfigListener listener = new ConfigScreen.ConfigListener() {
            /**
             * Creates all the necessary instances and passes on the values collected from GUI
             * @param speed sets the value according to the GUI
             * @param contagiousness sets the value according to the GUI
             * @param deathRate sets the value according to the GUI
             * @param healRate sets the value according to the GUI
             * @param delay sets the value according to the GUI
             * @param density sets the value according to the GUI
             * @param scale sets the value according to the GUI
             * @param maxPeople sets the value according to the GUI
             * @param maxAnimals sets the value according to the GUI
             * @param iter sets the value according to the GUI
             */
            @Override
            public void onConfigSelected(int speed, double contagiousness, double deathRate,double healRate,
                                         int delay, double density, double scale/*,int height*/,
                                         int maxPeople,int maxAnimals, int iter) {
                Board board = new Board(speed, contagiousness,deathRate,healRate,delay,density,scale/*,height*/,maxPeople,maxAnimals,iter);
                board.display();

                Virus virus = new Virus(board, board.contagiousness,board.deathRate);
                Cure cure = new Cure(board, virus,board.healRate, board.delay, board.height, board.iter);

                virus.patientZero();
                AtomicInteger i = new AtomicInteger();
                new Thread(() -> {
                    try {
                        while (1>0) {
                            Thread.sleep(board.speed); // tick length
                            virus.virusSpread();
                            virus.virusAction(); // virus deals damage
                            board.movePopulation();
                            board.moveAnimalPopulation();
                            board.movePlanes();
                            cure.cureStart(); // checks for correct tick to spawn itself
                            cure.cureSpread();
                            board.refreshVisualization(); // refreshes the visualization
                            board.showStats();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        };

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            /**
             * Turn on the starting screen
             */
            public void run() {
                new ConfigScreen(listener).setVisible(true);
            }
        });
    }
}