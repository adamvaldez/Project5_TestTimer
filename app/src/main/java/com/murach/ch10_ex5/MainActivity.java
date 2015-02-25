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
        messageTextView = (TextView) findViewById(R.id.messageTextView);


        pauseButton = (Button) findViewById(R.id.pauseButton);

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

    /** These two methods are what you needed  **/
    @Override
    public void onResume()
    {
        super.onResume();
        resumeTimer();

        // right here, if you want the timer to come up with the next number, then you would
        // elapsedTime += 1000 // here
    }

    @Override
    public void onPause()
    {
        super.onPause();
        pauseTimer();
    }

    private void pauseTimer()
    {
        pause = true;
    }

    private void resumeTimer()
    {
        pause = false;
    }


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