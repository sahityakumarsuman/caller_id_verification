package com.example.android.truecaller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.truecaller.calllogs.CallLogModel;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    public static ArrayList<CallLogModel> incomingCalls;


    public static final int INCOMING_CALLS = 1;

    public static final String CALL_TYPE = "TYPE";

    Button seeDetails;
    TextView name, location, phoneNo;

    Context context;
    AVLoadingIndicatorView progressbar;
    String number;


    public static final String NEXTCALLER_API = "";

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {

            super.onCreate(savedInstanceState);


            setContentView(R.layout.content_main);
            context = getApplicationContext();

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;
            int heigt = dm.heightPixels;
            getWindow().setLayout((int) (width * .8), (int) (heigt * .4));


            incomingCalls = new ArrayList<CallLogModel>();


            seeDetails = (Button) findViewById(R.id.seeDeatils);
            seeDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intetn = new Intent(MainActivity.this, CallerDetails.class);
                    startActivity(intetn);
                    finish();

                }
            });

            Button close = (Button) findViewById(R.id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    android.os.Process.killProcess(android.os.Process.myPid());
                    finish();
                }
            });


            new ReadingInComingCalls().execute();

            progressbar = (AVLoadingIndicatorView) findViewById(R.id.avloadingIndicatorView);


            number = getIntent().getStringExtra(
                    TelephonyManager.EXTRA_INCOMING_NUMBER);


            TextView text = (TextView) findViewById(R.id.phoneNo);

            if (number.contains(" "))
                text.setText(" ");

            text.setText(" " + number + " ");

            searchForIncomingNo(number);

        } catch (Exception e) {
            Log.d("Exception", e.toString());
            e.printStackTrace();
        }

    }

    private void searchForIncomingNo(String number) {


        new SearchNo().execute();

    }

    private void showMessage(String button) {

        Toast.makeText(MainActivity.this, button, Toast.LENGTH_SHORT).show();
    }


    private class SearchNo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressbar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... params) {


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressbar.setVisibility(View.INVISIBLE);
        }
    }

    private class ReadingInComingCalls extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressbar = new AVLoadingIndicatorView(MainActivity.this);
            progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            //readCallLogs();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressbar.setVisibility(View.INVISIBLE);
        }
    }


    @SuppressLint("NewApi")
    private void readCallLogs() {

        incomingCalls.clear();


        if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Cursor callLogCursor = getContentResolver().query(
                android.provider.CallLog.Calls.CONTENT_URI, null, null, null,
                android.provider.CallLog.Calls.DEFAULT_SORT_ORDER);


        if (callLogCursor != null) {
            while (callLogCursor.moveToNext()) {
                String id = callLogCursor.getString(callLogCursor
                        .getColumnIndex(CallLog.Calls._ID));
                String name = callLogCursor.getString(callLogCursor
                        .getColumnIndex(CallLog.Calls.CACHED_NAME));
                String cacheNumber = callLogCursor.getString(callLogCursor
                        .getColumnIndex(CallLog.Calls.CACHED_NUMBER_LABEL));
                String number = callLogCursor.getString(callLogCursor
                        .getColumnIndex(CallLog.Calls.NUMBER));
                long dateTimeMillis = callLogCursor.getLong(callLogCursor
                        .getColumnIndex(CallLog.Calls.DATE));
                long durationMillis = callLogCursor.getLong(callLogCursor
                        .getColumnIndex(CallLog.Calls.DURATION));
                int callType = callLogCursor.getInt(callLogCursor
                        .getColumnIndex(CallLog.Calls.TYPE));

                String duration = getDuration(durationMillis * 1000);

                String dateString = getDateTime(dateTimeMillis);

                if (cacheNumber == null)
                    cacheNumber = number;
                if (name == null)
                    name = "No Name";

                CallLogModel callLogModel = new CallLogModel(name, cacheNumber,
                        duration, dateString);
                if (callType == CallLog.Calls.INCOMING_TYPE) {
                    incomingCalls.add(callLogModel);


                }
                callLogCursor.close();
            }


        }
    }


    private String getDuration(long milliseconds) {
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        if (hours < 1)
            return minutes + ":" + seconds;
        return hours + ":" + minutes + ":" + seconds;
    }

    private String getDateTime(long milliseconds) {
        Date date = new Date(milliseconds);
        //return DateFormat.getDateTimeInstance().format(new Date());

        return date.toLocaleString();
    }

}