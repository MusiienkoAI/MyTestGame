package artem.musiienko.tanks.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import artem.musiienko.tanks.R;
import artem.musiienko.tanks.models.ServerModel;
import artem.musiienko.tanks.presenters.ServersPresenter;
import artem.musiienko.tanks.utils.Consts;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by artyom on 05.07.16.
 */
public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.ServerViewHolder> {


    private ServersPresenter presenter;

    private RealmResults<ServerModel> serverModels;

    public ServerAdapter(ServersPresenter presenter) {
        this.presenter = presenter;
        Realm realm = Realm.getDefaultInstance();
        serverModels = realm.where(ServerModel.class).equalTo("status", Consts.ServerStatus.ONLINE.ordinal()).findAll();


    }

    @Override
    public ServerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_server, parent, false);
        return new ServerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServerViewHolder holder, int position) {

        final ServerModel model = serverModels.get(position);
        holder.tvName.setText(model.getName());


        holder.tvPlayers.setText(
                String.format("%s %s/%s",
                        holder.itemView.getContext().getString(R.string.hint_players),
                        model.getCurCount(),
                        model.getMaxCount()));


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault());
        String time = simpleDateFormat.format(model.getCreateTime());
        holder.tvTime.setText(time);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onServerClick(model.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return serverModels.size();
    }

    class ServerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_players)
        TextView tvPlayers;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_lock)
        ImageView ivLock;


        public ServerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
