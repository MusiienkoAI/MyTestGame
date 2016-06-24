package artem.musiienko.mytestgame.utils.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

import artem.musiienko.mytestgame.R;
import artem.musiienko.mytestgame.presenters.GamePresenter;
import artem.musiienko.mytestgame.units.Bullet;
import artem.musiienko.mytestgame.units.Man;
import artem.musiienko.mytestgame.units.Tank;
import artem.musiienko.mytestgame.units.Unit;
import artem.musiienko.mytestgame.units.Walker;
import artem.musiienko.mytestgame.units.Wall;
import artem.musiienko.mytestgame.utils.Consts;

/**
 * Created by artyom on 23.06.16.
 */
public class Field extends FrameLayout {


    public static final String TAG = "Field";


    private int width;
    private int radius;

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

        TextView textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        if (unit instanceof Wall) {
            textView.setText("W");
            textView.setBackgroundColor(Color.BLUE);
        }
        if (unit instanceof Man) {
            textView.setText("M");
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.setCurId((Integer)v.getTag());
                }
            });
        }
        if (unit instanceof Tank) {
            textView.setText("T");
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.setCurId((Integer)v.getTag());
                }
            });
        }
        if (unit instanceof Bullet) {
            textView.setText("B");
        }

        int unitWidth = radius*unit.getwLong();
        int unitHeight = radius*unit.gethLong();


        LayoutParams layoutParams = new LayoutParams(unitWidth,unitHeight);
        layoutParams.setMargins(unit.getxStart(),unit.getyStart(),0,0);
        textView.setLayoutParams(layoutParams);
        unit.setxEnd(unit.getxStart()+unitWidth);
        unit.setyEnd(unit.getyStart()+unitHeight);
        textView.setTag(unit.getId());
        map.put(unit.getId(),textView);
        addView(textView);





    }

    public void removeUnit(Unit unit) {

            removeView(map.get(unit.getId()));

    }


    public void updateUnit(Unit unit) {
        TextView textView = (TextView) map.get(unit.getId());
        LayoutParams layoutParams = (FrameLayout.LayoutParams)textView.getLayoutParams();
        int unitWidth = radius*unit.getwLong();
        int unitHeight = radius*unit.gethLong();
        layoutParams.width = unitWidth;
        layoutParams.height = unitHeight;
        layoutParams.setMargins(unit.getxStart(),unit.getyStart(),0,0);
        textView.setLayoutParams(layoutParams);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

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
