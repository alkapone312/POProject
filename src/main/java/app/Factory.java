package app;

import app.Machine.*;
import app.Worker.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Factory extends JPanel {
    private int social;
    private int screws;
    private int constructions;
    private int products;
    private double budget;

    public ArrayList<Worker> workers;
    public ArrayList<Machine> machines;

    private BufferedImage buffer;
    private Graphics2D g2;

    private Map map;

    public Factory()
    {
        //Creates buffer and graphics context for drawing
        this.buffer = new BufferedImage(Reference.WIDTH, Reference.HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.g2 = buffer.createGraphics();

        //initiate map instance
        this.map = new Map();

        //initiate workers and machines
        this.workers = new ArrayList<>();
        this.machines = new ArrayList<>();

        //assign starting workers and machines
        this.workers.add(new Turner());
        this.workers.add(new Welder());
        this.workers.add(new Fitter());

        this.machines.add(new Lathe());
        this.machines.add(new WeldingMachine());
        this.machines.add(new AssemblyTable());
    }

    public void paintComponent(Graphics g)
    {
        g.drawImage(this.buffer,0,0, null);
    }

    public void draw(Graphics2D g2)
    {
        this.map.draw(g2);

        for(Worker worker : workers)
        {
            worker.draw(g2);
        }

        for(Machine machine : machines)
        {
            machine.draw(g2);
        }

        repaint();
    }

    public Graphics2D getGraphics2D()
    {
        return this.g2;
    }
}
