package app;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class SimulationObject {
    protected int x;
    protected int y;
    protected Color color;
    protected String name;
    
    public SimulationObject()
    {
        Random r = new Random();
        this.x = (int)(r.nextDouble()*Reference.COLS);
        this.y = (int)(r.nextDouble()*Reference.ROWS);
        this.color = Color.RED;
        this.name = "";
    }

    public SimulationObject(int x,int y)
    {
        Random r = new Random();
        this.x = x;
        this.y = y;
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
    
    public void drawWorker(Graphics2D g2) {
    	BufferedImage img = null;

    	try 
    	{
    	    img = ImageIO.read(new File(name)); 
    	} 
    	catch (IOException e) 
    	{
    	    e.printStackTrace();
    	}
    	g2.drawImage(img, null, x*Reference.PIXEL_SIZE, y*Reference.PIXEL_SIZE);
    }

    public boolean isNear(SimulationObject obj)
    {
        if (obj.getX() <= this.getX()+1
                        &&
            obj.getX() >= this.getX()-1
                        &&
            obj.getY() <= this.getY()+1
                        &&
            obj.getY() >= this.getY()-1)
            return true;
        return false;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public SimulationObject setX(int x)
    {
        this.x = x;
        return this;
    }

    public SimulationObject setY(int y)
    {
        this.y = y;
        return this;
    }

    public void setColor(Color c)
    {
        this.color = c;
    }
}
