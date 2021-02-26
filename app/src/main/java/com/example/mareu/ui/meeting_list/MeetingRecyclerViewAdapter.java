package com.example.mareu.ui.meeting_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mareu.R;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.view.ItemMeeting;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.example.mareu.ui.meeting_list.ListMeetingActivity.sApiService;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<ItemMeeting> {
    private Context mContext;
    private List<Meeting> mMeetings;

    MeetingRecyclerViewAdapter(Context context, Calendar date, String room) {
        mContext = context;
        mMeetings = sApiService.getMeetings(date, room);
    }

    @NonNull
    @Override
    public ItemMeeting onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_item, parent, false);
        return new ItemMeeting(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemMeeting holder, int position) {
        final Meeting meeting = mMeetings.get(position);

        //DEMANDER EXPLICATION POUR CI DESsOUS

        String itemInfoRecup = TextUtils.join(" - ", Arrays.asList(
                meeting.getRoomName(),
                DateFormat.getTimeFormat(mContext).format(meeting.getStartTime().getTime()),
                meeting.getTopic()));

        holder.mDescriptionTextView.setText(itemInfoRecup);
        holder.mParticipantsTextView.setText(
                TextUtils.join(", ",
                        meeting.getParticipants()));
        ((GradientDrawable)holder.mImageView.getBackground()).setColor(meeting.getColor());

        holder.mDeleteImageButton.setOnClickListener( v -> EventBus.getDefault().post(new DeleteMeetingEvent(meeting.getId())));




    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }
}










