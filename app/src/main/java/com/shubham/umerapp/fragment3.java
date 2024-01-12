package com.shubham.umerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

public class fragment3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        List<String> dataList = Arrays.asList("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 6", "Item 6", "Item 6", "Item 6",
                "Item 6", "Item 6", "Item 6", "Item 6", "Item 6", "Item 6", "Item 6", "Item 6", "Item 6", "Item 6", "Item 6", "Item 6", "Item 6",
                "Item 6", "Item 6", "Item 6", "Item 6", "Item 6", "Item 6");

        GridAdapter gridAdapter = new GridAdapter(getContext(), dataList);
        recyclerView.setAdapter(gridAdapter);

        return view;
    }


}