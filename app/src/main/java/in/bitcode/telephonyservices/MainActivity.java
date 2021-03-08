package in.bitcode.telephonyservices;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    BroadcastReceiver brSent = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mt("Message sent");
        }
    };

    BroadcastReceiver brDelivered = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mt("Message Delievered");
        }
    };


    @SuppressLint({"MissingPermission", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager telephonyManager =
                (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        mt("Network type: " + telephonyManager.getNetworkType());
        mt("Phone type: " + telephonyManager.getPhoneType());
        //mt("sim sr no " + telephonyManager.getSimSerialNumber());
        mt("data state " + telephonyManager.getDataState());
        mt("net op " + telephonyManager.getNetworkOperator());
        //mt("sim sr no " + telephonyManager.getImei());
        mt("soft version: " + telephonyManager.getDeviceSoftwareVersion() );
        //mt("Cell location: " + telephonyManager.getCellLocation().toString());

        telephonyManager.listen(
                new MyPhoneStateListener(),
                PhoneStateListener.LISTEN_CALL_STATE | PhoneStateListener.LISTEN_USER_MOBILE_DATA_STATE | PhoneStateListener.LISTEN_DATA_ACTIVITY
        );


        /*
        registerReceiver(
                brSent,
                new IntentFilter("in.bitcode.message.SENT")
        );
        registerReceiver(
                brDelivered,
                new IntentFilter("in.bitcode.message.DELIVERED")
        );

        SmsManager smsManager = SmsManager.getDefault();

        PendingIntent sentIntent = PendingIntent.getBroadcast(
                this,
                1,
                new Intent("in.bitcode.message.SENT"),
                0
        );
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(
                this,
                1,
                new Intent("in.bitcode.message.DELIVERED"),
                0
        );

        smsManager.sendTextMessage(
                "8888499494",
                null,
                "Hello from BitCode",
                sentIntent,
                deliveredIntent
        );

        //smsManager.sendMultimediaMessage();
        */

    }


    class MyPhoneStateListener extends PhoneStateListener {


        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            super.onCallStateChanged(state, phoneNumber);
            mt(state + " " + phoneNumber);
        }

        @Override
        public void onDataActivity(int direction) {
            super.onDataActivity(direction);
            mt("data activity " + direction);
        }

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            //mt("signal strength " + signalStrength.getLevel());
        }
    }

    private void mt(String text) {
        Log.e("tag", text);
    }
}