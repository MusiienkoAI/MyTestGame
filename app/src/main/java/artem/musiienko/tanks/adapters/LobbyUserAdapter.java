package artem.musiienko.tanks.adapters;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import artem.musiienko.tanks.R;
import artem.musiienko.tanks.models.ServerModel;
import artem.musiienko.tanks.models.UserModel;
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


    private ServersPresenter presenter;

    private RealmResults<UserModel> userModels;

    public LobbyUserAdapter(ServersPresenter presenter, String serverId) {
        this.presenter = presenter;
        Realm realm = Realm.getDefaultInstance();
        userModels = realm.where(UserModel.class).equalTo("serverId", serverId).findAll();


    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lobby_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        UserModel model = userModels.get(position);
        holder.tvName.setText(model.getName());


        if (model.isReady() == 1) {
            holder.cbReady.setChecked(true);
        } else {
            holder.cbReady.setChecked(false);
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
