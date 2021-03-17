package au.edu.jcu.cp3406.stopwatchapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity {
    private Stopwatch stopwatch;
    private Handler handler;
    private boolean isRunning;
    private TextView stopwatchTimeDisplay;
    private Button stopwatchToggle;
    private int speed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        stopwatchTimeDisplay = findViewById(R.id.stopwatchTimeDisplay);
        stopwatchToggle = findViewById(R.id.stopwatchToggle);

        isRunning = false;
        if (savedInstanceState == null) { // No saved object state
            stopwatch = new Stopwatch();
        } else {
            stopwatch = new Stopwatch(savedInstanceState.getString("Value"));
            boolean running = savedInstanceState.getBoolean("running");
            if (running) {
                enableStopwatch();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Value", stopwatch.toString());
        outState.putBoolean("running", isRunning);
    }

    private void enableStopwatch() {
        isRunning = true;
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    stopwatch.tick();
                    stopwatchTimeDisplay.setText(stopwatch.toString());
                    handler.postDelayed(this, speed); // Wait 1000 ms before running again
                }
            }
        });
    }

    private void disableStopwatch() {
        isRunning = false;
    }

    public void buttonClicked(View view) {
        if (!isRunning) {
            enableStopwatch();
        } else {
            disableStopwatch();
        }
        updateStopwatchToggleText();
    }

    private void updateStopwatchToggleText() {
        stopwatchToggle.setText(isRunning ? "Stop" : "Start");
    }

    public void settingsClicked(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, SettingsActivity.SETTINGS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SettingsActivity.SETTINGS_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    speed = data.getIntExtra("Speed", 1000);
                }
            }
        }
    }
}
