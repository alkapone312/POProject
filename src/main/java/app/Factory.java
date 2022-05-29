package app;

import app.Machine.*;
import app.Utils.HR;
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
    private boolean worktime = true;
    private ArrayList<JLabel> labels;

    public ArrayList<Worker> workers;
    public ArrayList<Machine> machines;

    public static SimulationObject magazine;

    private BufferedImage buffer;
    private Graphics2D g2;

    private Map map;

    public Factory()
    {
        //Creates buffer and graphics context for drawing
        this.buffer = new BufferedImage(Reference.WIDTH, Reference.HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.g2 = buffer.createGraphics();

        //initialize map instance
        this.map = new Map();

        //initialize workers and machines
        this.workers = new ArrayList<>();
        this.machines = new ArrayList<>();

        //initialize factory places
        Factory.magazine = new SimulationObject(10, 10);
        Factory.magazine.setColor(new Color(50,50,50));

        //assign starting workers and machines
        this.workers.add(new Turner());
        this.workers.add(new Welder());
        this.workers.add(new Fitter());

        this.machines.add(new Lathe());
        this.machines.add(new WeldingMachine());
        this.machines.add(new AssemblyTable());

        for(int i = 0 ; i < 3 ; i++)
        {
            HR.setWorkstand(this.workers.get(i), this.machines.get(i));
        }

        this.createLabels();

    }

    public void paintComponent(Graphics g)
    {
        g.drawImage(this.buffer,0,0, null);
    }

    public void draw(Graphics2D g2)
    {

        this.map.draw(g2);
        Factory.magazine.draw(g2);

        for(Worker worker : this.workers)
        {
            if(this.worktime)
                worker.goWork();
            else
                worker.setWay();

            if(worker.isNear(Factory.magazine) && worker.hasItem)
                this.updateMagazine(worker);

            worker.update();
            worker.draw(g2);
        }

        for(Machine machine : this.machines)
        {
            if(!machine.getWorker().hasItem)
                machine.update();
            machine.draw(g2);
        }

        repaint();
    }

    private void createLabels()
    {
        this.labels = new ArrayList<>();
        this.labels.add(new JLabel("Number of screws: " + this.screws));
        this.labels.add(new JLabel("Number of constructions: " + this.constructions));
        this.labels.add(new JLabel("Number of products: " + this.products));

        for (int i = 0 ; i < this.labels.size(); i++)
        {
            this.labels.get(i).setBounds(0, 40*i, Reference.WIDTH, Reference.HEIGHT);
            this.labels.get(i).setForeground(Color.WHITE);
            this.add(this.labels.get(i));
        }
    }

    private void updateLabels()
    {
        labels.get(0).setText("Number of screws: " + this.screws);
        labels.get(1).setText("Number of constructions: " + this.constructions);
        labels.get(2).setText("Number of products: " + this.products);
    }

    private void updateMagazine(Worker worker)
    {
        switch (worker.makes)
        {
            case "constructions":
                this.constructions++;worker.hasItem = false;break;
            case "screws":
                this.screws++;worker.hasItem = false;break;
            case "products":
                this.products++;worker.hasItem = false;break;
            default: break;
        }
        this.updateLabels();
    }


    public Graphics2D getGraphics2D()
    {
        return this.g2;
    }
}
