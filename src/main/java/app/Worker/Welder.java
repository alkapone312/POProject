package app.Worker;

import java.awt.*;

public class Welder extends Worker{
    private double modifier;
    public Welder()
    {
        this.makes = "constructions";
        //Yellow
        this.color = new Color(190, 200, 0);
        this.name = "Worker3.png";
    }
}
