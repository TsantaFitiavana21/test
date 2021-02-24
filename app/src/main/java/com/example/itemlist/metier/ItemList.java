package com.example.itemlist.metier;

public class ItemList {

    private int id;
    private String name;
    private int user;
    private int[] items;
    private float total;

    public ItemList(int id, String name, int user, int[] items, float total) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.items = items;
        this.total = total;
    }

    public ItemList(String name, int[] items, float total) {
        this.name = name;
        this.items = items;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int[] getItems() {
        return items;
    }

    public void setItems(int[] items) {
        this.items = items;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
