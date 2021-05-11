package com.app.trackit.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private boolean isRotated = false;
    private Context context;


    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    protected RecyclerView mRecyclerView;
    protected HomeAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected LayoutManagerType mCurrentLayoutManagerType;

    public HomeFragment() {
        super(R.layout.home_fragment);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // In this method the initialization of the dataset should be completed
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        rootView.setTag(TAG);

        mRecyclerView = rootView.findViewById(R.id.home_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mAdapter = new HomeAdapter();
        mRecyclerView.setAdapter(mAdapter);

        FabAnimation.init(rootView.findViewById(R.id.fab_add_exercise));
        FabAnimation.init(rootView.findViewById(R.id.fab_add_workout));

        rootView.findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRotated = FabAnimation.rotateFab(v, !isRotated);
                if (isRotated) {
                    FabAnimation.showIn(rootView.findViewById(R.id.fab_add_exercise));
                    FabAnimation.showIn(rootView.findViewById(R.id.fab_add_workout));
                } else {
                    FabAnimation.showOut(rootView.findViewById(R.id.fab_add_exercise));
                    FabAnimation.showOut(rootView.findViewById(R.id.fab_add_workout));
                }
            }
        });

        rootView.findViewById(R.id.fab_add_exercise).setOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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