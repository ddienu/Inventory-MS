package com.diegonunez.Inventory_MS.exception;

import com.diegonunez.Inventory_MS.dto.response.ProductErrorResponseJsonApi;
import com.diegonunez.Inventory_MS.exception.Unchecked.NoRelatedInventoryWithProduct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoRelatedInventoryWithProduct.class)
    public ResponseEntity<ProductErrorResponseJsonApi> noRelatedInventoryWithProductHandler(NoRelatedInventoryWithProduct e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ProductErrorResponseJsonApi(
                        "404",
                        "Not Found",
                        e.getMessage()
                )
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ProductErrorResponseJsonApi> llegalStateHandler(IllegalStateException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ProductErrorResponseJsonApi(
                        "409",
                        "Conflict",
                        e.getMessage()
                )
        );
    }


}
