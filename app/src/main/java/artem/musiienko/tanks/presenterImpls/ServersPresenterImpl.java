package artem.musiienko.tanks.presenterImpls;

import android.support.v7.widget.RecyclerView;

import artem.musiienko.tanks.adapters.ServerAdapter;
import artem.musiienko.tanks.models.ServerModel;
import artem.musiienko.tanks.presenters.ServersPresenter;

import artem.musiienko.tanks.views.ServersView;

/**
 * Created by artyom on 05.07.16.
 */
public class ServersPresenterImpl implements ServersPresenter {


    private ServersView gameView;

    public ServersPresenterImpl(ServersView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void setAdapter(RecyclerView recyclerView) {
        recyclerView.setAdapter(new ServerAdapter(this));
    }

    @Override
    public void checkPassword(String id, String password) {

    }

    @Override
    public void onServerClick(String id) {

    }
}
