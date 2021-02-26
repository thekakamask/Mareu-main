package com.example.mareu.utils.assertions;

import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ChipNoValueAssertion implements ViewAssertion {

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        int count = ((ChipGroup) view).getChildCount();

        assertEquals(0, count);
    }

    public static ChipNoValueAssertion NoChipGroup() {
        return new ChipNoValueAssertion();

    }
}
