package com.example.posture_correction_jacket;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

import static com.example.posture_correction_jacket.Main_menuActivity.getGlobalString;

public class Menu3_Activity extends AppCompatActivity{

    TextView checkData;
    private Thread workerThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu3);

        checkData = findViewById(R.id.checkData);

        final Handler handler = new Handler();
        workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!(Thread.currentThread().isInterrupted())) {
                    try {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                checkData.setText(getGlobalString());
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        workerThread.start();



    }
}