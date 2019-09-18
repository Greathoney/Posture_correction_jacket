package com.example.posture_correction_jacket;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;


public class Main_menuActivity extends AppCompatActivity {

    final static String bluetoothName = "BT04-A";

    Button menu1;
    Button menu2;
    Button menu3;
    Button menu4;
    Button help1;
    Button help2;
    Button help3;
    Button help4;
    TextView warning_;

    private BluetoothAdapter bluetoothAdapter; // 블루투스 어댑터
    private Set<BluetoothDevice> devices; // 블루투스 디바이스 데이터 셋
    private BluetoothDevice bluetoothDevice; // 블루투스 디바이스
    private BluetoothSocket bluetoothSocket = null; // 블루투스 소켓
    private OutputStream outputStream = null; // 블루투스에 데이터를 출력하기 위한 출력 스트림
    private InputStream inputStream = null; // 블루투스에 데이터를 입력하기 위한 입력 스트림
    private Thread workerThread = null; // 문자열 수신에 사용되는 쓰레드
    private byte[] readBuffer; // 수신 된 문자열을 저장하기 위한 버퍼
    private int readBufferPosition; // 버퍼 내 문자 저장 위치

    static int leftPress;
    static int rightPress;
    static int leftAngle;
    static int rightAngle;

    static int beforeLeftAngle_data = 0; //이전 데이터 값, 옷이 가만히 있는지 확인합니다.
    static int beforeRightAngle_data = 0;
    static int beforeCount = 0;

    private double LP_damped = 0.0;
    private double RP_damped = 0.0;
    private double LA_damped = 0.0;
    private double RA_damped = 0.0;

    private long checkTime = System.currentTimeMillis();

    //아래 4개의 변수는 센서로 측정된 값을 바탕으로 작성됩니다.

    static boolean switchVal1;  // 실시간으로 데이터를 수집합니다.
    static boolean switchVal2; // 기울어짐이 오래 지속되었을 때 알림 띄우기
    static boolean switchVal3; // 심하게 기울어졌을때 즉시 알림 띄우기

    final static double dampingRate = 0.9;

    private int leftAngleStandard = 5; //왼쪽 기울기의 표준치를 나타냅니다.
    private int rightAngleStandard = 5; //오른쪽 기울기의 표준치를 나타냅니다.

    private int AlertDelay; //지속성을 확인합니다. 1초씩 늘어납니다.
    private int AlertValue; //한 가지 모드가 지속되는지를 확인합니다. 1은 왼쪽 경고, 2는 오른쪽 경고를 나타냅니다.

    Button testButton;
    Button testButton2;


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
        warning_= findViewById(R.id.warning_);

        testButton = findViewById(R.id.testButton);
        testButton2 = findViewById(R.id.testButton2);


        SharedPreferences getSwitchData = getSharedPreferences("switchFile", MODE_PRIVATE);

        switchVal1 = getSwitchData.getBoolean("switchVal1", true);

        switchVal2 = getSwitchData.getBoolean("switchVal2", true);

        switchVal3 = getSwitchData.getBoolean("switchVal3", true);

        if (!switchVal1) {
            switchVal2 = false;
            switchVal3 = false;
        }

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 레이아웃 및 액티비티를 전환하기 위한 코드
                Intent intent = new Intent(Main_menuActivity.this, Menu1_Activity.class);
                startActivity(intent);
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_menuActivity.this, Menu2_Activity.class);
                startActivity(intent);
            }
        });


        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_menuActivity.this, Menu3_Activity.class);
                startActivity(intent);
            }
        });


        menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_menuActivity.this, Menu4_Activity.class);
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
                Intent intent = new Intent(Main_menuActivity.this, Help2_Activity.class);
                startActivity(intent);
            }
        });

        help3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 레이아웃 및 액티비티를 전환하기 위한 코드
                Intent intent = new Intent(Main_menuActivity.this, Help3_Activity.class);
                startActivity(intent);
            }
        });

        help4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 레이아웃 및 액티비티를 전환하기 위한 코드
                Intent intent = new Intent(Main_menuActivity.this, Help4_Activity.class);
                startActivity(intent);
            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoDataBase();
            }
        });
        testButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = MemoDbHelper.getInstance(Main_menuActivity.this).getWritableDatabase();;
                db.execSQL(String.format("DELETE FROM memo"));

            }
        });

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); // 블루투스 어댑터를 디폴트 어댑터로 설정

        connectDevice(bluetoothName);
    }



    public void connectDevice(String deviceName) {
        // 페어링 된 디바이스들을 모두 탐색
        devices = bluetoothAdapter.getBondedDevices();
        for(BluetoothDevice tempDevice : devices) {
            // 사용자가 선택한 이름과 같은 디바이스로 설정하고 반복문 종료
            if (deviceName.equals(tempDevice.getName())) {
                bluetoothDevice = tempDevice;
                break;
            }
        }
        // UUID 생성
        UUID uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        // Rfcomm 채널을 통해 블루투스 디바이스와 통신하는 소켓 생성
        try {
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();
            // 데이터 송,수신 스트림을 얻어옵니다.
            outputStream = bluetoothSocket.getOutputStream();
            inputStream = bluetoothSocket.getInputStream();
            // 데이터 수신 함수 호출
            receiveData();
        } catch (IOException e) {
            warning_.setText("현재 자세교정자켓과 연결되지 않았습니다. 평소 나의 좌우 벨런스 외 기능을 사용할 수 없습니다.");

//           // 블루투스 페어링 실패시 기능에 접근하지 못하도록 설정합니다.
//
//            menu1.setEnabled(false);
//            menu2.setEnabled(false);
//            menu3.setEnabled(false);

            e.printStackTrace();
        }
    }

    public void receiveData() {

        final Handler handler = new Handler();
        // 데이터를 수신하기 위한 버퍼를 생성
        readBufferPosition = 0;
        readBuffer = new byte[1024];

        // 데이터를 수신하기 위한 쓰레드 생성
        workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!(Thread.currentThread().isInterrupted())) {
                    try {
                        // 데이터를 수신했는지 확인합니다.
                        int byteAvailable = inputStream.available();
                        // 데이터가 수신 된 경우
                        if(byteAvailable > 0) {
                            // 입력 스트림에서 바이트 단위로 읽어 옵니다.
                            byte[] bytes = new byte[byteAvailable];
                            inputStream.read(bytes);
                            // 입력 스트림 바이트를 한 바이트씩 읽어 옵니다.
                            for(int i = 0; i < byteAvailable; i++) {
                                byte tempByte = bytes[i];
                                // 개행문자를 기준으로 받음(한줄)
                                if(tempByte == '\n') {
                                    // readBuffer 배열을 encodedBytes로 복사
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    // 인코딩 된 바이트 배열을 문자열로 변환
                                    final String text = new String(encodedBytes, "UTF-8");

                                    readBufferPosition = 0;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            // 스위치를 작동시켰을 때만 쓸 수 있게 합니다.
                                            if (switchVal1){
                                                setGlobalSensorValues(text);

                                                // 완만화된 값들 업데이트
                                                setDampedValue();

                                                //센서 값을 바탕으로 유효한 측정 상태인지 아닌지 판별합니다.
                                                if (isUserPutOn()) {
                                                    int angleBalance = AngleBalance();

                                                    if (switchVal2) {
                                                        if (angleBalance == -3) { //자세가 왼쪽으로 기울어짐
                                                            if (getAlertValue() == 1) { //알람기조를 유지해갔는가
                                                                if (getAlertDelay() == 60) {
                                                                    createNotification("자세가 많이 흐트려졌습니다.", "왼쪽으로 많이 기울여졌습니다. 바로 앉으시길 권장드립니다.", 1);
                                                                } else {
                                                                    setAlertDelay(AlertDelay++);
                                                                }
                                                            } else {
                                                                setAlertValue(1);
                                                                setAlertDelay(0);
                                                            }
                                                        } else if (angleBalance == 3) { //자세가 오른쪽으로 기울어짐
                                                            if (getAlertValue() == 2) { //알람기조를 유지해갔는가
                                                                if (getAlertDelay() == 60) {
                                                                    createNotification("자세가 많이 흐트려졌습니다.", "오른쪽으로 많이 기울여졌습니다. 바로 앉으시길 권장드립니다.", 1);
                                                                } else {
                                                                    setAlertDelay(AlertDelay++);
                                                                }
                                                            } else {
                                                                setAlertValue(2);
                                                                setAlertDelay(0);
                                                            }
                                                        }
                                                    }

                                                    else if (switchVal3) {
                                                        if (angleBalance == -1 || angleBalance == -2) { //자세가 왼쪽으로 기울어짐
                                                            if (getAlertValue() == 1) { //알람기조를 유지해갔는가
                                                                if (getAlertDelay() == 300) {
                                                                    createNotification("자세가 지속적으로 흐트려졌습니다.", "왼쪽으로 기울여졌으니 바로 앉으시길 권장드립니다.", 1);
                                                                } else {
                                                                    setAlertDelay(AlertDelay++);
                                                                }
                                                            } else {
                                                                setAlertValue(1);
                                                                setAlertDelay(0);
                                                            }
                                                        } else if (angleBalance == 13 || angleBalance == 2) { //자세가 오른쪽으로 기울어짐
                                                            if (getAlertValue() == 2) { //알람기조를 유지해갔는가
                                                                if (getAlertDelay() == 300) {
                                                                    createNotification("자세가 지속적으로 흐트려졌습니다.", "오른쪽으로 기울여졌으니 바로 앉으시길 권장드립니다.", 1);
                                                                } else {
                                                                    setAlertDelay(AlertDelay++);
                                                                }
                                                            } else {
                                                                setAlertValue(2);
                                                                setAlertDelay(0);
                                                            }
                                                        } else if (angleBalance == 0) { //자세가 왼쪽으로 기울어짐
                                                            if (getAlertValue() == 0) { //알람기조를 유지해갔는가
                                                                if (getAlertDelay() == 0) {
                                                                    removeNotification(1);
                                                                    setAlertDelay(AlertDelay++);
                                                                }
                                                            } else {
                                                                setAlertValue(0);
                                                                setAlertDelay(0);
                                                            }
                                                        }
                                                    }

                                                    //특정 시각에 해당하면 데이터베이스에 값을 넘기도록 함
                                                    if (getCheckTime() < System.currentTimeMillis() - 1800000) { //30분 간격으로 데이터베이스에 저장
                                                        gotoDataBase();
                                                        setCheckTime(System.currentTimeMillis());
                                                    }
                                                }
                                            }
                                        }
                                    });
                                } // 개행 문자가 아닐 경우
                                else {
                                    readBuffer[readBufferPosition++] = tempByte;
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        // 1초마다 받아옴
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        workerThread.start();
    }

    private boolean isUserPutOn(){
        //기울기 센서를 이용하여 착용자가 옷을 입어 센서 값이 유효한지를 확인해봅니다.

        int Critical_point_plus = 30;
        int Critical_point_minus = -20;


        if(beforeLeftAngle_data == leftAngle && beforeRightAngle_data == rightAngle){
            if (beforeCount > 20){
                return false;
            }
            else{
                beforeCount++;
            }
        }
        else{
            beforeCount = 0;
        }

        beforeLeftAngle_data = leftAngle;
        beforeRightAngle_data = rightAngle;

        if(LA_damped < Critical_point_plus && LA_damped > Critical_point_minus && RA_damped < Critical_point_plus && RA_damped >Critical_point_minus) {
           //모든 센서 값이 정상적인 범위에 있을 때 true를 반환합니다.
            return true;
        }

        //그렇지 않으면 false를 반환합니다.
        return false;
    }

    //Todo
    // 좋은 Warning의 기준??
    // 1. 반대 센서에 비해 상대값
    // 2. 센서의 절대 기준값 이것이 정답이느니라
    // 입어보고 만들어봐야지
    private int AngleBalance(){
        int leftState = leftAngle - leftAngleStandard;
        int rightState = rightAngle - rightAngleStandard;

        int val1 = 8;
        int val2 = 5;
        int val3 = 2;
        int val4 = -2;
        int val5 = -5;
        int val6 = -8;

        //2번 조건에 해당하는 것

        //센서 값을 보고 알고리즘을 만들어내겠음
        //진짜 값을 안보면 힘들다
        return 0;
    }

    private void setGlobalSensorValues(String str) {
        leftPress = Integer.parseInt(str.substring(1, 4));
        rightPress = Integer.parseInt(str.substring(4, 7));
        leftAngle = Integer.parseInt(str.substring(7, 11));
        rightAngle = Integer.parseInt(str.substring(11, 15));
    }

    private void setDampedValue() {
        LP_damped = getDampedValue(LP_damped, leftPress);
        RP_damped = getDampedValue(RP_damped, rightPress);
        LA_damped = getDampedValue(LA_damped, leftAngle);
        RA_damped = getDampedValue(RA_damped, rightAngle);
    }

    private void gotoDataBase(){
        ContentValues contentValues = new ContentValues();
        // 여기에 데이터베이스에 넘길 값을 입력해줍니다.

        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_DATE, new java.text.SimpleDateFormat("yyyy년 MM월 dd일").format(new java.util.Date()));
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_TIME, new java.text.SimpleDateFormat("HH시 mm분").format(new java.util.Date()));
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_ANGLE, Integer.toString(AngleBalance()));
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_ANGLE, Double.toString(LP_damped));
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_ANGLE, Double.toString(RP_damped));

        SQLiteDatabase db = MemoDbHelper.getInstance(this).getWritableDatabase();
        db.insert(MemoContract.MemoEntry.TABLE_NAME,
                null,
                contentValues);
    }

    public int getLeftPress() { return leftPress; }
    public int getRightPress() { return rightPress; }
    public int getLeftAngle() { return leftAngle; }
    public int getRightAngle() { return rightAngle; }

    private long getCheckTime(){
        return checkTime;
    }

    private void setCheckTime(long i){
        checkTime = i;
    }

    private int getAlertDelay(){
        return AlertDelay;
    }

    private void setAlertDelay(int i){
        AlertDelay = i;
    }

    private int getAlertValue(){
        return AlertValue;
    }

    private void setAlertValue(int i){
        AlertValue = i;
    }



    private double getDampedValue(double previousValue, int newValue) {
        return previousValue * dampingRate + newValue * (1-dampingRate);
    }


    void sendData(String text) {
        // 문자열에 개행문자("\n")를 추가해줍니다.
        text += "\n";
        try{
            // 데이터 송신
            outputStream.write(text.getBytes());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }


    private void createNotification(String title, String text, int id) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);
        builder.setContentText(text);

        builder.setColor(Color.RED);
        // 사용자가 탭을 클릭하면 자동 제거
        builder.setAutoCancel(true);

        // 알림 표시
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

        // id값은
        // 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(id, builder.build());
    }

    private void removeNotification(int id) {

        // Notification 제거
        NotificationManagerCompat.from(this).cancel(id);
    }


}


