package artem.musiienko.mytestgame.presenters;

import java.util.HashMap;

import artem.musiienko.mytestgame.units.Bullet;
import artem.musiienko.mytestgame.units.Unit;
import artem.musiienko.mytestgame.units.Walker;
import artem.musiienko.mytestgame.utils.customviews.Field;

/**
 * Created by artyom on 22.06.16.
 */
public interface GamePresenter {


    void setUnits(HashMap<Integer,Unit> units);

    void setField(Field field);

    void addUnit(Unit unit);

    void removeUnit(Unit unit);

    void updateUnit(Unit unit);

    void checkHit(Bullet unit);

    void startGame();

    int getRadius();

    int getWidth();

    int getHeight();

    void setCurId(Integer curId);

    void moveLeft();

    void moveRight();

    void moveDown();

    void moveUp();

    void watchLeft();

    void watchRight();

    void watchDown();

    void watchUp();

    void addTank(int xMargin, int yMargin);

    void addWall(int xMargin, int yMargin);

    void addMan(int xMargin, int yMargin);

    void addChecker(int xMargin, int yMargin);

    void removeChecker();

    void next();

    void fire();

    void onDestroy();
}
