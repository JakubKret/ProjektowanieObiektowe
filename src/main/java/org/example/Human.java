package org.example;

import java.util.Random;

public class Human {
    ////[    VARIABLES    ]\\\\
    /**
     * Object's X and Y position
     */
    private int posX,posY;
    /**
     * Human object's health which decides on their virus survivability.
     */
    private double health = 40;
    /**
     * Variable used to determine if Human object gets infected.
     */
    private double immunity = 0.1;
    /**
     * Variable used to storing information if object is infected.
     */
    private boolean isInfected=false;
    /**
     * Variable used to determine if object can infect in each cycle.
     */
    private boolean canInfect = false;
    /**
     * Variable used to storing information if objects health reached 0.
     */
    private boolean isDead = false;

    private boolean isOnPlane = false;
    private boolean isAfterFlight = false;
    /**
     * Map
     */
    private Tile[][] board;
    /**
     * Map size
     */
    private int height;
    Random rand = new Random();
    ////[    CONSTRUCTOR    ]\\\\

    /**
     * Applies input values into each Cure object values.
     * @param X Object's X position
     * @param Y Object's Y position
     * @param board Map
     * @param height Map size
     */
    public Human(int X,int Y, Tile[][] board, int height){
        this.posX = X;
        this.posY = Y;
        this.board = board;
        this.immunity = rand.nextDouble();
        this.height = height;
    }

    ////[    METHODS    ]\\\\

    /**
     * Method used to randomly move Human object to tile around it (excluding water tiles).
     */
    public void Move(){
        if(!isDead&&!isOnPlane){
            Random rand = new Random();
            int x = rand.nextInt(3);
            x=x-1;
            if(x==-1 && posX!=0 && board[posX-1][posY].isLand)    posX--;
            else if(x==1 && posX!= height-1 && board[posX+1][posY].isLand)   posX++;
            int y = rand.nextInt(3);
            y=y-1;
            if(y==-1 && posY!=0 && board[posX][posY-1].isLand)    posY--;
            else if(y==1 && posY!= height-1 && board[posX][posY+1].isLand)  posY++;
            isAfterFlight = false;
        }}

    /**
     * Method used to calculate Virus effect on object.
     * @param x - deathRate from Virus class.
     */
    public void virusEffect(double x){
        if(isInfected){
            x = x - (immunity/2);
            if(x <= 0.01){x = 0.01;}
            health = health - x;
        }
        if(health <= 0){
            canInfect = false;
            isDead = true;
        }
    }

    /**
     * Method used to calculate Cure effect on object.
     * @param x - healRate from Cure class.
     */
    public void cureEffect(double x){
        if(isInfected) {
            health = health + x;
        }
    }

    ////[    SETTERS    ]\\\\

    /**
     * Method used to change isInfected value to true
     */
    public void giveSick(){this.isInfected = true;}
    /**
     * Method used to change canInfect value to true
     */
    public void setCanInfect(){this.canInfect = true;}
    /**
     * Method used to change isInfected and canInfect values to false
     */
    public void setCure(){
        this.isInfected = false;
        this.canInfect=false;
    }
    public void setIsOnPlane()  {isOnPlane = !this.isOnPlane;}
    public void setIsAfterFlight()  {isAfterFlight = true;}

    ////[    GETTERS    ]\\\\

    /**
     * Method used to get health value
     * @return double health
     */
    public double getHealth() {return health;}
    /**
     * Method used to get position X
     * @return int position X
     */
    public int getPosX()    {return posX;}
    /**
     * Method used to get position Y
     * @return int position Y
     */
    public int getPosY()    {return posY;}
    /**
     * Method used to get isInfected value
     * @return boolean isInfected
     */
    public boolean getIsInfected()  {return isInfected;}
    /**
     * Method used to get canInfect value
     * @return boolean canInfect
     */
    public boolean getCanInfect()   {return canInfect;}
    /**
     * Method used to get value of immunity
     * @return double Immunity
     */
    public double getImmunity() {return immunity;}
    /**
     * Method used to get isDead value
     * @return boolean isDead
     */
    public boolean getIsDead()  { return isDead;}
    public boolean getIsOnPlane()   {return isOnPlane;}
    public boolean getIsAfterFlight()   {return isAfterFlight;}
}