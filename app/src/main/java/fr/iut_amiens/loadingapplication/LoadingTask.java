package fr.iut_amiens.loadingapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.VibrationEffect;
import android.util.Log;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class LoadingTask extends AsyncTask<Object, Integer, String> {

    private final Context context;
    private final TextView textView;
    private final int seconds;
    private final Button button;

    public LoadingTask(Context context, TextView textView, int seconds, Button button) {
        this.context = context;
        this.textView = textView;
        this.button = button;
        this.seconds = seconds;
    }

    @Override
    protected String doInBackground(Object[] params) {
        try {
            int progress = seconds;
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
        textView.setText(seconds+"");
    }

    @Override
    protected void onPostExecute(String s) {
        textView.setText("0");
        button.setText("Start");
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        textView.setText(values[0].toString());
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(context, "annulé", Toast.LENGTH_SHORT).show();
        textView.setText("0");
    }
}
