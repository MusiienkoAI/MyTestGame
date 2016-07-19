package artem.musiienko.tanks.fragments;

import android.os.Bundle;

/**
 * Created by artyom on 05.07.16.
 */
public class LobbyFragment extends BaseMenuFragment {


    public static final String TAG = "NewServerFragment";


    private static LobbyFragment fragment;

    public static LobbyFragment getInstance(Bundle bundle) {


        if (fragment == null) {
            fragment = new LobbyFragment();
            if (bundle != null)
                fragment.setArguments(bundle);
        }

        return fragment;
    }

}
