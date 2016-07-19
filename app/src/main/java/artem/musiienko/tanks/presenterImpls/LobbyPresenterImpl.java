package artem.musiienko.tanks.presenterImpls;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import artem.musiienko.tanks.adapters.LobbyUserAdapter;
import artem.musiienko.tanks.models.ServerModel;
import artem.musiienko.tanks.models.UserModel;
import artem.musiienko.tanks.presenters.LobbyPresenter;
import artem.musiienko.tanks.views.LobbyView;
import io.realm.Realm;

/**
 * Created by artyom on 19.07.16.
 */
public class LobbyPresenterImpl implements LobbyPresenter {


    public static final String TAG = LobbyPresenterImpl.class.getSimpleName();

    private LobbyView view;

    private LobbyUserAdapter adapter;

    private String serviceId;

    private DatabaseReference mDatabase;

    private String userId;


    public LobbyPresenterImpl(LobbyView view, String serviceId) {
        this.view = view;
        this.serviceId = serviceId;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase.addChildEventListener(childEventListener);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public void setAdapter(RecyclerView recyclerView) {
        adapter = new LobbyUserAdapter(this, serviceId, userId);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setUserReady(String userId, int ready) {
        mDatabase.child(userId).child("ready").setValue(ready);
    }

    @Override
    public void onDestroy() {
        leaveLobby();
        mDatabase.removeEventListener(childEventListener);
    }


    private void leaveLobby() {
        Realm realm = Realm.getDefaultInstance();
        ServerModel model = realm.where(ServerModel.class).equalTo("id", serviceId).findFirst();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("servers");
        if (model.getCurCount() == 1) {

            mDatabase.child(serviceId).removeValue();

            realm.beginTransaction();
            realm.where(ServerModel.class).equalTo("id", model.getId()).findAll().deleteFirstFromRealm();
            realm.commitTransaction();


        } else {
            realm.beginTransaction();
            model.setCurCount(model.getCurCount() - 1);
            realm.commitTransaction();
            mDatabase.child(serviceId).child("curCount").setValue(model.getCurCount());
        }
    }


    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            UserModel userModel = dataSnapshot.getValue(UserModel.class);
            Log.d(TAG, "userModel = " + userModel.getName() + "serverId = " + userModel.getServerId());
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.insertOrUpdate(userModel);
            realm.commitTransaction();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            UserModel userModel = dataSnapshot.getValue(UserModel.class);
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.insertOrUpdate(userModel);
            realm.commitTransaction();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
