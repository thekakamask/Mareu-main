package com.example.mareu.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class Meeting implements Comparable<Meeting> {
    private static Integer mFirID = 0;
    private static Random mRandom = new Random();


    private Integer mId;
    private String mRoomName;
    private Calendar mStartTime;
    private Calendar mEndTime;
    private String mTopic;
    private List<String> mParticipants;
    private Integer mColor;

    //CONSTRUCTEUR

    public Meeting(String roomName, Calendar startTime, Calendar endTime, String topic, List<String> participants) {
        mId=++mFirID;
        mRoomName=roomName;
        mStartTime=startTime;
        mEndTime=endTime;
        mTopic=topic;
        mParticipants= new ArrayList<>();
        this.setParticipants(participants);
        //COLOR RANDOM GENERATE;
        mColor = Color.argb(
                mRandom.nextInt(255),
                mRandom.nextInt(255),
                mRandom.nextInt(255),
                mRandom.nextInt(255));
    }

    //GET ET SET

    public Integer getId() {
        return mId;
    }

    public String getRoomName() {
        return mRoomName;
    }

    public Calendar getStartTime() {
        return mStartTime;
    }

    public Calendar getEndTime() {
        return mEndTime;
    }

    public String getTopic() {
        return mTopic;
    }

    public List<String> getParticipants() {
        return mParticipants;
    }

    public Integer getColor() {
        return mColor;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public void setRoomName(String roomName) {
        mRoomName = roomName;
    }

    public void setStartTime(Calendar startTime) {
        mStartTime = startTime;
    }

    public void setEndTime(Calendar endTime) {
        mEndTime = endTime;
    }

    public void setTopic(String topic) {
        mTopic = topic;
    }

    public void setColor(Integer color) {
        mColor = color;
    }

    private void setParticipants(List<String> participants) {
        String email_pattern =  "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(email_pattern, Pattern.CASE_INSENSITIVE);

        for (String participant : participants) {
            if (pattern.matcher(participant).matches()){
                mParticipants.add(participant);

            }
        }

    }

    @Override
    public int compareTo(Meeting o) {
        if (getStartTime() == null || o.getStartTime() == null) {
            return 0;
        }
        return getStartTime().compareTo(o.getStartTime());
    }

}
