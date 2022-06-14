package app.Utils;

import java.util.Random;

//class is giving the random number form given range with a higher chance to get the preferred one
public class PreferredRandom {
    Random r;

    //array to store values for draw
    int[] array;

    public PreferredRandom(int range, int preffered, int times)
    {
        //if preffered is -1 then its normal random in range
        if (preffered == -1)
            times = 0;

        //filling array with multiple preffered ones
        r = new Random();
        array = new int[range + times];
        for (int i = 0 ; i < range + times; i++)
        {
            if(i<range)
                array[i] = i;
            else
                array[i] = preffered;
        }
    }

    //get random key from array
    public int nextPrefferedInt()
    {
        return array[r.nextInt(array.length)];
    }
}