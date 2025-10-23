package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout container;

    // store image URIs as strings
    public static List<String> fullImageUris = new ArrayList<>();
    public static List<String> fullTitles = new ArrayList<>();
    public static List<String> fullDescriptions = new ArrayList<>();

    // working lists for search & display
    List<String> imageUris = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    List<String> descriptions = new ArrayList<>();

    private ActivityResultLauncher<Intent> uploadLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        container = findViewById(R.id.container);

        if (fullImageUris.isEmpty()) {
            addDrawableAsUri(R.drawable.pin1, "Sunset", "A beautiful orange sunset.");
            addDrawableAsUri(R.drawable.pin2, "Mountains", "Snowy peaks and a blue sky.");
            addDrawableAsUri(R.drawable.pin3, "City", "Night city lights.");
            addDrawableAsUri(R.drawable.pin4, "Beach", "Relaxing sea and sand.");
            addDrawableAsUri(R.drawable.pin5, "Forest", "Peaceful green forest.");
            addDrawableAsUri(R.drawable.pin6, "River", "Flowing through rocks.");
            addDrawableAsUri(R.drawable.pin7, "Desert", "Golden sand dunes.");
            addDrawableAsUri(R.drawable.pin8, "Flowers", "Colorful flower field.");
            addDrawableAsUri(R.drawable.pin9, "Bridge", "Old stone bridge.");
            addDrawableAsUri(R.drawable.pin10, "Lake", "Still lake reflection.");
            addDrawableAsUri(R.drawable.pin11, "Road", "Road into the hills.");
            addDrawableAsUri(R.drawable.pin12, "City Sunrise", "Morning lights and mist.");
        }

        copyFullToWorking();
        populateGrid();

        uploadLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                (ActivityResult result) -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        String uriStr = data.getStringExtra("imageUri");
                        String title = data.getStringExtra("title");
                        String desc = data.getStringExtra("description");

                        if (uriStr != null && title != null) {
                            fullImageUris.add(0, uriStr);
                            fullTitles.add(0, title);
                            fullDescriptions.add(0, desc == null ? "" : desc);
                            copyFullToWorking();
                            populateGrid();
                        }
                    }
                }
        );

        LinearLayout navHome = findViewById(R.id.nav_home);
        LinearLayout navUpload = findViewById(R.id.nav_upload);
        LinearLayout navProfile = findViewById(R.id.nav_profile);

        navHome.setOnClickListener(v -> { });

        navUpload.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UploadActivity.class);
            uploadLauncher.launch(intent);
        });

        navProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    private void addDrawableAsUri(int drawableResId, String title, String desc) {
        String uri = "android.resource://" + getPackageName() + "/" + drawableResId;
        fullImageUris.add(uri);
        fullTitles.add(title);
        fullDescriptions.add(desc);
    }

    private void copyFullToWorking() {
        imageUris.clear();
        titles.clear();
        descriptions.clear();
        imageUris.addAll(fullImageUris);
        titles.addAll(fullTitles);
        descriptions.addAll(fullDescriptions);
    }

    private void populateGrid() {
        populateGridFromLists(imageUris, titles, descriptions);
    }

    private void populateGridFromLists(List<String> uris, List<String> titlesList, List<String> descriptionsList) {
        container.removeAllViews();
        final int columns = 2;
        LayoutInflater inflater = LayoutInflater.from(this);

        int total = titlesList.size();
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

                String uriStr = uris.get(index);
                iv.setImageURI(Uri.parse(uriStr));
                tv.setText(titlesList.get(index));

                LinearLayout.LayoutParams itemLp =
                        new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                itemLp.setMargins(6, 6, 6, 6);
                item.setLayoutParams(itemLp);

                final int posForClick = index;
                item.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("title", titlesList.get(posForClick));
                    intent.putExtra("description", descriptionsList.get(posForClick));
                    intent.putExtra("imageUri", uris.get(posForClick));
                    startActivity(intent);
                });

                item.setOnLongClickListener(v -> {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Delete pin?")
                            .setMessage("Do you want to delete this pin?")
                            .setPositiveButton("Delete", (dialog, which) -> {
                                String uriToRemove = uris.get(posForClick);
                                String titleToRemove = titlesList.get(posForClick);

                                for (int k = 0; k < fullTitles.size(); k++) {
                                    if (fullTitles.get(k).equals(titleToRemove) &&
                                            fullImageUris.get(k).equals(uriToRemove)) {
                                        fullTitles.remove(k);
                                        fullImageUris.remove(k);
                                        fullDescriptions.remove(k);
                                        break;
                                    }
                                }
                                copyFullToWorking();
                                populateGrid();
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                    return true;
                });

                row.addView(item);
            }
            container.addView(row);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search by title...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterAndPopulate(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterAndPopulate(newText);
                return true;
            }
        });

        searchView.setOnCloseListener(() -> {
            copyFullToWorking();
            populateGrid();
            return false;
        });
        return true;
    }

    private void filterAndPopulate(String query) {
        if (query == null || query.trim().isEmpty()) {
            copyFullToWorking();
            populateGrid();
            return;
        }
        String q = query.toLowerCase();
        List<String> filteredUris = new ArrayList<>();
        List<String> filteredTitles = new ArrayList<>();
        List<String> filteredDescriptions = new ArrayList<>();

        for (int i = 0; i < fullTitles.size(); i++) {
            if (fullTitles.get(i).toLowerCase().contains(q)) {
                filteredTitles.add(fullTitles.get(i));
                filteredUris.add(fullImageUris.get(i));
                filteredDescriptions.add(fullDescriptions.get(i));
            }
        }
        populateGridFromLists(filteredUris, filteredTitles, filteredDescriptions);
    }
}
