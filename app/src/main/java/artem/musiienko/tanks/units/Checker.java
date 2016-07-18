package artem.musiienko.tanks.units;

import android.view.View;

import java.util.HashMap;

import artem.musiienko.tanks.interfaces.DemolishInterface;
import artem.musiienko.tanks.utils.customviews.Field;

/**
 * Created by artyom on 26.06.16.
 */
public class Checker extends Unit implements DemolishInterface {
    @Override
    public void demolish(Field field) {
        HashMap<Integer, View> map = field.getMap();
        field.removeView(map.get(getId()));
    }
}
