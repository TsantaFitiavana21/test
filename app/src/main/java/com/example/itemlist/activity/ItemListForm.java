package com.example.itemlist.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.itemlist.IP;
import com.example.itemlist.R;
import com.example.itemlist.api.ItemAPI;
import com.example.itemlist.metier.Item;
import com.example.itemlist.metier.ItemList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemListForm extends AppCompatActivity {

    String url = IP.getUrl();
    TextView titreItem, txtID;
    ImageView close;
    EditText id, name, items, total;
    Button btnAjouter, btnModifier, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_form);
    }
}