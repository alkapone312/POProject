package app.Worker;

import app.Reference;
import app.SimulationObject;

import java.util.Random;

public class Worker extends SimulationObject {
    private int salary;
    private int sanity;
    private int efficiency;
    private boolean isWorking;
    private int experience;

    Random r = new Random();

    public Worker()
    {
        //spawn area for workers
        this.x = (int)(r.nextDouble()*3) + Reference.COLS - 10;
        this.y = (int)(r.nextDouble()*3) + 10;
    }

    //all worker logic here
    public void update()
    {
        int way = (int)(r.nextDouble()* Reference.NUM_OF_WAYS);
        this.move(way);
    }

    //moves the worker in given way
    public void move(int way)
    {
        switch (way)
        {
            case Reference.UP:
                this.y--;
                break;
            case Reference.UP_RIGHT:
                this.y--;
                this.x++;
                break;
            case Reference.RIGHT:
                this.x++;
                break;
            case Reference.DOWN_RIGHT:
                this.x++;
                this.y++;
                break;
            case Reference.DOWN:
                this.y++;
                break;
            case Reference.DOWN_LEFT:
                this.x--;
                this.y++;
                break;
            case Reference.LEFT:
                this.x--;
                break;
            case Reference.UP_LEFT:
                this.y--;
                this.x--;
                break;
            default:
                break;
        }
    }

}
