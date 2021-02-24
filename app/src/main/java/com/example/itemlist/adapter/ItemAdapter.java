package com.example.itemlist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.itemlist.R;
import com.example.itemlist.metier.Item;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {

    private Context mContext;
    private int mRessource;

    public ItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Item> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mRessource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mRessource, parent, false);

        TextView txtId= (TextView) convertView.findViewById(R.id.txtId);
        txtId.setText(String.valueOf(getItem(position).getId()));

        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        txtName.setText(getItem(position).getName());

        TextView txtDescription = (TextView) convertView.findViewById(R.id.txtDescription);
        txtDescription.setText(getItem(position).getDescription());

        TextView txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);
        txtPrice.setText(getItem(position).getPrice() + " $");

        return convertView;
    }
}
