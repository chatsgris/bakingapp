package com.android.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.bakingapp.R;
import com.android.bakingapp.data.Recipes;

import butterknife.BindBool;

public class RecipeListFragment extends Fragment implements RecipeListAdapter.OnItemClicked {
    Recipes mRecipes;
    @BindBool(R.bool.is_tablet) boolean mTwoPane;
    OnRecipeClickListener mCallback;

    public interface OnRecipeClickListener {
        void onRecipeSelected(int position);
    }

    public RecipeListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recipes_rv_view);

        if (mTwoPane) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        }
        mRecipes = new Recipes();
        RecipeListAdapter mAdapter = new RecipeListAdapter(getContext(), mRecipes.getRecipes());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnClick(this);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecipeClickListener");
        }
    }

    @Override
    public void onItemClick(int position) {
        mCallback.onRecipeSelected(position);
    }
}
