package app;

import java.awt.*;

public class ControlPoint extends SimulationObject{
    public ControlPoint(int x, int y, int r, int g, int b)
    {
        this.x = x;
        this.y = y;
        this.color = new Color(r,g,b);
    }
}