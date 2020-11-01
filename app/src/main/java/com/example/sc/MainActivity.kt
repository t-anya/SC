package com.example.sc

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var number = ""
    var msg = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSendSMS.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View){
                sendSMS()
            }
        })

    }

    private fun sendSMS() {
        number = etvNumber.text.toString()
        msg = etvMessage.text.toString()

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.SEND_SMS
                )
            ) {
            } else {
                val smsPermission = Manifest.permission.SEND_SMS
                val permission = arrayOf(smsPermission)
                ActivityCompat.requestPermissions(this, permission, 0);
            }
        }
    }

    @Override
    fun onRequestPermissionsResult(
        requestCode: Int,
        permission: Array<String>,
        grantResults: Array<Int>
    ) {
        when (requestCode) {
            0 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    val smsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(number, null, msg, null, null);
                    Toast.makeText(
                        applicationContext, "SMS sent.",
                        Toast.LENGTH_LONG
                    ).show();
                } else {
                    Toast.makeText(
                        applicationContext,
                        "SMS faild, please try again.", Toast.LENGTH_LONG
                    ).show();
                    return;
                }
            }
        }
    }
}