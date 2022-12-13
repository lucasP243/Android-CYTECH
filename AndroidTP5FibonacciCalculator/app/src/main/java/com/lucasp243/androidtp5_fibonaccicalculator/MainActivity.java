package com.lucasp243.androidtp5_fibonaccicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Fields

    private EditText inputFibonacci;

    private TextView operationResult;
    private TextView operationTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputFibonacci = findViewById(R.id.input_fibonacci);
        operationResult = findViewById(R.id.operation_result);
        operationTime = findViewById(R.id.operation_time);

        findViewById(R.id.operation_java).setOnClickListener(
                v -> handleOperation(new JavaFibonacciComputer())
        );

        findViewById(R.id.operation_kotlin).setOnClickListener(
                v -> handleOperation(new KotlinFibonacciComputer())
        );

        findViewById(R.id.operation_clib).setOnClickListener(
                v -> handleOperation(new CLibFibonacciComputer())
        );

        displayResult("- ", "- ");
    }

    private void handleOperation(FibonacciComputer computer) {

        int input;

        FibonacciComputerProxy proxy = new FibonacciComputerProxy(computer);

        try {
            input = Integer.parseInt(inputFibonacci.getText().toString());
        } catch (NumberFormatException e) {
            Toast
                    .makeText(
                            this,
                            R.string.input_hint,
                            Toast.LENGTH_SHORT
                    )
                    .show();
            return;
        }

        long result = proxy.computeNthFibonacci(input);
        long time = proxy.getLastComputationTime();

        displayResult(
                Long.toString(result),
                Long.toString(time)
        );
    }

    private void displayResult(String result, String time) {
        operationResult.setText(
                String.format(
                        Locale.getDefault(),
                        getString(R.string.operation_result),
                        result
                )
        );

        operationTime.setText(
                String.format(
                        Locale.getDefault(),
                        getString(R.string.operation_time),
                        time
                )
        );
    }
}