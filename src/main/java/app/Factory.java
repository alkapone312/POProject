package app;

import app.Machine.*;
import app.Utils.HR;
import app.Utils.PerlinNoise;
import app.Worker.*;
import chart.Chart;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Factory extends JPanel {
    public ArrayList<Worker> workers;
    public ArrayList<Machine> machines;
    public ArrayList<ControlPoint> controlpoints;

    public static int screws;
    public static int constructions;
    public static int products;
    public static int dayTime = 0;
    public static int day = 0;
    public static SimulationObject magazine;
    public static ArrayList<ControlPoint> entranceWorkPath;
    public static ArrayList<ControlPoint> workSocialPath;
    public static ArrayList<ControlPoint> socialEntrancePath;

    private int social;
    public static double budget = 10000;
    private boolean worktime = true;
    private ArrayList<JLabel> labels;
    private BufferedImage buffer;
    private Graphics2D g2;
    private Chart chart;
    private int chartRefreshingRate = 8;

    private Map map;
    private Market market = new Market();

    public Factory() {
        //Creates buffer and graphics context for drawing
        this.buffer = new BufferedImage(Reference.WIDTH, Reference.HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.g2 = buffer.createGraphics();
        this.chart = new Chart();
        this.chart.addChart("Factory daytime", 0.0, 8000.0);
        this.chart.addChart("Average sanity", 0.0, 4000.0);

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
        if (Factory.dayTime == 1) {
        	if(Factory.budget<=0) {endSimulation();}

            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, Reference.WIDTH, Reference.HEIGHT);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Calibri", Font.PLAIN, 28));
            g2.drawString("Dzień: " + Factory.day, Reference.WIDTH/2-50, Reference.HEIGHT/2);
            repaint();
            try { Thread.sleep(2000); } catch (Exception e) {}
        }

        if(Factory.dayTime > 8000) {
            Factory.dayTime = 0;
            Factory.day++;
            this.market.update();
        }

        if(Factory.day%7 == 0 && Factory.day!=0 && Factory.dayTime==7999) {
        	PayWorkers();
            HireWorkerAddMachine();
        	market.sell();
            System.out.println("Selling products, budget after sell: " + Factory.budget);
        	market.buy(this.machines.size());
            System.out.println("Buying materials, budget after buying: " + Factory.budget);
            this.updateLabels();
        }

        this.map.draw(g2);

        int averageSanity = 0;
        for (Worker worker : this.workers) {

            HR.workerRoutine(worker);

            if (worker.isNear(Factory.magazine) && worker.hasItem)
                this.updateMagazine(worker);

            if(worker.getX() > Reference.COLS-1 || worker.getY() > Reference.ROWS-1 || map.getCharAtPos(worker.getX(), worker.getY()) == 'a')
                worker.revertMove();

            averageSanity += worker.getSanity();
            worker.draw(g2);
        }
        averageSanity /= workers.size();

        for (Machine machine : this.machines) {
            if (!machine.getWorker().hasItem)
                machine.update();
            machine.draw(g2);

            if(machine.isBroken()) {
                machine.payForRepair();
                machine.repair();
            }
        }
        if(Factory.dayTime%this.chartRefreshingRate == 0) {
            this.chart.addValue("Factory daytime", (double)Factory.dayTime);
            this.chart.addValue("Average sanity", (double)averageSanity);
            this.chart.graphCharts();
        }
        Factory.dayTime++;
        repaint();
    }

    public Graphics2D getGraphics2D() {
        return this.g2;
    }

    private void endSimulation() {
        System.out.println("STOP");
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
        Factory.entranceWorkPath = new ArrayList<>();
        Factory.socialEntrancePath = new ArrayList<>();
        Factory.workSocialPath = new ArrayList<>();

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
        for (int i = 0; i < this.workers.size(); i++) {
            HR.setWorkstand(this.workers.get(i), this.machines.get(i));
        }
    }

    private void HireWorkerAddMachine() {
        if(Factory.budget > 1800) {
            int leastItems = Factory.screws;
            int item = 1;
            if(Factory.constructions < leastItems) {
                leastItems = Factory.constructions;
                item = 2;
            }
            if(Factory.products < leastItems) {
                leastItems = Factory.products;
                item = 3;
            }

            Worker newWorker = null;
            Machine newMachine = null;
        	switch(item) {
        	case 1:
        		newWorker = new Turner();
        		newMachine = new Lathe();
        		Factory.budget = Factory.budget - Lathe.getPrice();
        		break;
        	case 2:
        		newWorker = new Welder();
        		newMachine = new WeldingMachine();
        		Factory.budget = Factory.budget - WeldingMachine.getPrice();
        		break;
        	case 3:
        		newWorker = new Fitter();
        		newMachine = new AssemblyTable();
        		Factory.budget = Factory.budget - AssemblyTable.getPrice();
        		break;
            default:
                break;
        	}

            if(newWorker != null && newMachine != null) {
                int z = this.map.machinesOcupiedCoordinates.getFirstFreeCoordinates().getFirst();
                int y = this.map.machinesOcupiedCoordinates.getFirstFreeCoordinates().getSecond();
                this.map.machinesOcupiedCoordinates.setFirstFreeCoordinatesOcupied();
                newMachine.setX(z).setY(y);
                HR.setWorkstand(newWorker, newMachine);
                this.workers.add(newWorker);
                this.machines.add(newMachine);

                System.out.println("Hired new worker: " + newWorker.getClass().getSimpleName());
                System.out.println("Buyed new machine: " + newMachine.getClass().getSimpleName() + " for price: " + newMachine.getPrice());
                System.out.println("Budget: " + Factory.budget);
            }
        }
    }

    private void PayWorkers() {
    	for(Worker worker : workers) {
    		Factory.budget = Factory.budget-worker.getSalary();
            System.out.println("Paying worker: " + worker.getSalary() + " Budget left: " + Factory.budget);
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
        Factory.entranceWorkPath.add(controlpoints.get(0));
        Factory.entranceWorkPath.add(controlpoints.get(1));
        Factory.entranceWorkPath.add(controlpoints.get(2));
        Factory.entranceWorkPath.add(controlpoints.get(4));
        Factory.entranceWorkPath.add(controlpoints.get(5));

        //work - social path
        Factory.workSocialPath.add(controlpoints.get(5));
        Factory.workSocialPath.add(controlpoints.get(4));
        Factory.workSocialPath.add(controlpoints.get(2));
        Factory.workSocialPath.add(controlpoints.get(3));
        Factory.workSocialPath.add(controlpoints.get(6));

        //social - entrance path
        Factory.socialEntrancePath.add(controlpoints.get(6));
        Factory.socialEntrancePath.add(controlpoints.get(4));
        Factory.socialEntrancePath.add(controlpoints.get(2));
        Factory.socialEntrancePath.add(controlpoints.get(1));
        Factory.socialEntrancePath.add(controlpoints.get(0));
    }

    private void createLabels() {
        this.labels = new ArrayList<>();
        this.labels.add(new JLabel("Number of screws: " + Factory.screws));
        this.labels.add(new JLabel("Number of constructions: " + Factory.constructions));
        this.labels.add(new JLabel("Number of products: " + Factory.products));
        this.labels.add(new JLabel("Current budget: " + Factory.budget));
        this.labels.add(new JLabel("Total Products Sold: " + market.Totalsold));

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
        labels.get(3).setText("Current Budget: " + Factory.budget);
        labels.get(4).setText("Total Products Sold:: " + market.Totalsold);
    }

    public static double getBudget() {
    	return Factory.budget;
    }

    public static void setBudget(double x) {
    	Factory.budget = x;
    }
}
