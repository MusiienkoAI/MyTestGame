package artem.musiienko.tanks.presenters;

import android.support.v7.widget.RecyclerView;

import artem.musiienko.tanks.models.ServerModel;

/**
 * Created by artyom on 05.07.16.
 */
public interface ServersPresenter {

    void setAdapter(RecyclerView recyclerView);


    void checkPassword(String id, String password);

    void onServerClick(String id);


    void onDestroy();

}
