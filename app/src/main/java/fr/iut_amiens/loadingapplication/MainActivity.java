package fr.iut_amiens.loadingapplication;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.time.LocalTime;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NumberPicker.OnValueChangeListener {

    private NumberPicker numberPickerSeconds, numberPickerMinutes;
    private TextView textView;
    private FloatingActionButton floatingActionButton;
    private LoadingTask loadingTask = null;
    private LocalTime timer = LocalTime.of(0,0,0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberPickerSeconds = findViewById(R.id.numberPickerSecond);
        numberPickerMinutes = findViewById(R.id.numberPickerMinutes);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        textView = findViewById(R.id.timeLeft);

        numberPickerSeconds.setMinValue(0);
        numberPickerSeconds.setMaxValue(59);
        numberPickerMinutes.setMinValue(0);
        numberPickerMinutes.setMaxValue(59);
        floatingActionButton.setOnClickListener(this);
        //if pickers values changes, then update the counter
        numberPickerSeconds.setOnValueChangedListener(this);
        numberPickerMinutes.setOnValueChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        if (loadingTask != null) {
            loadingTask.cancel(true);
            loadingTask = null;
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        } else {
            timer = LocalTime.of(0, numberPickerMinutes.getValue(),numberPickerSeconds.getValue());
            Log.d("===================", "doInBackground: "+timer.toString());
            loadingTask = new LoadingTask(this, textView, timer, floatingActionButton);
            loadingTask.execute();
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        }
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        textView.setText(LocalTime.of(0, numberPickerMinutes.getValue(),numberPickerSeconds.getValue()).toString());
    }
}
