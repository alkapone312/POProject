package app.Utils;

import app.Machine.Machine;
import app.Worker.Worker;

public class HR {
    public static void setWorkstand(Worker w, Machine m)
    {
        m.setWorker(w);
        w.setWorkstand(m);
    }
}
