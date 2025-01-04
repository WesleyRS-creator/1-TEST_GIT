-- Vue des détails des produits
CREATE VIEW v_product_details AS
SELECT
    p.ID_PRODUIT AS product_id,
    p.NOM_PRODUIT AS product_name,
    b.ID_BRAND AS brand_id,
    b.name AS brand_name,
    c.ID_CATEGORIE AS category_id,
    c.name AS category_name,
    p.DESCRIPTION_PRODUIT AS description,
    p.PRIX_UNITAIRE AS price_unit,
    p.NOTE AS rating,
    p.image_source,
    p.soft_delete
FROM
    PRODUIT p
JOIN
    BRAND b ON p.ID_BRAND = b.ID_BRAND
JOIN
    CATEGORIE c ON p.ID_CATEGORIE = c.ID_CATEGORIE;

-- Vue des détails des mouvements de stock
CREATE VIEW v_movement_details AS
SELECT
    m.ID_MOUVEMENT AS movement_id,
    m.QUANTITY AS movement,
    m.DATE_MOUVEMENT AS date_movement,
    vpd.* 
FROM
    MOUVEMENT m
JOIN
    v_product_details vpd ON m.ID_PRODUIT = vpd.product_id;

-- Vue des stocks disponibles par produit
CREATE VIEW v_stock AS
SELECT
    p.ID_PRODUIT AS product_id,
    p.NOM_PRODUIT AS product_name,
    p.PRIX_UNITAIRE AS price_unit,
    b.ID_BRAND AS brand_id,
    b.name AS brand_name,
    c.ID_CATEGORIE AS category_id,
    c.name AS category_name,
    SUM(m.QUANTITY) AS stock
FROM
    PRODUIT p
JOIN
    BRAND b ON p.ID_BRAND = b.ID_BRAND
JOIN
    CATEGORIE c ON p.ID_CATEGORIE = c.ID_CATEGORIE
LEFT JOIN
    MOUVEMENT m ON p.ID_PRODUIT = m.ID_PRODUIT
GROUP BY
    p.ID_PRODUIT, p.NOM_PRODUIT, p.PRIX_UNITAIRE, b.ID_BRAND, b.name, c.ID_CATEGORIE, c.name;

-- Vue des détails de l'historique des utilisateurs
CREATE VIEW v_user_history_details AS
SELECT
    uh.ID_USER_HISTORY AS user_history_id,
    f.ID_FACTURE AS facture_id,
    u.ID_MARKET_USER AS user_id,
    u.NOM AS user_name,
    vmd.*
FROM
    USER_HISTORY uh
JOIN
    FACTURE f ON uh.ID_FACTURE = f.ID_FACTURE
JOIN
    MARKET_USER u ON uh.ID_MARKET_USER = u.ID_MARKET_USER
JOIN
    v_movement_details vmd ON uh.ID_MOUVEMENT = vmd.movement_id;
