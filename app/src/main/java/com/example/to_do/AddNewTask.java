package com.example.to_do;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.to_do.ToDoModel.ToDoModel;
import com.example.to_do.utils.DBHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "AddNewTask";
    private EditText meditText;
    private Button mSaveBtn;
    private DBHelper dbHelper;
    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.new_task,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        meditText = view.findViewById(R.id.editTextNewTask);
        mSaveBtn = view.findViewById(R.id.saveTask);
        dbHelper = new DBHelper(getActivity());
        boolean isUpdated = false;
        Bundle bundle = getArguments();
        if (bundle != null){
            isUpdated = true;
            String task = bundle.getString("Task");
            meditText.setText(task);
            if (task.length()>0){
                mSaveBtn.setEnabled(false);
            }
        }
        meditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    mSaveBtn.setEnabled(false);
//                    mSaveBtn.setBackgroundColor(Color.green());
                }else {
                    mSaveBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        boolean finalIsUpdated = isUpdated;
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = meditText.getText().toString();
                if (finalIsUpdated) {
                    dbHelper.updateTask(bundle.getInt("ID"),txt,0);
                }else {
                    ToDoModel item = new ToDoModel();
                    item.setTask(txt);
                    item.setStatus(0);
                    dbHelper.insersertTask(item);
                }
                dismiss();

            }
        });

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof onDialogCloseListner){
            ((onDialogCloseListner)activity).onDialogClose(dialog);
        }
    }
}
