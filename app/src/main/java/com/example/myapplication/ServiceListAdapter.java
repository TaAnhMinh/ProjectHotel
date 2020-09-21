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

class ServiceListAdapter extends ArrayAdapter<Service> {
    private Context mContext;
    int mResource;

    public ServiceListAdapter(@NonNull Context context, int resource, @NonNull List<Service> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String serviceName = getItem(position).getServiceName();
        String description = getItem(position).getDescription();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView sname = (TextView) convertView.findViewById(R.id.textView1);
        TextView des = (TextView) convertView.findViewById(R.id.textView2);

        sname.setText(serviceName);
        des.setText(description);

        return convertView;

    }
}
