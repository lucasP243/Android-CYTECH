package com.lucasp243.androidtp2_favoritepicker;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;

public class ColorPickerActivity extends AppCompatActivity {

    LinearLayout colorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_picker);

        colorList = findViewById(R.id.colorList);

        try {
            generateColorList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateColorList() throws ClassNotFoundException, IllegalAccessException {

        Field[] fields = Class.forName(getPackageName()+".R$color").getDeclaredFields();
        for(Field field : fields) {
            String colorName = field.getName();
            int colorId = field.getInt(null);

            TextView text = new TextView(this);
            text.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            180
                    )
            );
            text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            text.setGravity(Gravity.CENTER_VERTICAL);
            text.setText(colorName);
            text.setBackgroundColor(getResources().getColor(colorId));

            text.setOnClickListener(view -> {
                Intent result = new Intent();
                result.putExtra("color", colorId);
                setResult(RESULT_OK, result);
                finish();
            });

            colorList.addView(text);
        }

    }
}