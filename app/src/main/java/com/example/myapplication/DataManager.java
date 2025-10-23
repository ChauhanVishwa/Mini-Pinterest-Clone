package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public static ArrayList<PinItem> pins = new ArrayList<>();

    public static void initializeData() {
        if (pins.isEmpty()) {
            pins.add(new PinItem("Beach Vibes", "Relaxing beach view", R.drawable.pin1));
            pins.add(new PinItem("City Lights", "Urban landscape at night", R.drawable.pin2));
            pins.add(new PinItem("Nature Trail", "Green forest path", R.drawable.pin3));
            pins.add(new PinItem("Mountain Top", "Adventure vibes", R.drawable.pin4));
            pins.add(new PinItem("Food Love", "Delicious dinner", R.drawable.pin5));
            pins.add(new PinItem("Sunset View", "Orange sky", R.drawable.pin6));
            pins.add(new PinItem("Vintage Car", "Classic car collection", R.drawable.pin7));
            pins.add(new PinItem("Street Style", "Fashion photography", R.drawable.pin8));
            pins.add(new PinItem("Flower Field", "Colorful blossoms", R.drawable.pin9));
            pins.add(new PinItem("Architecture", "Modern buildings", R.drawable.pin10));
            pins.add(new PinItem("Wildlife", "Animal photography", R.drawable.pin11));
            pins.add(new PinItem("Rain Drops", "Nature close-up", R.drawable.pin12));
        }
    }
}
