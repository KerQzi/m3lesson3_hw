package com.example.m3lesson3_hw;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m3lesson3_hw.Continent;
import com.example.m3lesson3_hw.CountryFragment;
import com.example.m3lesson3_hw.LocationAdapter;
import com.example.m3lesson3_hw.R;
import com.example.m3lesson3_hw.databinding.FragmentLocationBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ContinentFragment extends Fragment implements onItemClick{
    FragmentLocationBinding binding;

    private RecyclerView recyclerView;
    private LocationAdapter adapter;
    private List<Location> continentList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLocationBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadContinents();

        adapter = new LocationAdapter(continentList, getContext(), this::onClick);

        if (binding.recyclerView != null) {
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.recyclerView.setAdapter(adapter);
        }
    }

    private void loadContinents() {
        Gson gson = new Gson();

        // Используем try-with-resources для автоматического закрытия потока
        try (InputStreamReader reader = new InputStreamReader(getResources().openRawResource(R.raw.location_data))) {
            Type continentListType = new TypeToken<Map<String, List<Continent>>>(){}.getType();
            Map<String, List<Continent>> data = gson.fromJson(reader, continentListType);

            // Извлекаем список континентов
            List<Continent> continents = data.get("continents");

            // Добавляем данные в адаптер
            if (continents != null) {
                continentList.addAll(continents);
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace(); // Логирование ошибки для отладки
        }
    }



    @Override
    public void onClick(int position) {
        CountryFragment countryFragment = new CountryFragment();
        String continentName = continentList.get(position).getName();
        Bundle bundle = new Bundle();
        bundle.putString("continentName", continentName);
        countryFragment.setArguments(bundle);
        continentList.clear();

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, countryFragment)
                .addToBackStack(null)
                .commit();
    }
}
