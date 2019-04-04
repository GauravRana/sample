package com.dev.poplify.projectsample.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dev.poplify.projectsample.adapter.CustomListAdapter;
import com.dev.poplify.projectsample.R;
import com.dev.poplify.projectsample.model.TuneModel;
import com.dev.poplify.projectsample.presenter.Presenter;
import com.dev.poplify.projectsample.presenter.PresenterImp;
import com.dev.poplify.projectsample.view.ViewClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewClass {

    private URL url;
    private HttpURLConnection urlConnection = null;
    private ArrayList<TuneModel> listData = new ArrayList<>();
    public Presenter mPresenter;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.custom_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ShowDataActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("trackName", listData.get(position).getTrackName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mPresenter = new PresenterImp(this, this, listData);
        mPresenter.backgroundTask();
    }

    @Override
    public void onBackgroundTaskComplete() {
        listView.setAdapter(new CustomListAdapter(this, listData));
    }

}
