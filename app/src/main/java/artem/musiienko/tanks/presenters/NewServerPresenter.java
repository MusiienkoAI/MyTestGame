package artem.musiienko.tanks.presenters;

import android.support.v7.widget.AppCompatSpinner;

/**
 * Created by artyom on 05.07.16.
 */
public interface NewServerPresenter {


    void initSpinner(AppCompatSpinner spinner);

    void setPrivate(boolean aPrivate);

    void setName(String name);

    void setPassword(String password);

    void create();
}
