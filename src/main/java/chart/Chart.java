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
    private ArrayList<ArrayList<Integer>> chartData;
    private ArrayList<Pair<Integer, Integer>> chartRange;
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

    public void addChart(String name, int minRange, int maxRange) {
        this.chartNames.add(name);
        this.chartData.add(new ArrayList<Integer>());
        this.chartRange.add(new Pair<Integer, Integer>(minRange, maxRange));
    }

    public void addValue(String name, Integer value) {
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
