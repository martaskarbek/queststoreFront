package com.codecool.queststore.models;

import java.util.HashMap;
import java.util.Map;

public enum Category {

    BASIC(1),
    EXTRA(2);

    private final int categoryId;
    private static final Map<Integer, Category> map = new HashMap<>();


    Category(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public static int getCategoryValue(Category category){
        return category.getCategoryId();
    }

    public static Map<Integer, Category> getMap() {
        return map;
    }

    public static Category valueOf(int categoryId) {
        for (Category category : Category.values()) {
            map.put(category.categoryId, category);
        }
        return map.get(categoryId);
    }
}
