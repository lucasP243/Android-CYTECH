package com.lucasp243.androidtp2_favoritepicker;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_READ_CONTACTS_PERMISSION = 0;
    private static final int REQUEST_CONTACT = 1;

    private Button btnFavoritePkmn, btnFavoriteColor, btnFavoriteContact;

    private final ActivityResultLauncher<Intent> colorPicker = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();

                        if (intent == null) return;

                        int colorId = intent.getIntExtra("color", -1);
                        if (colorId != -1) {
                            int colorInt = getResources().getColor(colorId);
                            btnFavoritePkmn.setBackgroundColor(colorInt);
                            btnFavoriteColor.setBackgroundColor(colorInt);
                            btnFavoriteContact.setBackgroundColor(colorInt);
                        }
                    }
                }
            }
    );

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_CONTACT && data != null) {

            Uri contactUri = data.getData();
            String[] queryFields = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
            try (Cursor cursor = this.getContentResolver().query(contactUri, queryFields, null, null, null)) {
                if (cursor.getCount() == 0) return;
                cursor.moveToFirst();
                String name = cursor.getString(0);
                btnFavoriteContact.setText(name);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFavoritePkmn = findViewById(R.id.btnFavoritePkmn);
        Intent intentFavoritePkmn = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.dragonflycave.com/favorite.html")
        );

        btnFavoritePkmn.setOnClickListener(view -> startActivity(intentFavoritePkmn));

        btnFavoriteColor = findViewById(R.id.btnFavoriteColor);
        Intent intentFavoriteColor = new Intent(this, ColorPickerActivity.class);

        btnFavoriteColor.setOnClickListener(view -> colorPicker.launch(intentFavoriteColor));

        btnFavoriteContact = findViewById(R.id.btnFavoriteContact);
        Intent intentFavoriteContact = new Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        );

        btnFavoriteContact.setOnClickListener(view -> startActivityForResult(intentFavoriteContact, REQUEST_CONTACT));

        requestContactsPermission();
        enableContactBtn(hasContactsPermission());
    }

    private boolean hasContactsPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestContactsPermission() {
        if (hasContactsPermission()) return;

        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.READ_CONTACTS},
                REQUEST_READ_CONTACTS_PERMISSION
        );
    }

    private void enableContactBtn(boolean enable) {
        btnFavoriteContact.setEnabled(enable);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_READ_CONTACTS_PERMISSION && grantResults.length > 0) {
            enableContactBtn(grantResults[0] == PackageManager.PERMISSION_GRANTED);
        }
    }
}