package artem.musiienko.tanks.fragments;

/**
 * Created by artyom on 05.07.16.
 */
public class LobbyFragment extends BaseMenuFragment {


    public static final String TAG = "NewServerFragment";


    private static LobbyFragment fragment;

    public static LobbyFragment getInstance() {


        if (fragment == null)
            fragment = new LobbyFragment();

        return fragment;
    }

}
