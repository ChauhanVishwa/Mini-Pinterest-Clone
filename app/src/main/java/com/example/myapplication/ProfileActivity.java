package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {

    ImageView profileAvatar;
    TextView profileName, profileBio;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Profile");
        }

        profileAvatar = findViewById(R.id.profile_avatar);
        profileName = findViewById(R.id.profile_name);
        profileBio = findViewById(R.id.profile_bio);
        container = findViewById(R.id.container);

        profileAvatar.setImageResource(R.drawable.ic_person);
        profileName.setText("Vishwa Chauhan");
        profileBio.setText("Photographer & Travel Lover");

        populateGrid();
    }

    private void populateGrid() {
        container.removeAllViews();
        final int columns = 2;
        LayoutInflater inflater = LayoutInflater.from(this);

        int total = MainActivity.fullTitles.size();
        for (int i = 0; i < total; i += columns) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams rowParams =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(rowParams);

            for (int j = 0; j < columns; j++) {
                int index = i + j;
                if (index >= total) {
                    View spacer = new View(this);
                    LinearLayout.LayoutParams spacerLp = new LinearLayout.LayoutParams(0,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                    spacer.setLayoutParams(spacerLp);
                    row.addView(spacer);
                    continue;
                }

                View item = inflater.inflate(R.layout.item_pin, row, false);
                ImageView iv = item.findViewById(R.id.item_image);
                TextView tv = item.findViewById(R.id.item_title);

                iv.setImageURI(Uri.parse(MainActivity.fullImageUris.get(index)));
                tv.setText(MainActivity.fullTitles.get(index));

                LinearLayout.LayoutParams itemLp =
                        new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                itemLp.setMargins(6,6,6,6);
                item.setLayoutParams(itemLp);

                final int posForClick = index;
                item.setOnClickListener(v -> {
                    Intent intent = new Intent(ProfileActivity.this, DetailActivity.class);
                    intent.putExtra("title", MainActivity.fullTitles.get(posForClick));
                    intent.putExtra("description", MainActivity.fullDescriptions.get(posForClick));
                    intent.putExtra("imageUri", MainActivity.fullImageUris.get(posForClick));
                    startActivity(intent);
                });

                row.addView(item);
            }
            container.addView(row);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh grid in case new uploads were added
        populateGrid();
    }
}
