package artem.musiienko.tanks;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by artyom on 18.07.16.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initDB();
    }


    private void initDB() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
