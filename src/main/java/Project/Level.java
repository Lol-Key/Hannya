package Project;

import Project.Controllers.TaskStatmentController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class Level {
    public static Level currentLevel = new Level();
    public ArrayList<Boolean> lvls;

    public Timer timer;
    public boolean startedTimer;
    public boolean submit;
    Level(){
        lvls = new ArrayList<>(6);
        submit = false;
        lvls.addAll(Collections.nCopies(6, Boolean.FALSE));
    }

    public boolean isMarked(int idx){
        return lvls.get(idx);
    }

    public void switchLvl(int idx){
        lvls.set(idx, !lvls.get(idx));
    }

    public void startTimer(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startedTimer = false;
                TaskStatmentController.skip();
            }
        }, 900000);
    }
}
