package com.kfc.sakafo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Menu {
    private int id;
    private String name;
    private List<IngredientMenu> ingredients = new ArrayList<>();
    // Ajouter un ingrédient au menu
    public void addIngredient(IngredientMenu ingredientMenu) {
        ingredients.add(ingredientMenu);
    }

    // Supprimer un ingrédient du menu
    public void removeIngredient(Ingredient ingredient) {
        ingredients.removeIf(ingredientMenu -> ingredientMenu.getIngredient().getId() == ingredient.getId());
    }

    // Modifier la quantité d'un ingrédient dans le menu
    public void updateIngredientQuantity(Ingredient ingredient, double newQuantity) {
        for (IngredientMenu ingredientMenu : ingredients) {
            if (ingredientMenu.getIngredient().getId() == ingredient.getId()) {
                ingredientMenu.setQuantite(newQuantity);
                break;
            }
        }
    }
}
