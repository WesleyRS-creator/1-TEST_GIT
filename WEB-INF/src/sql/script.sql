CREATE USER supermarket_admin WITH PASSWORD '1234';

ALTER USER supermarket_admin WITH SUPERUSER;

-- ALTER USER superMarket_admin WITH PASSWORD '1234';

CREATE DATABASE supermarket;

\c supermarket;

UPDATE PRODUIT SET NOM_PRODUIT = "SPAS 12", ID_BRAND = 1, ID_CATEGORIE = 1, DESCRIPTION_PRODUIT = "new weapon", PRIX_UNITAIRE = "1200", NOTE = "3.25", image_source = "none", soft_delete = true WHERE ID_PRODUIT = 1;