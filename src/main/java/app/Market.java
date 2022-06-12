package app;

import java.util.Random;

import app.Utils.PerlinNoise;


public class Market {
    private double priceSell;
    private double priceMaterial;
    private double bud;
    private float[] noiseBuy;
    private float[] noiseSell;


    public Market() {
        this.bud = Factory.getBudget();
        PerlinNoise p1 = new PerlinNoise();
        PerlinNoise p2 = new PerlinNoise();
        this.noiseBuy = p1.PerlinNoise1D(365, 4, 3);
        this.noiseSell = p2.PerlinNoise1D(365, 4, 3);

        Factory.setBudget(bud);
    }

    public void sell(double ProductPrice) {
        this.priceSell = this.noiseSell[Factory.day % 365] * 13;
        bud = bud + priceSell;
    }

    public void buy(double ProductPrice) {
        this.priceMaterial = noiseBuy[Factory.day % 365] * 20;
        bud = bud - priceMaterial;
        Factory.setBudget(bud);
    }
}