package artem.musiienko.tanks.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import artem.musiienko.tanks.R;
import artem.musiienko.tanks.fragments.LobbyFragment;
import artem.musiienko.tanks.fragments.MenuFragment;
import artem.musiienko.tanks.fragments.NewServerFragment;
import artem.musiienko.tanks.fragments.OptionsFragment;
import artem.musiienko.tanks.fragments.ServersFragment;
import artem.musiienko.tanks.fragments.UpgradeFragment;
import artem.musiienko.tanks.utils.Consts;

public class NavigationActivity extends AppCompatActivity {


    private FragmentManager fragmentManager;


    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            selectItem(Consts.Tags.MENU);
        }
    }


    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public void selectItem(int position) {
        hideKeyboard();
        switch (position) {
            case Consts.Tags.MENU: {
                fragmentManager.beginTransaction().replace(R.id.content, new MenuFragment()).commit();
                break;
            }
            case Consts.Tags.MULTIPLAYER: {
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_out_from_right, R.anim.slide_out_to_left, R.anim.slide_from_left, R.anim.slide_to_right)
                        .replace(R.id.content, ServersFragment.getInstance()).addToBackStack(ServersFragment.TAG).commit();
                break;
            }
            case Consts.Tags.OPTIONS: {
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_out_from_right, R.anim.slide_out_to_left, R.anim.slide_from_left, R.anim.slide_to_right)
                        .replace(R.id.content, OptionsFragment.getInstance()).addToBackStack(OptionsFragment.TAG).commit();
                break;
            }

            case Consts.Tags.TRAINING: {
                Intent intent = new Intent(this, MainActivity.class);
                startActivityForResult(intent, RESULT_OK);

                break;
            }

            case Consts.Tags.CREATENEW: {
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_out_from_right, R.anim.slide_out_to_left, R.anim.slide_from_left, R.anim.slide_to_right)
                        .replace(R.id.content, NewServerFragment.getInstance()).addToBackStack(NewServerFragment.TAG).commit();

                break;
            }


            case Consts.Tags.LOBBY: {

                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_out_from_right, R.anim.slide_out_to_left, R.anim.slide_from_left, R.anim.slide_to_right)
                        .replace(R.id.content, LobbyFragment.getInstance(bundle)).addToBackStack(LobbyFragment.TAG).commit();

                break;
            }

            case Consts.Tags.UPGRADE: {
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_out_from_right, R.anim.slide_out_to_left, R.anim.slide_from_left, R.anim.slide_to_right)
                        .replace(R.id.content, UpgradeFragment.getInstance()).addToBackStack(UpgradeFragment.TAG).commit();

                break;
            }

        }
    }


    public void back() {
        fragmentManager.popBackStack();
        hideKeyboard();
    }


    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
