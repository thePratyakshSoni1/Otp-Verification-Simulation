package com.example.otpverify

import android.app.Service
import android.content.*
import android.widget.Toast
import com.example.otpverify.ui.theme.otpCode
import java.security.Provider

class OtpNotificationReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val clipboardManager = context?.getSystemService(Service.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("OTP CODE", otpCode.code.toString() )
        clipboardManager.setPrimaryClip(clip)

        Toast.makeText(context,"Code copied ✔",Toast.LENGTH_SHORT).show()

    }

}