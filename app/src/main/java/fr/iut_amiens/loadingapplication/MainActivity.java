package fr.iut_amiens.loadingapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NumberPicker numberPickerSeconds, numberPickerMinutes;
    private TextView textView;
    private Button button;
    private LoadingTask loadingTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberPickerSeconds = findViewById(R.id.numberPickerSecond);
        numberPickerMinutes = findViewById(R.id.numberPickerMinutes);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.timeLeft);

        numberPickerSeconds.setMinValue(0);
        numberPickerSeconds.setMaxValue(60);
        numberPickerMinutes.setMinValue(0);
        numberPickerMinutes.setMaxValue(60);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (loadingTask != null) {
            loadingTask.cancel(true);
            loadingTask = null;
            button.setText(R.string.start);
        } else {
            int totalSeconds = numberPickerSeconds.getValue() + numberPickerMinutes.getValue()*60;
            loadingTask = new LoadingTask(this, textView, totalSeconds, button);
            loadingTask.execute();
            button.setText(R.string.cancel);
        }
    }
}
