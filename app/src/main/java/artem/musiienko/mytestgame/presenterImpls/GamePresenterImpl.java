package artem.musiienko.mytestgame.presenterImpls;

import android.graphics.Color;
import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import artem.musiienko.mytestgame.presenters.GamePresenter;

import artem.musiienko.mytestgame.units.Bullet;
import artem.musiienko.mytestgame.units.Tank;
import artem.musiienko.mytestgame.units.Unit;
import artem.musiienko.mytestgame.units.Walker;
import artem.musiienko.mytestgame.utils.Consts;
import artem.musiienko.mytestgame.utils.customviews.Field;
import artem.musiienko.mytestgame.views.GameView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by artyom on 22.06.16.
 */
public class GamePresenterImpl implements GamePresenter {


    public static final String TAG = "GamePresenterImpl";

    Subscription leftSub, upSub, rightSub, downSub;

    private Integer curId;

    private HashMap<Integer, Unit> units = new HashMap<>();

    private GameView gameView;

    private Field field;

    private int radius;

    private int autoIndex;


    public GamePresenterImpl(GameView gameView) {
        this.gameView = gameView;
    }

    public void setCurId(Integer curId) {

        this.curId = curId;

        boolean isTank = units.get(curId) instanceof Tank;
        gameView.setAimRight(isTank);
        gameView.setAimLeft(isTank);
        gameView.setAimUp(isTank);
        gameView.setAimDown(isTank);
        gameView.setFireEnable(isTank);


        if (isTank) {
            int vector = ((Tank) units.get(curId)).getWatchVector();
            selectAim(vector);

        }
        field.selectUnit(curId);
        checkPossibilities();


    }

    public void setUnits(HashMap<Integer, Unit> units) {
        this.units = units;
    }

    public void setField(Field field) {
        this.field = field;
        radius = field.getRadius();
    }

    @Override
    public void addUnit(Unit unit) {
        unit.setId(autoIndex++);
        units.put(unit.getId(), unit);

        field.addUnit(unit);

        if (unit instanceof Walker) {
            ((Walker) unit).setMargin(radius);
            setCurId(unit.getId());

        }


    }

    @Override
    public void removeUnit(Unit unit) {

        field.removeUnit(unit);
        units.remove(unit.getId());
        if (curId == unit.getId()) {
            clearFocus();
        } else {
            checkPossibilities();
        }

    }

    @Override
    public void updateUnit(Unit unit) {
        field.updateUnit(unit);
    }

    @Override
    public void checkHit(final Bullet bullet) {

        Observable.from(units.values())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .map(new Func1<Unit, Unit>() {
                    @Override
                    public Unit call(Unit unit) {
                        if (unit.getId() != bullet.getId() && isInside(bullet, unit) && unit.getId() != bullet.getParentId())
                            return unit;
                        else
                            return null;

                    }
                })
                .subscribe(new Subscriber<Unit>() {
                    @Override
                    public void onCompleted() {
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Unit unit) {

                        if(unit!=null) {
                            if(unit instanceof Walker)
                                removeUnit(unit);

                            ((Bullet)bullet).setRemoved(true);
                            removeUnit(bullet);

                        }

                    }
                });


    }

    @Override
    public void startGame() {
        Observable.interval(200,TimeUnit.MILLISECONDS)
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
                        checkPossibilities();
                        unsubscribe();
                    }
                });
    }

    @Override
    public int getRadius() {
        return field.getRadius();
    }

    @Override
    public int getWidth() {
        return field.getColNumber() * radius;
    }

    @Override
    public int getHeight() {
        return field.getRawNumber() * radius;
    }

    @Override
    public void moveLeft() {
        Walker unit = (Walker) units.get(curId);
        if (unit.getxStart() > 0) {

            leftSub = Observable.just(unit)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map(funcLeft)
                    .subscribe(actionUpdate);

        }

    }

    @Override
    public void moveRight() {
        Walker unit = (Walker) units.get(curId);
        if (unit.getxEnd() < radius * field.getColNumber()) {
            rightSub = Observable.just(unit)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map(funcRight)
                    .subscribe(actionUpdate);
        }
    }

    @Override
    public void moveDown() {
        Walker unit = (Walker) units.get(curId);
        if (unit.getyEnd() < radius * field.getRawNumber()) {
            downSub = Observable.just(unit)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map(funcDown)
                    .subscribe(actionUpdate);
        }
    }

    @Override
    public void moveUp() {
        Walker unit = (Walker) units.get(curId);
        if (unit.getyStart() > 0) {
            upSub = Observable.just(unit)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map(funcUp)
                    .subscribe(actionUpdate);
        }
    }

    @Override
    public void watchLeft() {
        selectAim(Consts.Vector.LEFT);
        ((Tank) units.get(curId)).watch(Consts.Vector.LEFT);
        field.rotateTank(curId, Consts.Vector.LEFT);
    }

    @Override
    public void watchRight() {
        selectAim(Consts.Vector.RIGHT);
        ((Tank) units.get(curId)).watch(Consts.Vector.RIGHT);
        field.rotateTank(curId, Consts.Vector.RIGHT);
    }

    @Override
    public void watchDown() {
        selectAim(Consts.Vector.DOWN);
        gameView.setAimDown(false);
        ((Tank) units.get(curId)).watch(Consts.Vector.DOWN);
        field.rotateTank(curId, Consts.Vector.DOWN);
    }

    @Override
    public void watchUp() {
        selectAim(Consts.Vector.UP);
        gameView.setAimUp(false);
        ((Tank) units.get(curId)).watch(Consts.Vector.UP);
        field.rotateTank(curId, Consts.Vector.UP);
    }

    @Override
    public void next() {

    }

    @Override
    public void fire() {
        Tank curUnit = (Tank) units.get(curId);
        if (curUnit.getWatchVector() == Consts.Vector.NOWHEARE) {
            return;
        }
        Bullet bullet = new Bullet(autoIndex++, this, curUnit);

        units.put(bullet.getId(), bullet);
        field.addUnit(bullet);
        checkHit(bullet);

    }

    @Override
    public void onDestroy() {
        if (leftSub != null && !leftSub.isUnsubscribed())
            leftSub.unsubscribe();
        if (rightSub != null && !rightSub.isUnsubscribed())
            rightSub.unsubscribe();
        if (upSub != null && !upSub.isUnsubscribed())
            upSub.unsubscribe();
        if (downSub != null && !downSub.isUnsubscribed())
            downSub.unsubscribe();
    }


    private void checkPossibilities() {
        final Unit unit = units.get(curId);

        //PossibleLeft
        if (unit.getxStart() == 0) {

            gameView.setPossibleLeft(false);
        } else {

            Observable.from(units.values())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.computation())
                    .map(new Func1<Unit, Boolean>() {
                        @Override
                        public Boolean call(Unit unit) {
                            return unit instanceof Bullet || !(unit.getxEnd() == units.get(curId).getxStart() && isVerticalContact(unit));


                        }
                    })
                    .subscribe(new Subscriber<Boolean>() {
                        @Override
                        public void onCompleted() {
                            unsubscribe();
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(Boolean aBoolean) {
                            if (!aBoolean) {
                                gameView.setPossibleLeft(false);
                                onCompleted();
                            } else {
                                gameView.setPossibleLeft(true);
                            }

                        }
                    });


        }

        //PossibleUp

        if (unit.getyStart() == 0) {
            gameView.setPossibleUp(false);
        } else {
            Observable.from(units.values())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.computation())
                    .map(new Func1<Unit, Boolean>() {
                        @Override
                        public Boolean call(Unit unit) {

                            return unit instanceof Bullet || !(unit.getyEnd() == units.get(curId).getyStart() && isHorisontalContact(unit));

                        }
                    })
                    .subscribe(new Subscriber<Boolean>() {
                        @Override
                        public void onCompleted() {
                            unsubscribe();
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(Boolean aBoolean) {
                            if (!aBoolean) {
                                gameView.setPossibleUp(false);
                                onCompleted();
                            } else {
                                gameView.setPossibleUp(true);
                            }

                        }
                    });
        }


        //PossibleRight

        if (unit.getxEnd() == field.getRadius() * field.getColNumber()) {
            gameView.setPossibleRight(false);

        } else {

            Observable.from(units.values())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.computation())
                    .map(new Func1<Unit, Boolean>() {
                        @Override
                        public Boolean call(Unit unit) {

                            return unit instanceof Bullet || !(unit.getxStart() == units.get(curId).getxEnd() && isVerticalContact(unit));

                        }
                    })
                    .subscribe(new Subscriber<Boolean>() {


                        @Override
                        public void onCompleted() {

                            unsubscribe();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(Boolean aBoolean) {
                            if (!aBoolean) {

                                gameView.setPossibleRight(false);
                                onCompleted();

                            } else {

                                gameView.setPossibleRight(true);
                            }

                        }
                    });
        }


        //PossibleDown

        if (unit.getyEnd() == field.getRadius() * field.getRawNumber()) {
            gameView.setPossibleDown(false);
        } else {
            Observable.from(units.values())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.computation())
                    .map(new Func1<Unit, Boolean>() {
                        @Override
                        public Boolean call(Unit unit) {

                            return unit instanceof Bullet || !(unit.getyStart() == units.get(curId).getyEnd() && isHorisontalContact(unit));

                        }
                    })
                    .subscribe(new Subscriber<Boolean>() {
                        @Override
                        public void onCompleted() {
                            unsubscribe();
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(Boolean aBoolean) {
                            if (!aBoolean) {
                                gameView.setPossibleDown(false);
                                onCompleted();
                            } else {
                                gameView.setPossibleDown(true);
                            }

                        }
                    });
        }
    }


    private boolean isVerticalContact(Unit compareUnit) {
        Unit curUnit = units.get(curId);
        return (compareUnit.getyStart() <= curUnit.getyStart() && compareUnit.getyEnd() > curUnit.getyStart()
                || compareUnit.getyStart() < curUnit.getyEnd() && compareUnit.getyEnd() > curUnit.getyEnd());

    }


    private boolean isHorisontalContact(Unit compareUnit) {
        Unit curUnit = units.get(curId);
        return (compareUnit.getxStart() <= curUnit.getxStart() && compareUnit.getxEnd() > curUnit.getxStart()
                || compareUnit.getxStart() < curUnit.getxEnd() && compareUnit.getxEnd() > curUnit.getxEnd());

    }


    private boolean isInside(Unit bulletUnit, Unit compareUnit) {

        return (compareUnit.getxStart() <= bulletUnit.getxStart() && compareUnit.getxEnd() >= bulletUnit.getxEnd()
                && compareUnit.getyStart() <= bulletUnit.getyStart() && compareUnit.getyEnd() >= bulletUnit.getyEnd());
    }


    Func1<Walker, Walker> funcUp = new Func1<Walker, Walker>() {
        @Override
        public Walker call(Walker walker) {
            walker.walk(Consts.Vector.UP);
            return walker;
        }
    };


    Func1<Walker, Walker> funcLeft = new Func1<Walker, Walker>() {
        @Override
        public Walker call(Walker walker) {
            walker.walk(Consts.Vector.LEFT);
            return walker;

        }
    };


    Func1<Walker, Walker> funcDown = new Func1<Walker, Walker>() {
        @Override
        public Walker call(Walker walker) {
            walker.walk(Consts.Vector.DOWN);
            return walker;
        }
    };


    Func1<Walker, Walker> funcRight = new Func1<Walker, Walker>() {
        @Override
        public Walker call(Walker walker) {
            walker.walk(Consts.Vector.RIGHT);
            return walker;
        }
    };

    Action1<Walker> actionUpdate = new Action1<Walker>() {
        @Override
        public void call(Walker walker) {
            field.updateUnit(walker);
            checkPossibilities();
            onDestroy();
        }
    };


    private void selectAim(int vector) {
        switch (vector) {
            case Consts.Vector.UP: {
                gameView.setAimRight(true);
                gameView.setAimLeft(true);
                gameView.setAimUp(false);
                gameView.setAimDown(true);
                break;
            }
            case Consts.Vector.DOWN: {
                gameView.setAimRight(true);
                gameView.setAimLeft(true);
                gameView.setAimUp(true);
                gameView.setAimDown(false);
                break;
            }
            case Consts.Vector.LEFT: {
                gameView.setAimRight(true);
                gameView.setAimLeft(false);
                gameView.setAimUp(true);
                gameView.setAimDown(true);

                break;
            }
            case Consts.Vector.RIGHT: {
                gameView.setAimRight(false);
                gameView.setAimLeft(true);
                gameView.setAimUp(true);
                gameView.setAimDown(true);
                break;
            }
        }
    }


    private void clearFocus() {
        gameView.setAimRight(false);
        gameView.setAimLeft(false);
        gameView.setAimUp(false);
        gameView.setAimDown(false);

        gameView.setPossibleRight(false);
        gameView.setPossibleLeft(false);
        gameView.setPossibleUp(false);
        gameView.setPossibleDown(false);
    }
}
