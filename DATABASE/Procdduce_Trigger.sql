use all_in_store go CREATE
OR
ALTER PROCEDURE usp_DropTablesAndConstraints AS BEGIN
SET
    NOCOUNT ON;

PRINT '🔧 Disabling all foreign key constraints...';

EXEC sp_msforeachtable 'ALTER TABLE ? NOCHECK CONSTRAINT ALL';

PRINT '❌ Dropping tables in correct dependency order...';

DROP TABLE IF EXISTS order_items;

DROP TABLE IF EXISTS returns;

DROP TABLE IF EXISTS cancels;

DROP TABLE IF EXISTS orders;

DROP TABLE IF EXISTS cart_items;

DROP TABLE IF EXISTS reviews;

DROP TABLE IF EXISTS customs;

DROP TABLE IF EXISTS product_images;

DROP TABLE IF EXISTS cost_histories;

DROP TABLE IF EXISTS price_histories;

DROP TABLE IF EXISTS promotion_products;

DROP TABLE IF EXISTS promotions;

DROP TABLE IF EXISTS product_items;

DROP TABLE IF EXISTS base_products;

DROP TABLE IF EXISTS categories;

DROP TABLE IF EXISTS catalogs;

DROP TABLE IF EXISTS variant_values;

DROP TABLE IF EXISTS variants;

DROP TABLE IF EXISTS coupons;

DROP TABLE IF EXISTS news;

DROP TABLE IF EXISTS user_addresses;

DROP TABLE IF EXISTS e_wallet_transactions;

DROP TABLE IF EXISTS e_wallets;

DROP TABLE IF EXISTS shipping_methods;

DROP TABLE IF EXISTS payment_methods;

DROP TABLE IF EXISTS purchase_order_items;

DROP TABLE IF EXISTS purchase_orders;

DROP TABLE IF EXISTS UserLogs;

DROP TABLE IF EXISTS authorities;

DROP TABLE IF EXISTS roles;

DROP TABLE IF EXISTS accounts;

PRINT '✅ All specified tables dropped successfully.';

END;

GO