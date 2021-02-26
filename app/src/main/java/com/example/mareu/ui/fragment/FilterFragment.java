package com.example.mareu.ui.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class FilterFragment extends DialogFragment {
    @BindView(R.id.date_by_filter)
    TextInputEditText mDateByFilter;
    @BindView(R.id.room_by_filter)
    AutoCompleteTextView mRoomByFilter;

    private List<String> mRooms;
    private Calendar mDate;
    private String mRoom;

    private OnButtonClickedListener mCallback;

    public FilterFragment(List<String> rooms) {
        mRooms = rooms;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("inflateParams")
        View view = inflater.inflate(R.layout.filter, null);
        ButterKnife.bind(this, view);

        mRoomByFilter.setAdapter(new ArrayAdapter<>(
                Objects.requireNonNull(getContext()),
                R.layout.room_item,
                mRooms));

        builder.setView(view);
        builder.setTitle(R.string.select_filter);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            mCallback.onButtonClicked(mDate, mRoomByFilter.getEditableText().toString(), false);
        });

        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());

        builder.setNeutralButton(R.string.reset, (dialog, which) -> mCallback.onButtonClicked(mDate, mRoomByFilter.getEditableText().toString(), true));

        return builder.create();
    }



    public interface OnButtonClickedListener {
        void onButtonClicked(Calendar date, String room, boolean reset);
    }

    public void createCallbackToParentActivity() {
        mCallback = (OnButtonClickedListener) getActivity();
    }

    @OnClick(R.id.date_by_filter)
    void displayDatePicker(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog mDatePikerDialog;

        mDatePikerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()),
                (view, year, month, dayOfMonth) -> {
                    Calendar cal = Calendar.getInstance();
                    cal.set(year, month, dayOfMonth);
                    mDateByFilter.setText(DateFormat.getDateFormat(getContext()).format(cal.getTime()));
                    mDate = cal;
                },
                calendar.get(Calendar.YEAR),
                calendar.get(calendar.MONTH),
                calendar.get(calendar.DAY_OF_MONTH));

        mDatePikerDialog.show();
    }

    @OnTouch(R.id.room_by_filter)
    boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            mRoomByFilter.showDropDown();
            return true;
        }

        return (event.getAction() == MotionEvent.ACTION_UP);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        createCallbackToParentActivity();
    }




}
