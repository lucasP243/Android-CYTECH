package com.lucasp243.androidtp1_currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText firstValueInput, secondValueInput;

    private Spinner firstCurrencyInput, secondCurrencyInput;

    public boolean isUpdating = false;

    private ArrayAdapter<Currency> currencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.firstValueInput = findViewById(R.id.firstValueInput);
        this.secondValueInput = findViewById(R.id.secondValueInput);

        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!isUpdating) {
                    isUpdating = true;
                    computeConversion(firstValueInput.getEditableText() == editable);
                    isUpdating = false;
                }
            }
        };

        this.firstValueInput.addTextChangedListener(textWatcher);
        this.secondValueInput.addTextChangedListener(textWatcher);

        this.firstCurrencyInput = findViewById(R.id.firstCurrencyInput);
        this.secondCurrencyInput = findViewById(R.id.secondCurrencyInput);

        this.currencyAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Currency.values()
        );

        this.firstCurrencyInput.setAdapter(currencyAdapter);
        this.secondCurrencyInput.setAdapter(currencyAdapter);

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                isUpdating = true;
                computeConversion(true);
                isUpdating = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                firstValueInput.setText("");
                secondValueInput.setText("");
            }
        };

        this.firstCurrencyInput.setOnItemSelectedListener(listener);
        this.secondCurrencyInput.setOnItemSelectedListener(listener);
    }

    private void computeConversion(boolean isFirstToSecond) {

        String firstValueStr = this.firstValueInput.getText().toString();
        String secondValueStr = this.secondValueInput.getText().toString();

        Double firstValue = firstValueStr.isEmpty() ? 0 : Double.parseDouble(firstValueStr);
        Double secondValue = secondValueStr.isEmpty() ? 0 : Double.parseDouble(secondValueStr);

        Currency firstCurrency = currencyAdapter.getItem(
                firstCurrencyInput.getSelectedItemPosition()
        );

        Currency secondCurrency = currencyAdapter.getItem(
                secondCurrencyInput.getSelectedItemPosition()
        );

        if (isFirstToSecond) {
            Double result = firstValue * firstCurrency.getRateToUSD() / secondCurrency.getRateToUSD();
            secondValueInput.setText(String.format(Locale.US, "%.2f", result));
        } else {
            Double result = secondValue * secondCurrency.getRateToUSD() / firstCurrency.getRateToUSD();
            firstValueInput.setText(String.format(Locale.US, "%.2f", result));
        }

    }
}