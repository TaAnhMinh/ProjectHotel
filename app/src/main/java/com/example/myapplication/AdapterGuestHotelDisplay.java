package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterGuestHotelDisplay extends ArrayAdapter<HotelProfile> {

    private Context mContext;
    int mResource;

    public AdapterGuestHotelDisplay(@NonNull Context context, int resource, @NonNull List<HotelProfile> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String hotelname = getItem(position).getName();
        String hoteladdress = getItem(position).getAddress();
        String hotelcity = getItem(position).getCity();
        String countryhotel = getItem(position).getCountry();
        String openHotel = getItem(position).getOpen();
        String closeHotel = getItem(position).getClosing();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView name, address, city, country, open, close;
        name = convertView.findViewById(R.id.guestHotelSearch);
        address = convertView.findViewById(R.id.addressShowGuest);
        city = convertView.findViewById(R.id.cityShowGuest);
        country = convertView.findViewById(R.id.countryShowGuest);
        open = convertView.findViewById(R.id.openHotelGuest);
        close = convertView.findViewById(R.id.closeHotelGuest);

        name.setText(hotelname);
        address.setText(hoteladdress);
        city.setText(hotelcity);
        country.setText(countryhotel);
        open.setText(openHotel);
        close.setText(closeHotel);

        return convertView;
    }
}
