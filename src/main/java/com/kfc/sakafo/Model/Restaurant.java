package com.kfc.sakafo.Model;

import lombok.*;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class Restaurant {
    private int id;
    private String location;
    private List<Menu> menus;
    private List<MenuSale> menuSales;
    public void addMenu(Menu menu) {
        menus.add(menu);
    }

    public void removeMenu(Menu menu) {
        menus.remove(menu);
    }

    public List<Menu> getMenus() {
        return menus;
    }
    public void addMenuSale(MenuSale sale) {
        menuSales.add(sale);
    }

    public List<MenuSale> getMenuSales() {
        return menuSales;
    }
}
