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

    public void drawChart(int index, ArrayList<Integer> values, Pair<Integer, Integer> rangeVal, String name) {
        this.g2.setColor(Color.BLACK);
        this.g2.fillRect(0,(index-1)*100, this.buffer.getWidth(), index*100);
        this.g2.setColor(Color.GREEN);
        for(int x = 0 ; x < values.size(); x++) {
            int y = this.map(values.get(x), rangeVal.getFirst(), rangeVal.getSecond(), 0, 100);
            g2.fillRect(x, index*100-1-y, 2, 2);
            if(x > 400)
                values.remove(values.get(0));
        }
        this.g2.setColor(Color.WHITE);
        this.g2.drawString(name, 0, (index-1)*100+20);
    }

    private int map(int x, int in_min, int in_max, int out_min, int out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
