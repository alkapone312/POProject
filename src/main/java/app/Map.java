package app;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


//MAP GENERATIONS IS BASED ON 2D PERLIN NOISE GENERATOR
public class Map {
    private char[][] cMap = new char[Reference.COLS][Reference.ROWS];
    private BufferedImage mapBuffer;
    private Graphics2D mapBufferGraphics;
    private boolean mapBuffered = false;

    public Map()
    {
        this.mapBuffer = new BufferedImage(Reference.WIDTH, Reference.HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.mapBufferGraphics = this.mapBuffer.createGraphics();
    }

    public void draw(Graphics2D g2)
    {
        Color color;
        if(!mapBuffered) {
            Random r = new Random();
            for (int x = 0; x < Reference.COLS; x++) {
                for (int y = 0; y < Reference.ROWS; y++) {
                    switch (cMap[x][y]){
                        default:
                            color = new Color((int)(r.nextDouble()*255));
                            mapBufferGraphics.setColor(color);
                            mapBufferGraphics.fillRect(x*Reference.PIXEL_SIZE,y*Reference.PIXEL_SIZE, Reference.PIXEL_SIZE, Reference.PIXEL_SIZE);
                            break;

                    }
                }
            }
            this.mapBuffered = true;
        }
        g2.drawImage(this.mapBuffer, 0, 0, null);
    }
}