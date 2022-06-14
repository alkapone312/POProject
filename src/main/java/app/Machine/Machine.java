package app.Machine;

import app.Factory;
import app.SimulationObject;
import app.Worker.Worker;

import java.util.Random;

public class Machine extends SimulationObject {
    private int progress;
    private double maxProgress = 100;
    private static int price;
    protected Worker worker;
    private Random r = new Random();
    private int healthPoints = r.nextInt(50000) + 75000;
    private boolean isBroken = false;
    public Machine() {
        this.worker = new Worker();
    }

    public void update() {
        if (this.isNear(this.worker) && !worker.isHangingAround() && this.isBroken == false) {
            progress++;
        }
        this.maxProgress = 100/this.worker.getEfficiency();
        this.healthPoints--;
        if(this.healthPoints == 0) this.isBroken = true;
        if(this.isBroken == true && Factory.getBudget() > 2500 ){
            payForRepair();
            repair();
        }
    }

    public static int getPrice() {
    	return price;
    }
    
    public static void setPrice(int x) {
    	price = x;
    }

    public void payForRepair() {
        Factory.budget -= 2500;
    }

    public void repair() {
        this.isBroken = false;
        this.healthPoints = r.nextInt(50000) + 75000;
        System.out.println("Machine " + this.getClass().getSimpleName() + " is broken. Repairing machine for 2500. Budget after repair: " + Factory.budget);
    }

    public boolean isProductDone() {
        if (this.progress > this.maxProgress) {
            return true;
        }
        return false;
    }

    public boolean isBroken() {
        return this.isBroken;
    }

    public void createNewProduct() {
        this.progress = 0;
    }

    public Worker getWorker() {
        return this.worker;
    }

    public void setWorker(Worker w) {
        this.worker = w;
    }

}
