package artem.musiienko.tanks.presenterImpls;

import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import artem.musiienko.tanks.adapters.ServerAdapter;
import artem.musiienko.tanks.models.ServerModel;
import artem.musiienko.tanks.presenters.ServersPresenter;

import artem.musiienko.tanks.views.ServersView;
import io.realm.Realm;

/**
 * Created by artyom on 05.07.16.
 */
public class ServersPresenterImpl implements ServersPresenter {


    public static final String TAG = ServersPresenterImpl.class.getSimpleName();

    private ServersView view;

    private DatabaseReference mDatabase;

    private ServerAdapter serverAdapter;


    public ServersPresenterImpl(ServersView gameView) {
        this.view = gameView;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("servers");
        mDatabase.addChildEventListener(childEventListener);
    }

    @Override
    public void setAdapter(RecyclerView recyclerView) {

        serverAdapter = new ServerAdapter(this);


        recyclerView.setAdapter(serverAdapter);






    }

    @Override
    public void checkPassword(String id, String password) {

    }

    @Override
    public void onServerClick(String id) {
        view.enterTheLobby(id);
    }

    @Override
    public void onDestroy() {

        mDatabase.removeEventListener(childEventListener);
    }


    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            ServerModel model = dataSnapshot.getValue(ServerModel.class);
            realm.insertOrUpdate(model);
            realm.commitTransaction();
            serverAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            ServerModel model = dataSnapshot.getValue(ServerModel.class);
            realm.insertOrUpdate(model);
            realm.commitTransaction();
            serverAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            ServerModel model = dataSnapshot.getValue(ServerModel.class);
            realm.where(ServerModel.class).equalTo("id", model.getId()).findAll().deleteFirstFromRealm();
            realm.commitTransaction();
            serverAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };



}
