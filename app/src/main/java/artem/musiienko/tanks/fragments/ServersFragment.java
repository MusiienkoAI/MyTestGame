package artem.musiienko.tanks.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import artem.musiienko.tanks.R;
import artem.musiienko.tanks.models.UserModel;
import artem.musiienko.tanks.presenterImpls.ServersPresenterImpl;
import artem.musiienko.tanks.presenters.ServersPresenter;
import artem.musiienko.tanks.utils.Consts;
import artem.musiienko.tanks.views.ServersView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by artyom on 04.07.16.
 */
public class ServersFragment extends BaseMenuFragment implements ServersView {

    public static final String TAG = "ServersFragment";

    private static ServersFragment fragment;


    public static ServersFragment getInstance() {


        if (fragment == null)
            fragment = new ServersFragment();

        return fragment;
    }


    @BindView(R.id.rv_servers)
    RecyclerView recyclerView;


    private ServersPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multiplayer, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new ServersPresenterImpl(this);
        initRecyclerView();
    }


    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        presenter.setAdapter(recyclerView);

    }

    @OnClick(R.id.btn_new_server)
    void onNewServerClick() {
        activity.selectItem(Consts.Tags.CREATENEW);
    }

    @OnClick(R.id.iv_back)
    void onBack() {
        activity.back();
    }

    @Override
    public void enterTheLobby(String id) {
        Bundle bundle = new Bundle();
        bundle.putString(Consts.ARGS.SERVER_ID, id);
        activity.setBundle(bundle);
        activity.selectItem(Consts.Tags.LOBBY);
    }

    @Override
    public void showThePasswordDialog(String id) {

    }

    @Override
    public void errorResponse(int code) {
        if (this.getView() != null)
            Snackbar.make(this.getView(), R.string.server_full, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
