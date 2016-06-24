package artem.musiienko.mytestgame.units;

import artem.musiienko.mytestgame.interfaces.DemolishInterface;

/**
 * Created by artyom on 22.06.16.
 */
public abstract class Unit  {

    protected int id;

    protected int xStart;
    protected int yStart;
    protected int xEnd;
    protected int yEnd;
    protected int wLong = 1;
    protected int hLong = 1;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getxStart() {
        return xStart;
    }

    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    public int getyStart() {
        return yStart;
    }

    public void setyStart(int yStart) {
        this.yStart = yStart;
    }

    public int getxEnd() {
        return xEnd;
    }

    public void setxEnd(int xEnd) {
        this.xEnd = xEnd;
    }

    public int getyEnd() {
        return yEnd;
    }

    public void setyEnd(int yEnd) {
        this.yEnd = yEnd;
    }

    public int getwLong() {
        return wLong;
    }

    public void setwLong(int wLong) {
        this.wLong = wLong;
    }

    public int gethLong() {
        return hLong;
    }

    public void sethLong(int hLong) {
        this.hLong = hLong;
    }
}
