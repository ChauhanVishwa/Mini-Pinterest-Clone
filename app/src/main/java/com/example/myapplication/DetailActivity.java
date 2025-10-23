package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    ImageView detailImage;
    TextView detailTitle, detailDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailImage = findViewById(R.id.detail_image);
        detailTitle = findViewById(R.id.detail_title);
        detailDescription = findViewById(R.id.detail_description);

        if (getIntent() != null && getIntent().getExtras() != null) {
            String title = getIntent().getStringExtra("title");
            String desc = getIntent().getStringExtra("description");
            String imageUriStr = getIntent().getStringExtra("imageUri");

            detailTitle.setText(title);
            detailDescription.setText(desc);
            if (imageUriStr != null) {
                detailImage.setImageURI(Uri.parse(imageUriStr));
            }
        }
    }
}
