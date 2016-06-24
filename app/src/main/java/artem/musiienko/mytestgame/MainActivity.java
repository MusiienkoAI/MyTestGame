package artem.musiienko.mytestgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import artem.musiienko.mytestgame.presenterImpls.GamePresenterImpl;
import artem.musiienko.mytestgame.presenters.GamePresenter;
import artem.musiienko.mytestgame.units.Man;
import artem.musiienko.mytestgame.units.Tank;
import artem.musiienko.mytestgame.units.Wall;
import artem.musiienko.mytestgame.utils.customviews.Field;
import artem.musiienko.mytestgame.views.GameView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements GameView {



    @BindView(R.id.field)
    Field field;
    @BindView(R.id.btn_walk_up)
    Button btnWalkUp;
    @BindView(R.id.btn_walk_down)
    Button btnWalkDown;
    @BindView(R.id.btn_walk_left)
    Button btnWalkLeft;
    @BindView(R.id.btn_walk_right)
    Button btnWalkRight;
    @BindView(R.id.btn_watch_up)
    Button btnWatchUp;
    @BindView(R.id.btn_watch_down)
    Button btnWatchDown;
    @BindView(R.id.btn_watch_left)
    Button btnWatchLeft;
    @BindView(R.id.btn_watch_right)
    Button btnWatchRight;
    @BindView(R.id.btn_fire)
    Button btnFire;

    private GamePresenter gamePresenter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPresenter();

        field.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                gamePresenter.setField(field);
                field.setPresenter(gamePresenter);
                Wall wall = new Wall();

                wall.setxStart(3*field.getRadius());
                wall.setyStart(field.getRadius());
                wall.sethLong(3);
                wall.setwLong(2);
                gamePresenter.addUnit(wall);


                wall = new Wall();

                wall.setxStart(6*field.getRadius());
                wall.setyStart(field.getRadius());
                wall.sethLong(3);
                wall.setwLong(2);
                gamePresenter.addUnit(wall);

                Man man = new Man();

                man.setxStart(5*field.getRadius());
                man.setyStart(field.getRadius());
                gamePresenter.addUnit(man);



                Tank tank = new Tank();
                gamePresenter.addUnit(tank);


                gamePresenter.startGame();


                field.removeOnLayoutChangeListener(this);
            }
        });

    }


    private void initPresenter(){
        gamePresenter = new GamePresenterImpl(this);



    }



    @Override
    public void setPossibleLeft(boolean possible) {

        setButtonEnable(btnWalkLeft,possible);
    }

    @Override
    public void setPossibleRight(boolean possible) {

        setButtonEnable(btnWalkRight,possible);
    }

    @Override
    public void setPossibleUp(boolean possible) {

        setButtonEnable(btnWalkUp,possible);
    }

    @Override
    public void setPossibleDown(boolean possible) {


        setButtonEnable(btnWalkDown,possible);
    }

    @Override
    public void setAimLeft(boolean aim) {
        setButtonEnable(btnWatchLeft,aim);
    }

    @Override
    public void setAimRight(boolean aim) {
        setButtonEnable(btnWatchRight,aim);
    }

    @Override
    public void setAimUp(boolean aim) {
        setButtonEnable(btnWatchUp,aim);
    }

    @Override
    public void setAimDown(boolean aim) {
        setButtonEnable(btnWatchDown,aim);
    }

    @Override
    public void setFireEnable(boolean enable) {
        setButtonEnable(btnFire,enable);
    }

    private void setButtonEnable(Button button, boolean enable){
        button.setEnabled(enable);
    }







    @OnClick(R.id.btn_walk_right)
    void onRightWalkClick(){
        gamePresenter.moveRight();
    }


    @OnClick(R.id.btn_walk_left)
    void onLeftWalkClick(){
        gamePresenter.moveLeft();
    }


    @OnClick(R.id.btn_walk_up)
    void onUpWalkClick(){
        gamePresenter.moveUp();
    }


    @OnClick(R.id.btn_walk_down)
    void onDownWalkClick(){
        gamePresenter.moveDown();
    }

    @OnClick(R.id.btn_watch_right)
    void onRightWatchClick(){
        gamePresenter.watchRight();
    }


    @OnClick(R.id.btn_watch_left)
    void onLeftWatchClick(){
        gamePresenter.watchLeft();
    }


    @OnClick(R.id.btn_watch_up)
    void onUpWatchClick(){
        gamePresenter.watchUp();
    }


    @OnClick(R.id.btn_watch_down)
    void onDownWatchClick(){
        gamePresenter.watchDown();
    }

    @OnClick(R.id.btn_fire)
    void onFireClick(){
        gamePresenter.fire();
    }

    @Override
    protected void onDestroy() {
        gamePresenter.onDestroy();
        super.onDestroy();
    }
}
