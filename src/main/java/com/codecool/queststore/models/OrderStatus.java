package com.codecool.queststore.models;

import java.util.HashMap;
import java.util.Map;

public enum OrderStatus {
    USED(1),
    NOTUSED(2);

    private final int orderStatusId;
    private static final Map<Integer, OrderStatus> map = new HashMap<>();

    OrderStatus(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public static int getOrderStatusValue(OrderStatus orderStatus){
        return orderStatus.getOrderStatusId();
    }

    public static OrderStatus valueOf(int orderStatusId) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            map.put(orderStatus.orderStatusId, orderStatus);
        }
        return map.get(orderStatusId);
    }
}
