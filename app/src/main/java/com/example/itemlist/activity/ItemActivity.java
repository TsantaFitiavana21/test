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
import com.example.itemlist.api.ItemAPI;
import com.example.itemlist.metier.Item;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemActivity extends AppCompatActivity {

    ImageView btnAdd, btnRetour;
    String url = IP.getUrl();
    Retrofit retrofit;
    ItemAPI itemAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        btnAdd = findViewById(R.id.btnAdd);
        btnRetour = findViewById(R.id.btnRetour);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ItemActivity.this, ItemForm.class);
                startActivity(intent);
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ItemActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        itemAPI = retrofit.create(ItemAPI.class);

        showItem();
    }

    private void showItem() {
        Call<ArrayList<Item>> call = itemAPI.listeItems();

        call.enqueue(new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ItemActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
                else{
                    assert response.body() != null;
                    ArrayList<Item> items = response.body();

                    ListView listViewCompte = (ListView) findViewById(R.id.listeViewItem);

                    final ItemAdapter adapter = new ItemAdapter(ItemActivity.this, R.layout.item, items);
                    listViewCompte.setAdapter(adapter);

                    // Menu modifier quand on clique sur l'item
                    listViewCompte.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            TextView t = view.findViewById(R.id.txtId);
                            Intent intent=new Intent(ItemActivity.this, ItemForm.class);
                            intent.putExtra("id",t.getText().toString());
                            startActivity(intent);
                        }
                    });

                    // Menu supprimer on long press item
                    listViewCompte.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            final Item e = adapter.getItem(position);

                            new AlertDialog.Builder(ItemActivity.this)
                                    .setTitle("Suppression")
                                    .setMessage("Would you like to delete this item ?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            // Action API
                                            Call<Item> call = itemAPI.deleteItem(e.getId());

                                            call.enqueue(new Callback<Item>() {
                                                @Override
                                                public void onResponse(Call<Item> call, Response<Item> response) {
                                                    if (!response.isSuccessful()) {
                                                        Toast.makeText(ItemActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        // Supprimer l'élément de la liste
                                                        adapter.remove(e);
                                                        adapter.notifyDataSetChanged();
                                                        Toast.makeText(ItemActivity.this, "Item deleted !!", Toast.LENGTH_LONG).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Item> call, Throwable t) {
                                                    Toast.makeText(ItemActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, null).show();
                            return true;
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {
                Toast.makeText(ItemActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}