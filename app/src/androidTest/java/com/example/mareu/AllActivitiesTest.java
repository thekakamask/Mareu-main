package com.example.mareu;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import com.example.mareu.di.DI;
import com.example.mareu.service.MeetingApiServiceException;
import com.example.mareu.ui.AddMeetingActivity;
import com.example.mareu.ui.meeting_list.ListMeetingActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Collection;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static androidx.test.runner.lifecycle.Stage.RESUMED;
import static com.example.mareu.utils.assertions.RecyclerViewAssertion.itemCountAssertion;
import static com.example.mareu.utils.assertions.TextInputLayoutErrorValueAssertion.matchesErrorText;
import static com.example.mareu.utils.dummies.DummyCalendarGen.TOMORROW;
import static com.example.mareu.utils.dummies.DummyCalendarGen.TOMORROW_VALID_START_TIME;
import static com.example.mareu.utils.dummies.DummyCalendarGen.generateDateTimeFromTomo;
import static com.example.mareu.utils.dummies.DummyCalendarGen.generateTomorrowDateTime;
import static com.example.mareu.utils.dummies.DummyEmailGen.VALID_EMAIL_1;
import static com.example.mareu.utils.dummies.DummyMeetingGen.ITEMS_COUNT;
import static com.example.mareu.utils.dummies.DummyMeetingGen.MEETINGS_ROOM_NAME_1_COUNT;
import static com.example.mareu.utils.dummies.DummyMeetingGen.ROOM_NAME;
import static com.example.mareu.utils.dummies.DummyMeetingGen.ROOM_NAME_1;
import static com.example.mareu.utils.dummies.DummyMeetingGen.TOMO_MEETING_COUNT;
import static com.example.mareu.utils.dummies.DummyMeetingGen.TOM_MEETING_ROOM_NAME_1;
import static com.example.mareu.utils.dummies.DummyMeetingGen.TOPIC;
import static com.example.mareu.utils.dummies.DummyMeetingGen.generateMeetings;
import static com.example.mareu.utils.dummies.DummyMeetingGen.generateRooms;
import static com.example.mareu.utils.matchers.ToastMatcher.isToast;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AllActivitiesTest {

    // DEFINITION DES REGLES POUR INITIALISER LA LISTMEETINGACTIVITY
    // AVEC LES DONNEES DU TEST

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


    // VERIFICATION AVEC UNE SALLE LIBRE

    @Test
    public void reserveRoomValid_thenValidated() {

        // initialisation

        Calendar from = generateDateTimeFromTomo(2, 8, 0);
        Calendar to = generateDateTimeFromTomo(2, 9, 0);

        // remplissage des champs

        onView(withId(R.id.add_button)).perform(click());

        // salle
        onView(withId(R.id.room_name)).perform(click());
        onView(withText(ROOM_NAME))
                .inRoot(isPlatformPopup())
                .perform(click());

        // sujet

        onView(withId(R.id.topic)).perform(typeText(TOPIC));

        // date

        onView(withId(R.id.date)).perform(closeSoftKeyboard());
        onView(withId(R.id.date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(
                        from.get(Calendar.YEAR),
                        from.get(Calendar.MONTH) + 1,
                        from.get(Calendar.DAY_OF_MONTH)));

        onView(withText(android.R.string.ok)).perform(click());

        // heure du debut

        onView(withId(R.id.from)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(setTime(
                        from.get(Calendar.HOUR_OF_DAY),
                        from.get(Calendar.MINUTE)));

        onView(withText(android.R.string.ok)).perform(click());

        // heure de fin

        onView(withId(R.id.to)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(setTime(
                        to.get(Calendar.HOUR_OF_DAY),
                        to.get(Calendar.MINUTE)));

        onView(withText(android.R.string.ok)).perform(click());

        // email

        onView(withId(R.id.emails)).perform(typeText(VALID_EMAIL_1 + ""));

        onView(withId(R.id.emails)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

        onView(withId(R.id.button_add_meeting)).perform(click());


        // verification de l'ajout du meeting

        onView(withText(R.string.add_new_meeting))
                .inRoot(isToast())
                .check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.list))
                .check(itemCountAssertion(ITEMS_COUNT + 1));

    }

        // VERIFICATION AVEC UNE SALLE OCCUPE

    @Test
    public void reserveRoomInvalid_thenRefused() {

        // initialisation

        ListMeetingActivity activity = mActivityRule.getActivity();
        Calendar from = generateTomorrowDateTime(8, 0);
        Calendar to = generateTomorrowDateTime(9, 0);
        reserveRoomValid_thenValidated();

        // click sur add meeting

        onView(ViewMatchers.withId(R.id.add_button)).perform(click());

        // remplissage des espaces

        // salle
        onView(withId(R.id.room_name)).perform(click());
        onView(withText(ROOM_NAME))
                .inRoot(isPlatformPopup())
                .perform(click());

        // sujet
        onView(withId(R.id.topic)).perform(typeText(TOPIC));

        // date
        onView(withId(R.id.date)).perform(closeSoftKeyboard());
        onView(withId(R.id.date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(
                        from.get(Calendar.YEAR),
                        from.get(Calendar.MONTH) + 1,
                        from.get(Calendar.DAY_OF_MONTH) +2));
        onView(withText(android.R.string.ok)).perform(click());



        // debut
        onView(withId(R.id.from)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(
                        from.get(Calendar.HOUR_OF_DAY),
                        from.get(Calendar.MINUTE)));
        onView(withText(android.R.string.ok)).perform(click());

        // fin
        onView(withId(R.id.to)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(
                        to.get(Calendar.HOUR_OF_DAY),
                        to.get(Calendar.MINUTE)));
        onView(withText(android.R.string.ok)).perform(click());

        // email
        onView(withId(R.id.emails)).perform(typeText(VALID_EMAIL_1 + ""));
        onView(withId(R.id.emails)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

        // validation
        onView(withId(R.id.button_add_meeting)).perform(click());

        // verification
        onView(withText(R.string.error_meeting_already_create))
                .inRoot(isToast())
                .check(matches(isDisplayed()));
        onView(withId(R.id.room_name_layout))
                .check(matchesErrorText(activity.getString(R.string.error_meeting_already_create)));

    }

    // ANNULER UN MEETING EN COURS DE RESERVATION

    @Test
    public void clickToCancel_thenItsCancelled() {

        onView(ViewMatchers.withId(R.id.add_button))
            .perform(click());

        Activity currentAddMeetingActivity = getActivityInstance();

        // annuler

        onView(withContentDescription(R.string.abc_action_bar_up_description))
                .perform(click());

        // verification

        assertTrue(currentAddMeetingActivity.isFinishing());

        //TODO

       /* onView(withText(R.string.abort_add_meeting))
                .inRoot(isToast())
                .check(matches(isDisplayed()));*/

    }

    private Activity currentActivity = null;

    private Activity getActivityInstance() {
        getInstrumentation().runOnMainSync(() -> {
            Collection<Activity> resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
            for (Activity activity : resumedActivities) {
                currentActivity = activity;
                break;
            }

        });
        return currentActivity;
    }

    // VERIFICATION DU FILTRE PAR DATE

    @Test
    public void check_date_filter() {
        onView(ViewMatchers.withId(R.id.list))
                .check(itemCountAssertion(ITEMS_COUNT));

        onView(ViewMatchers.withId(R.id.filter))
                .perform(click());

        onView(withId(R.id.date_by_filter))
                .perform(click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(
                        TOMORROW.get(Calendar.YEAR),
                        TOMORROW.get(Calendar.MONTH) + 1,
                        TOMORROW.get(Calendar.DAY_OF_MONTH)));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(android.R.id.button1)).perform(click());

        onView(ViewMatchers.withId(R.id.list))
                .check(itemCountAssertion(TOMO_MEETING_COUNT));
    }

    // VERIFICATION DU FILTRE PAR SALLE

    @Test
    public void check_room_filter() {
        onView(ViewMatchers.withId(R.id.list))
                .check(itemCountAssertion(ITEMS_COUNT));

        onView(ViewMatchers.withId(R.id.filter))
                .perform(click());

        onView(withId(R.id.room_by_filter))
                .perform(click());

        onData(allOf(is(instanceOf(String.class)), is(ROOM_NAME_1)))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(withId(android.R.id.button1))
                .perform(click());

        onView(ViewMatchers.withId(R.id.list))
                .check(itemCountAssertion(MEETINGS_ROOM_NAME_1_COUNT));
    }


    //VERIFICATION DES DEUX EN MEME TEMPS

    @Test
    public void check_date_and_room_filter() {
        onView(ViewMatchers.withId(R.id.list))
                .check(itemCountAssertion(ITEMS_COUNT));

        // Room
        onView(ViewMatchers.withId(R.id.filter))
                .perform(click());

        onView(withId(R.id.room_by_filter))
                .perform(click());

        onData(allOf(is(instanceOf(String.class)), is(ROOM_NAME_1)))
                .inRoot(isPlatformPopup())
                .perform(click());

        // Date
        onView(withId(R.id.date_by_filter))
                .perform(click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(
                        TOMORROW.get(Calendar.YEAR),
                        TOMORROW.get(Calendar.MONTH) + 1,
                        TOMORROW.get(Calendar.DAY_OF_MONTH)));
        onView(withId(android.R.id.button1)).perform(click());

        // Valid
        onView(withId(android.R.id.button1)).perform(click());

        onView(ViewMatchers.withId(R.id.list))
                .check(itemCountAssertion(TOM_MEETING_ROOM_NAME_1));
    }

        



}