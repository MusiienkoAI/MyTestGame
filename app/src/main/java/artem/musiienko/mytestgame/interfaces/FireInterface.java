package artem.musiienko.mytestgame.interfaces;

import artem.musiienko.mytestgame.presenters.GamePresenter;
import artem.musiienko.mytestgame.units.Bullet;
import artem.musiienko.mytestgame.utils.customviews.Field;

/**
 * Created by artyom on 25.06.16.
 */
public interface FireInterface {


    Bullet createBullet(Field field, GamePresenter presenter, int autoindex);

    int getWatchVector();

    void watch(int vector);
}
