package com.example.pengwang.rubypp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;


import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pengwang.rubypp.R;

import java.util.Map;

/**
 * Created by Peng on 1/21/2017.
 * Use to show the statistic data
 */
public class ShowStatisticDialog extends DialogFragment{

    public static final String TAG = ShowStatisticDialog.class.getName();
    private Map<String, Integer> statisticMap;



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.statistic_dialog_title);
        LinearLayout viewGroup=new LinearLayout(getContext());
        viewGroup.setOrientation(LinearLayout.VERTICAL);
        boolean isShowMedal=true;
        for (Map.Entry<String,Integer> mapEntry:statisticMap.entrySet()){
            View view=LinearLayout.inflate(getContext(),R.layout.statistic_dialog,null);
            TextView nameView=(TextView)view.findViewById(R.id.statistic_dialog_name);
            nameView.setText(mapEntry.getKey());

            TextView numberView=(TextView)view.findViewById(R.id.statistic_dialog_number);
            numberView.setText(String.valueOf(mapEntry.getValue()));

            if (isShowMedal){
                view.findViewById(R.id.statistic_dialog_medal).setVisibility(View.VISIBLE);
                isShowMedal=false;
            }

            viewGroup.addView(view);

        }
        builder.setView(viewGroup);

        return builder.create();
    }

    public void setStatisticMap(Map<String, Integer> statisticMap) {
        this.statisticMap = statisticMap;
    }
}
