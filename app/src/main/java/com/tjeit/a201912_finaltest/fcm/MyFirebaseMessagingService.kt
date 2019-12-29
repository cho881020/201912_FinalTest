package com.tjeit.a201912_finaltest.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

//    푸시알림을 받으면 실행되는 함수
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

//     Notification 타입의 푸시에 사용되는 자료
//        remoteMessage?.notification

//    데이터메세지에 대한 자료
//        remoteMessage?.data

    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)

        Log.d("발급받은토큰", token)

    }


}
