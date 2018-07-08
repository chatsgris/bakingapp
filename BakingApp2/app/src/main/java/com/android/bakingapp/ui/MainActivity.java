package com.android.bakingapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.bakingapp.R;

public class MainActivity extends AppCompatActivity {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean getTwoPane() {
        if (getResources().getBoolean(R.bool.is_tablet)) {
            mTwoPane = true;
        } else {mTwoPane = false;}
        return mTwoPane;
    }
}
