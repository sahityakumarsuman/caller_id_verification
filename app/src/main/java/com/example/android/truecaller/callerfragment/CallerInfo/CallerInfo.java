package com.example.android.truecaller.callerfragment.CallerInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.truecaller.R;

/**
 * Created by Duke on 11/16/2015.
 */
public class CallerInfo extends Fragment {


    public CallerInfo() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.caller_info, container, false);

        return rootView;
    }
}
