package fr.iut_amiens.loadingapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.VibrationEffect;
import android.util.Log;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class LoadingTask extends AsyncTask<Object, Integer, String> {

    private final Context context;
    private final TextView textView;
    private final NumberPicker numberPicker;
    private final Button button;

    public LoadingTask(Context context, TextView textView, NumberPicker numberPicker, Button button) {
        this.context = context;
        this.textView = textView;
        this.numberPicker = numberPicker;
        this.button = button;
    }

    @Override
    protected String doInBackground(Object[] params) {
        try {
            int progress = numberPicker.getValue();
            Log.d("", "======================= doInBackground: "+progress);
            while (progress > 0) {
                Thread.sleep(1000);
                progress--;
                publishProgress(progress);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "terminé";
    }

    @Override
    protected void onPreExecute() {
        textView.setText(numberPicker.getValue()+"");
        Toast.makeText(context, "démarrage", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String s) {
        textView.setText("0");
        button.setText("Start");
        VibrationEffect Create(100, 255);
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        textView.setText(values[0].toString());
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(context, "cancelled", Toast.LENGTH_SHORT).show();
        textView.setText("0");
    }
}
