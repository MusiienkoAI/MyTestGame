package artem.musiienko.tanks.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import artem.musiienko.tanks.R;
import artem.musiienko.tanks.models.ServerModel;
import artem.musiienko.tanks.presenterImpls.LobbyPresenterImpl;
import artem.musiienko.tanks.presenters.LobbyPresenter;
import artem.musiienko.tanks.utils.Consts;
import artem.musiienko.tanks.views.LobbyView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by artyom on 05.07.16.
 */
public class LobbyFragment extends BaseMenuFragment implements LobbyView {


    public static final String TAG = LobbyFragment.class.getSimpleName();


    @BindView(R.id.rv_users)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    TextView tvTitle;


    private static LobbyFragment fragment;

    private LobbyPresenter presenter;


    public static LobbyFragment getInstance(Bundle bundle) {


        if (fragment == null) {
            fragment = new LobbyFragment();

        }

        if (bundle != null)
            fragment.setArguments(bundle);

        return fragment;
    }

    @OnClick(R.id.iv_back)
    void onBack() {
        activity.back();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lobby, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String serverId = getArguments().getString(Consts.ARGS.SERVER_ID);
        initTitle(serverId);
        presenter = new LobbyPresenterImpl(this, serverId);
        initRecyclerView();
    }


    private void initTitle(String id) {
        Realm realm = Realm.getDefaultInstance();
        ServerModel model = realm.where(ServerModel.class).equalTo("id", id).findFirst();
        tvTitle.setText(model.getName());
    }


    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        presenter.setAdapter(recyclerView);
    }

    @Override
    public void startTheGame() {

    }

    @Override
    public void onErrorResponse(int code) {

    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
