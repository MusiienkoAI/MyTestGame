package artem.musiienko.tanks.adapters;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import artem.musiienko.tanks.R;
import artem.musiienko.tanks.models.ServerModel;
import artem.musiienko.tanks.models.UserModel;
import artem.musiienko.tanks.presenters.LobbyPresenter;
import artem.musiienko.tanks.presenters.ServersPresenter;
import artem.musiienko.tanks.utils.Consts;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by artyom on 18.07.16.
 */
public class LobbyUserAdapter extends RecyclerView.Adapter<LobbyUserAdapter.UserViewHolder> {


    public static final String TAG = LobbyUserAdapter.class.getSimpleName();

    private LobbyPresenter presenter;

    private RealmResults<UserModel> userModels;

    private String userId;

    Realm realm;

    public LobbyUserAdapter(LobbyPresenter presenter, String serverId, String userId) {
        this.presenter = presenter;
        realm = Realm.getDefaultInstance();
        userModels = realm.where(UserModel.class).equalTo("serverId", serverId).findAll();
        this.userId = userId;

    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lobby_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final UserModel model = userModels.get(position);
        holder.tvName.setText(model.getName());


        if (model.isReady() == 1) {
            holder.cbReady.setChecked(true);
        } else {
            holder.cbReady.setChecked(false);
        }

        if (model.getId().equals(userId)) {
            holder.cbReady.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        if (model.isReady() == 1) {
                            presenter.setUserReady(userId, 0);
                            realm.beginTransaction();
                            model.setReady(0);
                            realm.commitTransaction();
                        } else {
                            presenter.setUserReady(userId, 1);
                            realm.beginTransaction();
                            model.setReady(1);
                            realm.commitTransaction();
                        }
                    }
                    return false;
                }
            });
        } else {
            holder.cbReady.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.cb_ready)
        AppCompatCheckBox cbReady;


        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
