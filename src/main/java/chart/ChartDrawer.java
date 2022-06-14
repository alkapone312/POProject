package chart;

import app.Reference;
import app.Utils.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ChartDrawer extends JPanel {

    private BufferedImage buffer;
    private Graphics2D g2;

    public ChartDrawer() {
        this.buffer = new BufferedImage(400, Reference.HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.g2 = buffer.createGraphics();
    }

    public void paintComponent(Graphics g) {
        g.drawImage(this.buffer, 0, 0, null);
    }

    public void drawChart(int index, ArrayList<Double> values, Pair<Double, Double> rangeVal, String name) {
        this.g2.setColor(Color.BLACK);
        this.g2.fillRect(0,(index-1)*100, this.buffer.getWidth(), index*100);
        this.g2.setColor(Color.GREEN);

        Double y;
        Double lastX;
        Double lastY;
        lastX=0.0;
        lastY=0.0;
        for(int x = 0 ; x < values.size(); x++) {
            y=map(values.get(x), rangeVal.getFirst(), rangeVal.getSecond(), 0.0, 100.0);;
            g2.drawLine(x, index*100, x, (int)(index*100-1-y));
            if(x > 400)
                values.remove(values.get(0));
            lastX=(double)x;
            lastY=y;
        }
        this.g2.setColor(Color.WHITE);
        this.g2.drawString(name, 0, (index-1)*100+20);
    }

    private Double map(Double x, Double in_min, Double in_max, Double out_min, Double out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
