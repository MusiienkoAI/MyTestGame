package artem.musiienko.tanks.units;


import artem.musiienko.tanks.interfaces.DemolishInterface;
import artem.musiienko.tanks.utils.Consts;

/**
 * Created by artyom on 22.06.16.
 */
public abstract class Walker extends Unit implements DemolishInterface {



    private int margin;

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public void walk(int vector) {
        switch (vector) {
            case Consts.Vector.UP: {
                yStart -= margin;
                yEnd -= margin;
                break;
            }
            case Consts.Vector.DOWN: {
                yStart += margin;
                yEnd += margin;
                break;
            }
            case Consts.Vector.RIGHT: {
                xStart += margin;
                xEnd += margin;
                break;
            }
            case Consts.Vector.LEFT: {
                xStart -= margin;
                xEnd -= margin;
                break;
            }
        }
    }


}
