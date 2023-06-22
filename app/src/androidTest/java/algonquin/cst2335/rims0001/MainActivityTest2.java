package algonquin.cst2335.rims0001;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
/**
 * Instrumented test class for MainActivity.
 */
public class MainActivityTest2 {

    /**
     * Rule to launch the MainActivity for testing.
     */
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Test case for validating the behavior of the MainActivity.
     * It performs actions on the views and checks the expected results.
     */
    @Test
    public void mainActivityTest2() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));

        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialButton = onView( withId(R.id.button));

        materialButton.perform(click());

        ViewInteraction textView = onView( withId(R.id.textView));
        textView.check(matches(withText("How dare you !?!? You shall not pass!!!")));

        ViewInteraction textView2 = onView( withId(R.id.textView));
        textView2.check(matches(withText("How dare you !?!? You shall not pass!!!")));
    }

    /**
     *Returns a Matcher that matches a child View at the specified position within a parent View
     * @param parentMatcher the Matcher for the parent View
     * @param position the position of the child View within the parent View
     * @return a Matcher<View> object representing the child at the specified position in the parent
     */
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    /**
     * Testing find missing upper case
     */
    @Test
    public void testFindMissingUpperCase(){
        // find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        // type in password123#$*
        appCompatEditText.perform(replaceText("123#$*"), closeSoftKeyboard());

        //find the button
        ViewInteraction materialButton = onView( withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView));
        //check the text
        textView.check(matches(withText("How dare you !?!? You shall not pass!!!")));
    }

    /**
     * Test find missing lower case
     */
    @Test
    public void testFindMissingLowerCase(){
        // find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        // type in password@T4518
        appCompatEditText.perform(replaceText("@T4518"), closeSoftKeyboard());

        //find the button
        ViewInteraction materialButton = onView( withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView));
        //check the text
        textView.check(matches(withText("How dare you !?!? You shall not pass!!!")));
    }

    /**
     * Test find missing digit
     */
    @Test
    public void testFindMissingDigit(){
        // find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        // type in password ImHungry
        appCompatEditText.perform(replaceText("ImHungry"), closeSoftKeyboard());

        //find the button
        ViewInteraction materialButton = onView( withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView));
        //check the text
        textView.check(matches(withText("How dare you !?!? You shall not pass!!!")));
    }

    /**
     * Test find missing special case
     */
    @Test
    public void testFindMissingSpacialCase(){
        // find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        // type in passwordHeju5TaFr13nD
        appCompatEditText.perform(replaceText("Heju5TaFr13nD"), closeSoftKeyboard());

        //find the button
        ViewInteraction materialButton = onView( withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView));
        //check the text
        textView.check(matches(withText("How dare you !?!? You shall not pass!!!")));
    }

    /**
     * Test the complexity of a password
     */
    @Test
    public void testPassword(){
        // find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        // type in password12kJ$5fE
        appCompatEditText.perform(replaceText("12kJ$5fE"), closeSoftKeyboard());

        //find the button
        ViewInteraction materialButton = onView( withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView));
        //check the text
        textView.check(matches(withText("Your password is complex enough.")));
    }

}
