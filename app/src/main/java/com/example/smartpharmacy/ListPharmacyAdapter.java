package com.example.smartpharmacy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListPharmacyAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Pharmacy> objects;

    ListPharmacyAdapter(Context context, ArrayList<Pharmacy> pharmacies){
        ctx = context;
        objects = pharmacies;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = lInflater.inflate(R.layout.list_item, parent, false);
        }

        Pharmacy p = getPharmacy(position);

        ((TextView) view.findViewById(R.id.namePharmacy)).setText(p.namePharmacy);
        ((TextView) view.findViewById(R.id.address)).setText(p.address);
        ((TextView) view.findViewById(R.id.time_work)).setText(p.time_work);
        ((TextView) view.findViewById(R.id.medicament)).setText(p.medicament);
        ((TextView) view.findViewById(R.id.price)).setText(p.price);

        return view;
    }

    Pharmacy getPharmacy(int position){
        return ((Pharmacy) getItem(position));
    }
}
