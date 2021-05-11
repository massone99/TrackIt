package com.app.trackit.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.R;
import com.app.trackit.ui.animation.FabAnimation;
import com.app.trackit.ui.recycler_view.HomeAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private boolean clicked = false;
    private Context context;


    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;

    protected RecyclerView mRecyclerView;
    protected HomeAdapter mAdapter;
    protected FloatingActionButton addFabButton;
    protected FloatingActionButton addExerciseFabButton;
    protected FloatingActionButton addWorkoutFabButton;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected LayoutManagerType mCurrentLayoutManagerType;

    public HomeFragment() {
        super(R.layout.home_fragment);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        rootView.setTag(TAG);

        mRecyclerView = rootView.findViewById(R.id.home_recycler_view);
        addFabButton =  rootView.findViewById(R.id.fab_add);
        addExerciseFabButton = rootView.findViewById(R.id.fab_add_exercise);
        addWorkoutFabButton = rootView.findViewById(R.id.fab_add_workout);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mAdapter = new HomeAdapter();
        mRecyclerView.setAdapter(mAdapter);

        FabAnimation.init(rootView.findViewById(R.id.fab_add_exercise));
        FabAnimation.init(rootView.findViewById(R.id.fab_add_workout));

        this.addFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabClick();
            }
        });

        this.addExerciseFabButton.setOnClickListener(v -> {
            onFabClick();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(
                    R.anim.slide_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.fade_in,   // popEnter
                    R.anim.slide_out  // popExit
            );
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.replace(R.id.fragment_container_view, AddExerciseFragment.class, null);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        return rootView;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    private void onFabClick() {
        clicked = FabAnimation.rotateFab(this.addFabButton, !clicked);
        if (clicked) {
            FabAnimation.showIn(this.addExerciseFabButton);
            FabAnimation.showIn(this.addWorkoutFabButton);
        } else {
            FabAnimation.showOut(this.addExerciseFabButton);
            FabAnimation.showOut(this.addWorkoutFabButton);
        }
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }
}