package artem.musiienko.mytestgame.utils.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import artem.musiienko.mytestgame.R;
import artem.musiienko.mytestgame.utils.Consts;


/**
 * Created by artyom on 06.04.16.
 */
public class MyGrid extends View {

    private int wigth;
    private int height;

    private Paint paint;


    private int gridWidth;

    private int colNumber;
    private int rawNumber;

    public MyGrid(Context context) {
        this(context, null);
    }

    public MyGrid(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyGrid(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.grid_color));
    }


    public void setColNumber(int colNumber) {
        this.colNumber = colNumber;
    }

    public void setRawNumber(int rawNumber) {
        this.rawNumber = rawNumber;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        wigth = w;
        height = h;


        gridWidth = (int) ((int) wigth / colNumber);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int calcWidth = gridWidth * colNumber;
        int calcHeight = gridWidth * rawNumber;
        int x = 0;
        int y = 0;
        while (x <= calcWidth) {
            canvas.drawLine(x, 0, x, calcHeight, paint);
            x += gridWidth;
        }

        while (y <= calcHeight) {
            canvas.drawLine(0, y, calcWidth, y, paint);
            y += gridWidth;
        }

    }
}
