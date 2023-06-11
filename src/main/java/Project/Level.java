package Project;

import java.util.ArrayList;
import java.util.Collections;

public class Level {
    public static Level currentLevel = new Level();
    public ArrayList<Boolean> lvls;

    Level(){
        lvls = new ArrayList<>(6);
        lvls.addAll(Collections.nCopies(6, Boolean.FALSE));
    }

    public boolean isMarked(int idx){
        return lvls.get(idx);
    }

    public void switchLvl(int idx){
        lvls.set(idx, !lvls.get(idx));
    }

}
