package com.android.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.bakingapp.R;
import com.android.bakingapp.data.Recipes;

public class RecipeListFragment extends Fragment {
    Recipes mRecipes;

    public RecipeListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recipes_rv_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecipes = new Recipes();
        RecipeListAdapter mAdapter = new RecipeListAdapter(getContext(), mRecipes.getRecipes());
        recyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
