package com.lucasp243.androidtp4_todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getPreferences(Context.MODE_PRIVATE);

        updateUserName();
    }

    private void updateUserName() {
        String userName = preferences.getString("USER_NAME", "");
        setTitle(
                userName.equals("") ?
                        getString(R.string.menu_title_default)
                        :
                        String.format(
                                Locale.getDefault(),
                                getString(R.string.menu_title_named),
                                userName
                        )
        );
    }

    /* Manage menu */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() != R.id.menu_edit_name) return super.onOptionsItemSelected(item);

        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_menu_title)
                .setPositiveButton(
                        R.string.dialog_confirm,
                        (dialog, which) -> {

                            EditText input = ((Dialog) dialog).findViewById(R.id.dialog_input);

                            // Push name to shared preferences
                            preferences
                                    .edit()
                                    .putString("USER_NAME", input.getText().toString())
                                    .apply();
                            dialog.dismiss();

                            updateUserName();
                        }
                )
                .setView(R.layout.dialog_input)
                .show();

        return true;
    }
}