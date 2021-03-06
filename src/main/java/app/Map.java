package app;

import app.Machine.OcupiedCoordinates;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class Map {

    public OcupiedCoordinates machinesOcupiedCoordinates;

    private char[][] cMap = new char[Reference.COLS][Reference.ROWS];
    private BufferedImage mapBuffer;
    private Graphics2D mapBufferGraphics;
    private boolean mapBuffered = false;

    public Map() {
        this.mapBuffer = new BufferedImage(Reference.WIDTH, Reference.HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.mapBufferGraphics = this.mapBuffer.createGraphics();
        this.machinesOcupiedCoordinates = new OcupiedCoordinates();
        this.fill_array_from_file("mapfile(128x96).txt");
        this.getMachineFields();
    }

    public void draw(Graphics2D g2) {
        Color color;
        if (!mapBuffered) {
            Random r = new Random();
            for (int x = 0; x < Reference.COLS; x++) {
                for (int y = 0; y < Reference.ROWS; y++) {
                    switch (cMap[x][y]) {
                        case 'a':
                            color = new Color(0, 0, 0);
                            break;
                        case 'b':
                            color = new Color(0, 245, 255);
                            break;
                        case 'c':
                        case 'm':
                            color = new Color(100, 100, 100);
                            break;
                        case 'd':
                            color = new Color(160, 160, 160);
                            break;
                        case 'e':
                            color = new Color(93, 229, 6);
                            break;
                        case 'q':
                            color = new Color(164, 120, 34);
                            break;
                        default:
                            color = new Color(255, 255, 255);
                            break;
                    }
                    //tutaj kolor jest drukowany do mapki w odpowiednim x i y
                    mapBufferGraphics.setColor(color);
                    mapBufferGraphics.fillRect(x * Reference.PIXEL_SIZE, y * Reference.PIXEL_SIZE, Reference.PIXEL_SIZE, Reference.PIXEL_SIZE);
                }
            }
            this.mapBuffered = true;
        }
        g2.drawImage(this.mapBuffer, 0, 0, null);
    }

    public char getCharAtPos(int x, int y)
    {
        return this.cMap[x][y];
    }

    private void fill_array_from_file(String file) {
        BufferedReader czytacz = null;
        try {
            czytacz = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            System.out.println("B????d inicjacji bufora.");
        }

        String line = "";
        int wiersz = 0;

        try {
            while (czytacz.ready()) {
                try {
                    line = czytacz.readLine();
                } catch (IOException e) {
                    System.out.println("B????d odczytu pliku");
                }
                for (int kolumna = 0; kolumna < line.length(); kolumna++) {
                    cMap[kolumna][wiersz] = line.charAt(kolumna);
                }
                wiersz++;
            }
        } catch (IOException e) {
            System.out.println("Unable to read from buffer");
        }
    }

    private void getMachineFields()
    {
        for (int x = 0; x < Reference.COLS; x++) {
            for (int y = 0; y < Reference.ROWS; y++) {
                if(cMap[x][y] == 'm') {
                    this.machinesOcupiedCoordinates.setNewCoordinates(x, y);
                }
            }
        }
    }
}