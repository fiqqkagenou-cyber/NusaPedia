package com.example.nusapedia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nusapedia.R;
import com.example.nusapedia.adapter.EndemikAdapter;
import com.example.nusapedia.database.EndemikDatabase;
import com.example.nusapedia.database.EndemikEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TumbuhanFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<EndemikEntity> list = EndemikDatabase.getInstance(requireContext())
                    .endemikDao().getByTipe("Tumbuhan");

            requireActivity().runOnUiThread(() -> {
                EndemikAdapter adapter = new EndemikAdapter(list);
                recyclerView.setAdapter(adapter);
            });
        });

        return view;
    }
}