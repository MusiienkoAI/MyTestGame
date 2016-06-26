package artem.musiienko.mytestgame;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import artem.musiienko.mytestgame.presenterImpls.GamePresenterImpl;
import artem.musiienko.mytestgame.presenters.GamePresenter;
import artem.musiienko.mytestgame.units.Checker;
import artem.musiienko.mytestgame.units.Man;
import artem.musiienko.mytestgame.units.Tank;
import artem.musiienko.mytestgame.units.Wall;
import artem.musiienko.mytestgame.utils.Consts;
import artem.musiienko.mytestgame.utils.customviews.Field;
import artem.musiienko.mytestgame.views.GameView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements GameView {


    public static final String TAG = "MainActivity";

    @BindView(R.id.field)
    Field field;
    @BindView(R.id.btn_walk_up)
    ImageView btnWalkUp;
    @BindView(R.id.btn_walk_down)
    ImageView btnWalkDown;
    @BindView(R.id.btn_walk_left)
    ImageView btnWalkLeft;
    @BindView(R.id.btn_walk_right)
    ImageView btnWalkRight;
    @BindView(R.id.btn_watch_up)
    ImageView btnWatchUp;
    @BindView(R.id.btn_watch_down)
    ImageView btnWatchDown;
    @BindView(R.id.btn_watch_left)
    ImageView btnWatchLeft;
    @BindView(R.id.btn_watch_right)
    ImageView btnWatchRight;
    @BindView(R.id.btn_fire)
    Button btnFire;


    @BindView(R.id.ll_expand_panel)
    LinearLayout llExpandPanel;


    private ObjectAnimator expandAnim, hideAnim;

    private GamePresenter gamePresenter;

    private boolean isExpand;

    private int xMargin;
    private int yMargin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPresenter();
        initAddingUnits();
        initAnimations();

    }


    private void initPresenter() {
        gamePresenter = new GamePresenterImpl(this);

        field.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                gamePresenter.setField(field);
                field.setPresenter(gamePresenter);

                initUnits();

                gamePresenter.startGame();


                field.removeOnLayoutChangeListener(this);
            }
        });
    }


    private void initAddingUnits() {
        field.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    float x = event.getX();
                    float y = event.getY();
                    xMargin = (int) (x / field.getRadius());
                    yMargin = (int) (y / field.getRadius());

                    if (y < field.getRawNumber() * field.getRadius()) {
                        if (!isExpand)
                            expand();
                        else {
                            removeChecker();
                            addChecker();
                        }

                    }


                }

                return false;
            }
        });
    }


    @Override
    public void setPossibleLeft(boolean possible) {

        setButtonEnable(btnWalkLeft, possible);
    }

    @Override
    public void setPossibleRight(boolean possible) {

        setButtonEnable(btnWalkRight, possible);
    }

    @Override
    public void setPossibleUp(boolean possible) {

        setButtonEnable(btnWalkUp, possible);
    }

    @Override
    public void setPossibleDown(boolean possible) {


        setButtonEnable(btnWalkDown, possible);
    }

    @Override
    public void setAimLeft(boolean aim) {
        setButtonEnable(btnWatchLeft, aim);
    }

    @Override
    public void setAimRight(boolean aim) {
        setButtonEnable(btnWatchRight, aim);
    }

    @Override
    public void setAimUp(boolean aim) {
        setButtonEnable(btnWatchUp, aim);
    }

    @Override
    public void setAimDown(boolean aim) {
        setButtonEnable(btnWatchDown, aim);
    }

    @Override
    public void setFireEnable(boolean enable) {
        setButtonEnable(btnFire, enable);
    }

    private void setButtonEnable(View button, boolean enable) {
        button.setEnabled(enable);
    }


    @OnClick(R.id.btn_walk_right)
    void onRightWalkClick() {
        gamePresenter.moveRight();
    }


    @OnClick(R.id.btn_walk_left)
    void onLeftWalkClick() {
        gamePresenter.moveLeft();
    }


    @OnClick(R.id.btn_walk_up)
    void onUpWalkClick() {
        gamePresenter.moveUp();
    }


    @OnClick(R.id.btn_walk_down)
    void onDownWalkClick() {
        gamePresenter.moveDown();
    }

    @OnClick(R.id.btn_watch_right)
    void onRightWatchClick() {
        gamePresenter.watchRight();
    }


    @OnClick(R.id.btn_watch_left)
    void onLeftWatchClick() {
        gamePresenter.watchLeft();
    }


    @OnClick(R.id.btn_watch_up)
    void onUpWatchClick() {
        gamePresenter.watchUp();
    }


    @OnClick(R.id.btn_watch_down)
    void onDownWatchClick() {
        gamePresenter.watchDown();
    }

    @OnClick(R.id.btn_fire)
    void onFireClick() {
        gamePresenter.fire();
    }

    @OnClick(R.id.iv_add_wall)
    void addWall() {
        gamePresenter.addWall(xMargin, yMargin);
        hide();

    }

    @OnClick(R.id.iv_add_man)
    void addMan() {
        gamePresenter.addMan(xMargin, yMargin);
        hide();
    }

    @OnClick(R.id.iv_add_tank)
    void addTank() {
        gamePresenter.addTank(xMargin, yMargin);
        hide();
    }

    @OnClick(R.id.iv_hide)
    void onHideClick() {
        hide();
    }


    @Override
    protected void onDestroy() {
        gamePresenter.onDestroy();
        super.onDestroy();
    }


    private void addChecker() {
        gamePresenter.addChecker(xMargin, yMargin);
    }


    private void removeChecker() {
        gamePresenter.removeChecker();
    }


    private void expand() {
        isExpand = true;
        addChecker();

        expandAnim.start();


    }

    @Override
    public void hide() {
        if (isExpand) {
            isExpand = false;
            hideAnim.start();
            removeChecker();
        }
    }


    private void initAnimations() {
        expandAnim = ObjectAnimator.ofFloat(llExpandPanel, "translationY", getResources().getDimensionPixelSize(R.dimen.bottom_bar_height), 0);
        expandAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                llExpandPanel.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        hideAnim = ObjectAnimator.ofFloat(llExpandPanel, "translationY", 0, getResources().getDimensionPixelSize(R.dimen.bottom_bar_height));
        hideAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                llExpandPanel.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    private void initUnits() {
        Wall wall = new Wall();

        wall.setxStart(3 * field.getRadius());
        wall.setyStart(field.getRadius());
        wall.sethLong(3);
        wall.setwLong(2);
        gamePresenter.addUnit(wall);


        Man man = new Man();

        man.setxStart(5 * field.getRadius());
        man.setyStart(field.getRadius());
        gamePresenter.addUnit(man);


        Tank tank = new Tank();
        tank.watch(Consts.Vector.RIGHT);
        gamePresenter.addUnit(tank);


        wall = new Wall();

        wall.setxStart(6 * field.getRadius());
        wall.setyStart(field.getRadius());
        wall.sethLong(3);
        wall.setwLong(2);
        gamePresenter.addUnit(wall);
    }
}
