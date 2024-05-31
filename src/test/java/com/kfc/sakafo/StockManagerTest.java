package com.kfc.sakafo;

import com.kfc.sakafo.Model.*;
import com.kfc.sakafo.Service.StockManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class StockManagerTest {
    private StockManager stockManager;

    @BeforeEach
    public void setUp() {
        stockManager = new StockManager();
        Unit piece = new Unit(1, "Pièce");
        Unit kg = new Unit(2, "KG");
        Unit litre = new Unit(3, "Litre");
        Ingredient pain = new Ingredient(1, "Pain", 500, piece);
        Ingredient saucisse = new Ingredient(2, "Saucisse", 20000, kg);
        Ingredient mayonnaise = new Ingredient(3, "Mayonnaise", 10000, litre);
        Ingredient ketchup = new Ingredient(4, "Ketchup", 5000, litre);
        List<Ingredient> ingredients = Arrays.asList(pain, saucisse, mayonnaise, ketchup);
        stockManager.initializeStock(ingredients);
    }

    @Test
    public void testSupplyIngredient() {
        stockManager.supplyIngredient(1, 100, Instant.now());
        assertEquals(100, stockManager.getRemainingQuantity(1));
    }

    @Test
    public void testGetRemainingQuantity() {
        stockManager.supplyIngredient(2, 50, Instant.now());
        assertEquals(50, stockManager.getRemainingQuantity(2));
    }

    @Test
    public void testSupplyIngredientNotFound() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            stockManager.supplyIngredient(999, 100, Instant.now());
        });

        String expectedMessage = "Ingredient ID not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void testGetRemainingQuantityNotFound() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            stockManager.getRemainingQuantity(999);
        });

        String expectedMessage = "Ingredient ID not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetStockMovementsInTimeInterval() {
        stockManager.stocks.add(new Stock(new Ingredient(1, "Pain", 500, new Unit(1, "Pièce")), 100, Instant.parse("2024-05-01T08:01:00Z"), MovementType.ENTREE));
        stockManager.stocks.add(new Stock(new Ingredient(2, "Saucisse", 20000, new Unit(2, "KG")), 50, Instant.parse("2024-05-01T08:30:00Z"), MovementType.ENTREE));
        stockManager.stocks.add(new Stock(new Ingredient(2, "Saucisse", 20000, new Unit(2, "KG")), 40, Instant.parse("2024-05-02T09:00:00Z"), MovementType.SORTIE));
        Instant startTime = Instant.parse("2024-05-01T08:00:00Z");
        Instant endTime = Instant.parse("2024-05-02T10:10:00Z");
        List<Stock> result = stockManager.getStockMovementsInTimeInterval(startTime, endTime);
        assertEquals(3, result.size());
    }
    @Test
    public void testSellMenu() {
        stockManager.supplyIngredient(1, 100, Instant.now());
        stockManager.supplyIngredient(2, 50, Instant.now());
        Menu menu = new Menu(1, "Menu 1");
        menu.addIngredient(new IngredientMenu(null, menu, new Ingredient(1, "Pain", 500, new Unit(1, "Pièce")), 50));
        menu.addIngredient(new IngredientMenu(null, menu, new Ingredient(2, "Saucisse", 20000, new Unit(2, "KG")), 30));
        stockManager.sellMenu(menu);
        assertEquals(50, stockManager.getRemainingQuantity(1));
        assertEquals(20, stockManager.getRemainingQuantity(2));
    }
    @Test
    public void testGetTopUsedIngredients() {
        Menu menu1 = new Menu(1, "Menu 1");
        menu1.addIngredient(new IngredientMenu(null, menu1, new Ingredient(1, "Pain", 500, new Unit(1, "Pièce")), 50));
        menu1.addIngredient(new IngredientMenu(null, menu1, new Ingredient(2, "Saucisse", 20000, new Unit(2, "KG")), 30));
        Menu menu2 = new Menu(2, "Menu 2");
        menu2.addIngredient(new IngredientMenu(null, menu2, new Ingredient(3, "Mayonnaise", 10000, new Unit(3, "Litre")), 20));
        menu2.addIngredient(new IngredientMenu(null, menu2, new Ingredient(2, "Saucisse", 20000, new Unit(2, "KG")), 10));
        stockManager.recordMenuSale(new MenuSale(menu1, Instant.parse("2024-05-01T08:30:00Z")));
        stockManager.recordMenuSale(new MenuSale(menu2, Instant.parse("2024-05-01T09:00:00Z")));
        Instant startTime = Instant.parse("2024-05-01T08:00:00Z");
        Instant endTime = Instant.parse("2024-05-02T10:10:00Z");
        List<TopIngredient> result = stockManager.getTopUsedIngredients(3, startTime, endTime);
        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(50, result.get(0).getQuantity());
        assertEquals(2, result.get(1).getId());
        assertEquals(30, result.get(1).getQuantity());
        assertEquals(3, result.get(2).getId());
        assertEquals(20, result.get(2).getQuantity());
    }
}