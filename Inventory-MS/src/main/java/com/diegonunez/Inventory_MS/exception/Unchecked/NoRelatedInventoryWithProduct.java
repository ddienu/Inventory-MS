package com.diegonunez.Inventory_MS.exception.Unchecked;

public class NoRelatedInventoryWithProduct extends RuntimeException{

    public NoRelatedInventoryWithProduct(String message){
        super(message);
    }

}
