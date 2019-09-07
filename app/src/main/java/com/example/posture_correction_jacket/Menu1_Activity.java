package com.example.posture_correction_jacket;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.CompoundButton.OnCheckedChangeListener;

import static com.example.posture_correction_jacket.Main_menuActivity.switchVal1;
import static com.example.posture_correction_jacket.Main_menuActivity.switchVal2;
import static com.example.posture_correction_jacket.Main_menuActivity.switchVal3;


import androidx.appcompat.app.AppCompatActivity;

public class Menu1_Activity extends AppCompatActivity {

    Switch switch1;
    Switch switch2;
    Switch switch3;

    Button returnMenu1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu1);

        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        switch3 = findViewById(R.id.switch3);

        returnMenu1 = findViewById(R.id.returnMenu1);


        SharedPreferences getSwitchData = getSharedPreferences("switchFile", MODE_PRIVATE);


////        boolean init_switchVal1 = getSwitchData.getBoolean("switchVal1", true);
//        boolean init_switchVal2 = getSwitchData.getBoolean("switchVal2", true);
//        boolean init_switchVal3 = getSwitchData.getBoolean("switchVal3", true);
//
////        switchVal1 = init_switchVal1;
//        switchVal2 = init_switchVal2;
//        switchVal3 = init_switchVal3;
//
////        switch1.setChecked(init_switchVal1);
//        switch2.setChecked(init_switchVal2);
//        switch3.setChecked(init_switchVal3);


        switch1.setChecked(switchVal1);

        switchVal2 = getSwitchData.getBoolean("switchVal2", true);
        switch2.setChecked(switchVal2);

        switchVal3 = getSwitchData.getBoolean("switchVal3", true);
        switch3.setChecked(switchVal3);

        if (!switchVal1) {
            switchVal2 = false;
            switchVal3 = false;
            switch2.setEnabled(false);
            switch3.setEnabled(false);
        }


        switch1.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if (!isChecked) {
                    switchVal2 = false;
                    switchVal3 = false;
                } else {
                    switchVal2 = switch2.isChecked();
                    switchVal3 = switch3.isChecked();
                }

                switch2.setEnabled(isChecked);
                switch3.setEnabled(isChecked);
                switchVal1 = isChecked;


//                Log.d(this.getClass().getName(), "0" + switchVal1 + switchVal2 + switchVal3);

            }
        });

        switch2.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                switchVal2 = isChecked;
//                Log.d(this.getClass().getName(), "1" + switchVal1 + switchVal2 + switchVal3);

            }
        });

        switch3.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                switchVal3 = isChecked;
//                Log.d(this.getClass().getName(), "2" + switchVal1 + switchVal2 + switchVal3);

            }
        });


//        switch1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!switch1.isChecked()) {
//                    switchVal2 = false;
//                    switchVal3 = false;
//                } else {
//                    switchVal2 = switch2.isChecked();
//                    switchVal3 = switch3.isChecked();
//                }
//
//                switch2.setEnabled(switch1.isChecked());
//                switch3.setEnabled(switch1.isChecked());
//                switchVal1 = switch1.isChecked();
//
//
////                Log.d(this.getClass().getName(), "1" + switchVal1 + switchVal2 + switchVal3);
//            }
//
//        });
//
//
//        switch2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchVal2 = switch2.isChecked();
//
////                Log.d(this.getClass().getName(), "2" + switchVal1 + switchVal2 + switchVal3);
//            }
//        });
//
//        switch3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchVal3 = switch3.isChecked();
//
////                Log.d(this.getClass().getName(), "3" + switchVal1 + switchVal2 + switchVal3);
//            }
//
//        });



        returnMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    protected void onStop() {
        super.onStop();

        // Activity가 종료되기 전에 저장한다.
        //SharedPreferences를 sFile이름, 기본모드로 설정
        SharedPreferences sharedPreferences = getSharedPreferences("switchFile",MODE_PRIVATE);

        //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // 사용자가 입력한 저장할 데이터
//        editor.putString("text",text); // key, value를 이용하여 저장하는 형태
        //다양한 형태의 변수값을 저장할 수 있다.
        //editor.putString();
        //editor.putBoolean();
        //editor.putFloat();
        //editor.putLong();
        //editor.putInt();
        //editor.putStringSet();

        editor.putBoolean("switchVal1", switch1.isChecked());
        editor.putBoolean("switchVal2", switch2.isChecked());
        editor.putBoolean("switchVal3", switch3.isChecked());

        //최종 커밋
        editor.commit();
    }




}
