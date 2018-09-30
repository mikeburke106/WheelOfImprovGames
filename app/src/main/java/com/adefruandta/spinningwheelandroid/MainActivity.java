package com.adefruandta.spinningwheelandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.adefruandta.spinningwheel.SpinningWheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SpinningWheelView.OnRotationListener<String> {

    private static final int RAND_MIN_TIME_MS = 1000;
    private static final int RAND_MAX_TIME_MS = 4000;

    private TextView resultTextView;
    private SpinningWheelView wheelView;
    private List<String> gamesList;
    private Random rng;
    private String lastItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wheelView = (SpinningWheelView) findViewById(R.id.wheel);
        wheelView.setOnRotationListener(this);

        resultTextView = (TextView) findViewById(R.id.tv_result);

        rng = new Random();
        resetWheel();
    }

    @Override
    public void onRotation() {

    }

    @Override
    public void onStopRotation(String item) {
        if (item == null || "".equals(item)) {
            item = gamesList.get(0);
        }

        lastItem = item;
        resultTextView.setText(item);
    }

    private void resetWheel() {
        String[] arrayResources = getResources().getStringArray(R.array.games);
        gamesList = new ArrayList<>(arrayResources.length);
        gamesList.addAll(Arrays.asList(arrayResources));

        wheelView.setItems(R.array.games);
    }

    private void spinWheel() {
        if (lastItem != null) {
            gamesList.remove(lastItem);
        }
        wheelView.setItems(gamesList);

        if (gamesList.size() > 0) {
            final int randomTime = rng.nextInt((RAND_MAX_TIME_MS - RAND_MIN_TIME_MS) + 1) + RAND_MIN_TIME_MS;
            wheelView.rotate(50, randomTime, 50);
        } else {
            wheelView.setVisibility(View.INVISIBLE);
            Toast.makeText(MainActivity.this, "Wheel is empty!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_reset:
                resetWheel();
                return true;
            case R.id.menu_action_spin:
                spinWheel();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
