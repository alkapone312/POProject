package app.Machine;

public class Coordinates {
    public int[][] machine_coordinates = new int[3][2];
    private int i=0;
    private int n=0;
    //1
    {
        machine_coordinates[i][0]=10;//x
        machine_coordinates[i][1]=13 + n;//y
        i++; n += 5;
    }
    //2
    {
        machine_coordinates[i][0]=10;//x
        machine_coordinates[i][1]=13 + n;//y
        i++; n += 5;
    }
    //3
    {
        machine_coordinates[i][0]=10;//x
        machine_coordinates[i][1]=13 + n;//y
        i++; n += 5;
    }

    public int[][] copyarray(){

        return machine_coordinates.clone();
    }

}
