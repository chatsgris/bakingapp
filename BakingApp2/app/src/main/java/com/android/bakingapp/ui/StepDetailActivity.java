package com.android.bakingapp.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.android.bakingapp.R;
import com.android.bakingapp.data.Recipes;

public class StepDetailActivity extends AppCompatActivity {
    private int mStepId;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        mStepId = getIntent().getIntExtra("StepId", -1);
        mPosition = getIntent().getIntExtra("Position", -1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

        public void onPrevButtonClick(View view) {
        if (mStepId > 0) {
            mStepId -=1;
            Bundle bundle = new Bundle();
            bundle.putInt("StepId", mStepId);
            bundle.putInt("Position", mPosition);
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.step_detail_container, stepDetailFragment).commit();
        } else {
            mStepId = new Recipes().getSteps(mPosition).length();
            onPrevButtonClick(view);
        }
    }

    public void onNextButtonClick(View view) {
        if (mStepId < new Recipes().getSteps(mPosition).length() - 1) {
            mStepId += 1;
            Bundle bundle = new Bundle();
            bundle.putInt("StepId", mStepId);
            bundle.putInt("Position", mPosition);
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.step_detail_container, stepDetailFragment).commit();
        } else {
            mStepId = 0;
            onNextButtonClick(view);
        }
    }
}
