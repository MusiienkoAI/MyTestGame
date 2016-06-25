package artem.musiienko.mytestgame.units;

import artem.musiienko.mytestgame.interfaces.DemolishInterface;
import artem.musiienko.mytestgame.utils.Consts;

/**
 * Created by artyom on 22.06.16.
 */
public class Tank extends Walker  {


    private int watchVector;

    public Tank() {
        watchVector = Consts.Vector.RIGHT;
    }

    @Override
    public void demolish() {

    }



    public void watch(int vector) {
       watchVector = vector;
    }

    public int getWatchVector() {
        return watchVector;
    }
}
