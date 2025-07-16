package com.diegonunez.Inventory_MS.controller;

import com.diegonunez.Inventory_MS.dto.request.InventoryRequest;
import com.diegonunez.Inventory_MS.dto.response.InventoryResponseJsonApi;
import org.springframework.http.ResponseEntity;

public interface IInventoryController {

    ResponseEntity<InventoryResponseJsonApi> getInventoryByProductId(Integer productId);

    ResponseEntity<InventoryResponseJsonApi> updateInventory(InventoryRequest inventoryRequest);
    ResponseEntity<InventoryResponseJsonApi> createInventory(InventoryRequest newInventory);
}
