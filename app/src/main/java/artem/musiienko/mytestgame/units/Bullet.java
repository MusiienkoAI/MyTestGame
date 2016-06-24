package artem.musiienko.mytestgame.units;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import artem.musiienko.mytestgame.presenters.GamePresenter;
import artem.musiienko.mytestgame.utils.Consts;
import artem.musiienko.mytestgame.utils.customviews.Field;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by artyom on 22.06.16.
 */
public class Bullet extends Walker {



    private Integer duration = 1000;


    public static final String TAG = "Bullet";

    private int parentId;

    private boolean isRemoved;

    private Subscription subscription;

    public int getParentId() {
        return parentId;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }

    public Bullet(int id, final GamePresenter presenter, Tank tank) {

        final Bullet bullet = Bullet.this;
        int radius = presenter.getRadius();
        setMargin(radius);
        setParentId(tank.getId());

       setId(id);
        switch (tank.getWatchVector()) {
            case Consts.Vector.UP: {
                setxStart(tank.getxStart());
                setyStart(tank.getyStart() - radius);
                subscription = Observable.interval(duration, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.computation())
                        .subscribe(new Subscriber<Long>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Long aLong) {

                                if(bullet.getyStart() ==0 || isRemoved) {
                                    unsubscribe();
                                    presenter.removeUnit(bullet);
                                }else {
                                    bullet.walk(Consts.Vector.UP);
                                    presenter.updateUnit(bullet);
                                    presenter.checkHit(bullet);
                                }
                            }
                        });
                break;
            }
            case Consts.Vector.DOWN: {
               setxStart(tank.getxStart());
                setyStart(tank.getyStart() + radius);

                subscription = Observable.interval(duration, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.computation())
                        .subscribe(new Subscriber<Long>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(Long aLong) {
                                if(bullet.getyEnd() >=presenter.getHeight() || isRemoved) {
                                    subscription.unsubscribe();
                                    presenter.removeUnit(bullet);
                                }else {
                                    bullet.walk(Consts.Vector.DOWN);
                                    presenter.updateUnit(bullet);
                                    presenter.checkHit(bullet);
                                }
                            }
                        });

                break;
            }
            case Consts.Vector.RIGHT: {
                setxStart(tank.getxStart() + radius);
               setyStart(tank.getyStart());




                subscription = Observable.interval(duration, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.computation())
                        .subscribe(new Subscriber<Long>() {
                            @Override
                            public void onCompleted() {
                                if(!subscription.isUnsubscribed())
                                unsubscribe();
                                presenter.removeUnit(bullet);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Long aLong) {
                                if(bullet.getxEnd() >=presenter.getWidth() || isRemoved) {

                                   onCompleted();
                                }else {

                                    bullet.walk(Consts.Vector.RIGHT);
                                    presenter.updateUnit(bullet);
                                    presenter.checkHit(bullet);
                                }
                            }
                        });
                break;
            }
            case Consts.Vector.LEFT: {
                setxStart(tank.getxStart() - radius);
                setyStart(tank.getyStart());

                subscription = Observable.interval(duration, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.computation())
                        .subscribe(new Subscriber<Long>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Long aLong) {
                                if(bullet.getxStart() ==0 || isRemoved)  {
                                    unsubscribe();
                                    presenter.removeUnit(bullet);
                                }else {
                                    bullet.walk(Consts.Vector.LEFT);
                                    presenter.updateUnit(bullet);
                                    presenter.checkHit(bullet);
                                }
                            }
                        });
                break;
            }
        }


    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public void demolish() {

    }






}
