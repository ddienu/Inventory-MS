package com.diegonunez.Inventory_MS.service.impl;

import com.diegonunez.Inventory_MS.dto.request.InventoryRequest;
import com.diegonunez.Inventory_MS.dto.response.InventoryResponseJsonApi;
import com.diegonunez.Inventory_MS.dto.response.ProductResponseJsonApi;
import com.diegonunez.Inventory_MS.entity.Inventory;
import com.diegonunez.Inventory_MS.exception.Unchecked.NoRelatedInventoryWithProduct;
import com.diegonunez.Inventory_MS.repository.IInventoryRepository;
import com.diegonunez.Inventory_MS.service.IInventoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InventoryService implements IInventoryService {

    private final IInventoryRepository inventoryRepository;
    private final RestTemplate restTemplate;

    @Value("${product.api.base-url}")
    private String productBaseUrl;

    public InventoryService(IInventoryRepository inventoryRepository, RestTemplate restTemplate){
        this.inventoryRepository = inventoryRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public InventoryResponseJsonApi getInventoryByProductId(Integer productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(
                () -> new NoRelatedInventoryWithProduct("No inventory found for the given product")
        );

        ResponseEntity<ProductResponseJsonApi> productResponse = restTemplate.getForEntity(
                productBaseUrl +"/"+ productId,
                ProductResponseJsonApi.class
        );

        ProductResponseJsonApi.Data data = productResponse.getBody().getData();
        ProductResponseJsonApi.Attributes attr = data.getAttributes();

        return new InventoryResponseJsonApi(
                inventory.getId(),
                productId,
                inventory.getQuantity(),
                attr.getName(),
                attr.getPrice()
        );
    }

    @Override
    public InventoryResponseJsonApi updateInventory(InventoryRequest inventoryUpdate) {
        Inventory inventory = inventoryRepository.findByProductId(inventoryUpdate.getProductId())
                .orElseThrow(() -> new NoRelatedInventoryWithProduct("No inventory found for the given product"));

        inventory.setQuantity(inventoryUpdate.getQuantity());
        inventoryRepository.save(inventory);

        ResponseEntity<ProductResponseJsonApi> response = restTemplate.getForEntity(
                productBaseUrl+"/"+inventoryUpdate.getProductId(),
                ProductResponseJsonApi.class
        );

        ProductResponseJsonApi.Attributes attributes = response.getBody().getData().getAttributes();

        System.out.printf("🟢 Inventory updated for productId %d. New quantity: %d%n", inventoryUpdate.getProductId(), inventoryUpdate.getQuantity());

        return new InventoryResponseJsonApi(
                inventory.getId(),
                inventory.getProductId(),
                inventory.getQuantity(),
                attributes.getName(),
                attributes.getPrice()
        );
    }

    @Override
    public InventoryResponseJsonApi createInventory(InventoryRequest newInventory) {
        Integer productId = newInventory.getProductId();

        ResponseEntity<ProductResponseJsonApi> response = restTemplate.getForEntity(
                productBaseUrl +"/"+productId,
                ProductResponseJsonApi.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new EntityNotFoundException("Product not found");
        } else {
            response.getBody();
        }

        inventoryRepository.findByProductId(productId).ifPresent(inv -> {
            throw new IllegalStateException("Inventory already exists for this product");
        });

        Inventory inventoryToSave = new Inventory();
        inventoryToSave.setProductId(productId);
        inventoryToSave.setQuantity(newInventory.getQuantity());

        Inventory saved = inventoryRepository.save(inventoryToSave);

        ProductResponseJsonApi.Attributes attr = response.getBody().getData().getAttributes();

        return new InventoryResponseJsonApi(
                saved.getId(),
                productId,
                saved.getQuantity(),
                attr.getName(),
                attr.getPrice()
        );
    }
}
