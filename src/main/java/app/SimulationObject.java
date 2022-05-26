package app;

import java.awt.*;

public class SimulationObject {
    private int x;
    private int y;
    private Color color;

    public SimulationObject()
    {
        this.x = 10;
        this.y = 10;
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
