package com.kfc.sakafo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class Stock {
    private Ingredient ingredient;
    private double quantity;
    private Instant lastUpdatedTime;
    private MovementType movementType;
}
