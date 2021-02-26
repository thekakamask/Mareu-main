package com.example.mareu.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;

public class ItemMeeting extends RecyclerView.ViewHolder {

    public final ImageView mImageView;
    public final TextView mDescriptionTextView;
    public final TextView mParticipantsTextView;
    public final ImageButton mDeleteImageButton;

    public ItemMeeting(View itemView) {
        super(itemView);

        mImageView = itemView.findViewById(R.id.circle_item);
        mDescriptionTextView = itemView.findViewById(R.id.description_item);
        mParticipantsTextView = itemView.findViewById(R.id.partipants_item);
        mDeleteImageButton = itemView.findViewById(R.id.delete_item);
    }


}
