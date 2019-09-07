package com.example.posture_correction_jacket;

import android.app.ActivityManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.example.posture_correction_jacket.Main_menuActivity.leftPress;
import static com.example.posture_correction_jacket.Main_menuActivity.rightPress;
import static com.example.posture_correction_jacket.Main_menuActivity.leftAngle;
import static com.example.posture_correction_jacket.Main_menuActivity.rightAngle;



public class Menu3_Activity extends AppCompatActivity{

    TextView checkData;
    BarChart barChart;
    private Thread workerThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu3);

        checkData = findViewById(R.id.checkData);
        barChart = (BarChart) findViewById(R.id.barchart);

        final Handler handler = new Handler();
        workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!(Thread.currentThread().isInterrupted()) && isActivityTop()) {
                    try {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<BarEntry> entries = new ArrayList<>();
                                entries.add(new BarEntry(leftPress, 0));
                                entries.add(new BarEntry(rightPress, 1));
                                entries.add(new BarEntry(leftAngle, 2));
                                entries.add(new BarEntry(rightAngle, 3));

                                BarDataSet barDataSet = new BarDataSet(entries, " ");

                                ArrayList<String> labels = new ArrayList<>();
                                labels.add("LP");
                                labels.add("RP");
                                labels.add("LA");
                                labels.add("RA");

                                BarData data = new BarData(labels, barDataSet);
                                barChart.setData(data); // set the data and list of lables into chart

                                barChart.setDescription("Description");  // set the description

                                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

//                                barChart.animateY(100);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        workerThread.start();




    }


    private boolean isActivityTop(){

        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> info;

        info = activityManager.getRunningTasks(1);

        if(info.get(0).topActivity.getClassName().equals(Menu3_Activity.this.getClass().getName())) { //에러가 뜨는 이유는 deprecated 되었기 때문, 그러나 쓸 수 있다.

            return true;

        } else {

            return false;

        }

    }
}