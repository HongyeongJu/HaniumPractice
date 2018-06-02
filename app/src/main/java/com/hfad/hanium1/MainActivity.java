package com.hfad.hanium1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView timeText;
    Button startButton;
    Button stopButton;
    Button resetButton;
    Button dialogButton;

    boolean isRunning = false;
    int second = 0;
    boolean wasRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeText = (TextView)findViewById(R.id.time);
        startButton = (Button)findViewById(R.id.start);
        stopButton = (Button)findViewById(R.id.stop);
        resetButton = (Button)findViewById(R.id.reset);
        dialogButton = (Button)findViewById(R.id.dialog);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(isRunning){
                    second++;

                }

                int sec = second % 60;
                int min = (second % 3600) / 60;
                int hour = second / 3600;

                String str = String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, min, sec);
                timeText.setText(str);
                handler.postDelayed(this, 1000);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning=true;
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning= false;
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                second = 0;
            }
        });

        Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_SHORT).show();

        if(savedInstanceState !=null){
            second = savedInstanceState.getInt("second");
            isRunning = savedInstanceState.getBoolean("isRunning");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }


        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isRunning", isRunning);
        outState.putInt("second", second);
        outState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 크레이트 되고나서 바로 실행
        // Create  -> onStart  ->onResume -> onPause - > onStop -> onReStart -> onStart
    }
    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = wasRunning;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = isRunning;
        isRunning = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //  onStop - > onRestart - > onStart
    }
}
