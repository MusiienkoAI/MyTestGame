package artem.musiienko.tanks.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by artyom on 19.07.16.
 */

@RealmClass
public class UserModel implements RealmModel {

    @PrimaryKey
    private String id;

    private String name;

    private int ready;

    private String serverId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int isReady() {


        return ready;
    }

    public void setReady(int ready) {
        this.ready = ready;
    }


    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("ready", ready);
        result.put("serverId", serverId);

        return result;
    }
}
