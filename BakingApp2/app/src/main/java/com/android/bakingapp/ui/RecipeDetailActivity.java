package com.android.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.bakingapp.R;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnStepClickListener {
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        mPosition = getIntent().getIntExtra("Position", -1);

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("Position", mPosition);
            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            recipeDetailFragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.recipe_detail_container, recipeDetailFragment).commit();
        }
    }

    @Override
    public void onStepSelected(int stepId) {
        Bundle b = new Bundle();
        b.putInt("StepId", stepId);
        b.putInt("Position", mPosition);
        final Intent intent = new Intent(this, StepDetailActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }
}
