package artem.musiienko.tanks.presenterImpls;

import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import artem.musiienko.tanks.presenters.NewServerPresenter;
import artem.musiienko.tanks.views.NewServerView;

/**
 * Created by artyom on 05.07.16.
 */
public class NewServerPresenterImpl implements NewServerPresenter, AdapterView.OnItemSelectedListener {


    private NewServerView view;

    public NewServerPresenterImpl(NewServerView view) {
        this.view = view;
    }

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

    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public void create() {

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("onItemClick", "position = " + i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
