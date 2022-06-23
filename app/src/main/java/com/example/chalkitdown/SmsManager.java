package com.example.chalkitdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


public class SmsManager extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_CODE_SEND_SMS = 1;
    private static final String LOG_TAG = "AndroidSmsChalkItDown";
    private int sendSmsPermission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_manager);

    }

    public static void smsSenderChalkItDown(String phoneNumber, String secretCode)
    {
        SmsManager smsManager = new SmsManager();
        if (smsManager.sendSmsPermission == -1) {
            smsManager.requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 8000);
            return;
        }else{
            smsManager.sendSMS_by_smsManager(phoneNumber, secretCode);
        }
    }


    private void askPermissionAndSendSMS(String phoneNumber, String secretCode) {

        if (android.os.Build.VERSION.SDK_INT >=  android.os.Build.VERSION_CODES.M) {

            sendSmsPermission = checkSelfPermission(Manifest.permission.SEND_SMS);

            //if (sendSmsPermission == -1) {
            //    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},8000);
            //    return;
            //}else{
            //    this.sendSMS_by_smsManager(phoneNumber, secretCode);
            //}
        }

    }

    private void sendSMS_by_smsManager(String phoneNumber, String secretCode)  {

        try {
            // Get the default instance of the SmsManager
            Context context = null;
            SmsManager smsManager = context.getSystemService(SmsManager.class);
            // Send Message
            smsManager.sendSMS_by_smsManager(phoneNumber, secretCode);

            Log.i( LOG_TAG,"Your sms has successfully sent!");
            Toast.makeText(getApplicationContext(),"Your sms has successfully sent!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.e( LOG_TAG,"Your sms has failed...", ex);
            Toast.makeText(getApplicationContext(),"Your sms has failed... " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

}