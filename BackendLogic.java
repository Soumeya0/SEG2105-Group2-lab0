package com.example.group2calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonAdd, buttonSub, buttonMul, buttonDiv; // objects created for buttons
    EditText editTextN1, editTextN2; //objects created for selecting two numbers to perform operation on
    TextView textVeiw; // created an object to store the result of the operation
    int num1, num2;






    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAdd = findViewById(R.id.btn_add); //parsed the add button form activity_main file to buttonAdd
        buttonSub = findViewById(R.id.btn_sub); //parsed to substraction
        buttonMul = findViewById(R.id.btn_mul); //parsed to multiplication
        buttonDiv = findViewById(R.id.btn_div); //parsed to division
        editTextN1 = findViewById(R.id.number1); //parsed the number1 from activity_main to java object editTestN1
        editTextN2 = findViewById(R.id.number2); //parsed the number2
        textVeiw = findViewById(R.id.answer); // parsed the small screen that shows the answer created as answer in the activity_main

        buttonAdd.setOnClickListener(this); //since we implemented the View.OnclickListener we have to call each button for listener with this
        buttonSub.setOnClickListener(this); //same for substraction
        buttonMul.setOnClickListener(this); //same for multiplication
        buttonDiv.setOnClickListener(this); //same for division

    }

    public int getIntFromEditText(EditText editText) {
        //till this method we just created the buttons as objects then we parsed it with the xml elements that we created for the button in the activity_main file
        //then we created click listeners to prepare the button for clicking
        //now in onClick we implement the actual logic behind the button
        //this method will be taking editTextN1 and editTextN2 as object and check the edge case and the else part on it
        //then in the onClick method we can call this method on editTextN1 and N2 for num1 and num2, it will take the values of editTextN1 and N2 and store it in num1 and num2

        if (editText.getText().toString().equals("")){ //this is the first edge case, here we check if the editTextN1 which store the first value entered by user is empty (means suppose user forget to enter a number)
            Toast.makeText(this, "Enter number", Toast.LENGTH_SHORT).show(); //so it will check it, if it is empty it toast Enter Number message for user
            return 0;
        } else
            return Integer.parseInt(editText.getText().toString()); //other then the edge case if user enter a number it will take it and assign it to num1

    }

    @Override
    public void onClick(View view) {
        num1 = getIntFromEditText(editTextN1); //now with calling the upper method we assigned editTextN1 to num1
        num2 = getIntFromEditText(editTextN2); //now we assigned editTextN2 to num2

        //since now after checking the edge case with if state in the getIntFromEditText method and also assigned the editTexts to num1 and num2
        //now we will do the logic for sum, sub, mul, and div

        // we can implement it with switch attributes of java

        int viewId = view.getId();

        if (viewId == R.id.btn_add) {
            textVeiw.setText("Answer = " + (num1 + num2));
        } else if (viewId == R.id.btn_sub) {
            textVeiw.setText("Answer = " + (num1 - num2));
        } else if (viewId == R.id.btn_mul) {
            textVeiw.setText("Answer = " + (num1 * num2));
        } else if (viewId == R.id.btn_div) {
            textVeiw.setText("Answer = " + ((float) num1 / (float) num2));
        }








    }
}