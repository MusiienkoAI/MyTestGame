package artem.musiienko.mytestgame.interfaces;

import artem.musiienko.mytestgame.units.Unit;

/**
 * Created by artyom on 24.06.16.
 */
public interface FieldInterface {
     void removeUnit(Unit unit);
     void updateUnit(Unit unit);
     void addUnit(Unit unit);
}
