package artem.musiienko.tanks.presenters;

import android.support.v7.widget.RecyclerView;

/**
 * Created by artyom on 18.07.16.
 */
public interface LobbyPresenter {

    void setAdapter(RecyclerView recyclerView);


    void setUserReady(String userId, int ready);

    void onDestroy();
}
