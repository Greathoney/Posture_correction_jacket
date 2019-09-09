package com.example.posture_correction_jacket;

import static com.example.posture_correction_jacket.Main_menuActivity.debouncing_target;
import static com.example.posture_correction_jacket.Main_menuActivity.n_BagMode;
import static com.example.posture_correction_jacket.Main_menuActivity.n_BagMode_debouncing;
import static com.example.posture_correction_jacket.Main_menuActivity.isUserPutOn;
import static com.example.posture_correction_jacket.Main_menuActivity.isUserRunning;

import static com.example.posture_correction_jacket.Main_menuActivity.leftPress;
import static com.example.posture_correction_jacket.Main_menuActivity.rightPress;
import static com.example.posture_correction_jacket.Main_menuActivity.leftAngle;
import static com.example.posture_correction_jacket.Main_menuActivity.rightAngle;
import static com.example.posture_correction_jacket.Main_menuActivity.targetBagMode;

class Check {

    final static int pressure_threshold = 200; //압력 임계치
    final static int debouncing_limit = 50; //오랜 시간이 지나 값이 고정될 수 있도록 설정합니다.


    static void CheckBagState() {

        if (leftPress > pressure_threshold) { //측정된 가방 모드 0 가방 들지 않음 1 왼쪽 한끈 가방 듬 2 오른쪽 한끈 가방 듬 3 양쪽 책가방 듬
            targetBagMode++;
        }
        if (rightPress > pressure_threshold) {
            targetBagMode += 2;
        }

        if (n_BagMode != targetBagMode) { //현재 가방 모드가 측정된 가방 모드와 다르다면
            if (n_BagMode_debouncing > debouncing_limit) { //한계치가 넘어간다면
                n_BagMode = debouncing_target;
                n_BagMode_debouncing = 0;
                //가방 모드를 바꿔라~~
            } else if (debouncing_target == targetBagMode) {
                n_BagMode_debouncing++;
                //계속 그 모드를 바뀐채로 유지하는지 보아라~~
            } else {
                debouncing_target = targetBagMode;
                n_BagMode_debouncing = 0;
                //다른 값으로 바뀌고 카운트를 다시 시작하라~~
            }
        }
    }

    static void CheckIsPutOnState(){

        //TODO menu엑티비티에 리스트를 만들어서 시간-데이터 식으로 만들어서 상태를 비교하는 식으로 만들어야 하나...
        // 아니면 최근 데이터 받은거 10개를 기준으로 상태 판별하는 식 만들어도 좋을듯

    }

}
