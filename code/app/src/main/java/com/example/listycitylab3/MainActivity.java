package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private CityArrayAdapter cityAdapter;
    private ListView cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {"Edmonton", "Vancouver", "Toronto"};
        String[] provinces = {"AB", "BC", "ON"};

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // Add new city
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            AddCityFragment f = new AddCityFragment(); // no args => add mode
            f.show(getSupportFragmentManager(), "AddCity");
        });

        // Edit on tap
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City selected = dataList.get(position);

            Bundle b = new Bundle();
            b.putInt(AddCityFragment.ARG_POSITION, position);
            b.putString(AddCityFragment.ARG_NAME, selected.getName());
            b.putString(AddCityFragment.ARG_PROVINCE, selected.getProvince());

            AddCityFragment f = new AddCityFragment();
            f.setArguments(b);
            f.show(getSupportFragmentManager(), "EditCity");
        });
    }

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCity(int position, City updatedCity) {
        if (position >= 0 && position < dataList.size()) {
            dataList.set(position, updatedCity);
            cityAdapter.notifyDataSetChanged();
        }
    }
}
