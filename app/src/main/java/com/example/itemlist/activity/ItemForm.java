package com.example.itemlist.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itemlist.IP;
import com.example.itemlist.R;
import com.example.itemlist.api.ItemAPI;
import com.example.itemlist.metier.Item;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemForm extends AppCompatActivity {

    String url = IP.getUrl();
    TextView titreItem, txtID;
    ImageView close;
    EditText id, name, description, price;
    Button btnAjouter, btnModifier, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_form);

        titreItem = findViewById(R.id.titreItem);
        txtID = findViewById(R.id.label_id);
        close = findViewById(R.id.close);

        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);

        btnAjouter = findViewById(R.id.btnAjouter);
        btnModifier = findViewById(R.id.btnModifier);
        btnCancel = findViewById(R.id.btnCancel);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ItemAPI itemAPI = retrofit.create(ItemAPI.class);

        final String _id = getIntent().getStringExtra("id");

        if (_id != null) {
            titreItem.setText("Update Item");
            showOne(_id, itemAPI);

            btnAjouter.setVisibility(View.GONE);
            btnModifier.setVisibility(View.VISIBLE);
        }
        else {
            titreItem.setText("Add Item");

            id.setVisibility(View.GONE);
            txtID.setVisibility(View.GONE);
            btnAjouter.setVisibility(View.VISIBLE);
            btnModifier.setVisibility(View.GONE);
        }

        // Bouton Close
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemForm.this, ItemActivity.class));
                finish();
            }
        });

        // Bouton Annuler
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemForm.this, ItemActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Bouton ajout
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _name = name.getText().toString();
                String _description = description.getText().toString();
                String _price = price.getText().toString();

                if ( _name.isEmpty() || _description.isEmpty() || _price.isEmpty()){
                    Toast.makeText(ItemForm.this, "Please fill all fields !!", Toast.LENGTH_LONG).show();
                }
                else{
                    Item item = new Item( _name, _description, Float.parseFloat(_price));
                    Call<Item> call = itemAPI.createItem(item);

                    call.enqueue(new Callback<Item>() {
                        @Override
                        public void onResponse(Call<Item> call, Response<Item> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(ItemForm.this, response.message(), Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent=new Intent(ItemForm.this, ItemActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Item> call, Throwable t) {
                            Toast.makeText(ItemForm.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        // bouton modifier
        btnModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String __id = id.getText().toString();
                String _name = name.getText().toString();
                String _description = description.getText().toString();
                String _price = price.getText().toString();

                if (__id.isEmpty() || _name.isEmpty() || _description.isEmpty() || _price.isEmpty()){
                    Toast.makeText(ItemForm.this, "Please fill all fields !!", Toast.LENGTH_LONG).show();
                }

                else{
                    final Item item = new Item(Integer.parseInt(__id), _name, _description, Float.parseFloat(_price));

                    Call<Item> call = itemAPI.updateItem(Integer.parseInt(__id), item);

                    call.enqueue(new Callback<Item>() {
                        @Override
                        public void onResponse(Call<Item> call, Response<Item> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(ItemForm.this, response.message(), Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(ItemForm.this, ItemActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Item> call, Throwable t) {
                            Toast.makeText(ItemForm.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void showOne(String _id, ItemAPI itemAPI) {

        Call<Item> call = itemAPI.getItem(Integer.parseInt(_id));

        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                else {
                    Item i = response.body();

                    int __id = i.getId();
                    String _name = i.getName();
                    String _description = i.getDescription();
                    float _price = i.getPrice();

                    id.setText(String.valueOf(__id));
                    name.setText(_name);
                    description.setText(_description);
                    price.setText(String.valueOf(_price));
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
            }
        });

    }
}