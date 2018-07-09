package com.android.bakingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.bakingapp.R;

public class MainActivity extends AppCompatActivity implements RecipeListFragment.OnRecipeClickListener{

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

    @Override
    public void onRecipeSelected(int position) {
        Bundle b = new Bundle();
        b.putInt("Position", position);
        final Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }
}
