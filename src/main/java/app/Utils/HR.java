package app.Utils;

import app.ControlPoint;
import app.Factory;
import app.Machine.Machine;
import app.Reference;
import app.Settings;
import app.Worker.Worker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class HR {
    public static void setWorkstand(Worker w, Machine m) {
        m.setWorker(w);
        w.setWorkstand(m);
    }

    public static void workerRoutine(Worker worker) {
        if(worker.path.size() == 0 || Factory.dayTime == 0) {
            if (Factory.dayTime == 0) {
                Random r = new Random();
                worker.setX(r.nextInt(14) + 6);
                worker.setY(r.nextInt(4) + Reference.ROWS - 3);
                worker.path = new ArrayList<>();
            }
            if (!worker.isTired()){
                if (!worker.isWorking() && Factory.dayTime < 3000) {
                    worker.path = (ArrayList<ControlPoint>) Factory.entranceWorkPath.clone();
                    worker.setWorking();
                }

                if (
                    !worker.isResting()
                            && Factory.dayTime > 4000 - Settings.restVal
                            && Factory.dayTime < 4000
                 ) {
                    worker.path = (ArrayList<ControlPoint>) Factory.workSocialPath.clone();
                    worker.setResting();
                }

                if (!worker.isWorking() && Factory.dayTime > 4000) {
                    worker.path = (ArrayList<ControlPoint>) Factory.workSocialPath.clone();
                    Collections.reverse(worker.path);
                    worker.setWorking();
                }

                if (!worker.isResting() && Factory.dayTime > 7800) {
                    worker.path = (ArrayList<ControlPoint>) Factory.entranceWorkPath.clone();
                    Collections.reverse(worker.path);
                    worker.setResting();
                }
            }
            else if (!worker.isResting()){
                worker.path = (ArrayList<ControlPoint>) Factory.workSocialPath.clone();
                worker.setResting();
            }
        }

        if(worker.isWorking()) {
            worker.goWork();
        }

        if(worker.isResting()) {
            worker.setWay();
        }

        if(worker.path.size() > 0) {
            worker.goTroughPath();
        }

        worker.update();
    }
}
