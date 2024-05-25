package com.kfc.sakafo;

import com.kfc.sakafo.Model.Ingredient;
import com.kfc.sakafo.Model.MovementType;
import com.kfc.sakafo.Model.Stock;
import com.kfc.sakafo.Model.Unit;
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
}