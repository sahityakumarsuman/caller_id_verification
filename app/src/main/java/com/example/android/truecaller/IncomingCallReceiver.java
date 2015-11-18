package com.example.android.truecaller;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Duke on 11/15/2015.
 */
public class IncomingCallReceiver extends BroadcastReceiver {


    Intent _intent;
    Context _context;

    @SuppressLint("LongLogTag")
    @Override
    public void onReceive(Context context, Intent intent) {


        _intent = intent;
        _context = context;

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int events = PhoneStateListener.LISTEN_CALL_STATE;
        tm.listen(phoneStateListener, events);


        String phoneNo = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Log.d("IncmingRecivr: onRecve: ", state);
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)
                || state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {

            Log.d("Ringing", "Phone is ringing");
            Intent i = new Intent(context, MainActivity.class);
            i.putExtras(intent);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            //Wait.oneSec();

                context.startActivity(i);

        }

    }


    private final PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {


            String callState = "UNKNOWN";
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    callState = "IDLE";
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    // -- check international call or not.
                    if (incomingNumber.startsWith("00")) {
                        Toast.makeText(_context, "International Call- " + incomingNumber, Toast.LENGTH_LONG).show();
                        callState = "International - Ringing (" + incomingNumber + ")";
                    } else {
                        Toast.makeText(_context, "Local Call - " + incomingNumber, Toast.LENGTH_LONG).show();
                        callState = "Local - Ringing (" + incomingNumber + ")";
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    String dialingNumber = _intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                    if (dialingNumber.startsWith("00")) {
                        Toast.makeText(_context, "International - " + dialingNumber, Toast.LENGTH_LONG).show();

                        callState = "International - Dialing (" + dialingNumber + ")";
                    } else {
                        Toast.makeText(_context, "Local Call - " + dialingNumber, Toast.LENGTH_LONG).show();

                        callState = "Local - Dialing (" + dialingNumber + ")";
                    }
                    break;
            }
            Log.i(">>>Broadcast", "onCallStateChanged " + callState);
            super.onCallStateChanged(state, incomingNumber);
        }
    };


    public boolean contactExists(Context context, String number) {

        Uri lookupUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cur = context.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
        try {
            if (cur.moveToFirst()) {
                return true;
            }
        } finally {
            if (cur != null)
                cur.close();
        }
        return false;
    }

}
