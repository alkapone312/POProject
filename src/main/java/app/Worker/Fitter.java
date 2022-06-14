package app.Worker;

import app.Factory;
import app.Reference;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Fitter extends Worker{
    private double modifier;
    public Fitter()
    {
        this.makes = "products";
        //red
        this.color = new Color(210,30,0);
        this.setSprite("Worker1.png");
    }

}
