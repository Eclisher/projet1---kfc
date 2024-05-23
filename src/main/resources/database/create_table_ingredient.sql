CREATE TABLE Ingredient (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prix double PRECISION NOT NULL,
    unite_id BIGINT,
    FOREIGN KEY (unite_id) REFERENCES Unite(id)
);
