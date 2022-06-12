package app.Machine;
import app.Utils.Pair;
import java.util.ArrayList;

public class OcupiedCoordinates {
    private ArrayList<Pair<Integer, Integer>> coordinates;
    private int size;
    private ArrayList<Boolean> isSet;

    public OcupiedCoordinates()
    {
        this.coordinates = new ArrayList<>();
        this.isSet = new ArrayList<>();
    }

    public void setNewCoordinates(int x, int y) {
        this.coordinates.add(new Pair<Integer, Integer>(x, y));
        this.isSet.add(false);
        this.size++;
    }

    public Pair<Integer, Integer> getCoordinates(int index) {
        return this.coordinates.get(index);
    }

    public Pair<Integer, Integer> getFirstFreeCoordinates() {
        for(int i = 0 ; i < isSet.size(); i++) {
            if(!isSet.get(i)) {
                return this.coordinates.get(i);
            }
        }
        return new Pair<>(0,0);
    }

    public boolean getOcupied(int index) {
        return this.isSet.get(index);
    }

    public OcupiedCoordinates setCoordinates(int index, int x, int y) {
        this.coordinates.set(index, new Pair<Integer, Integer>(x, y));
        return this;
    }

    public OcupiedCoordinates setFirstFreeCoordinatesOcupied() {
        for(int i = 0 ; i < isSet.size(); i++) {
            if(!this.isSet.get(i)) {
                this.isSet.set(i, true);
                break;
            }
        }

        return this;
    }

    public OcupiedCoordinates setOcupied(int index) {
        this.isSet.set(index, true);
        return this;
    }

    public OcupiedCoordinates setFree(int index) {
        this.isSet.set(index, false);
        return this;
    }
}
