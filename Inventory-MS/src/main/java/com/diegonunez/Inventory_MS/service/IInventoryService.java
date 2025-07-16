package com.diegonunez.Inventory_MS.service;

import com.diegonunez.Inventory_MS.dto.request.InventoryRequest;
import com.diegonunez.Inventory_MS.dto.response.InventoryResponseJsonApi;

public interface IInventoryService {
    InventoryResponseJsonApi getInventoryByProductId(Integer productId);
    InventoryResponseJsonApi updateInventory(InventoryRequest inventoryUpdate);

    InventoryResponseJsonApi createInventory(InventoryRequest newInventory);
}
