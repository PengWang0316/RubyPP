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
    private static final int VIEW_TAG_KEY = 1;
    private static final String FUTURE_VIEW_OBJECT = "futureView";
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
        holder.bindData(recordArrayList.get(position),holder.itemView);
    }

    @Override
    public int getItemCount() {
        return recordArrayList.size();
    }

    //View holder and onClick listenter
    public static class RecordHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final static String TAG = "recyclerview.viewholder";
        private TextView timeView;
        private TextView spouseTimeView;
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
            spouseTimeView=(TextView)itemView.findViewById(R.id.main_recycler_row_spouse_time);

            //Setting up the click listener
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view.getTag()==FUTURE_VIEW_OBJECT) addNewReord(view);
            else Log.d(TAG, "-------------------Record View was clicked--------------------------");
        }

        /********
         * delete current view and add a new view to the recycler view
         * Update the database simultaneity
         * @param view the current view that was clicked
         */
        private void addNewReord(View view) {
            String spouseTime=((TextView)view.findViewById(R.id.main_recycler_row_spouse_time)).getText().toString();
            Log.d(TAG,"----------------Spouse time: "+spouseTime+" ----------------");
        }

        /**********************************************
         * Should do some recognizes for different situations such as it si a record or others
         * also may need to change listener
         * @param record the record that need to be binded
         */
        private void bindData(Record record,View view) {
            //Binding the data
            timeView.setText(record.getTime());
            dateView.setText(record.getDate());
            spouseTimeView.setText(record.getSpouseTime());
            //Judging whether show icons
            if (record.isPeed()) peedView.setVisibility(View.VISIBLE);
            else peedView.setVisibility(View.INVISIBLE);
            if(record.isPooped()) poopedView.setVisibility(View.VISIBLE);
            else poopedView.setVisibility(View.INVISIBLE);
            if(record.isAte()) ateView.setVisibility(View.VISIBLE);
            else ateView.setVisibility(View.INVISIBLE);

            //Add a tag to future view in order to use in onClickListener
            if(!record.isRecord()) view.setTag(FUTURE_VIEW_OBJECT);
        }
    }
}
