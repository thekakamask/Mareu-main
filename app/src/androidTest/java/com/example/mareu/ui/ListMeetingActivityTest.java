package com.example.mareu.ui;

import android.text.format.DateFormat;
import android.view.View;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.service.MeetingApiServiceException;
import com.example.mareu.ui.meeting_list.ListMeetingActivity;
import com.example.mareu.utils.assertions.RecyclerViewAssertion;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import androidx.test.rule.ActivityTestRule;


import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.utils.actions.ClickButtonAction.clickToDeleteButton;
import static com.example.mareu.utils.dummies.DummyMeetingGen.DELETE_ITEM_POSITION;
import static com.example.mareu.utils.dummies.DummyMeetingGen.EXPECTED_DESCRIPTION_12H;
import static com.example.mareu.utils.dummies.DummyMeetingGen.EXPECTED_DESCRIPTION_24h;
import static com.example.mareu.utils.dummies.DummyMeetingGen.EXPECTED_ITEM_POSITION;
import static com.example.mareu.utils.dummies.DummyMeetingGen.EXPECTED_PARTICIPANTS;
import static com.example.mareu.utils.dummies.DummyMeetingGen.ITEMS_COUNT;
import static com.example.mareu.utils.dummies.DummyMeetingGen.generateMeetings;
import static com.example.mareu.utils.dummies.DummyMeetingGen.generateRooms;
import static com.example.mareu.utils.matchers.ToastMatcher.isToast;
import static org.hamcrest.CoreMatchers.notNullValue;
import static com.example.mareu.utils.assertions.RecyclerViewAssertion.itemCountAssertion;
import static com.example.mareu.utils.assertions.TextViewAssertion.matchDescriptionAtPosition;
import static com.example.mareu.utils.assertions.TextViewAssertion.matchParticipantsPosition;

@RunWith(AndroidJUnit4.class)
public class ListMeetingActivityTest {

    //REGLE DEFNIT POUR INITIALISER LISTMEETINGACTIVITY AVEC DES DONNEES

    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityRule =
            new ActivityTestRule<ListMeetingActivity>(ListMeetingActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    try {
                        DI.initMeetingApiService(generateRooms(), generateMeetings());
                    } catch (MeetingApiServiceException e) {
                        e.printStackTrace();
                    }
                }
            };

    //INITIALISE LISTMEETINGACTIVITY AVEC LES DONNEES ET VERIFIE
    // QUE ELLE N'EST PAS VIDE

    @Before
    public void setUp() {
        ListMeetingActivity activity = mActivityRule.getActivity();
        assertThat(activity, notNullValue());
    }

    //VERIFIER LE NOMBRE D'OBJETS

    @Test
    public void giveMeetingsListStartGetValidMeetingCount() {
        onView(withId(R.id.list))
                .check(itemCountAssertion(ITEMS_COUNT));

    }

    // VERIFIER LA DESCRIPTION DU PREMIER OBJET

    @Test
    public void giveMeetingsListStartDisplayValidMeetingDescription()  {
        ListMeetingActivity activity = mActivityRule.getActivity();
        String expected_description;

        if (DateFormat.is24HourFormat(activity.getApplicationContext()))
                expected_description = EXPECTED_DESCRIPTION_24h;
        else
            expected_description = EXPECTED_DESCRIPTION_12H;

        onView(withId(R.id.list))
                .check(matchDescriptionAtPosition(EXPECTED_ITEM_POSITION, expected_description));

    }

    //VERIFIER LA LISTE DES PARTICIPANTS DU PREMIER OBJET

    @Test
    public void giveMeetingsListStartValidMeetingParticipant() {
        onView(withId(R.id.list))
                .check(matchParticipantsPosition(EXPECTED_ITEM_POSITION,EXPECTED_PARTICIPANTS));

    }

    //VERIFIER QUE LE SUPPRESSION DU 2EME OBJET EST EFFECTIVE

    @Test
    public void givenMeetingsListDeleteClickThenRemoveMeeting() {
        onView(ViewMatchers.withId(R.id.list))
                .check(itemCountAssertion(ITEMS_COUNT));
        onView(ViewMatchers.withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(DELETE_ITEM_POSITION, clickToDeleteButton()));

        //TODO
        /*onView(withText(R.string.toast_text_delete_meeting))
                .inRoot(isToast())
                .check(matches(isDisplayed()));*/



        onView(ViewMatchers.withId(R.id.list))
                .check(itemCountAssertion(ITEMS_COUNT - 1));

    }

    //VERIFIER QUE LA SECONDE ACTIVITE EST AFFICHE
    // LORS DU CLICK POUR AJOUTER UN MEETING

    @Test
    public void givenMeetingsListAddButtonClickDisplayAddActivity() {
        Intents.init();
        onView(ViewMatchers.withId(R.id.add_button))
                .perform(click());
        intended(hasComponent(AddMeetingActivity.class.getName()));
        Intents.release();
    }

}
