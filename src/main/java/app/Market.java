package app;
import java.util.Random;
import app.Utils.PerlinNoise;


public class Market{
    private double priceSell;
    private double priceMaterial;
    private double bud;
    private float[] noise;
    

    public Market() {
        this.bud = Factory.getBudget();
        PerlinNoise p = new PerlinNoise();
        this.noise = p.PerlinNoise1D(20,4,3);
        
        Factory.setBudget(bud);
    }
    
    public void sell(double ProductPrice){
        Random r = new Random();
        this.priceSell = r.nextDouble(13);
        
        bud = bud+priceSell;
        
    }

    public void buy(double ProductPrice){
        Random r = new Random();
        int choose = r.nextInt(21);
        this.priceMaterial = r.nextDouble(20)*noise[choose];
        
        bud = bud-priceMaterial;
        
        Factory.setBudget(bud);
        
    }
}