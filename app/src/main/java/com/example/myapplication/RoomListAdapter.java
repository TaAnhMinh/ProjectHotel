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

class RoomListAdapter extends ArrayAdapter<Room> {
    private Context mContext;
    int mResource;

    public RoomListAdapter(@NonNull Context context, int resource, @NonNull List<Room> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String roomNumber =  getItem(position).getRoomnumber();
        String size  = getItem(position).getRoomSize();
        String smoking = getItem(position).getSmoking();
        String max = getItem(position).getMax();
        String avail = getItem(position).getAvail();
        String current = getItem(position).getCurrent();
        String in = getItem(position).getCheckin();
        String out = getItem(position).getCheckout();
        String bedT = getItem(position).getBedT();
        String bedQ = getItem(position).getBedQ();
        String bath = getItem(position).getBathroom();
        String wifi = getItem(position).getWifi();
        String table = getItem(position).getTable();
        String dryer = getItem(position).getDryer();
        String towel = getItem(position).getTowel();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView roomNum = (TextView) convertView.findViewById(R.id.roomNumber);
        TextView available = (TextView) convertView.findViewById(R.id.trueOrfalse);
        TextView Roomsize = (TextView) convertView.findViewById(R.id.disRoomSize);
        TextView smokingg = (TextView) convertView.findViewById(R.id.smokingTorFShow);
        TextView maximum = (TextView) convertView.findViewById(R.id.maxppl);
        TextView currentppl = (TextView) convertView.findViewById(R.id.currentppl);
        TextView checkin = (TextView) convertView.findViewById(R.id.fromH);
        TextView checkout = (TextView) convertView.findViewById(R.id.toHH);

        roomNum.setText(roomNumber);
        available.setText(avail);
        Roomsize.setText(size);
        smokingg.setText(smoking);
        maximum.setText(max);
        currentppl.setText(current);
        checkin.setText(in);
        checkout.setText(out);

        return convertView;
    }
}

