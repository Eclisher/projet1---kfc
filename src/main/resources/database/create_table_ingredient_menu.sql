CREATE TABLE Ingredient_Menu (
    id SERIAL PRIMARY KEY,
    menu_id BIGINT,
    ingredient_id BIGINT,
    quantite DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (menu_id) REFERENCES Menu(id),
    FOREIGN KEY (ingredient_id) REFERENCES Ingredient(id)
);
