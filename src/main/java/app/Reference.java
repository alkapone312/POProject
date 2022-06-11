package app;

public class Reference {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static int PIXEL_SIZE = 4;
    public static final int ROWS = Reference.HEIGHT/Reference.PIXEL_SIZE;
    public static final int COLS = Reference.WIDTH/Reference.PIXEL_SIZE;
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int UP_LEFT = 4;
    public static final int UP_RIGHT = 5;
    public static final int DOWN_LEFT = 6;
    public static final int DOWN_RIGHT = 7;
    public static final int NUM_OF_WAYS = 8;
}
