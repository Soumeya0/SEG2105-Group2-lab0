package com.example.group2calculator;

import static org.junit.Assert.*;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

/**
 * MainActivityTest
 * ----------------
 * This class contains unit tests for MainActivity.
 *
 * We use ROBOLECTRIC instead of Android instrumentation tests because:
 *   ✔ Robolectric runs JVM tests WITHOUT needing an emulator
 *   ✔ Faster development and automation
 *   ✔ Allows simulating UI events (button clicks, text input)
 *
 * These tests validate:
 *   1. User inputs two numbers
 *   2. User clicks a math operation button
 *   3. Result is displayed correctly on the screen
 */
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    /**
     * Test Case 1 — Addition Logic
     * ----------------------------
     * This test ensures:
     *   - Two numbers are correctly entered into the EditText fields
     *   - The addition button click triggers onClick()
     *   - Result shown in the TextView matches expected output
     */
    @Test
    public void testAddition() {
        // Step 1: Build the activity in a simulated Android environment
        MainActivity activity = Robolectric.buildActivity(MainActivity.class)
                .create().start().resume().get();

        // Step 2: Find UI elements inside the activity
        EditText n1 = activity.findViewById(R.id.number1);
        EditText n2 = activity.findViewById(R.id.number2);
        Button addBtn = activity.findViewById(R.id.btn_add);
        TextView result = activity.findViewById(R.id.answer);

        // Step 3: Simulate user input into EditTexts
        n1.setText("10");
        n2.setText("5");

        // Step 4: Simulate clicking the + button
        addBtn.performClick();

        // Step 5: Verify expected output
        assertEquals("Answer = 15", result.getText().toString());
    }

    /**
     * Test Case 2 — Division Logic
     * ----------------------------
     * This test checks:
     *   - Division computation is handled by Evaluator through MainActivity
     *   - UI correctly updates with the result
     */
    @Test
    public void testDivision() {
        // Step 1: Create activity using Robolectric
        MainActivity activity = Robolectric.buildActivity(MainActivity.class)
                .create().start().resume().get();

        // Step 2: Retrieve UI elements
        EditText n1 = activity.findViewById(R.id.number1);
        EditText n2 = activity.findViewById(R.id.number2);
        Button divBtn = activity.findViewById(R.id.btn_div);
        TextView result = activity.findViewById(R.id.answer);

        // Step 3: Simulate user input
        n1.setText("20");
        n2.setText("4");

        // Step 4: Simulate clicking the / button
        divBtn.performClick();

        // Step 5: Check that result is correct
        assertEquals("Answer = 5", result.getText().toString());
    }
}
