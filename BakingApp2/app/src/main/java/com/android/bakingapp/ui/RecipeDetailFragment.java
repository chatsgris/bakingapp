package com.android.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.bakingapp.R;
import com.android.bakingapp.data.Recipes;

public class RecipeDetailFragment extends Fragment {
    private Recipes mRecipes;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        mRecipes = new Recipes();

        RecyclerView ingredientsRecyclerView = rootView.findViewById(R.id.ingredients_rv_view);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(getContext(), mRecipes.getRecipes());
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}