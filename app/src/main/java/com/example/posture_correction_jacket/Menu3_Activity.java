package com.example.posture_correction_jacket;

import android.app.ActivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
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

import static com.example.posture_correction_jacket.Main_menuActivity.rightAngleStandard;
import static com.example.posture_correction_jacket.Main_menuActivity.leftAngleStandard;



public class Menu3_Activity extends AppCompatActivity{

    TextView checkData;
    BarChart barChart1;
    BarChart barChart2;
    private Thread workerThread = null;

    Button setZero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu3);

        checkData = findViewById(R.id.checkData);
        barChart1 = (BarChart) findViewById(R.id.barchart1);
        barChart2 = (BarChart) findViewById(R.id.barchart2);
        setZero = findViewById(R.id.setZero);

        final Handler handler = new Handler();
        workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!(Thread.currentThread().isInterrupted()) && isActivityTop()) {
                    try {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<BarEntry> entries1 = new ArrayList<>();
                                entries1.add(new BarEntry(leftPress, 0));
                                entries1.add(new BarEntry(rightPress, 1));

                                ArrayList<BarEntry> entries2 = new ArrayList<>();
                                entries2.add(new BarEntry(leftAngle, 0));
                                entries2.add(new BarEntry(rightAngle, 1));

                                BarDataSet barDataSet1 = new BarDataSet(entries1, " ");
                                BarDataSet barDataSet2 = new BarDataSet(entries2, " ");

                                YAxis y1 = barChart1.getAxisLeft();
                                y1.setAxisMaxValue(500);
                                y1.setAxisMinValue(0);

                                YAxis y2 = barChart2.getAxisLeft();
                                y2.setAxisMaxValue(180);
                                y2.setAxisMinValue(-180);

                                ArrayList<String> labels1 = new ArrayList<>();
                                labels1.add("LP");
                                labels1.add("RP");

                                ArrayList<String> labels2 = new ArrayList<>();
                                labels2.add("LA");
                                labels2.add("RA");

                                BarData data1 = new BarData(labels1, barDataSet1);
                                barChart1.setData(data1); // set the data and list of lables into chart

                                barChart1.setDescription("Pressure");  // set the description

                                barChart1.setPinchZoom(false);
                                barChart1.setScaleEnabled(false);
                                barChart1.setDoubleTapToZoomEnabled(false);
                                barChart1.getAxisRight().setEnabled(false);

                                barChart1.notifyDataSetChanged();
                                barChart1.invalidate();

                                barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

                                BarData data2 = new BarData(labels2, barDataSet2);
                                barChart2.setData(data2); // set the data and list of lables into chart

                                barChart2.setDescription("Angle");  // set the description

                                barChart2.setPinchZoom(false);
                                barChart2.setScaleEnabled(false);
                                barChart2.setDoubleTapToZoomEnabled(false);
                                barChart2.getAxisRight().setEnabled(false);

                                barChart2.notifyDataSetChanged();
                                barChart2.invalidate();

                                barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000); //일단 1초로 모두 맞추어놓을게
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        workerThread.start();

        setZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightAngleStandard = rightAngle;
                leftAngleStandard = leftAngle;
            }
        });
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