package com.example.firestorecrud;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView mTitleTv, mDescriptionTv;
    View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
        //item long click listener
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });

        //initialize views with model_layout.xml
        mTitleTv = itemView.findViewById(R.id.rTitleTv);
        mDescriptionTv = itemView.findViewById(R.id.rDescriptionTv);
    }
    private ViewHolder.ClickListener mClickListener;
    //iterface for click listener
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);

    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener) {
       mClickListener = clickListener;
    }
}
