package com.example.mareu.events;

public class DeleteMeetingEvent {

    private int meetingId;

    public DeleteMeetingEvent(int meetingId) {
        this.meetingId = meetingId;
    }

    public int getMeetingId() {
        return meetingId;
    }

}
