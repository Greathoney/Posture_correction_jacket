package com.example.posture_correction_jacket;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class Menu1_Activity extends AppCompatActivity {

    Switch switch1;
    Switch switch2;
    Switch switch3;

    Button returnMenu1;

    boolean switchVal1;
    boolean switchVal2;
    boolean switchVal3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu1);

        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        switch3 = findViewById(R.id.switch3);

        returnMenu1 = findViewById(R.id.returnMenu1);


        SharedPreferences getSwitchData = getSharedPreferences("switchFile",MODE_PRIVATE);


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


        switchVal1 = getSwitchData.getBoolean("switchVal1", true);
        switch1.setChecked(switchVal1);

        switchVal2 = getSwitchData.getBoolean("switchVal2", true);
        switch2.setChecked(switchVal2);

        switchVal3 = getSwitchData.getBoolean("switchVal3", true);
        switch3.setChecked(switchVal3);

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchVal1 = switch1.isChecked();
            }

        });

        switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchVal2 = switch2.isChecked();

            }

        });

        switch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchVal3 = switch3.isChecked();
            }

        });

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

        editor.putBoolean("switchVal1", switchVal1);
        editor.putBoolean("switchVal2", switchVal2);
        editor.putBoolean("switchVal3", switchVal3);

        //최종 커밋
        editor.commit();
    }
}
