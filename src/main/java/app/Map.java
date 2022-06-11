package app;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Map {
    private char[][] cMap = new char[Reference.COLS][Reference.ROWS];
    private BufferedImage mapBuffer;
    private Graphics2D mapBufferGraphics;
    private boolean mapBuffered = false;

    public Map()
    {
        this.mapBuffer = new BufferedImage(Reference.WIDTH, Reference.HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.mapBufferGraphics = this.mapBuffer.createGraphics();
        fill_map();
    }
    
    public void fill_map(){
    	
    	String temp;
    	
    	try {
    		//fill cmap with em letters:
        	BufferedReader tx = new BufferedReader(new FileReader("C:\\Users\\user\\Desktop\\PoProj\\POProject\\src\\main\\java\\app\\maptext.txt"));
                for (int y = 0; y < Reference.ROWS; y++) {
                	temp = tx.readLine();
                	for(int x = 0 ; x<Reference.COLS ; x++) {
                		cMap[x][y] = temp.charAt(x);
                	}
                }
        	tx.close();
        	
    	}
    	
    	catch(FileNotFoundException ex) {
    		ex.printStackTrace();
    	}
    	catch(IOException ex) {
    		ex.printStackTrace();
    	}
    	
    }

    public void draw(Graphics2D g2){
    	
        Color color;
        if(!mapBuffered) {
            for (int x = 0; x < Reference.COLS; x++) {
                for (int y = 0; y < Reference.ROWS; y++) {
                	
                    switch (cMap[x][y]){
                        default:
                            color = new Color(255,0,0);
                            break;
                        case 'a':
                        	color = new Color(0,0,0); //black
                            break;
                        case 'b':
                        	color = new Color(106,217,230); //cyan
                            break;
                        case 'c':
                        	color = new Color(56,56,56); //dark gray
                            break;
                        case 'd':
                        	color = new Color(230,142,18); //orange
                            break;
                        case 'e':
                        	color = new Color(230,215,18); //yellow
                            break;
                        case 'f':
                        	color = new Color(28,237,9); //green
                            break;
                        case 'g':
                        	color = new Color(82,63,9); //brown
                            break;
                        	
                    }
                    
                    
                    //tutaj kolor jest drukowany do mapki w odpowiednim x i y
                    mapBufferGraphics.setColor(color);
                    mapBufferGraphics.fillRect(x*Reference.PIXEL_SIZE,y*Reference.PIXEL_SIZE, Reference.PIXEL_SIZE, Reference.PIXEL_SIZE);
                }
            }
            this.mapBuffered = true;
        }
        g2.drawImage(this.mapBuffer, 0, 0, null);
    }
}