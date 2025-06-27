package com.example.to_do;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);  // match the ID
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        List<String> items = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            items.add("Item " + i);
//        }
//
//        MyAdapter adapter = new MyAdapter(items);  // your custom adapter class
//        recyclerView.setAdapter(adapter);


    }
}