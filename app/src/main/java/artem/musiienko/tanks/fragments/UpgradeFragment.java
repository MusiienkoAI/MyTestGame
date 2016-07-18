package artem.musiienko.tanks.fragments;

/**
 * Created by artyom on 05.07.16.
 */
public class UpgradeFragment extends BaseMenuFragment {


    public static final String TAG = "UpgradeFragment";

    private static UpgradeFragment fragment;

    public static UpgradeFragment getInstance() {


        if (fragment == null)
            fragment = new UpgradeFragment();

        return fragment;
    }

}
