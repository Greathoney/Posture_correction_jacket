package com.example.posture_correction_jacket;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button pairing_button;
    private static final int REQUEST_ENABLE_BT = 10; // 블루투스 활성화 상태
    private BluetoothAdapter bluetoothAdapter; // 블루투스 어댑터
    private Set<BluetoothDevice> devices; // 블루투스 디바이스 데이터 셋
    private int pairedDeviceCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pairing_button = findViewById(R.id.pairing_button);

        pairing_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); // 블루투스 어댑터를 디폴트 어댑터로 설정

                if (bluetoothAdapter == null) { // 디바이스가 블루투스를 지원하지 않을 때
                    //뒤로가게 만들기
                    Toast.makeText(getApplicationContext(), "디바이스가 블루투스를 지원하지 않습니다.", Toast.LENGTH_LONG).show();
                    onBackPressed();

                } else { // 디바이스가 블루투스를 지원 할 때

                    if (bluetoothAdapter.isEnabled()) { // 블루투스가 활성화 상태 (기기에 블루투스가 켜져있음)
                        selectBluetoothDevice(); // 블루투스 디바이스 선택 함수 호출

                    } else { // 블루투스가 비 활성화 상태 (기기에 블루투스가 꺼져있음)
                        // 블루투스를 활성화 하기 위한 다이얼로그 출력
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        // 선택한 값이 onActivityResult 함수에서 콜백된다.
                        startActivityForResult(intent, REQUEST_ENABLE_BT);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_ENABLE_BT :
                if(resultCode == RESULT_OK) { // '사용'을 눌렀을 때
                    selectBluetoothDevice(); // 블루투스 디바이스 선택 함수 호출
                }
                else { // '취소'를 눌렀을 때

                    // 여기에 처리 할 코드를 작성하세요.
                    Toast.makeText(this, "블루투스를 활성화시켜 주세요.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    public void selectBluetoothDevice() {


        // 이미 페어링 되어있는 블루투스 기기를 찾습니다.
        devices = bluetoothAdapter.getBondedDevices();
        // 페어링 된 디바이스의 크기를 저장
        pairedDeviceCount = devices.size();
        // 페어링 되어있는 장치가 없는 경우
        if (pairedDeviceCount == 0) {
            // 페어링을 하기위한 함수 호출
        }
        // 페어링 되어있는 장치가 있는 경우
        else {
            // 디바이스를 선택하기 위한 다이얼로그 생성
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("페어링 되어있는 블루투스 디바이스 목록");
            // 페어링 된 각각의 디바이스의 이름과 주소를 저장
            List<String> list = new ArrayList<>();
            // 모든 디바이스의 이름을 리스트에 추가
            for (BluetoothDevice bluetoothDevice : devices) {
                //TODO 필요한 것만 띄우기

                list.add(bluetoothDevice.getName());
            }
            list.add("취소");

            // List를 CharSequence 배열로 변경
            final CharSequence[] charSequences = list.toArray(new CharSequence[list.size()]);
            list.toArray(new CharSequence[list.size()]);

            // 해당 아이템을 눌렀을 때 호출 되는 이벤트 리스너
            builder.setItems(charSequences, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (charSequences[which].toString().equals("BT04-A")){

                    // 블루투스 켤 시에 다음 화면으로 넘어갑니다.
                    onBackPressed();
                    Intent intent = new Intent(MainActivity.this, Main_menuActivity.class);
                    startActivity(intent);
                    }

                    else{
                        Toast.makeText(getApplicationContext(), "맞는 블루투스 기기를 찾아주세요.", Toast.LENGTH_LONG).show();
                    }
                }
            });
            // 뒤로가기 버튼 누를 때 창이 안닫히도록 설정
            builder.setCancelable(false);
            // 다이얼로그 생성
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
