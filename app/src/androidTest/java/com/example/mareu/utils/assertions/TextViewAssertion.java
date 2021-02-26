package com.example.mareu.utils.assertions;



import android.service.autofill.TextValueSanitizer;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import com.example.mareu.R;

import static org.junit.Assert.assertEquals;

public class TextViewAssertion implements ViewAssertion {
    private final int mItemPosition;
    private final int mExpectedId;
    private final String mExpectedText;

    private TextViewAssertion(int itemPosition, int expectedId, String expectedText) {
        mItemPosition = itemPosition;
        mExpectedId = expectedId;
        mExpectedText=expectedText;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(mItemPosition);
        assert viewHolder != null;
        TextView textView = viewHolder.itemView.findViewById(mExpectedId);

        assertEquals(mExpectedText, textView.getText().toString());


    }

    public static TextViewAssertion matchDescriptionAtPosition(int itemPosition, String expectedText) {
        return new TextViewAssertion(itemPosition, R.id.description_item, expectedText);

    }


    public static TextViewAssertion matchParticipantsPosition(int itemPosition, String expectedText) {
        return new TextViewAssertion(itemPosition, R.id.partipants_item, expectedText);

    }


}
