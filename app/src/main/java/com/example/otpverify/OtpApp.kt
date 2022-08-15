package com.example.otpverify

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class OtpApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val channel = NotificationChannel(
                OtpCodesNotificationService.OTP_CODES_CHANNEL_ID,
                "OTP codes",
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.description = "Used to receieve simulated OTP codes"
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }

    }

}