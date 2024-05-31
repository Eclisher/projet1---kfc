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

    public Menu(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addIngredient(IngredientMenu ingredientMenu) {
        ingredients.add(ingredientMenu);
    }
    public void removeIngredient(Ingredient ingredient) {
        ingredients.removeIf(ingredientMenu -> ingredientMenu.getIngredient().getId() == ingredient.getId());
    }
    public void updateIngredientQuantity(Ingredient ingredient, double newQuantity) {
        for (IngredientMenu ingredientMenu : ingredients) {
            if (ingredientMenu.getIngredient().getId() == ingredient.getId()) {
                ingredientMenu.setQuantite(newQuantity);
                break;
            }
        }
    }
}
