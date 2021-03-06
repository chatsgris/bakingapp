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
import android.widget.TextView;

import com.android.bakingapp.R;
import com.android.bakingapp.data.Recipes;

import org.w3c.dom.Text;

public class RecipeDetailFragment extends Fragment implements StepsAdapter.OnStepClicked {
    private Recipes mRecipes;
    private int mPosition;
    OnStepClickListener mCallback;

    public interface OnStepClickListener {
        void onStepSelected(int stepId);
    }

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRecipes = new Recipes();
        mPosition = this.getArguments().getInt("Position");

        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        RecyclerView ingredientsRecyclerView = rootView.findViewById(R.id.ingredients_rv_view);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(getContext(), mRecipes.getIngredients(mPosition));
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);

        RecyclerView stepsRecyclerView = rootView.findViewById(R.id.steps_rv_view);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        StepsAdapter stepsAdapter = new StepsAdapter(getContext(), mRecipes.getSteps(mPosition));
        stepsRecyclerView.setAdapter(stepsAdapter);
        stepsAdapter.setOnClick(this);

        TextView ingredientsHeader = rootView.findViewById(R.id.ingredient_header);
        TextView stepsHeader = rootView.findViewById(R.id.steps_header);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    @Override
    public void onStepClick(int stepId) {
        mCallback.onStepSelected(stepId);
    }
}
