package com.kfc.sakafo;

import com.kfc.sakafo.Model.Ingredient;
import com.kfc.sakafo.Model.Menu;
import com.kfc.sakafo.Model.Unit;
import com.kfc.sakafo.Service.MenuManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MenuManagerTest {
    private MenuManager menuManager;

    @BeforeEach
    public void setUp() {
        menuManager = new MenuManager();
        Unit piece = new Unit(1, "Pièce");
        Unit kg = new Unit(2, "KG");
        Unit litre = new Unit(3, "Litre");
        Ingredient pain = new Ingredient(1, "Pain", 500, piece);
        Ingredient saucisse = new Ingredient(2, "Saucisse", 20000, kg);
        Ingredient mayonnaise = new Ingredient(3, "Mayonnaise", 10000, litre);
        Ingredient ketchup = new Ingredient(4, "Ketchup", 5000, litre);
    }
    @Test
    public void testCreateMenu() {
        menuManager.createMenu(1, "Menu 1");
        assertNotNull(menuManager.getMenu(1));
        assertEquals("Menu 1", menuManager.getMenu(1).getName());
    }

    @Test
    public void testAddIngredientToMenu() {
        menuManager.createMenu(1, "Menu 1");
        Unit piece = new Unit(1, "Pièce");
        Ingredient pain = new Ingredient(1, "Pain", 500, piece);
        menuManager.addIngredientToMenu(1, pain, 2);
        Menu menu = menuManager.getMenu(1);

        assertEquals(1, menu.getIngredients().size());
        assertEquals("Pain", menu.getIngredients().get(0).getIngredient().getName());
        assertEquals(2, menu.getIngredients().get(0).getQuantite());
    }

    @Test
    public void testUpdateIngredientQuantityInMenu() {
        menuManager.createMenu(1, "Menu 1");
        Unit piece = new Unit(1, "Pièce");
        Ingredient pain = new Ingredient(1, "Pain", 500, piece);
        Ingredient saucisse = new Ingredient(2, "Saucisse", 20000, piece);
        menuManager.createMenu(1, "Menu 1");
        menuManager.addIngredientToMenu(1, pain, 2);
        menuManager.addIngredientToMenu(1, saucisse, 1);
        menuManager.updateIngredientQuantityInMenu(1, pain, 5);
        Menu menu = menuManager.getMenu(1);
        assertEquals(5, menu.getIngredients().get(0).getQuantite());
    }

}