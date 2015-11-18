package com.example.android.truecaller.calllogs;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.truecaller.R;
import com.example.android.truecaller.callerfragment.CallerInfo.CallerHistory;

public class CallLogsArrayAdapter extends ArrayAdapter<CallLogModel> {
    private LayoutInflater inflater;


    public CallLogsArrayAdapter(Context context, List<CallLogModel> callLogsList) {
        super(context, R.layout.list_item, R.id.nameTV, callLogsList);

        inflater = LayoutInflater.from(context);
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final CallLogModel callLogModel = this.getItem(position);

        TextView nameTV;
        TextView numberTV;
        TextView dateTV;

        TextView durationTV;


        convertView = inflater.inflate(R.layout.list_item, null);


        nameTV = (TextView) convertView.findViewById(R.id.nameTV);
        numberTV = (TextView) convertView.findViewById(R.id.numberTV);
        dateTV = (TextView) convertView.findViewById(R.id.dateTV);
        durationTV = (TextView) convertView.findViewById(R.id.durationTV);

        nameTV.setText("Name: " + callLogModel.getName());
        numberTV.setText("Number: " + callLogModel.getNumber());
        dateTV.setText("Date & Time: " + callLogModel.getDate());
        durationTV.setText("Duration: " + callLogModel.getDuration() + " secs");
        return convertView;
    }

}
