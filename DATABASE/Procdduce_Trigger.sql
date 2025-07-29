use all_in_store 
go 
CREATE
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

DROP TABLE IF EXISTS message;

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

DROP TABLE IF EXISTS user_product_events;

DROP TABLE IF EXISTS authorities;

DROP TABLE IF EXISTS roles;

DROP TABLE IF EXISTS accounts;

PRINT '✅ All specified tables dropped successfully.';

END;

GO
create 
or
ALTER TRIGGER trgg_auto_insert_history_cost_and_price
    ON product_items
    FOR INSERT, UPDATE
    AS
    BEGIN
        SET NOCOUNT ON;
        
        -- Chỉ chạy khi INSERT hoặc UPDATE cost/price
        IF NOT EXISTS (SELECT 1 FROM deleted) OR UPDATE(cost) OR UPDATE(price)
        BEGIN
            INSERT INTO cost_histories(product_item_id, cost)
            SELECT id, cost FROM inserted;
            
            INSERT INTO price_histories(product_item_id, price)
            SELECT id, price FROM inserted;
            
            PRINT N'Đã thêm lịch sử thay đổi giá';
        END
    END
UPDATE orders
SET order_code = 'L3BXXN'
WHERE id = 2;

/**CREATE TRIGGER trg_update_user_address
ON user_addresses
AFTER UPDATE
AS
BEGIN
  SET NOCOUNT ON;
  UPDATE user_addresses
  SET updated_at = GETDATE()
  FROM inserted
  WHERE user_addresses.id = inserted.id;
END;**\