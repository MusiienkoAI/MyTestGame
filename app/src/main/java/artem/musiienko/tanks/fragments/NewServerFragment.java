package artem.musiienko.tanks.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import artem.musiienko.tanks.R;
import artem.musiienko.tanks.presenterImpls.NewServerPresenterImpl;
import artem.musiienko.tanks.presenters.NewServerPresenter;
import artem.musiienko.tanks.utils.Consts;
import artem.musiienko.tanks.views.NewServerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by artyom on 05.07.16.
 */
public class NewServerFragment extends BaseMenuFragment implements NewServerView {

    public static final String TAG = "NewServerFragment";


    @BindView(R.id.sp_count)
    AppCompatSpinner spinner;

    @BindView(R.id.et_name)
    AppCompatEditText etName;

    @BindView(R.id.et_password)
    AppCompatEditText etPassword;

    @BindView(R.id.cb_private)
    AppCompatCheckBox cbPrivate;


    private NewServerPresenter presenter;

    private ObjectAnimator showPass, hidePass;


    private static NewServerFragment fragment;

    public static NewServerFragment getInstance() {


        if (fragment == null)
            fragment = new NewServerFragment();

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_server, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new NewServerPresenterImpl(this);
        presenter.initSpinner(spinner);
        initAnimation();
        initTextWatchers();
    }


    private void showPassword() {
        presenter.setPrivate(true);
        showPass.start();
    }


    private void hidePassword() {
        presenter.setPrivate(false);
        hidePass.start();
    }

    @OnClick(R.id.btn_create)
    void onCreateClick() {
        presenter.setName(etName.getText().toString());
        presenter.setPassword(etPassword.getText().toString());
        presenter.create();
    }


    @OnClick(R.id.rl_private)
    void onPrivateClick() {
        if (cbPrivate.isChecked()) {
            cbPrivate.setChecked(false);
            hidePassword();

        } else {
            cbPrivate.setChecked(true);
            showPassword();
        }
    }


    @OnClick(R.id.iv_back)
    void onBack() {
        activity.back();
    }


    private void initTextWatchers() {
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.setName(editable.toString());
                etName.setError(null);
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.setPassword(editable.toString());
                etPassword.setError(null);
            }
        });
    }










    private void initAnimation() {
        showPass = ObjectAnimator.ofFloat(etPassword, "scaleY", 0, 1f);
        showPass.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                etPassword.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


        hidePass = ObjectAnimator.ofFloat(etPassword, "scaleY", 1f, 0);
        hidePass.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                etPassword.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onValidationError(int errorCode) {
        switch (errorCode) {
            case Consts.Errors.EMPTY_NAME:
                etName.setError(getString(R.string.empty_name));
                break;
            case Consts.Errors.SHORT_NAME:
                etName.setError(getString(R.string.short_name));
                break;
            case Consts.Errors.EMPTY_PASSWORD:
                etPassword.setError(getString(R.string.empty_password));
                break;
        }
    }

    @Override
    public void enterTheLobby(String id) {
        Bundle bundle = new Bundle();
        bundle.putString(Consts.ARGS.SERVER_ID, id);
        activity.setBundle(bundle);
        activity.selectItem(Consts.Tags.LOBBY);
    }

    @Override
    public void clearPassword() {
        etPassword.setText("");
    }
}
