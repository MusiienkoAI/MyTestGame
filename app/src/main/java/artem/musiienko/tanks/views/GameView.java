package artem.musiienko.tanks.views;

/**
 * Created by artyom on 22.06.16.
 */
public interface GameView {


    void setPossibleLeft(boolean possible);

    void setPossibleRight(boolean possible);

    void setPossibleUp(boolean possible);

    void setPossibleDown(boolean possible);

    void setAimLeft(boolean aim);

    void setAimRight(boolean aim);

    void setAimUp(boolean aim);

    void setAimDown(boolean aim);

    void setFireEnable(boolean enable);

    void hide();


}
