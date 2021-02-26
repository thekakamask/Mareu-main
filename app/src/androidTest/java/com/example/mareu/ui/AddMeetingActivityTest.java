package com.example.mareu.ui;

import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.R;
import com.example.mareu.ui.meeting_list.ListMeetingActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

import static android.media.CamcorderProfile.get;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.utils.actions.ClickCloseIconAction.clickOnCloseIconChip;
import static com.example.mareu.utils.assertions.ChipNoValueAssertion.NoChipGroup;
import static com.example.mareu.utils.assertions.ChipValueAssertion.matchesChipTextAtPosition;
import static com.example.mareu.utils.assertions.TextInputLayoutErrorValueAssertion.matchesErrorText;
import static com.example.mareu.utils.assertions.TextInputLayoutNoErrorValueAssertion.matchesNoErrorText;
import static com.example.mareu.utils.dummies.DummyCalendarGen.TODAY_INVALID_START_TIME;
import static com.example.mareu.utils.dummies.DummyCalendarGen.TOMORROW_INVALID_END_TIME;
import static com.example.mareu.utils.dummies.DummyCalendarGen.TOMORROW_VALID_END_TIME;
import static com.example.mareu.utils.dummies.DummyCalendarGen.TOMORROW_VALID_START_TIME;
import static com.example.mareu.utils.dummies.DummyCalendarGen.YESTERDAY;
import static com.example.mareu.utils.dummies.DummyEmailGen.INVALID_EMAIL;
import static com.example.mareu.utils.dummies.DummyEmailGen.VALID_EMAIL_1;
import static com.example.mareu.utils.dummies.DummyEmailGen.VALID_EMAIL_2;
import static com.example.mareu.utils.dummies.DummyEmailGen.VALID_EMAIL_3;
import static com.example.mareu.utils.dummies.DummyMeetingGen.ROOM_NAME;
import static com.example.mareu.utils.dummies.DummyMeetingGen.TOPIC;
import static com.example.mareu.utils.matchers.ToastMatcher.isToast;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertTrue;

@RunWith(Enclosed.class)
public class AddMeetingActivityTest {

    public static class SingleTestsForElement {


        //JE DEFINIT LES REGLES POUR INITIALISER LACTIVITE ADDMEETINGACTIVITY

        @Rule
        public ActivityTestRule<ListMeetingActivity> mActivityRuleInit =
                new ActivityTestRule<>(ListMeetingActivity.class);

        @Rule
        public ActivityTestRule<AddMeetingActivity> mActivityRule=
                new ActivityTestRule<>(AddMeetingActivity.class);

        @Before
        public void setUp() {
            AddMeetingActivity activity = mActivityRule.getActivity();
            assertThat(activity, notNullValue());
        }


        @Test
        public void giveRoom_thenGetRoomWithoutErrorWhenAdd() {
            onView(withId(R.id.room_name)).perform(click());
            onView(withText(ROOM_NAME))
                    .inRoot(RootMatchers.isPlatformPopup())
                    .perform(click());

            onView(withId(R.id.button_add_meeting)).perform(click());
            onView(withId(R.id.room_name)).check(matches(withText(ROOM_NAME)));
            onView(withId(R.id.room_name_layout)).check(matchesNoErrorText());

        }


        @Test
        public void giveTopic_thenGetTopicWithoutErrorWhenAdd() {
            onView(withId(R.id.topic)).perform(click());
            onView(withId(R.id.topic)).perform(typeText(TOPIC));

            onView(withId(R.id.button_add_meeting)).perform(click());

            onView(withId(R.id.topic)).check(matches(withText(TOPIC)));
            onView(withId(R.id.topic)).check(matches(withHint(R.string.meeting_topic)));
            onView(withId(R.id.topic_layout)).check(matchesNoErrorText());

        }


        @Test
        public void giveValidEmail_thenRecupEmailWithoutErrorWhenEnter() {
            onView(withId(R.id.emails)).perform(closeSoftKeyboard());
            onView(withId(R.id.emails)).perform(typeText(VALID_EMAIL_1));
            onView(withId(R.id.emails)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

            onView(withId(R.id.emails_group)).check(matchesChipTextAtPosition(1, VALID_EMAIL_1));
            onView(withId(R.id.emails)).check(matches(withHint(R.string.list_of_participants)));
            onView(withId(R.id.participants)).check(matchesNoErrorText());


        }


        @Test
        public void giveThreeValidEmail_thenRecupEmailWithoutErrorWhenEnter() {
            onView(withId(R.id.emails)).perform(closeSoftKeyboard());
            onView(withId(R.id.emails)).perform(typeText(VALID_EMAIL_1));
            onView(withId(R.id.emails)).perform(pressKey(KeyEvent.KEYCODE_ENTER));
            onView(withId(R.id.emails_group)).check(matchesChipTextAtPosition(1, VALID_EMAIL_1));

            onView(withId(R.id.emails)).perform(typeText(VALID_EMAIL_2));
            onView(withId(R.id.emails)).perform(pressKey(KeyEvent.KEYCODE_ENTER));
            onView(withId(R.id.emails_group)).check(matchesChipTextAtPosition(2, VALID_EMAIL_2));

            onView(withId(R.id.emails)).perform(typeText(VALID_EMAIL_3));
            onView(withId(R.id.emails)).perform(pressKey(KeyEvent.KEYCODE_ENTER));
            onView(withId(R.id.emails_group)).check(matchesChipTextAtPosition(3, VALID_EMAIL_3));


            onView(withId(R.id.emails)).check(matches(withHint(R.string.list_of_participants)));
            onView(withId(R.id.participants)).check(matchesNoErrorText());
        }


        @Test
        public void giveInvalidEmail_thenGetErrorWhenEnter(){
            AddMeetingActivity activity = mActivityRule.getActivity();
            onView(withId(R.id.emails)).perform(typeText(INVALID_EMAIL));
            onView(withId(R.id.emails)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

            onView(withId(R.id.participants)).check(matchesErrorText(activity.getString(R.string.error_invalid_email)));
            onView(withId(R.id.emails)).check(matches(withHint(R.string.list_of_participants)));



        }

        @Test
        public void giveOneValidOneInvalid_thenGetErrorWhenEnter() {
            AddMeetingActivity activity = mActivityRule.getActivity();

            onView(withId(R.id.emails)).perform(closeSoftKeyboard());

            onView(withId(R.id.emails)).perform(typeText(VALID_EMAIL_1));
            onView(withId(R.id.emails)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

            onView(withId(R.id.emails_group)).check(matchesChipTextAtPosition(1, VALID_EMAIL_1));

            onView(withId(R.id.emails)).perform(typeText(INVALID_EMAIL));
            onView(withId(R.id.emails)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

            onView(withId(R.id.participants)).check(matchesErrorText(activity.getString(R.string.error_invalid_email)));
            onView(withId(R.id.emails)).check(matches(withHint(R.string.list_of_participants)));


        }


        @Test
        public void giveEmailChip_thenClicktoChip_thenDeleteChip() {

            onView(withId(R.id.emails)).perform(closeSoftKeyboard());

            onView(withId(R.id.emails)).perform(typeText(VALID_EMAIL_1));
            onView(withId(R.id.emails)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

            onView(withId(R.id.emails_group)).check(matchesChipTextAtPosition(1, VALID_EMAIL_1));
            onView(withId(R.id.emails)).check(matches(withHint(R.string.list_of_participants)));
            onView(withId(R.id.participants)).check(matchesNoErrorText());

            onView(withId(R.id.emails_group)).perform(clickOnCloseIconChip(1));
            onView(withId(R.id.emails_group)).check(NoChipGroup());

        }


        @Test
        public void giveValidDate_thenClickDatePicker_ThenGetValidStringDate() {
            AddMeetingActivity activity = mActivityRule.getActivity();

            onView(withId(R.id.date)).perform(click());
            onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                    .perform(PickerActions.setDate(
                            TOMORROW_VALID_START_TIME.get(Calendar.YEAR),
                            TOMORROW_VALID_START_TIME.get(Calendar.MONTH) + 1,
                            TOMORROW_VALID_START_TIME.get(Calendar.DAY_OF_MONTH)));
            onView(withText(android.R.string.ok)).perform(click());

            onView(withId(R.id.button_add_meeting)).perform(click());
            onView(withId(R.id.date))
                    .check(matches(withText(DateFormat.getDateFormat(activity.getApplicationContext())
                            .format(TOMORROW_VALID_START_TIME.getTime()))));
            onView(withId(R.id.date_layout)).check(matchesNoErrorText());


        }


        @Test
        public void giveInvalidDate_thenClickDatePicker_ThenGetError() {
            AddMeetingActivity activity = mActivityRule.getActivity();

            onView(withId(R.id.date)).perform(click());
            onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                    .perform(PickerActions.setDate(
                            YESTERDAY.get(Calendar.YEAR),
                            YESTERDAY.get(Calendar.MONTH) + 1,
                            YESTERDAY.get(Calendar.DAY_OF_MONTH)));
            onView(withText(android.R.string.ok)).perform(click());

            onView(withId(R.id.button_add_meeting)).perform(click());

            onView(withId(R.id.date_layout))
                    .check(matchesErrorText(activity.getString(R.string.error_date_passed)));
            onView(withId(R.id.date)).check(matches(withHint(R.string.date)));

        }

        @Test
        public void giveValidTime_thenClickTimePicker_ThenGetValidStringDate() {
            AddMeetingActivity activity = mActivityRule.getActivity();

            onView(withId(R.id.from)).perform(click());
            onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                    .perform(PickerActions.setTime(
                            TOMORROW_VALID_START_TIME.get(Calendar.HOUR_OF_DAY),
                            TOMORROW_VALID_START_TIME.get(Calendar.MINUTE)));
            onView(withText(android.R.string.ok)).perform(click());

            onView(withId(R.id.to)).perform(closeSoftKeyboard());
            onView(withId(R.id.to)).perform(click());
            onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                    .perform(PickerActions.setTime(
                            TOMORROW_VALID_END_TIME.get(Calendar.HOUR_OF_DAY),
                            TOMORROW_VALID_END_TIME.get(Calendar.MINUTE)));
            onView(withText(android.R.string.ok)).perform(click());

            onView(withId(R.id.button_add_meeting)).perform(click());

            onView(withId(R.id.from))
                    .check(matches(withText(DateFormat
                            .getTimeFormat(activity.getApplicationContext())
                            .format(TOMORROW_VALID_START_TIME.getTime()))));
            onView(withId(R.id.from_layout)).check(matchesNoErrorText());

            onView(withId(R.id.to))
                    .check(matches(withText(DateFormat
                            .getTimeFormat(activity.getApplicationContext())
                            .format(TOMORROW_VALID_END_TIME.getTime()))));
            onView(withId(R.id.to_layout)).check(matchesNoErrorText());

        }


        @Test
        public void giveInvalidStartTime_thenClickTimePicker_ThenGetError() {
            AddMeetingActivity activity = mActivityRule.getActivity();

            onView(withId(R.id.date)).perform(click());
            onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                    .perform(PickerActions.setDate(
                            TODAY_INVALID_START_TIME.get(Calendar.YEAR),
                            TODAY_INVALID_START_TIME.get(Calendar.MONTH) + 1,
                            TODAY_INVALID_START_TIME.get(Calendar.DAY_OF_MONTH)));
            //onView(withId(R.id.date)).perform(closeSoftKeyboard());
            onView(withText(android.R.string.ok)).perform(click());

            onView(withId(R.id.from)).perform(click());
            onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                    .perform(PickerActions.setTime(
                            TODAY_INVALID_START_TIME.get(Calendar.HOUR_OF_DAY),
                            TODAY_INVALID_START_TIME.get(Calendar.MINUTE)));
            onView(withText(android.R.string.ok)).perform(click());
            onView(withId(R.id.button_add_meeting)).perform(click());

            onView(withId(R.id.from_layout))
                    .check(matchesErrorText(activity.getString(R.string.error_time_passed)));
            onView(withId(R.id.from)).check(matches(withHint(R.string.from)));


        }

        @Test
        public void giveInvalidEndTime_thenClickTimePicker_ThenGetError() {
            AddMeetingActivity activity = mActivityRule.getActivity();

            onView(withId(R.id.from)).perform(click());
            onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                    .perform(PickerActions.setTime(
                            TOMORROW_VALID_START_TIME.get(Calendar.HOUR_OF_DAY),
                            TOMORROW_VALID_START_TIME.get(Calendar.MINUTE)));
            onView(withText(android.R.string.ok)).perform(click());


            onView(withId(R.id.to)).perform(closeSoftKeyboard());
            onView(withId(R.id.to)).perform(click());
            onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                    .perform(PickerActions.setTime(
                            TOMORROW_INVALID_END_TIME.get(Calendar.HOUR_OF_DAY),
                            TOMORROW_INVALID_END_TIME.get(Calendar.MINUTE)));
            onView(withText(android.R.string.ok)).perform(click());

            onView(withId(R.id.button_add_meeting)).perform(click());

            onView(withId(R.id.to_layout))
                    .check(matchesErrorText(activity.getString(R.string.error_time_comparaison)));
            onView(withId(R.id.to)).check(matches(withHint(R.string.to)));


        }


        @Test
        public void CancelAddMeeting_byPerformReturn() {
            onView(withContentDescription(R.string.abc_action_bar_up_description))
                    .perform(click());

            assertTrue(mActivityRule.getActivity().isFinishing());

            //TODO
            /*onView(withText(R.string.abort_add_meeting))
                     .inRoot(isToast())
                    .check(matches(isDisplayed()));*/

        }

    }



    @RunWith(Parameterized.class)
    public static class ComponentTestsEmptyFields {

        @Parameters
        public static Collection<Object> data() {
            return Arrays.asList(new Object[] {
                R.id.room_name_layout,
                R.id.topic_layout,
                R.id.date_layout,
                R.id.from_layout,
                R.id.to_layout,
                R.id.participants
            });

        }

        @Parameterized.Parameter
        public int viewId;


        @Rule
        public ActivityTestRule<ListMeetingActivity> mActivityRuleInit =
                new ActivityTestRule<>(ListMeetingActivity.class);

        @Rule
        public ActivityTestRule<AddMeetingActivity> mActivityRule =
                new ActivityTestRule<>(AddMeetingActivity.class);

        @Before
        public void setUp() {
            AddMeetingActivity activity = mActivityRule.getActivity();
            assertThat(activity, notNullValue());
        }

        @Test
        public void Empty_GetErrorMessage_whenClickAdd() {
            AddMeetingActivity activity = mActivityRule.getActivity();

            onView(withId(R.id.button_add_meeting)).perform(click());
            onView(withId(viewId))
                    .check(matchesErrorText(activity.getString(R.string.error_empty_field)));

            onView(withText(R.string.error_add_new_meeting))
                    .inRoot(isToast())
                    .check(matches(isDisplayed()));

        }

        @RunWith(Parameterized.class)
        public static class ComponentEmailsField {

            @Parameters
            public static Collection<Object> data() {
                return Arrays.asList(new Object [] { "", ","});

            }

            @Parameterized.Parameter
            public String delimiterField;

            @Rule
            public ActivityTestRule<ListMeetingActivity> mActivityRuleInit =
                    new ActivityTestRule<>(ListMeetingActivity.class);

            @Rule
            public ActivityTestRule<AddMeetingActivity> mActivityRule =
                    new ActivityTestRule<>(AddMeetingActivity.class);

            @Before
            public void setUp() {
                AddMeetingActivity activity = mActivityRule.getActivity();
                assertThat(activity, notNullValue());
            }

            @Test
            public void giveValidEmail_withTextDelimiter_thenGetEmail() {
                onView(withId(R.id.emails))
                        .perform(typeText(VALID_EMAIL_1 + delimiterField ));

                onView(withId(R.id.emails_group))
                        .check(matchesChipTextAtPosition(1 ,VALID_EMAIL_1));
                onView(withId(R.id.emails))
                        .check(matches(withHint(R.string.list_of_participants)));
                onView(withId(R.id.participants))
                        .check(matchesNoErrorText());


            }


            @Test
            public void giveThreeValidEmail_withTextDelimiter_thenGetEmail() {
                onView(withId(R.id.emails))
                        .perform(typeText(VALID_EMAIL_1+ delimiterField));
                onView(withId(R.id.emails_group))
                        .check(matchesChipTextAtPosition(1, VALID_EMAIL_1));

                onView(withId(R.id.emails))
                        .perform(typeText(VALID_EMAIL_2+ delimiterField));
                onView(withId(R.id.emails_group))
                        .check(matchesChipTextAtPosition(2, VALID_EMAIL_2));

                onView(withId(R.id.emails))
                        .perform(typeText(VALID_EMAIL_3+delimiterField));
                onView(withId(R.id.emails_group))
                        .check(matchesChipTextAtPosition(3, VALID_EMAIL_3));

                onView(withId(R.id.emails))
                        .check(matches(withHint(R.string.list_of_participants)));
                onView(withId(R.id.participants))
                        .check(matchesNoErrorText());

            }


            @Test
            public void giveInvalidEmail_withTextDelimiter_thenGetError(){
                AddMeetingActivity activity = mActivityRule.getActivity();

                onView(withId(R.id.emails))
                        .perform(typeText(INVALID_EMAIL + delimiterField));

                onView(withId(R.id.participants))
                        .check(matchesErrorText(activity.getString(R.string.error_invalid_email)));
                onView(withId(R.id.emails))
                        .check(matches(withHint(R.string.list_of_participants)));

            }


            @Test
            public void giveValidAndInvalidEmail_withTextDelimiter_thenGetError(){

                AddMeetingActivity activity = mActivityRule.getActivity();

                onView(withId(R.id.emails))
                        .perform(typeText(VALID_EMAIL_1 + delimiterField));

                onView(withId(R.id.emails_group))
                        .check(matchesChipTextAtPosition(1, VALID_EMAIL_1));

                onView(withId(R.id.emails))
                        .perform(typeText(INVALID_EMAIL + delimiterField));

                onView(withId(R.id.participants))
                        .check(matchesErrorText(activity.getString(R.string.error_invalid_email)));
                onView(withId(R.id.emails))
                        .check(matches(withHint(R.string.list_of_participants)));


            }


        }

    }

}
