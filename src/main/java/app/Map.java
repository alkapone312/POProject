package app;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

//MAP GENERATIONS IS BASED ON 2D PERLIN NOISE GENERATOR
public class Map {
    // TODO: Stworzenie mapy ze znaków ascii
    private char[][] cMap = new char[Reference.COLS/Reference.PIXEL_SIZE][Reference.ROWS/Reference.PIXEL_SIZE];
    private BufferedImage mapBuffer;
    private Graphics2D mapBufferGraphics;
    private boolean mapBuffered = false;

    public Map()
    {
        this.mapBuffer = new BufferedImage(Reference.WIDTH, Reference.HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.mapBufferGraphics = this.mapBuffer.createGraphics();
    }
    public void fill_array_from_file() throws IOException {
        try (BufferedReader czytacz = new BufferedReader(new FileReader("mapfile(128x96).txt"))) {
            String line = czytacz.readLine();
            int wiersz = 0;
            while (line != null) {
                for (int kolumna = 0; kolumna < line.length(); kolumna++) {
                    cMap[wiersz][kolumna] = line.charAt(kolumna);
                }
                wiersz++;
                line = czytacz.readLine();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }//Funkcja nie funkcjonuje poprawnie ale dziala(nie wiem czy trzeba ja wywolac gdzies bo sie pogubilem)
    }    //array cmap jest pusty pomimo jej
    public void draw(Graphics2D g2)
    {
        Color color;
        if(!mapBuffered) {
            Random r = new Random();
            for (int x = 0; x < Reference.COLS/Reference.PIXEL_SIZE; x++) {
                for (int y = 0; y < Reference.ROWS/Reference.PIXEL_SIZE; y++) {
                    // TODO: Wykorzystać tego switcha do narysowania mapy
                    // On przelatuje przez wszystkie znaki jakie są w zmiennej cMap i może ją przerobić na konkretne kolory, dla każdego case'a po prostu trzeba dać inny kolor
                    switch (cMap[x][y]){
                        default:
                            color = new Color(100,100,100);
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