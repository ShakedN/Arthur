package com.example.arthur;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<dataMoudle> dataset;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CustomeAddapter addapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerViewResult);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        dataset = new ArrayList<>();


        for (int i = 0; i < myData.nameArray.length; i++) {
            dataset.add(new dataMoudle(
                    myData.nameArray[i],
                    myData.description[i],
                    myData.drawableArray[i],
                    myData.id_[i]
            ));
        }

        addapter = new CustomeAddapter(dataset);
        recyclerView.setAdapter(addapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                addapter.filter(query.trim());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                addapter.filter(newText.trim());
                return true;
            }
        });
    }
}