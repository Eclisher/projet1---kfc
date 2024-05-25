package com.kfc.sakafo.Service;

import com.kfc.sakafo.Model.Ingredient;
import com.kfc.sakafo.Model.IngredientMenu;
import com.kfc.sakafo.Model.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuManager {
    private Map<Integer, Menu> menuMap = new HashMap<>();
    public Menu getMenu(int menuId) {
        return menuMap.get(menuId);
    }
    public void createMenu(int id, String name) {
        Menu menu = new Menu(id, name, new ArrayList<>());
        menuMap.put(id, menu);
    }
    public void addIngredientToMenu(int menuId, Ingredient ingredient, double quantity) {
        Menu menu = menuMap.get(menuId);
        if (menu != null) {
            IngredientMenu ingredientMenu = new IngredientMenu(null, menu, ingredient, quantity);
            menu.addIngredient(ingredientMenu);
        } else {
            throw new IllegalArgumentException("Menu ID not found");
        }
    }
    public void removeIngredientFromMenu(int menuId, Ingredient ingredient) {
        Menu menu = menuMap.get(menuId);
        if (menu != null) {
            menu.removeIngredient(ingredient);
        } else {
            throw new IllegalArgumentException("Menu ID not found");
        }
    }
    public void updateIngredientQuantityInMenu(int menuId, Ingredient ingredient, double newQuantity) {
        Menu menu = menuMap.get(menuId);
        if (menu != null) {
            menu.updateIngredientQuantity(ingredient, newQuantity);
        } else {
            throw new IllegalArgumentException("Menu ID not found");
        }
    }
    public void displayMenus() {
        for (Menu menu : menuMap.values()) {
            System.out.println("Menu: " + menu.getName());
            for (IngredientMenu ingredientMenu : menu.getIngredients()) {
                System.out.println("  Ingredient: " + ingredientMenu.getIngredient().getName() +
                        ", Quantity: " + ingredientMenu.getQuantite() +
                        ", Unit: " + ingredientMenu.getIngredient().getUnit().getName());
            }
        }
    }


}