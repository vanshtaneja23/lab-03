package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    // ARG KEYS (no Serializable!)
    public static final String ARG_POSITION = "arg_position";
    public static final String ARG_NAME     = "arg_name";
    public static final String ARG_PROVINCE = "arg_province";

    interface AddCityDialogListener {
        void addCity(City city);
        void updateCity(int position, City updatedCity);
    }

    private AddCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // EDIT mode if we have a valid position in args
        Bundle args = getArguments();
        final boolean isEdit = args != null && args.containsKey(ARG_POSITION);
        final int position = isEdit ? args.getInt(ARG_POSITION, -1) : -1;

        if (isEdit) {
            editCityName.setText(args.getString(ARG_NAME, ""));
            editProvinceName.setText(args.getString(ARG_PROVINCE, ""));
        }

        return new AlertDialog.Builder(requireContext())
                .setView(view)
                .setTitle(isEdit ? "Edit city" : "Add a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(isEdit ? "Save" : "Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString().trim();
                    String provinceName = editProvinceName.getText().toString().trim();
                    City c = new City(cityName, provinceName);

                    if (isEdit && position >= 0) {
                        listener.updateCity(position, c);
                    } else {
                        listener.addCity(c);
                    }
                })
                .create();
    }
}
