package com.example.mareu.utils;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static com.example.mareu.utils.CalendarUtil.sameDate;

public class MeetingUtil {

    public static List<Meeting> getMeetingsMatchDate(Calendar date, List<Meeting> meetings) {
        List<Meeting> mmd = new ArrayList<>();

        for (Meeting m : meetings)
            if(sameDate(m.getStartTime(),date))
                mmd.add(m);

        Collections.sort(mmd);

        return mmd;
    }

    public static List<Meeting> getMeetingsMatchRoom(String roomName, List<Meeting> meetings) {
        List<Meeting>mmd = new ArrayList<>();

        for(Meeting m : meetings)
            if(m.getRoomName().trim().equals(roomName.trim()))
                mmd.add(m);

        Collections.sort(mmd);

        return mmd;

    }





}

