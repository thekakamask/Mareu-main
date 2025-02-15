package com.example.mareu.utils.assertions;

import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import com.google.android.material.textfield.TextInputLayout;

import static org.junit.Assert.assertNull;

public class TextInputLayoutNoErrorValueAssertion implements ViewAssertion {

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        assertNull(((TextInputLayout) view).getError());
    }

    public static TextInputLayoutNoErrorValueAssertion matchesNoErrorText() {
        return new TextInputLayoutNoErrorValueAssertion();
    }
}
