package com.example.pengwang.rubypp.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pengwang.rubypp.R;
import com.example.pengwang.rubypp.dao.Record;

import java.util.ArrayList;

/**
 * Created by Peng on 12/15/2016.
 * The adapter and View holder for main recycler view.
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.RecordHolder> {
    //private static final String TAG = "MainRecyclerViewAdapter";
    private ArrayList<Record> recordArrayList;

    public MainRecyclerViewAdapter(ArrayList<Record> recordArrayList){
        this.recordArrayList=recordArrayList;
    }

    @Override
    /**Creating a new holder when there is no one can be used**/
    public MainRecyclerViewAdapter.RecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflateView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_row_item,parent,false);
        return new RecordHolder(inflateView);
    }

    @Override
    public void onBindViewHolder(MainRecyclerViewAdapter.RecordHolder holder, int position) {
        //Log.d(TAG, "-----------Adapter onBindViewHoler--------------");
        holder.bindData(recordArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return recordArrayList.size();
    }

    //View holder and onClick listenter
    public static class RecordHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final static String TAG = "recyclerview.viewholder";
        private TextView timeView;
        private ImageView peedView;
        private ImageView poopedView;
        private ImageView ateView;
        private TextView dateView;

        public RecordHolder(View itemView) {
            super(itemView);

            //Initial views in one row
            timeView=(TextView) itemView.findViewById(R.id.main_recycler_row_time);
            peedView=(ImageView)itemView.findViewById(R.id.main_recycler_row_peed);
            poopedView=(ImageView)itemView.findViewById(R.id.main_recycler_row_pooped);
            ateView=(ImageView)itemView.findViewById(R.id.main_recycler_row_ate);
            dateView=(TextView)itemView.findViewById(R.id.main_recycler_row_date);

            //Setting up the click listener
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "-------------------Recycler item was clicked--------------------------");
        }

        /**********************************************
         * Should do some recognizes for different situations such as it si a record or others
         * also may need to change listener
         * @param record the record that need to be binded
         */
        private void bindData(Record record) {
            timeView.setText(record.getTime());
            dateView.setText(record.getDate());
            //Judging whether show icons
            if (record.isPeed()) peedView.setVisibility(View.VISIBLE);
            else peedView.setVisibility(View.INVISIBLE);
            if(record.isPooped()) poopedView.setVisibility(View.VISIBLE);
            else poopedView.setVisibility(View.INVISIBLE);
            if(record.isAte()) ateView.setVisibility(View.VISIBLE);
            else ateView.setVisibility(View.INVISIBLE);
        }
    }
}
