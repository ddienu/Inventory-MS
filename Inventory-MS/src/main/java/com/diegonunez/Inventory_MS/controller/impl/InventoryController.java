package com.diegonunez.Inventory_MS.controller.impl;

import com.diegonunez.Inventory_MS.controller.IInventoryController;
import com.diegonunez.Inventory_MS.dto.request.InventoryRequest;
import com.diegonunez.Inventory_MS.dto.response.InventoryResponseJsonApi;
import com.diegonunez.Inventory_MS.service.IInventoryService;
import com.diegonunez.Inventory_MS.service.impl.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/inventory")
public class InventoryController implements IInventoryController {

    private final IInventoryService inventoryService;

    public InventoryController(IInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    @GetMapping(path = "/{productId}")
    @Override
    public ResponseEntity<InventoryResponseJsonApi> getInventoryByProductId(@PathVariable Integer productId) {
        InventoryResponseJsonApi response = inventoryService.getInventoryByProductId(productId);

        return ResponseEntity.status(HttpStatus.OK).body(
                response
        );
    }

    @PutMapping
    @Override
    public ResponseEntity<InventoryResponseJsonApi> updateInventory(@Valid @RequestBody InventoryRequest inventoryRequest) {
        InventoryResponseJsonApi response = inventoryService.updateInventory(inventoryRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                response
        );
    }

    @PostMapping
    @Override
    public ResponseEntity<InventoryResponseJsonApi> createInventory(@Valid @RequestBody InventoryRequest newInventory) {
        InventoryResponseJsonApi response = inventoryService.createInventory(newInventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                response
        );
    }
}
