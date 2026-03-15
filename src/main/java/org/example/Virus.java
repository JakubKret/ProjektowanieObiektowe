package org.example;

import java.util.Random;

public class Virus {

    ////[    VARIABLES    ]\\\\

    double contagiousness = 0.2, deathRate = 0.15;
    Random rand = new Random();
    Random rand1 = new Random();
    Board board;
    ////[    CONSTRUCTOR    ]\\\\

    /**
     * Applies input values into each Animal object values.
     * @param board Map
     * @param contagiousness Variable defining chances for Human object to get infected
     * @param deathRate Variable deciding amount of damage taken each cycle if human object is infected
     */
    public Virus(Board board, double contagiousness, double deathRate) {
        this.board = board;
        this.contagiousness = contagiousness;
        this.deathRate = deathRate;
    }
    ////[    METHODS    ]\\\\

    /**
     * Method that randomly chooses Human object which gets then infected as a first patient.
     */
    public void patientZero() {
        int zero = rand.nextInt(board.getPopulation().size());
        board.getPopulation().get(zero).giveSick();
        board.getPopulation().get(zero).setCanInfect();
    }

    /**
     * Method that spreads virus on Human objects.
     */
    public void virusSpread() {
        if(contagiousness!=0){
            for (int x = 0; x < board.height; x++) {
                for (int y = 0; y < board.height; y++) {
                    if (board.getBoardTable()[x][y].isLand && !board.getBoardTable()[x][y].getPeople().isEmpty()) {
                        for (int i = 0; i < board.getBoardTable()[x][y].getPeople().size(); i++) {
                            if (board.getBoardTable()[x][y].getPeople().get(i).getCanInfect() && !board.getBoardTable()[x][y].getPeople().get(i).getIsDead()) {
                                for (int z = 0; z < board.getBoardTable()[x][y].getPeople().size(); z++) {
                                    if (contagiousness + rand.nextDouble() > board.getBoardTable()[x][y].getPeople().get(z).getImmunity() + rand1.nextDouble()) {
                                        board.getBoardTable()[x][y].getPeople().get(z).giveSick();
                                    }
                                }
                            }
                        }
                    }

                }
            }
            if(contagiousness!=0){
                for (int x = 0; x < board.height; x++) {
                    for (int y = 0; y < board.height; y++) {
                        if (board.getBoardTable()[x][y].isLand && (!board.getBoardTable()[x][y].getPeople().isEmpty() && !board.getBoardTable()[x][y].getAnimals().isEmpty())) {
                            for (int i = 0; i < board.getBoardTable()[x][y].getAnimals().size(); i++) {
                                if (board.getBoardTable()[x][y].getAnimals().get(i).getCanInfect()) {
                                    for (int z = 0; z < board.getBoardTable()[x][y].getPeople().size(); z++) {
                                        if (contagiousness + rand.nextDouble() > board.getBoardTable()[x][y].getPeople().get(z).getImmunity() + rand1.nextDouble()) {
                                            board.getBoardTable()[x][y].getPeople().get(z).giveSick();
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }

            for(Human human : board.getPopulation()) {
                if (human.getIsInfected()) {
                    human.setCanInfect();
                }}}
    }

    /**
     * Method that spreads virus on Animal objects.
     */
    public void animalVirus(){
        if(contagiousness!=0){
            for (int x = 0; x < board.height; x++) {
                for (int y = 0; y < board.height; y++) {
                    if (board.getBoardTable()[x][y].isLand && !board.getBoardTable()[x][y].getPeople().isEmpty()) {
                        for (int i = 0; i < board.getBoardTable()[x][y].getPeople().size(); i++) {
                            if (board.getBoardTable()[x][y].getPeople().get(i).getCanInfect() && !board.getBoardTable()[x][y].getPeople().get(i).getIsDead()) {
                                for (int z = 0; z < board.getBoardTable()[x][y].getAnimals().size(); z++) {
                                    board.getBoardTable()[x][y].getAnimals().get(z).setIsInfected();
                                }
                            }
                        }
                    }

                }
            }
            if(contagiousness!=0){
                for (int x = 0; x < board.height; x++) {
                    for (int y = 0; y < board.height; y++) {
                        if (board.getBoardTable()[x][y].isLand && (!board.getBoardTable()[x][y].getPeople().isEmpty() && !board.getBoardTable()[x][y].getAnimals().isEmpty())) {
                            for (int i = 0; i < board.getBoardTable()[x][y].getAnimals().size(); i++) {
                                if (board.getBoardTable()[x][y].getAnimals().get(i).getCanInfect()) {
                                    for (int z = 0; z < board.getBoardTable()[x][y].getAnimals().size(); z++) {
                                        board.getBoardTable()[x][y].getAnimals().get(z).setIsInfected();
                                    }
                                }
                            }
                        }
                    }

                }
            }

            for(Human human : board.getPopulation()) {
                if (human.getIsInfected()) {
                    human.setCanInfect();
                }}}
    }

    /**
     * Method that executes virus effect on each infected Human object.
     */
    public void virusAction() {
        for (Human human : board.getPopulation()) {
            if (human.getIsInfected()) {
                human.virusEffect(deathRate);
            }
        }

    }
}