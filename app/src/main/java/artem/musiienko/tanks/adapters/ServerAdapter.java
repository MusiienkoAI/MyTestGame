package artem.musiienko.tanks.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import artem.musiienko.tanks.R;
import artem.musiienko.tanks.presenters.ServersPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by artyom on 05.07.16.
 */
public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.ServerViewHolder> {


    private ServersPresenter presenter;

    public ServerAdapter(ServersPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ServerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_server, parent, false);
        return new ServerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServerViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onServerClick("id");
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
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
