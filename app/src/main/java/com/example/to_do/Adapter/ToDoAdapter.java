package com.example.to_do.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do.AddNewTask;
import com.example.to_do.MainActivity;
import com.example.to_do.R;
import com.example.to_do.ToDoModel.ToDoModel;
import com.example.to_do.utils.DBHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {
    private List<ToDoModel> mlist ;
    private MainActivity activity;
    private DBHelper dbHelper;
    public ToDoAdapter(DBHelper dbHelper,MainActivity activity){
        this.activity = activity;
        this.dbHelper = dbHelper;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ToDoModel item = mlist.get(position);
        holder.checkBox.setText((item.getTask()));
        holder.checkBox.setChecked(inttobool(item.getStatus()) );
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    dbHelper.updateStatus(item);
                }else {
                    dbHelper.updateStatus(item);
                }
            }
        });

    }
    public Context getContext(){
        return activity;
    }
    public void setTask(List<ToDoModel> mlist){
        this.mlist = mlist;
        notifyDataSetChanged();
    }
    public void deleteTask(int position){
        ToDoModel item = mlist.get(position);
        dbHelper.deleteTask(item);
        mlist.remove(position);
        notifyItemRemoved(position);
    }
    public void editTask(int position){
        ToDoModel item = mlist.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("ID",item.getId());
        bundle.putString("Task",item.getTask());
        AddNewTask addNewTask = new AddNewTask();
        addNewTask.setArguments(bundle);
        addNewTask.show(activity.getSupportFragmentManager(), addNewTask.getTag());
    }
    public boolean inttobool(int n){
       return n != 0;
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.chkBox);
        }
    }
}
