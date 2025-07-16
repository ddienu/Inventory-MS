package com.diegonunez.Inventory_MS.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductResponseJsonApi {

    private Data data;

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
        private String name;
        private BigDecimal price;
    }
}
