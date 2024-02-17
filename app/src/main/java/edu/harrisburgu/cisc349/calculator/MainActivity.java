package edu.harrisburgu.cisc349.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTextView;
    StringBuilder currentNumber;
    String leftValue;
    String rightValue;
    String currentOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize TextView
        resultTextView = findViewById(R.id.tvResult);
        resultTextView.setText("0");

        // Initialize StringBuilder for current number
        currentNumber = new StringBuilder();

        // Set OnClickListener for buttons
        findViewById(R.id.btnClear).setOnClickListener(this);
        findViewById(R.id.btnBackspace).setOnClickListener(this);
        findViewById(R.id.btnDivide).setOnClickListener(this);
        findViewById(R.id.btnMultiply).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnSubtract).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btnEquals).setOnClickListener(this);
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btnDecimal).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Handle button clicks
        Button button = (Button) v;
        String buttonText = button.getText().toString();

        switch (buttonText) {
            case "C":
                // Clear the calculator
                clearCalculator();
                break;
            case "⌫":
                // Handle backspace
                handleBackspace();
                break;
            case "+":
            case "-":
            case "×":
            case "÷":
                // Handle operators
                if (!currentNumber.toString().isEmpty()) {
                    handleOperator(buttonText);
                }
                break;
            case "=":
                // Calculate result
                calculateResult();
                break;
            default:
                // Append digit or decimal point
                appendToCurrentNumber(buttonText);
                break;
        }
    }
    private void clearCalculator() {
        currentNumber.setLength(0);
        leftValue = "";
        rightValue = "";
        currentOperator = "";
        resultTextView.setText("0");
    }

    private void handleBackspace() {
        if (currentNumber.length() > 0) {
            currentNumber.deleteCharAt(currentNumber.length() - 1);
            if (currentNumber.length() == 0) {
                resultTextView.setText("0");
            } else {
                resultTextView.setText(currentNumber.toString());
            }
        }
    }

    private void handleOperator(String operator) {
        if (currentNumber.length() > 0) {
            leftValue = currentNumber.toString();
            currentOperator = operator;
            currentNumber.setLength(0); // Reset currentNumber
        }
    }

    private void appendToCurrentNumber(String digit) {
        // Append digit or decimal point
        if (!digit.equals(".") || !currentNumber.toString().contains(".")) {
            currentNumber.append(digit);
            resultTextView.setText(currentNumber.toString());
        }
    }

    @SuppressLint("SetTextI18n")
    private void calculateResult() {
        if (!leftValue.isEmpty() && !currentNumber.toString().isEmpty() && !currentOperator.isEmpty()) {
            rightValue = currentNumber.toString();
            double left = Double.parseDouble(leftValue);
            double right = Double.parseDouble(rightValue);
            double result = 0;

            switch (currentOperator) {
                case "+":
                    result = left + right;
                    break;
                case "-":
                    result = left - right;
                    break;
                case "×":
                    result = left * right;
                    break;
                case "÷":
                    if (right != 0) {
                        result = left / right;
                    } else {
                        // Handle division by zero error
                        resultTextView.setText("Error");
                        return;
                    }
                    break;
            }

            // Display the result
            resultTextView.setText(String.valueOf(result));

            // Update leftValue for continued calculation
            leftValue = String.valueOf(result);
        }
    }
}
