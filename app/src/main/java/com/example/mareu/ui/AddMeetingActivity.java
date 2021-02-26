package com.example.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.mareu.R;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiServiceException;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;

import static com.example.mareu.ui.meeting_list.ListMeetingActivity.sApiService;
import static com.example.mareu.utils.Validator.validEmail;

public class AddMeetingActivity extends AppCompatActivity {

    @BindView(R.id.room_name_layout)
    TextInputLayout mRoomNameTextInputLayout;
    @BindView(R.id.room_name)
    AutoCompleteTextView mRoomNameAutoCompleteTextView;

    @BindView(R.id.topic_layout)
    TextInputLayout mTopicTextInputLayout;
    @BindView(R.id.topic)
    TextInputEditText mTopicInputEditText;

    @BindView(R.id.date_layout)
    TextInputLayout mDateTextInputLayout;
    @BindView(R.id.date)
    TextInputEditText mDateTextInputEditText;

    @BindView(R.id.from_layout)
    TextInputLayout mFromTextInputLayout;
    @BindView(R.id.from)
    TextInputEditText mFromTextInputEditText;

    @BindView(R.id.to_layout)
    TextInputLayout mToTextInputLayout;
    @BindView(R.id.to)
    TextInputEditText mToTextInputEditText;

    @BindView(R.id.participants)
    TextInputLayout mParticipantsTextInputLayout;
    @BindView(R.id.emails_group)
    ChipGroup mEmailChipGroup;
    @BindView(R.id.emails)
    TextInputEditText mEmailTextInputEditText;

    private boolean mError;
    private Calendar mNow;
    private List<String> mRooms;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_meeting);
        ButterKnife.bind(this);
        mError = false;
        mNow = Calendar.getInstance();

        mRooms = sApiService.getRooms();

        mRoomNameAutoCompleteTextView.setAdapter(new ArrayAdapter<>(
                this, R.layout.room_item,
                mRooms));

        initEmailsOnKeyListener();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_meeting, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.button_add_meeting:
                addMeeting();
                return true;
            case android.R.id.home:
                Toast.makeText(this.getApplicationContext(), R.string.abort_add_meeting, Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);

    }

    @OnTouch(R.id.room_name)
    boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mRoomNameAutoCompleteTextView.showDropDown();
            return true;
        }
        return (event.getAction() == MotionEvent.ACTION_UP);
    }

    @OnClick(R.id.date)
    void displayDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog mDatePickerDialog;

        mDatePickerDialog = new DatePickerDialog(AddMeetingActivity.this,
                (view, year, month, dayOfMonth) -> {
                    Calendar cal = Calendar.getInstance();
                    cal.set(year, month, dayOfMonth);
                    mDateTextInputEditText.setText(DateFormat.getDateFormat(getApplicationContext()).format(cal.getTime()));
                    if(cal.before(calendar))
                        mDateTextInputLayout.setError(getText(R.string.error_date_passed));

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        mDatePickerDialog.show();

    }

    @OnClick({R.id.from, R.id.to})
    //POURQUOI VIEW V ET PAS AUSSI DANS LE DISPLAYDATEPICKER
    void displayTimePicker(View v) {
        final int id = v.getId();

        Calendar time = Calendar.getInstance();
        TimePickerDialog mTimePickerDialog;

        // EXPLIQUEZ CES DEUX LIGNES
        int roundedMinutes = time.get(Calendar.MINUTE) % 15;
        time.add(Calendar.MINUTE, roundedMinutes > 0 ? (15 - roundedMinutes) : 0);

        mTimePickerDialog = new TimePickerDialog(AddMeetingActivity.this,
                (view, hourOfDay, minute) -> {
                    Calendar tim = Calendar.getInstance();
                    tim.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    tim.set(Calendar.MINUTE, minute);
                    if (id == R.id.from)
                        mFromTextInputEditText.setText(DateFormat.getTimeFormat(getApplicationContext()).format(tim.getTime()));
                    else if (id == R.id.to)
                        mToTextInputEditText.setText(DateFormat.getTimeFormat(getApplicationContext()).format(tim.getTime()));
                },
                time.get(Calendar.HOUR_OF_DAY),
                time.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(AddMeetingActivity.this));

        mTimePickerDialog.show();
    }

    private void addMeeting() {
        String roomName = validateTextInput(mRoomNameTextInputLayout);
        String topic = validateTextInput(mTopicTextInputLayout);
        Calendar date = validateDateInput(mDateTextInputLayout);
        Calendar start = validateTimeInput(mFromTextInputLayout);
        Calendar end = validateTimeInput(mToTextInputLayout);
        List<String> participants = validateEmailInput(mParticipantsTextInputLayout,mEmailChipGroup);

        if (start != null && end != null) {
            if (end.before(start)) {
                mToTextInputLayout.setError(getText(R.string.error_time_comparaison));
                mError = true;

            }
        }

        if (date != null && end != null) {
            start.set(Calendar.YEAR, date.get(Calendar.YEAR));
            start.set(Calendar.MONTH, date.get(Calendar.MONTH));
            start.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));

            if(start.before(mNow)) {
                mFromTextInputLayout.setError(getText(R.string.error_time_passed));
                mError = true;

            }

        }
        if (date!= null && end !=null) {
            end.set(Calendar.YEAR, date.get(Calendar.YEAR));
            end.set(Calendar.MONTH, date.get(Calendar.MONTH));
            end.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
        }

        if (mError) {
            Toast.makeText(this.getApplicationContext(), R.string.error_add_new_meeting, Toast.LENGTH_LONG).show();
            mError = false;

        } else {
            try {
                sApiService.addMeeting(new Meeting(
                        roomName,
                        start,
                        end,
                        topic,
                        participants));
                Toast.makeText(this.getApplicationContext(),R.string.add_new_meeting, Toast.LENGTH_LONG).show();

                finish();

            } catch(MeetingApiServiceException e) {
                mRoomNameTextInputLayout.setError(getText(R.string.error_meeting_already_create));
                Toast.makeText(this.getApplicationContext(),R.string.error_meeting_already_create, Toast.LENGTH_LONG).show();
                mError =false;

            }


        }


    }

    private List<String> validateEmailInput(TextInputLayout inputValue, ChipGroup emailChipGroup) {
        inputValue.setError(null);
        int nb= emailChipGroup.getChildCount();
        List<String> lEmails = new ArrayList<>();

        if (nb == 0) {
            inputValue.setError(getText(R.string.error_empty_field));
            mError=true;
            return null;
        } else {
            for (int i = 0; i < nb; i++) {
                Chip tmpEmail = (Chip) emailChipGroup.getChildAt(i);
                String email = tmpEmail.getText().toString();
                lEmails.add(email);

            }
            return lEmails;

        }

    }

    private Calendar validateTimeInput(TextInputLayout inputValue) {
        String tmpValue = Objects.requireNonNull(inputValue.getEditText().getText().toString().trim());

        if (tmpValue.isEmpty()) {
            inputValue.setError(getText(R.string.error_empty_field));
            mError = true;
            return null;
        } else {

            try {
                Date dTime = android.text.format.DateFormat.getTimeFormat(getApplicationContext()).parse(tmpValue);

                Calendar time = Calendar.getInstance();
                time.setTime(Objects.requireNonNull(dTime));

                time.set(Calendar.YEAR, mNow.get(Calendar.YEAR));
                time.set(Calendar.MONTH, mNow.get(Calendar.MONTH));
                time.set(Calendar.DAY_OF_MONTH, mNow.get(Calendar.DAY_OF_MONTH));

                inputValue.setError(null);

                return time;
            } catch (ParseException e) {
                inputValue.setError(getText(R.string.error_invalid_time_format));
                mError = true;
                return null;
            }
        }
    }

    private Calendar validateDateInput(TextInputLayout inputValue) {
        String tmpValue = Objects.requireNonNull(inputValue.getEditText()).getText().toString().trim();

        if (tmpValue.isEmpty()) {
            inputValue.setError(getText(R.string.error_empty_field));
            mError = true;
            return null;
        } else {
            //FORMAT VALIDE OU NON?
            try {
                Date dDate = DateFormat.getDateFormat(getApplicationContext()).parse(tmpValue);

                Calendar now = Calendar.getInstance();
                now.set(Calendar.HOUR_OF_DAY, 0);
                now.set(Calendar.MINUTE, 0);
                now.set(Calendar.SECOND, 0);
                now.set(Calendar.MILLISECOND, 0);

                Calendar date = (Calendar) now.clone();
                date.setTime(Objects.requireNonNull(dDate));

                if (date.before(now)) {
                    inputValue.setError(getText(R.string.error_date_passed));
                    mError = true;
                    return null;
                }

                inputValue.setError(null);

                return date;

            } catch (ParseException e) {
                inputValue.setError(getText(R.string.error_invalid_date_format));
                mError = true;
                return null;
            }
        }
    }

    private String validateTextInput(TextInputLayout inputValue) {
        String tmpValue = Objects.requireNonNull(inputValue.getEditText()).getText().toString().trim();

        if (tmpValue.isEmpty()) {
            inputValue.setError(getText(R.string.error_empty_field));
            mError = true;
            return null;
        } else {
            inputValue.setError(null);
            return tmpValue;
        }
    }

    private void initEmailsOnKeyListener() {
        mEmailTextInputEditText.setOnKeyListener((v,keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String value = Objects.requireNonNull(mEmailTextInputEditText.getText()).toString().trim();

                    if (!value.isEmpty()) {
                        if (!validEmail(value)) {
                            mParticipantsTextInputLayout.setError(getText(R.string.error_invalid_email));

                            return false;
                        } else {
                            addEmailToChipGroup(value);

                            return true;
                        }
                    }
                }
            }
            return false;
        });
    }

    @OnTextChanged(R.id.emails)
    void afterTextChanged(Editable s) {
        String value = s.toString();

        if (value.length() > 0) {
            char lastChar = value.charAt(value.length() - 1);

            if (lastChar == ' ' || lastChar == ',') {
                value = value.substring(0, value.length() - 1);
                value = value.trim();

                if (!value.isEmpty()) {
                    if (!validEmail(value)) {
                        mParticipantsTextInputLayout.setError(getText(R.string.error_invalid_email));
                    } else {
                        addEmailToChipGroup(value);
                    }
                }
            }
        }
    }

    private void addEmailToChipGroup (String email) {
        final Chip emailChip = new Chip (AddMeetingActivity.this);
        emailChip.setText(email);
        emailChip.setCloseIconVisible(true);
        emailChip.setOnCloseIconClickListener(v -> mEmailChipGroup.removeView(emailChip));

        mEmailChipGroup.addView(emailChip);
        mEmailTextInputEditText.setText("");
        mParticipantsTextInputLayout.setError(null);

    }


}
