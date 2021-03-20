package au.edu.jcu.cp3406.stopwatchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {
    public static final int SETTINGS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void doneClicked(View view) {
        EditText speedEntry = findViewById(R.id.speedEntry);
        String text = speedEntry.getText().toString();
        int speed = Integer.parseInt(text);

        Intent intent = new Intent();
        intent.putExtra("speed", speed);
        setResult(RESULT_OK, intent);
        finish();
    }
}