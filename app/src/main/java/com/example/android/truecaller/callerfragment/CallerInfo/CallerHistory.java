package com.example.android.truecaller.callerfragment.CallerInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.truecaller.MainActivity;
import com.example.android.truecaller.R;
import com.example.android.truecaller.calllogs.CallLogModel;
import com.example.android.truecaller.calllogs.CallLogsArrayAdapter;

/**
 * Created by Duke on 11/16/2015.
 */
public class CallerHistory extends Fragment {

    public CallerHistory() {

    }

    ListView listView;


    private ArrayAdapter<CallLogModel> listAdapter;

    Bundle extras;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.caller_history, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setSmoothScrollbarEnabled(true);




            listAdapter = new CallLogsArrayAdapter(getActivity(),
                    MainActivity.incomingCalls);

        listView.setAdapter(listAdapter);


        return rootView;
    }

    public void initElements() {
        listAdapter.notifyDataSetChanged();
    }
}
