package com.kfc.sakafo.Model;

import lombok.*;

import java.time.Instant;
@Getter
@Setter
@AllArgsConstructor
public class MenuSale {
    private Menu menu;
    private Instant saleTime;
}
