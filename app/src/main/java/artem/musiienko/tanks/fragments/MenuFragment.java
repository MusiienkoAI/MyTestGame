package artem.musiienko.tanks.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import artem.musiienko.tanks.R;
import artem.musiienko.tanks.utils.Consts;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by artyom on 04.07.16.
 */
public class MenuFragment extends BaseMenuFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.tv_multiplayer)
    void onMultiplayerClick() {
        activity.selectItem(Consts.Tags.MULTIPLAYER);
    }

    @OnClick(R.id.tv_options)
    void onOptionsClick() {
        activity.selectItem(Consts.Tags.OPTIONS);
    }

    @OnClick(R.id.tv_training)
    void onTrainingClick() {
        activity.selectItem(Consts.Tags.TRAINING);
    }

    @OnClick(R.id.tv_upgrade)
    void onUpgradeClick() {
        activity.selectItem(Consts.Tags.UPGRADE);
    }
}
