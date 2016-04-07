package com.tylerrockwood.atg.api.models;


import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;

public class GroceryList {

    @Id
    Long id;
    String name;
    @Index
    List<Key<GroceryItem>> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Key<GroceryItem>> getItems() {
        return items;
    }

    public void setItems(List<Key<GroceryItem>> items) {
        this.items = items;
    }
}
