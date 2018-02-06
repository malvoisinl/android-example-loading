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
    private final Integer timerInMilliseconds;
    private final FloatingActionButton floatingActionButton;

    public LoadingTask(Context context, TextView textView, FloatingActionButton floatingActionButton, Integer timerInMilliseconds) {
        this.context = context;
        this.textView = textView;
        this.timerInMilliseconds = timerInMilliseconds;
        this.floatingActionButton = floatingActionButton;
    }
    @Override
    protected String doInBackground(Object[] params) {
        try {
            //int progress = timer.getSecond()+timer.getMinute()*60;
            int minutes = timerInMilliseconds/60000;
            int seconds = (timerInMilliseconds/1000)%60;
            Log.d("", "======================= doInBackground: "+timerInMilliseconds);
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
        DisplayTime();
    }

    @Override
    protected void onPostExecute(String s) {
        DisplayTime();
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.d("=======================", "onProgressUpdate: "+values[0]+" "+values[1]);
        DisplayTime(values[0], values[1]);
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(context, "annulé", Toast.LENGTH_SHORT).show();
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
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
    public void DisplayTime(Integer minutes, Integer seconds){
        String textToDisplay;
        if (minutes < 10){textToDisplay = "0"+minutes;}
        else {textToDisplay = ( String.valueOf(minutes));}
        if (seconds < 10){textToDisplay += ":0"+seconds;}
        else {textToDisplay += ":"+String.valueOf(seconds);}

        textView.setText(textToDisplay);
    }
}
