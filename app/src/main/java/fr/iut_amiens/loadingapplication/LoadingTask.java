package fr.iut_amiens.loadingapplication;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalTime;

public class LoadingTask extends AsyncTask<Object, Integer, String> {

    private final Context context;
    private final TextView textView;
    private final LocalTime timer;
    private final FloatingActionButton floatingActionButton;

    public LoadingTask(Context context, TextView textView, LocalTime timer, FloatingActionButton floatingActionButton) {
        this.context = context;
        this.textView = textView;
        this.timer = timer;
        this.floatingActionButton = floatingActionButton;
    }

    @Override
    protected String doInBackground(Object[] params) {
        try {
            //int progress = timer.getSecond()+timer.getMinute()*60;
            int minutes = timer.getMinute();
            int seconds = timer.getSecond();
            Log.d("", "======================= doInBackground: "+timer.toString());
            while (minutes*60+seconds > 0) {
                Thread.sleep(1000);
                if (seconds <= 0){
                    minutes--;
                    seconds = 59;
                }
                else {
                    seconds--;
                }
                publishProgress(minutes, seconds);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "terminé";
    }

    @Override
    protected void onPreExecute() {
        textView.setText(timer.toString()+"");
    }

    @Override
    protected void onPostExecute(String s) {
        textView.setText(timer.toString());
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        //textView.setText(values[0].toString());
        textView.setText(LocalTime.of(0,values[0], values[1]).toString());
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(context, "annulé", Toast.LENGTH_SHORT).show();
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        textView.setText(timer.toString());
    }
}
