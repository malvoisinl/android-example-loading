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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NumberPicker.OnValueChangeListener {

    private NumberPicker numberPickerSeconds, numberPickerMinutes;
    private TextView textView;
    private FloatingActionButton floatingActionButton;
    private LoadingTask loadingTask = null;
    private int timerInMilliseconds = 0;

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
            timerInMilliseconds = numberPickerMinutes.getValue()*60000+numberPickerSeconds.getValue()*1000;
            Log.d("===================", "doInBackground: "+timerInMilliseconds);
            loadingTask = new LoadingTask(this, textView, floatingActionButton, timerInMilliseconds);
            loadingTask.execute();
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        }
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        timerInMilliseconds = numberPickerMinutes.getValue()*60000+numberPickerSeconds.getValue()*1000;
        DisplayTime();
    }
    public void DisplayTime(){
        int minutes = 0, seconds = 0;
        String textToDisplay;
        try {
            minutes = timerInMilliseconds/60000;
            seconds = (timerInMilliseconds/1000)%60;
        }
        catch (Exception e){
            Log.d("Exception ===========>", "DisplayTime: "+e.getMessage());
        }
        if (minutes < 10){textToDisplay = "0"+minutes;}
        else {textToDisplay = ( String.valueOf(minutes));}
        if (seconds < 10){textToDisplay += ":0"+seconds;}
        else {textToDisplay += ":"+String.valueOf(seconds);}

        textView.setText(textToDisplay);
    }
}
