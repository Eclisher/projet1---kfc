package com.kfc.sakafo.Service;

import com.kfc.sakafo.Model.IngredientMenu;
import com.kfc.sakafo.Model.Menu;
import com.kfc.sakafo.Model.MenuSale;
import com.kfc.sakafo.Model.Restaurant;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantSale {
    public String synthesizeRestaurantSalesData(List<Restaurant> restaurants, Instant startTime, Instant endTime) {
        String header = "ID restaurant\tLieu du restaurant\tNombre Menu 1 Vendus\tNombre Menu 2 Vendus\tNombre Menu 3 Vendus\tMontant Menu 1 Vendus\tMontant Menu 2 Vendus\tMontant Menu 3 Vendus\n";
        StringBuilder summary = new StringBuilder(header);

        for (Restaurant restaurant : restaurants) {
            int[] menuCounts = new int[3];
            double[] menuAmounts = new double[3];

            List<MenuSale> salesInInterval = restaurant.getMenuSales().stream()
                    .filter(sale -> !sale.getSaleTime().isBefore(startTime) && !sale.getSaleTime().isAfter(endTime))
                    .collect(Collectors.toList());

            for (MenuSale sale : salesInInterval) {
                Menu menu = sale.getMenu();
                int menuId = menu.getId();
                menuCounts[menuId - 1]++;
                menuAmounts[menuId - 1] += calculateMenuPrice(menu);
            }

            summary.append(restaurant.getId()).append("\t")
                    .append(restaurant.getLocation()).append("\t")
                    .append(menuCounts[0]).append("\t")
                    .append(menuCounts[1]).append("\t")
                    .append(menuCounts[2]).append("\t")
                    .append(menuAmounts[0]).append("\t")
                    .append(menuAmounts[1]).append("\t")
                    .append(menuAmounts[2]).append("\t\n");
        }

        return summary.toString();
    }

    private double calculateMenuPrice(Menu menu) {
        return menu.getIngredients().stream()
                .mapToDouble(ingredientMenu -> ingredientMenu.getIngredient().getPrice() * ingredientMenu.getQuantite())
                .sum();
    }
}
