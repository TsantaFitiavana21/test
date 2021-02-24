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
import com.example.itemlist.metier.ItemList;

import java.util.ArrayList;

public class ItemListAdapter extends ArrayAdapter<ItemList> {

    private Context mContext;
    private int mRessource;

    public ItemListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ItemList> objects) {
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

        TextView txtItems = (TextView) convertView.findViewById(R.id.txtItems);
        int [] items = getItem(position).getItems();
        String items_string = "";
        for (int item : items){
            items_string = items_string + "\n" + item;
        }
        txtItems.setText(items_string);

        TextView txtTotal = (TextView) convertView.findViewById(R.id.txtTotal);
        txtTotal.setText(getItem(position).getTotal() + "");

        return convertView;
    }
}
