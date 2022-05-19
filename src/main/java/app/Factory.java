package app;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Factory extends JPanel {
    private int social;
    private int screws;
    private int constructions;
    private int products;
    private double budget;

    private BufferedImage buffer;
    private Graphics2D g2;

    private Map map;
    public Factory()
    {
        buffer = new BufferedImage(Reference.WIDTH, Reference.HEIGHT, BufferedImage.TYPE_INT_RGB);
        g2 = buffer.createGraphics();
        this.map = new Map();

    }

    public void paintComponent(Graphics g)
    {
        g.drawImage(this.buffer,0,0, null);
    }

    public void draw(Graphics2D g2)
    {
        this.map.draw(g2);
        repaint();
    }

    public Graphics2D getGraphics2D()
    {
        return this.g2;
    }
}
