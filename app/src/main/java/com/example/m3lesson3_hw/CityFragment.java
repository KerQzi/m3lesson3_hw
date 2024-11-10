package com.example.m3lesson3_hw;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.m3lesson3_hw.databinding.FragmentCountryBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CityFragment extends Fragment implements onItemClick{
    private FragmentCountryBinding binding;
    private LocationAdapter adapter;
    private List<Location> cityList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCountryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String countryName = getArguments() != null ? getArguments().getString("countryName") : "";

        loadCitiesForCountry(countryName);

        adapter = new LocationAdapter(cityList, getContext(), this::onClick);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadCitiesForCountry(String countryName) {
        try (InputStreamReader reader = new InputStreamReader(getResources().openRawResource(R.raw.location_data))) {
            Gson gson = new Gson();

            // Десериализуем JSON в список континентов
            Type continentListType = new TypeToken<Map<String, List<Continent>>>(){}.getType();
            Map<String, List<Continent>> data = gson.fromJson(reader, continentListType);

            // Извлекаем список континентов
            List<Continent> continents = data.get("continents");

            for (Continent continent : continents) {
                for (Country country : continent.getCountries()) {
                    // Ищем нужную страну по названию
                    if (country.getName().equals(countryName)) {
                        // Добавляем города этой страны в список
                        for (City city : country.getCities()) {
                            cityList.add(new Location(city.getName(), city.getImageUrl()));
                        }
                        break; // Прерываем, как только находим нужную страну
                    }
                }
            }

            // Обновляем адаптер
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }







    @Override
    public void onClick(int position) {
        CityDetailFragment cityDetailFragment = new CityDetailFragment();
        String cityName = cityList.get(position).getName();
        String cityUrl = cityList.get(position).getImageUrl();
        Bundle bundle = new Bundle();
        bundle.putString("cityName", cityName);
        bundle.putString("cityUrl", cityUrl);
        cityDetailFragment.setArguments(bundle);
        cityList.clear();

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, cityDetailFragment)
                .addToBackStack(null)
                .commit();
    }
}