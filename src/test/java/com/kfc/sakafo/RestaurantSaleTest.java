package com.kfc.sakafo;

import com.kfc.sakafo.Model.*;
import com.kfc.sakafo.Service.RestaurantSale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestaurantSaleTest {
    private RestaurantSale service;

    @BeforeEach
    public void setUp() {
        service = new RestaurantSale();
    }

    @Test
    public void testSynthesizeRestaurantSalesData() {
        Ingredient pain = new Ingredient(1, "Pain", 500, new Unit(1, "Pièce"));
        Ingredient saucisse = new Ingredient(2, "Saucisse", 20000, new Unit(1, "Pièce"));
        Ingredient mayonnaise = new Ingredient(3, "Mayonnaise", 10000, new Unit(1, "Pièce"));

        Menu menu1 = new Menu(1, "Menu 1");
        menu1.addIngredient(new IngredientMenu(null, menu1, pain, 1));
        Menu menu2 = new Menu(2, "Menu 2");
        menu2.addIngredient(new IngredientMenu(null, menu2, saucisse, 1));
        Menu menu3 = new Menu(3, "Menu 3");
        menu3.addIngredient(new IngredientMenu(null, menu3, mayonnaise, 1));
        Restaurant restaurant1 = new Restaurant(1, "TWF", Arrays.asList(menu1, menu2, menu3), Arrays.asList(
                new MenuSale(menu1, Instant.parse("2024-05-01T08:30:00Z")),
                new MenuSale(menu2, Instant.parse("2024-05-01T09:00:00Z"))
        ));
        Restaurant restaurant2 = new Restaurant(2, "Akoor", Arrays.asList(menu1, menu2, menu3), Arrays.asList(
                new MenuSale(menu3, Instant.parse("2024-05-02T10:00:00Z"))
        ));
        List<Restaurant> restaurants = Arrays.asList(restaurant1, restaurant2);
        Instant startTime = Instant.parse("2024-05-01T00:00:00Z");
        Instant endTime = Instant.parse("2024-05-02T23:59:59Z");

        String summary = service.synthesizeRestaurantSalesData(restaurants, startTime, endTime);

        System.out.println(summary);
    }

}
