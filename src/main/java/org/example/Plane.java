package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Plane {

    ////[    VARIABLES    ]\\\\

    private int posX,posY;
    int[] move = new int[2];
    ArrayList<Island> islands;
    private boolean isMoving=false;
    public List<Human> humans = new ArrayList<Human>();
    Tile[][] board;
    int x;
    int y;

    /**
     * Creates a Plane in one of the Airports and sets its destination
     * @param posX Plane x position
     * @param posY Plane y position
     * @param islands List of islands
     * @param board State of the main Board
     */
    public Plane(int posX, int posY, ArrayList<Island> islands, Tile[][] board){
        this.posX = posX;
        this.posY = posY;
        this.islands = islands;
        this.board = board;
        calculateMove();
    }

    /**
     * Calculates next destination and if possible takes and leaves People
     */
    private void calculateMove()
    {
        if(!isMoving && !board[posX][posY].getPeople().isEmpty()){isMoving=true;}
        if (isMoving){
        Random random = new Random();
        int r = random.nextInt(islands.size());
        while (islands.get(r).getAirport().posX==this.posX&&islands.get(r).getAirport().posY==this.posY){r = random.nextInt(islands.size());}
        x=islands.get(r).getAirport().posX;
        y=islands.get(r).getAirport().posY;

        for (Human human : board[posX][posY].getPeople()) {
            if (!human.getIsDead() && !human.getIsAfterFlight() && !human.getIsOnPlane()) {
                human.setIsOnPlane();
                this.humans.add(human);
                //board[posX][posY].humans.remove(human);
            }
        }
        for (Human human : this.humans)
        {
            board[posX][posY].getPeople().remove(human);
        }

//        int steps =fastFloor(Math.min(Math.abs(x-posX)/20,Math.abs(y-posY)/20));
//        if (((x-posX)/(steps*2))%2!=0)
//        {posX++;}
//        if (((y-posY)/(steps*2))%2!=0)
//        {posY++;}
        move[0]=(x-posX);//(steps*2);
        move[1]=(y-posY);//(steps*2);
        //System.out.println(steps);
        }
    }

    /**
     * If there are people on the Tile, moves the Plane
     */
    public void Move(){

        if (!isMoving && !board[posX][posY].getPeople().isEmpty()){isMoving=true;}
        if (!humans.isEmpty()){isMoving=true;}
        if(isMoving){
        board[posX][posY].getPlanes().remove(this);
        //System.out.println(posX+" "+posY);
        //System.out.println(x + " "+ y);
        //System.out.println(move[0]+" "+move[1]);
        //posX+=move[0];
        //posY+=move[1];
        posX = x;
        posY = y;
        if (posX==x && posY==y){
            for (Human human : humans){
                human.setIsAfterFlight();
                human.setIsOnPlane();

                board[posX][posY].getPeople().add(human);

            }
            //System.out.println(humans.size());
            isMoving=false;
            humans.clear();

            calculateMove();
        }

        board[posX][posY].getPlanes().add(this);
    }}

    /**
     * Rounding down
     * @param x starting value
     * @return value without the decimal part
     */
    private static int fastFloor(double x) {
        return x > 0 ? (int) x : (int) x - 1;
    }

    ////[    GETTERS    ]\\\\

    /**
     * Gets the x position
     * @return Plane x position
     */
    public int getPosX()    {return posX;}
    /**
     * Gets the y position
     * @return Plane y position
     */
    public int getPosY()    {return posY;}
}
