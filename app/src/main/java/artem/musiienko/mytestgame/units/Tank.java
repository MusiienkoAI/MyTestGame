package artem.musiienko.mytestgame.units;


import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import artem.musiienko.mytestgame.R;
import artem.musiienko.mytestgame.interfaces.DemolishInterface;
import artem.musiienko.mytestgame.interfaces.FireInterface;
import artem.musiienko.mytestgame.presenters.GamePresenter;
import artem.musiienko.mytestgame.utils.Consts;
import artem.musiienko.mytestgame.utils.customviews.Field;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by artyom on 22.06.16.
 */
public class Tank extends Walker implements FireInterface {


    private int watchVector;


    @Override
    public void watch(int vector) {
       watchVector = vector;
    }


    @Override
    public int getWatchVector() {
        return watchVector;
    }


    @Override
    public Bullet createBullet(Field field, GamePresenter presenter, int autoindex) {
        return new Bullet(autoindex, presenter, this);

    }

    @Override
    public void demolish(final Field field) {
        final HashMap<Integer, View> map = field.getMap();
        ImageView imageView = (ImageView) map.get(getId());
        imageView.setImageResource(R.drawable.smoke_explosion);
        Observable.interval(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        field.removeView(map.get(getId()));
                        onCompleted();
                    }
                });
    }
}
