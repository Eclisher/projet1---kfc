package com.kfc.sakafo.Service;

import com.kfc.sakafo.InsufficientStockException;
import com.kfc.sakafo.Model.*;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class StockManager {
    private Map<Integer, Stock> stockMap = new HashMap<>();
    public List<Stock> stocks = new ArrayList<>();
    private List<MenuSale> menuSales = new ArrayList<>();
    public void initializeStock(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            stockMap.put(ingredient.getId(), new Stock(ingredient, 0, Instant.now(), null));
        }
    }

    // Aprovisionner un ingrédient
    public void supplyIngredient(int ingredientId, double quantity, Instant supplyDate) {
        Stock stock = stockMap.get(ingredientId);
        if (stock != null) {
            stock.setQuantity(stock.getQuantity() + quantity);
            stock.setLastUpdatedTime(Instant.now());
            stock.setMovementType(MovementType.ENTREE);
        } else {
            throw new IllegalArgumentException("Ingredient ID not found");
        }
    }
    public double getRemainingQuantity(int ingredientId) {
        Stock stock = stockMap.get(ingredientId);
        if (stock != null) {
            return stock.getQuantity();
        } else {
            throw new IllegalArgumentException("Ingredient ID not found");
        }
    }

    public void displayStock() {
        for (Map.Entry<Integer, Stock> entry : stockMap.entrySet()) {
            Stock stock = entry.getValue();
            System.out.println("Ingredient: " + stock.getIngredient().getName() +
                    ", Quantity: " + stock.getQuantity() +
                    ", Last Updated Time: " + stock.getLastUpdatedTime());
        }
    }
    public List<Stock> getStockMovementsInTimeInterval(Instant startTime, Instant endTime) {
        stocks.sort(Comparator.comparing(Stock::getLastUpdatedTime).reversed());
        List<Stock> result = new ArrayList<>();
        for (Stock stock : stocks) {
            Instant lastUpdatedTime = stock.getLastUpdatedTime();
            if (lastUpdatedTime != null && lastUpdatedTime.isAfter(startTime) && lastUpdatedTime.isBefore(endTime)) {
                result.add(stock);
            }
        }
        return result;
    }
    public void sellMenu(Menu menu) {
        for (IngredientMenu ingredientMenu : menu.getIngredients()) {
            Stock stock = stockMap.get(ingredientMenu.getIngredient().getId());
            if (stock == null || stock.getQuantity() < ingredientMenu.getQuantite()) {
                throw new InsufficientStockException("Insufficient stock for ingredient: " + ingredientMenu.getIngredient().getName());
            }
        }
        for (IngredientMenu ingredientMenu : menu.getIngredients()) {
            Stock stock = stockMap.get(ingredientMenu.getIngredient().getId());
            stock.setQuantity(stock.getQuantity() - ingredientMenu.getQuantite());
            stock.setLastUpdatedTime(Instant.now());
            stock.setMovementType(MovementType.SORTIE);
        }
    }
    public void recordMenuSale(MenuSale menuSale) {
        menuSales.add(menuSale);
    }

    public List<TopIngredient> getTopUsedIngredients(int X, Instant startTime, Instant endTime) {
        Map<Ingredient, Double> ingredientUsage = new HashMap<>();
        List<MenuSale> salesInInterval = menuSales.stream()
                .filter(sale -> !sale.getSaleTime().isBefore(startTime) && !sale.getSaleTime().isAfter(endTime))
                .collect(Collectors.toList());
        for (MenuSale sale : salesInInterval) {
            for (IngredientMenu ingredientMenu : sale.getMenu().getIngredients()) {
                ingredientUsage.merge(ingredientMenu.getIngredient(), ingredientMenu.getQuantite(), Double::sum);
            }
        }
        List<TopIngredient> topIngredients = ingredientUsage.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .map(e -> new TopIngredient(e.getKey().getId(), e.getKey().getName(), e.getValue(), e.getKey().getUnit().getName()))
                .collect(Collectors.toList());
        if (X > topIngredients.size()) {
            return topIngredients;
        } else {
            return topIngredients.subList(0, X);
        }
    }




}