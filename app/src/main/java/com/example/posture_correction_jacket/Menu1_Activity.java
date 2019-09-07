package com.example.posture_correction_jacket;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class Menu1_Activity extends AppCompatActivity {

    Switch switch1;
    Switch switch2;
    Switch switch3;


    private boolean switchVal1;
    private boolean switchVal2;
    private boolean switchVal3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu1);

        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        switch3 = findViewById(R.id.switch3);


        SharedPreferences getSwitchData = getSharedPreferences("switchFile",MODE_PRIVATE);

        switchVal1 = getSwitchData.getBoolean("switchVal1", true);
        switchVal2 = getSwitchData.getBoolean("switchVal2", true);
        switchVal3 = getSwitchData.getBoolean("switchVal3", true);

        switch1.setChecked(switchVal1);
        switch2.setChecked(switchVal2);
        switch3.setChecked(switchVal3);



        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch1.isChecked()){
                    switchVal1 = true;

                }
                else{
                    switchVal1 = false;

                }

            }

        });

        switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch2.isChecked()){
                    switchVal2 = true;

                }
                else{
                    switchVal2 = false;

                }

            }

        });

        switch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch3.isChecked()){
                    switchVal3 = true;

                }
                else{
                    switchVal3 = false;

                }

            }

        });




//        option1 = getSharedPreferences("option1", MODE_PRIVATE);
//        load();
//
//        idText
//
//    }
//
//    private void save(){
//        SharedPreferences.Editor = editor = appData.edit();
//
//        editor.putBoolean("SAVE")
//    }
//
//    private void load(){

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
