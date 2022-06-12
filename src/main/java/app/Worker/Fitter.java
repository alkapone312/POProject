package app.Worker;

import app.Factory;

import java.awt.*;

public class Fitter extends Worker{
    private double modifier;
    public Fitter()
    {
        this.makes = "products";
        //red
        this.color = new Color(210,30,0);
        this.name = "Worker1.png";
    }

    public void update()
    {
        if(Factory.screws > 1 && Factory.constructions > 1 && isNear(this.workstand))
            if(this.workstand.isProductDone()) {
                this.hasItem = true;
                this.workstand.createNewProduct();
                Factory.screws--;
                Factory.constructions--;
            }

        this.move();
    }
}
