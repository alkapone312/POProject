package chart;

import javax.swing.*;

import app.Reference;
import app.Utils.Pair;
import app.Window;
import chart.ChartDrawer;

import java.awt.*;
import java.util.ArrayList;

public class Chart extends JFrame {
    private ArrayList<String> chartNames;
    private ArrayList<ArrayList<Double>> chartData;
    private ArrayList<Pair<Double, Double>> chartRange;
    private ChartDrawer chartDrawer;

    public Chart() {
        Window chart = new Window();
        chart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chart.setSize(400,Reference.HEIGHT);
        chart.setLocation(300+Reference.WIDTH, 0);
        this.chartDrawer = new ChartDrawer();
        chart.add(chartDrawer);
        chart.setResizable(false);
        chart.setVisible(true);

        this.chartNames = new ArrayList<>();
        this.chartData = new ArrayList<>();
        this.chartRange = new ArrayList<>();
    }

    public void addChart(String name, Double minRange, Double maxRange) {
        this.chartNames.add(name);
        this.chartData.add(new ArrayList<Double>());
        this.chartRange.add(new Pair<Double, Double>(minRange, maxRange));
    }

    public void addValue(String name, Double value) {
        for(int i = 0 ; i < chartNames.size(); i++) {
            if(chartNames.get(i) == name) {
                this.chartData.get(i).add(value);
            }
        }
    }

    public void graphCharts() {
        for(int i = 0 ; i < chartNames.size(); i++) {
                this.chartDrawer.drawChart(i+1, this.chartData.get(i), this.chartRange.get(i), this.chartNames.get(i));
        }
        this.chartDrawer.repaint();
    }

}
