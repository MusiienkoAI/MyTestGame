package artem.musiienko.tanks.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import artem.musiienko.tanks.R;
import artem.musiienko.tanks.presenterImpls.ServersPresenterImpl;
import artem.musiienko.tanks.presenters.ServersPresenter;
import artem.musiienko.tanks.utils.Consts;
import artem.musiienko.tanks.views.ServersView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    }

    @Override
    public void showThePasswordDialog(String id) {

    }
}
