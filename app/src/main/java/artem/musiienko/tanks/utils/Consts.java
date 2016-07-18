package artem.musiienko.tanks.utils;

/**
 * Created by artyom on 22.06.16.
 */
public class Consts {


    public static class Vector{
        public static final int UP =1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
        public static final int LEFT = 4;
        public static final int NOWHEARE = 0;

    }

    public static class Defines {
        public static final int RADIUS = 10;
    }


    public enum ServerStatus {
        Offline,
        Online,
        Ready,
        Playing
    }


    public static class Tags {
        public static final int MENU = 1;
        public static final int MULTIPLAYER = 2;
        public static final int OPTIONS = 3;
        public static final int TRAINING = 4;
        public static final int CREATENEW = 5;
        public static final int LOBBY = 6;
        public static final int UPGRADE = 7;
    }



}
