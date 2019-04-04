package com.dev.poplify.projectsample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dev.poplify.projectsample.R;

public class ShowDataActivity extends AppCompatActivity {

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_data_activity);
        text = findViewById(R.id.text);
        text.setText("TrackName: "+this.getIntent().getExtras().get("trackName").toString());
    }
}
