package app;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public Factory canvas;
    private static int speed = 0;

    public Window()
    {
        super();
        this.setTitle("Symulacja");
    }

    public static void main (String[] args)
    {
        //Creates window with canvas
        Window window = new Window();
        window.canvas = new Factory();
        window.canvas.setPreferredSize(new Dimension(Reference.WIDTH, Reference.HEIGHT));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(window.canvas);
        window.pack();
        window.setLocation(200,0);
        //window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setResizable(false);
        //SimulationListener listener = new SimulationListener(window);


        //Creates window with settings
        Window settings = new Window();
        settings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        settings.setSize(200,window.getHeight());
        settings.setLocation(window.getX()-settings.getWidth()-100, window.getY());
        Settings settingsPanel = new Settings();
        settings.add(settingsPanel);
        settings.setResizable(false);
        settings.setVisible(true);


        //game loop
        while (true)
        {
            //speed control from control panel
            speed = 202-2*settingsPanel.getSpeedValue();

            try { Thread.sleep(speed); } catch (Exception e){}
            if(!settingsPanel.stopped) {
                //invoke main draw function with given context
                window.canvas.draw(window.canvas.getGraphics2D());
            }
        }
    }
}
