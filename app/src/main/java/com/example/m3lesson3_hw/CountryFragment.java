package com.example.m3lesson3_hw;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m3lesson3_hw.databinding.FragmentCountryBinding;
import com.example.m3lesson3_hw.databinding.FragmentLocationBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CountryFragment extends Fragment implements onItemClick{

    private FragmentCountryBinding binding;
    private LocationAdapter adapter;
    private List<Location> countryList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCountryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String continentName = getArguments() != null ? getArguments().getString("continentName") : "";

        // Инициализация данных для стран
        loadCountriesForContinent(continentName);

        // Инициализация адаптера
        adapter = new LocationAdapter(countryList, getContext(), this::onClick);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadCountriesForContinent(String continentName) {
        try (InputStreamReader reader = new InputStreamReader(getResources().openRawResource(R.raw.location_data))) {
            Gson gson = new Gson();
            Type continentListType = new TypeToken<Map<String, List<Continent>>>(){}.getType();
            Map<String, List<Continent>> data = gson.fromJson(reader, continentListType);

            // Извлекаем список континентов
            List<Continent> continents = data.get("continents");

            for (Continent continent : continents) {
                if (continent.getName().equals(continentName)) {
                    // Добавляем страны этого континента в список
                    for (Country country : continent.getCountries()) {
                        countryList.add(new Location(country.getName(), country.getImageUrl()));
                    }
                    break;
                }
            }

            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onClick(int position) {
        CityFragment cityFragment = new CityFragment();
        String countryName = countryList.get(position).getName();
        Bundle bundle = new Bundle();
        bundle.putString("countryName", countryName);
        cityFragment.setArguments(bundle);
        countryList.clear();

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, cityFragment)
                .addToBackStack(null)
                .commit();
    }
}
