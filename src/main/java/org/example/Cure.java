package org.example;

import java.util.Random;

public class Cure {

    ////[    VARIABLES    ]\\\\
    /**
     * delay Variable defining number of cycles before Cure starts to spread,
     * side variable used for some iterations,
     * speed amount of cycles between cure spawns,
     * iter starting amount of tiles that the Cure is spawned
     */
    private int delay = 50, count = 0, speed = 2, iter = 1000;
    /**
     * Variable affecting effectiveness of Cure
     */
    private double healRate = 0.45;
    /**
     * Map
     */
    private Board visualizer;
    /**
     * Virus class object
     */
    private Virus virus;
    /**
     * Map size
     */
    private int height;
    ////[    CONSTRUCTOR    ]\\\\

    /**
     * Applies input values into each Cure object values.
     * @param visualizer Map
     * @param virus Virus object
     * @param healRate Variable affecting effectiveness of Cure
     * @param delay Variable defining number of cycles before Cure starts to spread
     * @param height Map size
     * @param iter starting amount of times that the Cure spawned
     */
    public Cure(Board visualizer, Virus virus, double healRate, int delay,int height,int iter) {
        this.visualizer = visualizer;
        this.virus = virus;
        this.healRate = healRate;
        this.delay = delay;
        this.height = height;
        this.iter = iter;
    }

    ////[    METHODS    ]\\\\

    /**
     * Method that starts spreading Cure after a number(delay) of cycles.
     */
    void cureStart() {

        if(count!=0 && count == delay){
            while(true) {
                Random rand =  new Random();
                int x = rand.nextInt(height);
                int y = rand.nextInt(height);
                if (visualizer.getBoardTable()[x][y].isLand && !visualizer.getBoardTable()[x][y].getPeople().isEmpty()) {
                    for (int i = 0; i < visualizer.getBoardTable()[x][y].getPeople().size(); i++) {
                        if (visualizer.getBoardTable()[x][y].getPeople().get(i).getIsInfected() && !visualizer.getBoardTable()[x][y].getPeople().get(i).getIsDead()) {
                            for (int z = 0; z < visualizer.getBoardTable()[x][y].getPeople().size(); z++) {
                                visualizer.getBoardTable()[x][y].getPeople().get(z).cureEffect(healRate);
                                if (virus.contagiousness + rand.nextDouble() < visualizer.getBoardTable()[x][y].getPeople().get(z).getImmunity() + healRate)  {
                                    visualizer.getBoardTable()[x][y].getPeople().get(z).setCure();
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        count++;
    }

    /**
     * Method that spawns cure at random Tiles and uses it's effect on Human object and calculates if Human object gets cured from Virus.
     */
    void cureSpread() {

        if(count > delay) {
            if (count % speed == 0) {
                for (int k = 0; k < iter; k++) {
                    while (true) {
                        Random rand = new Random();
                        int x = rand.nextInt(height);
                        int y = rand.nextInt(height);
                        if (visualizer.getBoardTable()[x][y].isLand && !visualizer.getBoardTable()[x][y].getPeople().isEmpty()) {
                            for (int i = 0; i < visualizer.getBoardTable()[x][y].getPeople().size(); i++) {
                                if (visualizer.getBoardTable()[x][y].getPeople().get(i).getIsInfected() && !visualizer.getBoardTable()[x][y].getPeople().get(i).getIsDead()) {
                                    for (int z = 0; z < visualizer.getBoardTable()[x][y].getPeople().size(); z++) {
                                        visualizer.getBoardTable()[x][y].getPeople().get(z).cureEffect(healRate);
                                        if (virus.contagiousness + rand.nextDouble() < visualizer.getBoardTable()[x][y].getPeople().get(z).getImmunity() + healRate) {
                                            visualizer.getBoardTable()[x][y].getPeople().get(z).setCure();
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                if (iter < (height* height)/150) iter++;
            }
        }
    }

    ////[    GETTERS    ]\\\\

    /**
     * Method that returns value of healRate
     * @return double healRate
     */
    public double getHealRate(){return healRate;}

}