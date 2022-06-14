package app;

import app.Utils.PerlinNoise;
import chart.Chart;


public class Market {
    private double priceSell;
    private double priceMaterial;
    private double bud;
    private float[] noiseBuy;
    private float[] noiseSell;
    public int Totalsold;


    public Market() {
        this.Totalsold=0;
        PerlinNoise p1 = new PerlinNoise();
        PerlinNoise p2 = new PerlinNoise();
        this.noiseBuy = p1.PerlinNoise1D(400, 6, 1);
        this.noiseSell = p2.PerlinNoise1D(400, 6, 1);

        Chart chart = new Chart();
        chart.addChart("Buying price", 0.0, 1.0);
        chart.addChart("Selling price", 0.0, 1.0);

        for(int i = 0 ; i < 400; i++)
        {
            chart.addValue("Buying price", (double)this.noiseBuy[i]);
            chart.addValue("Selling price", (double)this.noiseSell[i]);
            chart.graphCharts();
            try {Thread.sleep(10);} catch (Exception e) {}
        }
        chart.graphCharts();
    }

    public void sell() {
        this.bud = Factory.getBudget();
        this.Totalsold = this.Totalsold + Factory.products;
        double priceForProducts = this.priceSell*Factory.products;
        Factory.setBudget(bud+priceForProducts);
        System.out.println("Selled " + Factory.products + " products for " + this.priceSell + " price " + priceForProducts + " in total");
    	Factory.products=0;
    }

    public void buy(int machineCount) {
        this.bud = Factory.getBudget();
        bud = bud - priceMaterial*machineCount;
        System.out.println("Buying materials for " + machineCount + " machines, total price: " + priceMaterial*machineCount);
        Factory.setBudget(bud);
    }

    public void update() {
        this.priceSell = noiseSell[Factory.day % 400]*500;
        this.priceMaterial = this.noiseBuy[Factory.day % 400]*3000;
    }

    public double getPriceBuy() {
        return this.priceMaterial;
    }

    public double getPriceSell() {
        return this.priceSell;
    }
    
    	
}
    
    
