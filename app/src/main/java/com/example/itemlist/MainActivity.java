package com.example.itemlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.itemlist.activity.ItemActivity;
import com.example.itemlist.activity.MenuActivity;
import com.example.itemlist.api.ItemAPI;
import com.example.itemlist.metier.Login;
import com.example.itemlist.metier.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    String url = IP.getUrl();
    EditText txtusername, txtmdp;
    Button btnLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(MainActivity.this, ItemListActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        }, 2000);*/

        txtusername = findViewById(R.id.txt_username);
        txtmdp = findViewById(R.id.txt_mpd);

        txtmdp.setText("1@2QGP$A49pe");

        btnLog = findViewById(R.id.btnLog);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ItemAPI itemAPI = retrofit.create(ItemAPI.class);

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String _user = txtusername.getText().toString();
            String _mdp = txtmdp.getText().toString();

            if ( _user.isEmpty() || _mdp.isEmpty()){
                Toast.makeText(MainActivity.this, "Please fill all fields !!", Toast.LENGTH_LONG).show();
            }
            else{
                Login login = new Login( _user, _mdp);
                Call<Token> call = itemAPI.login(login);

                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent=new Intent(MainActivity.this, MenuActivity.class);
                            intent.putExtra("token",response.body().get_token());
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            }
        });
    }
}