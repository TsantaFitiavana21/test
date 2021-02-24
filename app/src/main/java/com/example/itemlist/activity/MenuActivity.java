package com.example.itemlist.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.itemlist.R;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    public CardView card1, card2;

    //token
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        card1 = (CardView) findViewById(R.id.Card_item);
        card2 = (CardView) findViewById(R.id.Card_itemList);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);

        token = getIntent().getStringExtra("token");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case  R.id.Card_item :
                Intent i = new Intent(this, ItemActivity.class);
                startActivity(i);
                break;

            case  R.id.Card_itemList :
                Intent j = new Intent(this, ItemListActivity.class);
                j.putExtra("token", token);
                startActivity(j);
                break;
        }
    }
}