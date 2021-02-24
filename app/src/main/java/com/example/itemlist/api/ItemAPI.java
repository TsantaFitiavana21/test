package com.example.itemlist.api;

import com.example.itemlist.metier.Item;
import com.example.itemlist.metier.ItemList;
import com.example.itemlist.metier.Login;
import com.example.itemlist.metier.Token;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ItemAPI {
    @GET("items/")
    Call<ArrayList<Item>> listeItems();

    @GET("items/{id}")
    Call<Item> getItem(@Path("id") int id);

    @POST("items/")
    Call<Item> createItem(@Body Item compte);

    @PUT("items/{id}/")
    Call<Item> updateItem(@Path("id") int id, @Body Item item);

    @DELETE("items/{id}")
    Call<Item> deleteItem(@Path("id") int id);

    // LOGIN
    @POST("api-token-auth/")
    Call<Token> login(@Body Login login);

    //ITEM LIST
    @GET("itemlists/")
    Call<ArrayList<ItemList>> getListItems(@Header("Authorization") String auth);

    @GET("itemlists/{id}")
    Call<ArrayList<ItemList>> getListItem(@Path("id") int id, @Header("Authorization") String auth);
}
