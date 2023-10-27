package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action when "Add" button is pressed
        Button addButton = findViewById(R.id.b_Add);
        Button subButton = findViewById(R.id.b_Sub);
        Button multButton = findViewById(R.id.b_Mult);
        Button divButton = findViewById(R.id.b_Div);

        addButton.setOnClickListener(v -> {
            Log.d("Calculator app", "User tapped the Add button");

            Double d1 = 0.0;
            Double d2 = 0.0;
            Double answer = 0.0;

            EditText textN1 = findViewById(R.id.editableNum1);
            EditText textN2 = findViewById(R.id.editableNum2);
            // we actually don't need to get ans from screen
            EditText textANS = findViewById(R.id.readOnlyAnswer);

            try {
                d1 = Double.parseDouble(textN1.getText().toString());
                d2 = Double.parseDouble(textN2.getText().toString());
                answer = d1 + d2;
            }
            catch (Exception e) {
            }

            // Set the Answer into the the answer field
            textANS.setText(answer.toString());

            // log what we are doing
            Log.w("Calculator add button", "Add Selected with => " + d1 + " + " + d2 + " = " + answer);
        });

        subButton.setOnClickListener(v -> {
            Log.d("Calculator app", "User tapped the Subtract button");

            Double d1 = 0.0;
            Double d2 = 0.0;
            Double answer = 0.0;

            EditText textN1 = findViewById(R.id.editableNum1);
            EditText textN2 = findViewById(R.id.editableNum2);
            // we actually don't need to get ans from screen
            EditText textANS = findViewById(R.id.readOnlyAnswer);

            try {
                d1 = Double.parseDouble(textN1.getText().toString());
                d2 = Double.parseDouble(textN2.getText().toString());
                answer = d1 - d2;
            }
            catch (Exception e) {
                Log.w("Calculator sub button", "Subtract Selected with no inputs ... " + answer);
            }

            // Set the Answer into the the answer field
            textANS.setText(answer.toString());

            // log what we are doing
            Log.w("Calculator sub button", "Subtract Selected with => " + d1 + " - " + d2 + " = " + answer);
        });

        multButton.setOnClickListener(v -> {
            Log.d("Calculator app", "User tapped the multiplication button");

            Double d1 = 0.0;
            Double d2 = 0.0;
            Double answer = 0.0;

            EditText textN1 = findViewById(R.id.editableNum1);
            EditText textN2 = findViewById(R.id.editableNum2);
            // we actually don't need to get ans from screen
            EditText textANS = findViewById(R.id.readOnlyAnswer);

            try {
                d1 = Double.parseDouble(textN1.getText().toString());
                d2 = Double.parseDouble(textN2.getText().toString());
                answer = d1 * d2;
            }
            catch (Exception e) {
                Log.w("Calculator multiply button", "multiply Selected with no inputs ... " + answer);
            }

            // Set the Answer into the the answer field
            textANS.setText(answer.toString());

            // log what we are doing
            Log.w("Calculator multiply button", "Multiply Selected with => " + d1 + " * " + d2 + " = " + answer);
        });

        divButton.setOnClickListener(v -> {
            Log.d("Calculator app", "User tapped the divide button");

            Double d1 = 0.0;
            Double d2 = 0.0;
            Double answer = 0.0;

            EditText textN1 = findViewById(R.id.editableNum1);
            EditText textN2 = findViewById(R.id.editableNum2);
            // we actually don't need to get ans from screen
            EditText textANS = findViewById(R.id.readOnlyAnswer);

            try {
                d1 = Double.parseDouble(textN1.getText().toString());
                d2 = Double.parseDouble(textN2.getText().toString());
                answer = d1 / d2;
            }
            catch (Exception e) {
                Log.w("Calculator divide button", "Divide Selected with no inputs ... " + answer);
            }

            // Set the Answer into the the answer field
            textANS.setText(answer.toString());

            // log what we are doing
            Log.w("Calculator div button", "divide Selected with => " + d1 + " / " + d2 + " = " + answer);
        });
    }
}
