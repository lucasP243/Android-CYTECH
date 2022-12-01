package com.lucasp243.androidtp3mathinspector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.findViewById(R.id.btn_about).setOnClickListener(v ->
            new MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.about)
                    .setMessage(R.string.about_text)
                    .setNegativeButton(
                            R.string.about_close,
                            (dialog, which) -> dialog.dismiss()
                    )
                    .create()
                    .show()
        );
    }
}