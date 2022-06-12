package app;

import app.Machine.*;
import app.Utils.HR;
import app.Worker.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Factory extends JPanel {
    public ArrayList<Worker> workers;
    public ArrayList<Machine> machines;
    public ArrayList<ControlPoint> controlpoints;

    public static int screws;
    public static int constructions;
    public static int products;
    public static SimulationObject magazine;

    private int social;
    private double budget;
    private boolean worktime = true;
    private ArrayList<ControlPoint> entranceWorkPath;
    private ArrayList<ControlPoint> workSocialPath;
    private ArrayList<ControlPoint> socialEntrancePath;
    private ArrayList<JLabel> labels;
    private BufferedImage buffer;
    private Graphics2D g2;

    private Map map;

    public Factory() {
        //Creates buffer and graphics context for drawing
        this.buffer = new BufferedImage(Reference.WIDTH, Reference.HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.g2 = buffer.createGraphics();

        //init all simulation
        this.initFactoryFields();
        this.setStartingWorkstations();
        this.setControlPoints();
        this.setPaths();
        this.createLabels();
    }

    public void paintComponent(Graphics g) {
        g.drawImage(this.buffer, 0, 0, null);
    }

    public void draw(Graphics2D g2) {

        this.map.draw(g2);
        Factory.magazine.draw(g2);

        for (ControlPoint controlPoint : this.controlpoints) {
            controlPoint.draw(g2);
        }

        for (Worker worker : this.workers) {
            if (this.worktime)
                worker.goWork();
            else
                worker.setWay();

            if (worker.isNear(Factory.magazine) && worker.hasItem)
                this.updateMagazine(worker);

            worker.update();
            if(map.getCharAtPos(worker.getX(), worker.getY()) == 'a')
                worker.revertMove();
            worker.draw(g2);
        }

        for (Machine machine : this.machines) {
            if (!machine.getWorker().hasItem)
                machine.update();
            machine.draw(g2);
        }

        repaint();
    }

    public Graphics2D getGraphics2D() {
        return this.g2;
    }

    private void updateMagazine(Worker worker) {
        switch (worker.makes) {
            case "constructions":
                Factory.constructions++;
                worker.hasItem = false;
                break;
            case "screws":
                Factory.screws++;
                worker.hasItem = false;
                break;
            case "products":
                Factory.products++;
                worker.hasItem = false;
                break;
            default:
                break;
        }
        this.updateLabels();
    }

    private void initFactoryFields() {
        //initialize map instance
        this.map = new Map();

        //initialize workers and machines and control points
        this.workers = new ArrayList<>();
        this.machines = new ArrayList<>();
        this.controlpoints = new ArrayList<>();

        //initialize factory paths
        this.entranceWorkPath = new ArrayList<>();
        this.socialEntrancePath = new ArrayList<>();
        this.workSocialPath = new ArrayList<>();

        //initialize factory magazine
        Factory.magazine = new SimulationObject(Reference.COLS - 5, 30);
        Factory.magazine.setColor(new Color(50, 50, 50));
    }

    private void setStartingWorkstations() {
        //assign starting workers and machines
        this.workers.add(new Turner());
        this.workers.add(new Welder());
        this.workers.add(new Fitter());
        this.machines.add(new Lathe());
        this.machines.add(new WeldingMachine());
        this.machines.add(new AssemblyTable());

        for(Machine machine : machines) {
            int x = this.map.machinesOcupiedCoordinates.getFirstFreeCoordinates().getFirst();
            int y = this.map.machinesOcupiedCoordinates.getFirstFreeCoordinates().getSecond();
            this.map.machinesOcupiedCoordinates.setFirstFreeCoordinatesOcupied();
            machine.setX(x).setY(y);
        }
        for (int i = 0; i < 3; i++) {
            HR.setWorkstand(this.workers.get(i), this.machines.get(i));
        }
    }

    private void setControlPoints() {
        //add control points in various places of factory
        this.controlpoints.add(new ControlPoint(14, 85, 255, 255, 255));// entrance hallway
        this.controlpoints.add(new ControlPoint(24, 85, 255, 255, 255));// entrance - long corridor entrance
        this.controlpoints.add(new ControlPoint(44, 85, 255, 255, 255));// long corridor
        this.controlpoints.add(new ControlPoint(63, 85, 255, 255, 255));// long corridor-social room entrance
        this.controlpoints.add(new ControlPoint(44, 72, 255, 255, 255));// long corridor- factory room entrance
        this.controlpoints.add(new ControlPoint(44, 40, 255, 255, 255));// factory room
        this.controlpoints.add(new ControlPoint(83, 85, 255, 255, 255));// social room

    }

    private void setPaths() {
        //entrance - work path
        this.entranceWorkPath.add(controlpoints.get(0));
        this.entranceWorkPath.add(controlpoints.get(1));
        this.entranceWorkPath.add(controlpoints.get(2));
        this.entranceWorkPath.add(controlpoints.get(4));
        this.entranceWorkPath.add(controlpoints.get(5));

        //work - social path
        this.workSocialPath.add(controlpoints.get(5));
        this.workSocialPath.add(controlpoints.get(4));
        this.workSocialPath.add(controlpoints.get(2));
        this.workSocialPath.add(controlpoints.get(3));
        this.workSocialPath.add(controlpoints.get(6));

        //social - entrance path
        this.socialEntrancePath.add(controlpoints.get(6));
        this.socialEntrancePath.add(controlpoints.get(4));
        this.socialEntrancePath.add(controlpoints.get(2));
        this.socialEntrancePath.add(controlpoints.get(1));
        this.socialEntrancePath.add(controlpoints.get(0));
    }

    private void createLabels() {
        this.labels = new ArrayList<>();
        this.labels.add(new JLabel("Number of screws: " + Factory.screws));
        this.labels.add(new JLabel("Number of constructions: " + Factory.constructions));
        this.labels.add(new JLabel("Number of products: " + Factory.products));

        for (int i = 0; i < this.labels.size(); i++) {
            this.labels.get(i).setBounds(0, 40 * i, Reference.WIDTH, Reference.HEIGHT);
            this.labels.get(i).setForeground(Color.WHITE);
            this.add(this.labels.get(i));
        }
    }

    private void updateLabels() {
        labels.get(0).setText("Number of screws: " + Factory.screws);
        labels.get(1).setText("Number of constructions: " + Factory.constructions);
        labels.get(2).setText("Number of products: " + Factory.products);
    }
}
