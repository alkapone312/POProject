package app.Machine;

import app.SimulationObject;
import app.Worker.Worker;

public class Machine extends SimulationObject {
    private int healthPoints;
    private int progress;
    private int maxProgress = 100;
    private int price;
    private Worker worker;

    public void update()
    {
        if (this.isNear(this.worker))
        {
            progress++;
        }
    }

    public void getPrice()
    {

    }

    public void payForRepair()
    {

    }

    public void repair()
    {

    }

    public boolean isProductDone()
    {
        if(this.progress == this.maxProgress)
        {
            return true;
        }
        return false;
    }

    public void createNewProduct()
    {
        this.progress = 0;
    }

    public void setWorker(Worker w)
    {
        this.worker = w;
    }

    public Worker getWorker()
    {
        return this.worker;
    }
}
