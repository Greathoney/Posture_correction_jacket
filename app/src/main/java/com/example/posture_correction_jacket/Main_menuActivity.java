package com.example.posture_correction_jacket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Main_menuActivity extends AppCompatActivity {

    Button menu1;
    Button menu2;
    Button menu3;
    Button menu4;
    Button help1;
    Button help2;
    Button help3;
    Button help4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        menu1 = findViewById(R.id.menu1);
        menu2 = findViewById(R.id.menu2);
        menu3 = findViewById(R.id.menu3);
        menu4 = findViewById(R.id.menu4);
        help1 = findViewById(R.id.help1);
        help2 = findViewById(R.id.help2);
        help3 = findViewById(R.id.help3);
        help4 = findViewById(R.id.help4);


        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 레이아웃 및 액티비티를 전환하기 위한 코드
                Intent intent = new Intent(Main_menuActivity.this, Bluetooth_pairingActivity.class);
                startActivity(intent);
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 레이아웃 및 액티비티를 전환하기 위한 코드
                Intent intent = new Intent(Main_menuActivity.this, Bluetooth_pairingActivity.class);
                startActivity(intent);
            }
        });

        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 레이아웃 및 액티비티를 전환하기 위한 코드
                Intent intent = new Intent(Main_menuActivity.this, Bluetooth_pairingActivity.class);
                startActivity(intent);
            }
        });

        menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 레이아웃 및 액티비티를 전환하기 위한 코드
                Intent intent = new Intent(Main_menuActivity.this, Bluetooth_pairingActivity.class);
                startActivity(intent);
            }
        });

        help1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 레이아웃 및 액티비티를 전환하기 위한 코드
                Intent intent = new Intent(Main_menuActivity.this, Help1_Activity.class);
                startActivity(intent);
            }
        });

        help2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 레이아웃 및 액티비티를 전환하기 위한 코드
                Intent intent = new Intent(Main_menuActivity.this, Bluetooth_pairingActivity.class);
                startActivity(intent);
            }
        });

        help3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 레이아웃 및 액티비티를 전환하기 위한 코드
                Intent intent = new Intent(Main_menuActivity.this, Bluetooth_pairingActivity.class);
                startActivity(intent);
            }
        });

        help4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 레이아웃 및 액티비티를 전환하기 위한 코드
                Intent intent = new Intent(Main_menuActivity.this, Bluetooth_pairingActivity.class);
                startActivity(intent);
            }
        });




    }
}
