package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class UploadActivity extends AppCompatActivity {

    private ImageView previewImage;
    private EditText editTitle, editDescription;
    private Uri imageUri;

    // Modern Activity Result Launcher
    private ActivityResultLauncher<String> pickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        previewImage = findViewById(R.id.previewImage);
        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        Button btnChoose = findViewById(R.id.btnChoose);
        Button btnUpload = findViewById(R.id.btnUpload);

        // Initialize launcher
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        imageUri = uri;
                        previewImage.setImageURI(uri);

                        // Persist permission so other activities can access this URI
                        final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                        try {
                            getContentResolver().takePersistableUriPermission(uri, takeFlags);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        // Choose image
        btnChoose.setOnClickListener(v -> pickImageLauncher.launch("image/*"));

        // Upload (send data back to MainActivity)
        btnUpload.setOnClickListener(v -> {
            if (imageUri != null && !editTitle.getText().toString().isEmpty()) {
                Intent result = new Intent();
                result.putExtra("imageUri", imageUri.toString());
                result.putExtra("title", editTitle.getText().toString());
                result.putExtra("description", editDescription.getText().toString());
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });
    }
}
