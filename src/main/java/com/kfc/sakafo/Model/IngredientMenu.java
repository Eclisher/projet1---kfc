package com.kfc.sakafo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IngredientMenu {
    private Long id;
    private Menu menu;
    private Ingredient ingredient;
    private double quantite;
}
