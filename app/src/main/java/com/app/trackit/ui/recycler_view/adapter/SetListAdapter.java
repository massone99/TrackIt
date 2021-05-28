package com.app.trackit.ui.recycler_view.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.app.trackit.model.Set;
import com.app.trackit.model.viewmodel.WorkoutViewModel;
import com.app.trackit.ui.recycler_view.viewholder.SetListViewHolder;

public class SetListAdapter extends ListAdapter<Set, SetListViewHolder> {

    // TODO: fare in modo che indice del nuovo set da aggiungere venga
    //  ricordato magari tramite Bundle del fragment

    private WorkoutViewModel model;

    public SetListAdapter(@NonNull DiffUtil.ItemCallback<Set> diffCallback, WorkoutViewModel model) {
        super(diffCallback);
        this.model = model;
    }

    @NonNull
    @Override
    public SetListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return SetListViewHolder.create(parent, model);
    }

    @Override
    public void onBindViewHolder(@NonNull SetListViewHolder holder, int position) {
        Set current = getItem(position);
        holder.bind(current);
        holder.showRightLayout();
    }

    public static class SetDiff extends DiffUtil.ItemCallback<Set> {

        @Override
        public boolean areItemsTheSame(@NonNull Set oldItem, @NonNull Set newItem) {
            return oldItem.setId == newItem.setId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Set oldItem, @NonNull Set newItem) {
            return oldItem.equals(newItem);
        }
    }
}
