package com.diegonunez.Inventory_MS.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
public class InventoryResponseJsonApi {
    private final Data data;

    public InventoryResponseJsonApi(Integer id, Integer productId, Integer quantity, String productName, BigDecimal productPrice) {
        this.data = new Data("inventory", String.valueOf(id), new Attributes(productId, quantity, productName, productPrice));
    }

    @Getter
    @AllArgsConstructor
    public static class Data {
        private String type;
        private String id;
        private Attributes attributes;
    }

    @Getter
    @AllArgsConstructor
    public static class Attributes {
        private Integer productId;
        private Integer quantity;
        private String productName;
        private BigDecimal productPrice;
    }
}
