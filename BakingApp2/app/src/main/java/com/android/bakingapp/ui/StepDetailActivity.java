package com.android.bakingapp.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.bakingapp.R;

public class StepDetailActivity extends AppCompatActivity {
    private int mStepId;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        mStepId = getIntent().getIntExtra("StepId", -1);
        mPosition = getIntent().getIntExtra("Position", -1);

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("StepId", mStepId);
            bundle.putInt("Position", mPosition);
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.step_detail_container, stepDetailFragment).commit();
        }
    }
}
