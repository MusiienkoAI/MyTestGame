package artem.musiienko.tanks.presenterImpls;

import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import artem.musiienko.tanks.models.ServerModel;
import artem.musiienko.tanks.models.UserModel;
import artem.musiienko.tanks.presenters.NewServerPresenter;
import artem.musiienko.tanks.utils.Consts;
import artem.musiienko.tanks.views.NewServerView;
import io.realm.Realm;

/**
 * Created by artyom on 05.07.16.
 */
public class NewServerPresenterImpl implements NewServerPresenter, AdapterView.OnItemSelectedListener {


    private NewServerView view;

    private DatabaseReference mDatabase;

    public NewServerPresenterImpl(NewServerView view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("servers");
        serverModel = new ServerModel();
    }


    private ServerModel serverModel;

    @Override
    public void initSpinner(AppCompatSpinner spinner) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("2");
        strings.add("4");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(spinner.getContext(), android.R.layout.simple_list_item_1, strings);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(adapter);
    }

    @Override
    public void setPrivate(boolean aPrivate) {
        if (aPrivate)
            serverModel.setPrivateStatus(Consts.ServerPrivateStatus.PRIVATE.ordinal());
        else {
            serverModel.setPrivateStatus(Consts.ServerPrivateStatus.PUBLIC.ordinal());
            serverModel.setPassword("");
            view.clearPassword();
        }
    }

    @Override
    public void setName(String name) {
        serverModel.setName(name);
    }

    @Override
    public void setPassword(String password) {
        serverModel.setPassword(password);
    }

    @Override
    public void create() {
        if (isValid()) {
            String key = mDatabase.push().getKey();
            serverModel.setId(key);
            serverModel.setCreateTime(Calendar.getInstance().getTimeInMillis());
            serverModel.setCurCount(1);
            mDatabase.child(key).setValue(serverModel);


            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Realm realm = Realm.getDefaultInstance();
            UserModel userModel = realm.where(UserModel.class).equalTo("id", uid).findFirst();
            realm.beginTransaction();
            userModel.setServerId(key);
            realm.commitTransaction();
            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("serverId").setValue(key);


            view.enterTheLobby(key);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
            serverModel.setMaxCount(2);
        }
        if (i == 1) {
            serverModel.setMaxCount(4);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private boolean isValid() {
        if (serverModel.getName().trim().length() == 0) {
            view.onValidationError(Consts.Errors.EMPTY_NAME);
            return false;
        }

        if (serverModel.getName().trim().length() < 4) {
            view.onValidationError(Consts.Errors.SHORT_NAME);
            return false;
        }


        if (serverModel.getPassword().trim().length() == 0 && serverModel.getPrivateStatus() == Consts.ServerPrivateStatus.PRIVATE.ordinal()) {
            view.onValidationError(Consts.Errors.EMPTY_PASSWORD);
            return false;
        }

        return true;
    }
}
