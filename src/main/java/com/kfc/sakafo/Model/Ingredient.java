package com.kfc.sakafo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Ingredient {
    private int id;
    private String name;
    private double price;
    private Unit unit;
}
