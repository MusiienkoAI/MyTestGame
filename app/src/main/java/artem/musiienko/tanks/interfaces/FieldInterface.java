package artem.musiienko.tanks.interfaces;

import artem.musiienko.tanks.units.Unit;

/**
 * Created by artyom on 24.06.16.
 */
public interface FieldInterface {
     void removeUnit(Unit unit);
     void updateUnit(Unit unit);
     void addUnit(Unit unit);
}
