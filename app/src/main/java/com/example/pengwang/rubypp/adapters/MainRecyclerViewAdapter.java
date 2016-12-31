package com.example.pengwang.rubypp.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pengwang.rubypp.Fragments.AddRecordDialogFragment;
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
        holder.bindData(recordArrayList.get(position),holder.itemView, position);
        holder.setRecordArryList(recordArrayList);
        holder.setAdapter(this);
    }

    @Override
    public int getItemCount() {
        return recordArrayList.size();
    }


    //View holder and onClick listenter
    public static class RecordHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final static String TAG = "recyclerview.viewholder";
        private static final String UPDATE_DIALOG_TAG = "UpdateDialogTag";
        private TextView timeView;
        private TextView indexView;
        private ImageView peedView;
        private ImageView poopedView;
        private ImageView ateView;
        private TextView dateView;
        private TextView nameView;
        private ImageView peedInsideView;
        private ImageView poopedInsideView;

        private ArrayList<Record> recordArrayList;
        private RecyclerView.Adapter adapter;

        public RecordHolder(View itemView) {
            super(itemView);

            //Initial views in one row
            timeView=(TextView) itemView.findViewById(R.id.main_recycler_row_time);
            peedView=(ImageView)itemView.findViewById(R.id.main_recycler_row_peed);
            poopedView=(ImageView)itemView.findViewById(R.id.main_recycler_row_pooped);
            ateView=(ImageView)itemView.findViewById(R.id.main_recycler_row_ate);
            dateView=(TextView)itemView.findViewById(R.id.main_recycler_row_date);
            indexView=(TextView)itemView.findViewById(R.id.main_recycler_row_index);
            nameView=(TextView)itemView.findViewById(R.id.main_recycler_row_name);
            peedInsideView=(ImageView)itemView.findViewById(R.id.main_recycler_row_peed_inside);
            poopedInsideView=(ImageView)itemView.findViewById(R.id.main_recycler_row_pooped_inside);

            //Setting up the click listener

//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            /*Do not need to do this judgement
            *Records and future items will use the same method to update
            if(view.getTag()==FUTURE_VIEW_OBJECT) addNewRecord(view);
            else Log.d(TAG, "-------------------Record View was clicked--------------------------");
            */
            updateRecord(view);
        }

        /********
         * delete current view and add a new view to the recycler view
         * Update the database simultaneity
         * @param view the current view that was clicked
         */
        private void updateRecord(View view) {
            int index=Integer.parseInt(((TextView)view.findViewById(R.id.main_recycler_row_index)).getText().toString());
            AddRecordDialogFragment dialogFragment=new AddRecordDialogFragment();
            dialogFragment.setHolder(this);
            dialogFragment.setRecord(recordArrayList.get(index));
            dialogFragment.setIndex(index);
            dialogFragment.show(((AppCompatActivity)view.getContext()).getSupportFragmentManager(), UPDATE_DIALOG_TAG);
//            Log.d(TAG,"----------------Adding a new record----------------");

        }

        public void callBackAddNewRecord(int index){
//            Log.d(TAG,"----------------callBackAddNewRecord----------------");
            adapter.notifyItemChanged(index);
        }

        /**********************************************
         * Should do some recognizes for different situations such as it si a record or others
         * also may need to change listener
         * @param record the record that need to be binded
         */
        private void bindData(Record record,View view,int position) {
            //Binding the data
            timeView.setText(record.getTime());
            dateView.setText(record.getDate());
            indexView.setText(String.valueOf(position));
            nameView.setText(record.getName());
            //Judging whether show icons
            if (record.isPeed()) peedView.setVisibility(View.VISIBLE);
            else peedView.setVisibility(View.INVISIBLE);
            if(record.isPooped()) poopedView.setVisibility(View.VISIBLE);
            else poopedView.setVisibility(View.INVISIBLE);
            if(record.isAte()) ateView.setVisibility(View.VISIBLE);
            else ateView.setVisibility(View.INVISIBLE);
            if (record.isPeedInside()) peedInsideView.setVisibility(View.VISIBLE);
            else peedInsideView.setVisibility(View.INVISIBLE);
            if(record.isPoopedInside()) poopedInsideView.setVisibility(View.VISIBLE);
            else poopedInsideView.setVisibility(View.INVISIBLE);

            //Set different background colors for record and future item.
            //and set the listener here

            if(record.isRecord()){
                itemView.setOnClickListener(this);
                view.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.record_background));
            } else{
                view.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.future_item_background));
                itemView.setOnClickListener(null);
            }
            /*
            if(record.isRecord()) timeView.setTextColor(Color.GREEN);
            else timeView.setTextColor(Color.GRAY);*/

            //Add a tag to future view in order to use in onClickListener
            //if(!record.isRecord()) view.setTag(FUTURE_VIEW_OBJECT);
        }

        public void setRecordArryList(ArrayList<Record> recordArrayList) {
            this.recordArrayList=recordArrayList;
        }

        public void setAdapter(RecyclerView.Adapter adapter) {
            this.adapter=adapter;
        }
    }
}
