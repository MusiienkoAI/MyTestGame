package artem.musiienko.tanks.fragments;

/**
 * Created by artyom on 04.07.16.
 */
public class OptionsFragment extends BaseMenuFragment {

    public static final String TAG = "OptionsFragment";

    private static OptionsFragment fragment;

    public static OptionsFragment getInstance() {


        if (fragment == null)
            fragment = new OptionsFragment();

        return fragment;
    }
}
