package com.murach.ch10_ex5;

import java.util.Timer;
import java.util.TimerTask;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class MainActivity extends Activity
{
    //Variable Declarations
    private TextView messageTextView;
    private Button pauseButton;
    private boolean pause;
    private long elapsedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        elapsedTime = 0;

        //Linking the variables to Widgets
        messageTextView = (TextView) findViewById(R.id.messageTextView);
        pauseButton = (Button) findViewById(R.id.pauseButton);

        //When pauseButton is clicked, it calls pauseTimer() to pause time
        pauseButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                pauseTimer();
            }
        });

        pause = false;
        startTimer();
    }

    //Starts the timer
    private void startTimer()
    {
        final long startMillis = System.currentTimeMillis();
        Timer timer = new Timer(true);
        TimerTask task = new TimerTask()
        {

            @Override
            public void run()
            {

                if(!pause)
                {
                    elapsedTime += 1000;
                    updateView(elapsedTime);
                }
            }
        };
        timer.schedule(task, 0, 1000);
    }

    //When app is resumed, the timer continues from paused position
    @Override
    public void onResume()
    {
        super.onResume();
        resumeTimer();

        // right here, if you want the timer to come up with the next number, then you would
        // elapsedTime += 1000 // here
    }

    //When app is paused, it pauses the timer via the pauseTimer()
    @Override
    public void onPause()
    {
        super.onPause();
        pauseTimer();
    }

    //Changed the pause boolean to true
    private void pauseTimer()
    {
        pause = true;
    }

    //Changed the pause boolean back to false
    private void resumeTimer()
    {
        pause = false;
    }

    //Displays time on TextView
    private void updateView(final long elapsedMillis)
    {
        // UI changes need to be run on the UI thread
        messageTextView.post(new Runnable()
        {

            int elapsedSeconds = (int) elapsedMillis / 1000;

            @Override
            public void run()
            {
                messageTextView.setText("Seconds: " + elapsedSeconds);
            }
        });
    }
}