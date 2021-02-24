package com.example.itemlist.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itemlist.IP;
import com.example.itemlist.MainActivity;
import com.example.itemlist.R;
import com.example.itemlist.adapter.ItemAdapter;
import com.example.itemlist.adapter.ItemListAdapter;
import com.example.itemlist.api.ItemAPI;
import com.example.itemlist.metier.Item;
import com.example.itemlist.metier.ItemList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemListActivity extends AppCompatActivity {

    ImageView btnAdd, btnRetour;
    String url = IP.getUrl();
    Retrofit retrofit;
    ItemAPI itemAPI;
    String token ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        token = getIntent().getStringExtra("token");

        btnAdd = findViewById(R.id.btnAdd_list);
        btnRetour = findViewById(R.id.btnRetour);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ItemListActivity.this, ItemListForm.class);
                startActivity(intent);
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ItemListActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        itemAPI = retrofit.create(ItemAPI.class);

        // TOKEN
        token = "Token " + token;
        showItemList(token);
    }

    private void showItemList(String auth) {

        Call<ArrayList<ItemList>> call = itemAPI.getListItems(auth);

        call.enqueue(new Callback<ArrayList<ItemList>>() {
            @Override
            public void onResponse(Call<ArrayList<ItemList>> call, Response<ArrayList<ItemList>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ItemListActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
                else{
                    assert response.body() != null;
                    ArrayList<ItemList> items = response.body();

                    ListView listViewCompte = (ListView) findViewById(R.id.listeViewItemList);

                    final ItemListAdapter adapter = new ItemListAdapter(ItemListActivity.this, R.layout.item_list, items);
                    listViewCompte.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<ItemList>> call, Throwable t) {
                Toast.makeText(ItemListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}