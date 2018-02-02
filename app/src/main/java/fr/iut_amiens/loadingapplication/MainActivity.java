package fr.iut_amiens.loadingapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NumberPicker numberPicker;
    private TextView textView;
    private Button button;
    private LoadingTask loadingTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberPicker = (NumberPicker) findViewById(R.id.numberPicker2);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.timeLeft);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(60);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (loadingTask != null) {
            loadingTask.cancel(true);
            loadingTask = null;
            button.setText(R.string.start);
        } else {
            loadingTask = new LoadingTask(this, textView, numberPicker, button);
            loadingTask.execute();
            button.setText(R.string.cancel);
        }
    }
}
