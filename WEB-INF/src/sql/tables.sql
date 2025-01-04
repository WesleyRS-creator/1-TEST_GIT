CREATE TABLE CATEGORIE (
    ID_CATEGORIE SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE BRAND (
    ID_BRAND SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE MARKET_USER (
    ID_MARKET_USER SERIAL PRIMARY KEY,
    NOM VARCHAR(255) UNIQUE NOT NULL,
    MOT_DE_PASSE VARCHAR(255) NOT NULL,
    ADMIN BOOLEAN DEFAULT FALSE
);

CREATE TABLE PRODUIT (
    ID_PRODUIT SERIAL PRIMARY KEY,
    NOM_PRODUIT VARCHAR(255) NOT NULL,
    ID_BRAND INT NOT NULL REFERENCES BRAND(ID_BRAND),
    ID_CATEGORIE INT NOT NULL REFERENCES CATEGORIE(ID_CATEGORIE),
    DESCRIPTION_PRODUIT VARCHAR(255),
    PRIX_UNITAIRE INT NOT NULL CHECK (PRIX_UNITAIRE >= 0),
    NOTE DECIMAL(8, 2) CHECK (NOTE BETWEEN 0 AND 5),
    image_source VARCHAR(255),
    soft_delete BOOLEAN NOT NULL
);

CREATE TABLE FACTURE (  
    ID_FACTURE SERIAL PRIMARY KEY
);

CREATE TABLE MOUVEMENT (
    ID_MOUVEMENT SERIAL PRIMARY KEY,
    ID_PRODUIT INT NOT NULL REFERENCES PRODUIT(ID_PRODUIT),
    QUANTITY INT NOT NULL,
    ID_TYPE_MOUVEMENT INT NOT NULL REFERENCES TYPE_MOUVEMENT(ID_TYPE_MOUVEMENT),
    DATE_MOUVEMENT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE TYPE_MOUVEMENT (
    ID_TYPE_MOUVEMENT SERIAL PRIMARY KEY,
    DESCRIPTION_TYPE_MOUVEMENT VARCHAR(255)
);

CREATE TABLE USER_HISTORY (
    ID_USER_HISTORY SERIAL PRIMARY KEY,
    ID_FACTURE INT NOT NULL REFERENCES FACTURE(ID_FACTURE),
    ID_MARKET_USER INT NOT NULL REFERENCES MARKET_USER(ID_MARKET_USER),
    ID_MOUVEMENT INT NOT NULL REFERENCES MOUVEMENT(ID_MOUVEMENT)
);