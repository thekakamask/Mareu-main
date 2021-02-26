package com.example.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.service.MeetingApiService;
import com.example.mareu.ui.AddMeetingActivity;
import com.example.mareu.ui.fragment.FilterFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListMeetingActivity extends AppCompatActivity implements FilterFragment.OnButtonClickedListener {
    public static MeetingApiService sApiService;

    @BindView(R.id.list) RecyclerView mRecyclerView;
    @BindView(R.id.add_button) FloatingActionButton mFloatingActionButton;
    private MeetingRecyclerViewAdapter mMeetingRecyclerViewAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        ButterKnife.bind(this);

        sApiService = DI.getMeetingApiService();

        mFloatingActionButton.setOnClickListener
                (v -> startActivity(new Intent(ListMeetingActivity.this, AddMeetingActivity.class)));

        //EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        init(null,"");
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);


    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter) {
            performFilter();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void performFilter() {
        FilterFragment filterDialog = new FilterFragment(sApiService.getRooms());
        filterDialog.show(getSupportFragmentManager(), "filter");
    }

    private void init(Calendar date, String room) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMeetingRecyclerViewAdapter = new MeetingRecyclerViewAdapter(this, date, room);
        mRecyclerView.setAdapter(mMeetingRecyclerViewAdapter);
    }


    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        sApiService.delMeeting(event.getMeetingId());

        Toast.makeText(getApplicationContext()," the meeting has been deleted!", Toast.LENGTH_SHORT).show();

        init(null, "");
    }


    //BOUTON
    @Override
    public void onButtonClicked(Calendar date, String room, boolean reset) {
        if(reset || date != null || ! room.isEmpty())
            init(date,room);
    }


    //METTRE DANS LE ON CREATE LE METHODE SUBSCRIBE

}