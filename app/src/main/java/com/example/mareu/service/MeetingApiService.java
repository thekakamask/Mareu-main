package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.Calendar;
import java.util.List;

public interface MeetingApiService {


    //RECUPERER LES SALLES
    public List<String> getRooms();

    //AJOUTER UNE SALLE
    public void addRoom(String room);

    //SUPPRIMER UNE SALLE
    public void delRoom(String room);

    //SUPPRIMER TTES LES SALLES POUR L'INITIALISATION
    public void delAllRooms();

    // RECUPERE LES REUNIONS
    public List<Meeting> getMeetings(Calendar date, String roomName);

    // AJOUTER UNE REUNION
    public void addMeeting(Meeting meeting) throws MeetingApiServiceException;

    // SUPPRIMER UNE REUNION
    public void delMeeting(Integer idMeeting);

}
