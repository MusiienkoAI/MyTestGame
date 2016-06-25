package artem.musiienko.mytestgame.utils.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.RotateDrawable;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import artem.musiienko.mytestgame.R;
import artem.musiienko.mytestgame.presenters.GamePresenter;
import artem.musiienko.mytestgame.units.Bullet;
import artem.musiienko.mytestgame.units.Man;
import artem.musiienko.mytestgame.units.Tank;
import artem.musiienko.mytestgame.units.Unit;
import artem.musiienko.mytestgame.units.Walker;
import artem.musiienko.mytestgame.units.Wall;
import artem.musiienko.mytestgame.utils.Consts;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by artyom on 23.06.16.
 */
public class Field extends FrameLayout {


    public static final String TAG = "Field";


    private int width;
    private int radius;

    private int oldId = -1;

    private Paint paint;


    private int colNumber;
    private int rawNumber;

    private GamePresenter presenter;


    private HashMap<Integer,View> map = new HashMap<>();

    public Field(Context context) {
        this(context, null);
    }


    public Field(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Field(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Field, defStyleAttr, 0);
        colNumber = typedArray.getInteger(R.styleable.Field_col_number, 7);
        rawNumber = typedArray.getInteger(R.styleable.Field_row_number, 7);

        typedArray.recycle();

        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorAccent));

        initGrid();

    }

    public void setPresenter(GamePresenter presenter) {
        this.presenter = presenter;
    }

    private void initGrid() {
       LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        MyGrid myGrid = new MyGrid(getContext());
        myGrid.setLayoutParams(layoutParams);
        myGrid.setColNumber(colNumber);
        myGrid.setRawNumber(rawNumber);
        addView(myGrid);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = w;
        radius = (int) ((int) width / colNumber);
    }


    public void addUnit(Unit unit) {

        ImageView imageView = new ImageView(getContext());


        if (unit instanceof Wall) {
            imageView.setBackgroundResource(R.drawable.wall);
        }
        if (unit instanceof Man) {
            imageView.setImageResource(R.drawable.standing_up_man);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.setCurId((Integer)v.getTag());
                }
            });
        }
        if (unit instanceof Tank) {
            imageView.setImageResource(R.drawable.tank_u);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.setCurId((Integer)v.getTag());
                }
            });
            Tank tank = (Tank) unit;
            switch (tank.getWatchVector()) {
                case Consts.Vector.DOWN: {
                    imageView.setImageResource(R.drawable.tank_d);
                    break;
                }
                case Consts.Vector.UP: {
                    imageView.setImageResource(R.drawable.tank_u);
                    break;
                }
                case Consts.Vector.RIGHT: {
                    imageView.setImageResource(R.drawable.tank_r);
                    break;
                }
                case Consts.Vector.LEFT: {
                    imageView.setImageResource(R.drawable.tank_l);
                    break;
                }

            }


        }
        if (unit instanceof Bullet) {

            Bullet bullet = (Bullet) unit;
            switch (bullet.getVector()) {
                case Consts.Vector.DOWN: {
                    imageView.setImageResource(R.drawable.bullet_d);
                    break;
                }
                case Consts.Vector.UP: {
                    imageView.setImageResource(R.drawable.bullet_u);
                    break;
                }
                case Consts.Vector.RIGHT: {
                    imageView.setImageResource(R.drawable.bullet_r);
                    break;
                }
                case Consts.Vector.LEFT: {
                    imageView.setImageResource(R.drawable.bullet_l);
                    break;
                }

            }





        }
        imageView.setPadding(10, 10, 10, 10);
        int unitWidth = radius*unit.getwLong();
        int unitHeight = radius*unit.gethLong();


        LayoutParams layoutParams = new LayoutParams(unitWidth,unitHeight);
        layoutParams.setMargins(unit.getxStart(),unit.getyStart(),0,0);
        imageView.setLayoutParams(layoutParams);
        unit.setxEnd(unit.getxStart()+unitWidth);
        unit.setyEnd(unit.getyStart()+unitHeight);
        imageView.setTag(unit.getId());
        map.put(unit.getId(), imageView);
        addView(imageView);





    }

    public void removeUnit(final Unit unit) {
        if ((unit instanceof Bullet)) {
            removeView(map.get(unit.getId()));
        } else {
            ImageView imageView = (ImageView) map.get(unit.getId());
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
                            removeView(map.get(unit.getId()));
                            onCompleted();
                        }
                    });
        }

    }


    public void rotateTank(Integer id, int vector) {
        ImageView view = (ImageView) map.get(id);
        switch (vector) {
            case Consts.Vector.DOWN: {
                view.setImageResource(R.drawable.tank_d);
                break;
            }
            case Consts.Vector.UP: {
                view.setImageResource(R.drawable.tank_u);
                break;
            }
            case Consts.Vector.RIGHT: {
                view.setImageResource(R.drawable.tank_r);
                break;
            }
            case Consts.Vector.LEFT: {
                view.setImageResource(R.drawable.tank_l);
                break;
            }

        }
    }

    public void updateUnit(Unit unit) {
        View view = map.get(unit.getId());
        LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        int unitWidth = radius*unit.getwLong();
        int unitHeight = radius*unit.gethLong();
        layoutParams.width = unitWidth;
        layoutParams.height = unitHeight;
        layoutParams.setMargins(unit.getxStart(),unit.getyStart(),0,0);
        view.setLayoutParams(layoutParams);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }


    public void selectUnit(Integer id) {
        if (id != null) {
            if (map.get(oldId) != null) {
                map.get(oldId).setBackgroundColor(Color.TRANSPARENT);
            }
            View view = map.get(id);
            view.setBackgroundResource(R.drawable.shape_selected_unit);
            oldId = id;
        }
    }
    public int getRadius() {
        return radius;
    }

    public int getColNumber() {
        return colNumber;
    }

    public int getRawNumber() {
        return rawNumber;
    }
}
