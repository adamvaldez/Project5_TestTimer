package com.murach.ch10_ex5;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.Log;
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
    private Button startButton;
    private boolean pause;
    private long elapsedTime;
    private long innerTime;


    /**
     *
     * This is commented out because the school blocks it and causes app to crash
     *
        //For the RSS FEED
        private final String URL_STRING = "http://rss.cnn.com/rss/cnn_tech.rss";
        private final String FILENAME = "news_feed.xml";
        private Context context = null;
        public MainActivity(Context context) {
            this.context = context;
        }
    **/

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        elapsedTime = 0;
        innerTime = 0;

        //Linking the variables to Widgets
        messageTextView = (TextView) findViewById(R.id.messageTextView);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        startButton = (Button) findViewById(R.id.startButton);

        //When pauseButton is clicked, it calls pauseTimer() to pause time
        pauseButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                pauseTimer();
            }
        });

        //When startButton is clicked, it calls the resumeTimer() to resume time
        startButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                resumeTimer();
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
                    innerTime += 1000;

                    if(innerTime == 10000)
                    {
                        elapsedTime += 1000;
                        updateView(elapsedTime);
                        /**
                         * Causes app to crash, thanks to school
                         *
                         * downloadFile();
                         *
                         */
                        innerTime = 0;

                    }
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
                messageTextView.setText("Times Downloaded: " + elapsedSeconds);
            }
        });
    }
/**
 *
 * Commented out because school blocks it and causes app to crash
 * Used to download RSS feed
 *
    public void downloadFile()
    {
        try{
            // get the URL
            URL url = new URL(URL_STRING);

            // get the input stream
            InputStream in = url.openStream();

            // get the output stream
            FileOutputStream out =
                    context.openFileOutput(FILENAME, Context.MODE_PRIVATE);

            // read input and write output
            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            while (bytesRead != -1)
            {
                out.write(buffer, 0, bytesRead);
                bytesRead = in.read(buffer);
            }
            out.close();
            in.close();
        }
        catch (IOException e) {
            Log.e("News reader", e.toString());
        }
    }
**/
}