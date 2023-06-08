package Project;

public class Level {
    public static Level currentLevel = new Level();
    public boolean lvl1;
    public boolean lvl2;

    Level(){
        lvl1 = false;
        lvl2 = false;
    }

    public void switchLvl1(){
        lvl1 = !lvl1;
    }
    public void switchLvl2(){
        lvl2= !lvl2;
    }
    public void toFalse(){
        lvl1 = false;
        lvl2 = false;
    }

}
