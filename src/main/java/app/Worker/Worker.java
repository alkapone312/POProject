package app.Worker;

import app.ControlPoint;
import app.Factory;
import app.Machine.Machine;
import app.Reference;
import app.SimulationObject;
import app.Utils.PreferredRandom;

import java.util.ArrayList;
import java.util.Random;

public class Worker extends SimulationObject {
    public String makes;
    private int salary;
    public int sanity = 4000;
    private int chanceofdrop=0;
    private boolean isTired;
    private int efficiency;
    private boolean isWorking;
    private boolean isResting;
    private int experience;
    private int way;
    private int lastX;
    private int lastY;
    protected Machine workstand;
    public boolean hasItem = false;

    public ArrayList<ControlPoint> path;

    private Random r = new Random();

    public Worker()
    {
        //spawn area for workers
        this.x = r.nextInt(14) + 6;
        this.y = r.nextInt(4) + Reference.ROWS - 3;
        this.path = new ArrayList<>();
    }

    //all worker logic here
    public void update()
    {
        if(isNear(this.workstand))
            if(this.workstand.isProductDone()) {
                this.hasItem = true;
                this.workstand.createNewProduct();
            }
        if(this.isWorking){
            if (r.nextInt(100)< 60 + this.chanceofdrop){
                this.sanity--;
                this.chanceofdrop=0;
            }
            else this.chanceofdrop += 5;
        }
        if (this.sanity==100) this.isTired = true;
        if(this.isResting){
            this.sanity++;
        }
        if (this.sanity==3500) this.isTired = false;
        this.move();
    }

    public void goTroughPath() {
        ControlPoint p = path.get(0);
        if(this.getX() == p.getX() && this.getY() == p.getY())
            this.path.remove(p);

        this.setWay(p);
    }

    public void setWay()
    {
        this.way = (int)(this.r.nextDouble()* Reference.NUM_OF_WAYS);
    }

    public void setWay(SimulationObject obj)
    {
        double angle = -1;

        double distanceX = this.getX() - obj.getX();
        double distanceY = this.getY() - obj.getY();
        angle = Math.abs(Math.toDegrees(Math.atan2(distanceY, distanceX)) - 180.0);

        if(angle > 337.5)
            this.way = Reference.RIGHT;
        if(angle < 337.5)
            this.way = Reference.DOWN_RIGHT;
        if(angle < 292.5)
            this.way = Reference.DOWN;
        if(angle < 247.5)
            this.way = Reference.DOWN_LEFT;
        if(angle < 202.5)
            this.way = Reference.LEFT;
        if(angle < 157.5)
            this.way = Reference.UP_LEFT;
        if(angle < 112.5)
            this.way = Reference.UP;
        if(angle < 67.5)
            this.way = Reference.UP_RIGHT;
        if (angle < 22.5)
            this.way = Reference.RIGHT;
        if (angle < 0)
            this.way = -1;

        PreferredRandom pr = new PreferredRandom(Reference.NUM_OF_WAYS, this.way, 16);
        this.way = pr.nextPrefferedInt();
    }

    //moves the worker in given way
    public void move()
    {
        lastX = this.x;
        lastY = this.y;
        switch (this.way)
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

    public void revertMove()
    {
        this.x = lastX;
        this.y = lastY;
    }

    public void goWork()
    {
        if(this.hasItem)
            this.setWay(Factory.magazine);
        else
            this.setWay(this.workstand);
    }

    public void setWorkstand(Machine workstand)
    {
        this.workstand = workstand;
    }

    public boolean isWorking() {
        return this.isWorking;
    }

    public boolean isResting() {
        return this.isResting;
    }
    public boolean isTired() {
        return this.isTired;
    }

    public void setWorking() {
        this.isResting = false;
        this.isWorking = true;
    }

    public void setResting() {
        this.isWorking = false;
        this.isResting = true;
    }
}
