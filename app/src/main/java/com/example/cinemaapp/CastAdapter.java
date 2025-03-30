package com.example.cinemaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private Context context;
    private ArrayList<String> actorList;

    public CastAdapter(Context context, ArrayList<String> actorList) {
        this.context = context;
        this.actorList = actorList;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cast, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        String actor = actorList.get(position);
        holder.actorName.setText(actor);
    }

    @Override
    public int getItemCount() {
        return actorList.size();
    }

    public static class CastViewHolder extends RecyclerView.ViewHolder {

        TextView actorName;

        public CastViewHolder(View itemView) {
            super(itemView);
            actorName = itemView.findViewById(R.id.actorName);
        }
    }
}
