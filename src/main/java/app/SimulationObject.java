package app;

import java.awt.*;
import java.util.Random;

public class SimulationObject {
    protected int x;
    protected int y;
    protected Color color;

    public SimulationObject()
    {
        Random r = new Random();
        this.x = (int)(r.nextDouble()*Reference.COLS);
        this.y = (int)(r.nextDouble()*Reference.ROWS);
        this.color = Color.RED;
    }


    public void draw(Graphics2D g2)
    {
        //draws object of given color to the given graphics context
        g2.setColor(color);
        g2.fillRect(
            Reference.PIXEL_SIZE*this.x,
            Reference.PIXEL_SIZE*this.y,
            Reference.PIXEL_SIZE,
            Reference.PIXEL_SIZE
        );
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }
}
