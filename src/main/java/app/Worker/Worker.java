package app.Worker;

import app.ControlPoint;
import app.Factory;
import app.Machine.Machine;
import app.Reference;
import app.SimulationObject;
import app.Utils.PreferredRandom;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Worker extends SimulationObject {
    public String makes;
    public boolean hasItem = false;

    private BufferedImage workerSprite;
    private int salary;
    private int sanity;
    private int maxsanity;
    private int chanceofdrop=0;
    private boolean isTired;
    private double efficiency;
    private boolean isWorking;
    private boolean isResting;
    private boolean isHangingAround;
    private int experience;
    private int way;
    private int lastX;
    private int lastY;
    private int timeHasPath = 0;

    protected Machine workstand;

    public ArrayList<ControlPoint> path;

    private Random r = new Random();

    public Worker()
    {
        //spawn area for workers
        this.x = r.nextInt(14) + 6;
        this.y = r.nextInt(4) + Reference.ROWS - 3;
        this.path = new ArrayList<>();

        this.sanity = 4000;
        this.maxsanity = this.sanity;
        this.efficiency = 1.0;
        this.salary = 2000;
    }

    //all worker logic here
    public void update()
    {
        this.handleProduct();
        this.handleSanity();
        this.handleBuggedPath();

        this.move();
    }

    public void goTroughPath() {
        ControlPoint p = path.get(0);
        if(this.getX() == p.getX() && this.getY() == p.getY())
            this.path.remove(p);

        this.timeHasPath++;
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

    public double getEfficiency(){
        return this.efficiency;
    }

    public int getSanity() {
        return this.sanity;
    }

    public int getSalary() {
        return this.salary;
    }

    public boolean isHangingAround() {
        return this.isHangingAround;
    }

    public void setWorking() {
        this.isResting = false;
        this.isWorking = true;
    }

    public void setResting() {
        this.isWorking = false;
        this.isResting = true;
    }

    public void setHangingAround(boolean b) {
        this.isHangingAround = b;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(this.workerSprite, null, Reference.PIXEL_SIZE*this.x, Reference.PIXEL_SIZE*this.y);
    }

    protected void setSprite(String fileName) {
        try {
            this.workerSprite = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleProduct() {
        if(isNear(this.workstand))
            if(this.workstand.isProductDone()) {
                this.hasItem = true;
                this.workstand.createNewProduct();
            }

        if(this.getClass().getSimpleName() == "Fitter") {
            if(Factory.screws > 1 && Factory.constructions > 1 && isNear(this.workstand))
                if(this.workstand.isProductDone()) {
                    this.hasItem = true;
                    this.workstand.createNewProduct();
                    Factory.screws--;
                    Factory.constructions--;
                }
        }
    }

    private void handleSanity() {
        if(Factory.dayTime == 0) {
            this.sanity += r.nextDouble() * 400 + 800;
        }
        if(this.isWorking){
            if (r.nextInt(100)< 60 + this.chanceofdrop){
                this.sanity--;
                this.chanceofdrop=0;
            }
            else this.chanceofdrop += 5;
        }
        if(this.sanity < 100) {
            this.sanity = 100;
        }
        if (this.sanity==100) {
            this.isTired = true;
        }
        if(this.isResting) {
            this.sanity += 4;
        }
        if (this.sanity==3500) {
            this.isTired = false;
        }
        if(this.sanity > this.maxsanity) {
            this.sanity = this.maxsanity;
        }
        this.efficiency = (double)this.sanity/(double)this.maxsanity;
        if(this.efficiency < 0.1)
        {
            this.efficiency = 0.1;
        }

    }

    private void handleBuggedPath() {
        if(this.timeHasPath > 500 && this.path.size() > 0) {
            this.path = new ArrayList<>();
            this.timeHasPath = 0;
        }
        if(this.path.size() == 0) {
            this.timeHasPath = 0;
        }
    }
}
