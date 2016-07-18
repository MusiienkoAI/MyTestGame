package artem.musiienko.tanks.interfaces;

import artem.musiienko.tanks.presenters.GamePresenter;
import artem.musiienko.tanks.units.Bullet;
import artem.musiienko.tanks.utils.customviews.Field;

/**
 * Created by artyom on 25.06.16.
 */
public interface FireInterface {


    Bullet createBullet(Field field, GamePresenter presenter, int autoindex);

    int getWatchVector();

    void watch(int vector);
}
