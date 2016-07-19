package artem.musiienko.tanks.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import artem.musiienko.tanks.activities.NavigationActivity;

/**
 * Created by artyom on 04.07.16.
 */
abstract class BaseMenuFragment extends Fragment {


    protected NavigationActivity activity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (NavigationActivity) getActivity();
    }
}
