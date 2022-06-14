package app;

import java.util.Random;

import app.Machine.Lathe;
import app.Machine.Machine;
import app.Utils.HR;
import app.Utils.PerlinNoise;
import app.Worker.Worker;


public class Market {
    private double priceSell;
    private double priceMaterial;
    private double bud;
    private float[] noiseBuy;
    private float[] noiseSell;
    public int Totalsold;


    public Market() {
        this.bud = Factory.getBudget();
        this.Totalsold=0;
        PerlinNoise p1 = new PerlinNoise();
        PerlinNoise p2 = new PerlinNoise();
        this.noiseBuy = p1.PerlinNoise1D(365, 4, 3);
        this.noiseSell = p2.PerlinNoise1D(365, 4, 3);

        Factory.setBudget(bud);
    }

    public void sell() {
    	int x = Factory.screws;
    	int y = Factory.constructions;
    	int z = Factory.products;
    	
    	this.Totalsold = this.Totalsold + x+y+z;
    	
    	Factory.screws=0;
    	Factory.constructions=0;
    	Factory.products=0;
    	
        this.priceSell = this.noiseSell[Factory.day % 365]*2.7; //needs adjust
        
        for(int c = 0; c<= x+y+z ; c++) {
        	Factory.setBudget(bud+this.priceSell);
        }
        
    }

    public void buy() {
        this.priceMaterial = noiseBuy[Factory.day % 365]; // needs adjust
        bud = bud - priceMaterial;
        Factory.setBudget(bud);
    }
    
    	
}
    
    
