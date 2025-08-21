 --create database all_in_store;
--drop database all_in_store
use all_in_store
go
use all_in_store
go
exec usp_DropTablesAndConstraints;
go
exec usp_DropTablesAndConstraints;
go
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

DROP TABLE IF EXISTS message;

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

DROP TABLE IF EXISTS User_Logs;

DROP TABLE IF EXISTS user_product_events;

DROP TABLE IF EXISTS authorities;

DROP TABLE IF EXISTS roles;

DROP TABLE IF EXISTS accounts;
go


go
create table
	accounts (
		id int identity (1, 1) primary key,
		email varchar(100) unique not null,
		password varchar(100) not null,
		gender bit default 0,
		birthday date ,
		fullname nvarchar (100) not null,
		avatar_url varchar(1000),
		phone varchar(15) null,
		average_order_value decimal(18, 2) default 0,
		user_rank nvarchar (20) default N'Đồng',
		total_spent decimal(18, 2) default 0,
		total_order int default 0,
		loyalty_point int default 0,
		is_active bit default 1,
		created_at datetime default getdate (),
		updated_at datetime default getdate ()
	);
GO
create table
	roles (
		id int identity (1, 2) primary key,
		name nvarchar (50),
		created_at datetime default getdate (),
		updated_at datetime default getdate ()
	) 
go
create table
	authorities (
		id int identity (1, 5) primary key,
		account_id int not null,
		role_id int not null,
		created_at datetime default getdate (),
		updated_at datetime default getdate (),
		foreign key (account_id) references accounts (id) ON DELETE CASCADE,
		foreign key (role_id) references roles (id) ON DELETE CASCADE
	) 
GO
CREATE TABLE
	user_addresses (
		id INT IDENTITY(1,1) PRIMARY KEY,
		account_id INT NOT NULL,

		recipient_name NVARCHAR(100) NOT NULL,
		phone VARCHAR(15) NOT NULL,
		province NVARCHAR(100) NOT NULL,
		district NVARCHAR(100) NOT NULL,
		ward NVARCHAR(100) NOT NULL,
		street NVARCHAR(100) NOT NULL,
		label NVARCHAR(20),
		is_default BIT DEFAULT 0,
		note NVARCHAR(255),

		created_at DATETIME DEFAULT GETDATE(),
		updated_at DATETIME DEFAULT GETDATE(),

		ghn_province_id INT,            -- tỉnh GHN
		ghn_district_id INT,            -- mã huyện GHN
		ghn_ward_code VARCHAR(20),      -- mã phường GHN

		FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);
GO



create table
	catalogs (
		id int identity (1, 1) primary key,
		name nvarchar (100) unique,
		created_at datetime default getdate (),
		updated_at datetime default getdate ()
	);
GO
create table
	categories (
		id int identity (1, 1) primary key,
		catalog_id int not null,
		name nvarchar (100) unique,
		created_at datetime default getdate (),
		updated_at datetime default getdate (),
		foreign key (catalog_id) references catalogs (id) ON DELETE CASCADE
	);
GO
create table
	base_products (
		id int identity (1, 1) primary key,
		name nvarchar (100) not null,
		material nvarchar (50) not null,
		category_id int not null,
		main_image_url varchar(255) not null,
		is_custom bit default 0,
		turn_buy int default 0,
		rating int default 0,
		is_active bit default 1,
		created_at datetime default getdate (),
		updated_at datetime default getdate (),
		foreign key (category_id) references categories (id) ON DELETE CASCADE
	);
GO
create table
	product_items (
		id int identity (1, 1) primary key,
		base_id int not null,
		cost decimal(18, 2) not null,
		price decimal(18, 2) not null,
		turn_buy int default 0,
		description nvarchar (max),
		sku varchar(100),
		safety_stock int default 0,
		qty int default 0,
		is_active bit default 1,
		sell_start datetime default getdate (),
		sell_end datetime default getdate (),
		created_at datetime default getdate (),
		updated_at datetime default getdate (),
		foreign key (base_id) references base_products (id) ON DELETE CASCADE
	);
GO

--CREATE TABLE
--	user_logs (
--		id INT IDENTITY (1, 1) PRIMARY KEY,
--		user_id INT NOT NULL,
--		action NVARCHAR (100) NOT NULL,
--		description NVARCHAR (500),
--		ip_address VARCHAR(45),
--		user_agent NVARCHAR (255),
--		created_at DATETIME DEFAULT GETDATE (),
--		module NVARCHAR (100)
--	);
CREATE TABLE user_product_events (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    event_type NVARCHAR(50) NOT NULL,       -- e.g., 'click', 'add_to_cart', 'purchase'
    event_time DATETIME DEFAULT GETDATE(),  -- when it happened
    session_id NVARCHAR(100),               -- optional: group actions per session
    device NVARCHAR(100),                   -- e.g., 'mobile', 'desktop'
    ip_address VARCHAR(45),
    user_agent NVARCHAR(255),
    position_in_list INT,                   -- optional: product position in list
    time_spent_seconds INT,                 -- optional: how long user viewed the product
    referrer NVARCHAR(255)                  -- optional: where the user came from (e.g. home page)
	foreign key (product_id) references product_items (id) ON DELETE CASCADE,
	foreign key ( user_id) references accounts (id) ON DELETE CASCADE
);
GO
GO
create table
	product_images (
		id int identity (1, 1) primary key,
		product_item_id int not null,
		image_url varchar(255) not null,
		created_at datetime default getdate (),
		updated_at datetime default getdate (),
		is_default bit default 0,
		foreign key (product_item_id) references product_items (id) ON DELETE CASCADE
	);
GO
create table
	news (
		id int identity (1, 1) primary key,
		image_url varchar(255) not null,
		title nvarchar(500),
		is_home bit default 1,
		description nvarchar (max) not null,
		created_at datetime default getdate (),
		updated_at datetime default getdate ()
	);
GO
CREATE TABLE
	promotions (
		id INT identity (1, 1) PRIMARY KEY,
		name NVARCHAR (100),
		description NVARCHAR (255),
		type VARCHAR(30) CHECK (type IN ('DISCOUNT', 'COMBO')),
		discount_value DECIMAL(10, 2),
		combo_price decimal(18, 2),
		qty int not null,
		start_at datetime default getdate (),
		end_at datetime default getdate (),
		is_active bit default 1,
		created_at datetime default getdate (),
		updated_at datetime default getdate ()
	);
GO

CREATE TABLE
	promotion_products (
		id INT IDENTITY PRIMARY KEY,
		promotion_id INT NOT NULL,
		product_item_id INT NULL,         -- nếu áp dụng cụ thể từng item
		require_qty int,
		is_gift bit default 0,
		gift_option varchar(20) not null,
		created_at datetime default getdate (),
		updated_at datetime default getdate (),
		foreign key (product_item_id) references product_items (id) ON DELETE CASCADE,
		foreign key (promotion_id) references promotions (id) ON DELETE CASCADE,
	);
GO
create table
	cost_histories (
		id int identity (1, 1) primary key,
		product_item_id int not null,
		cost decimal(18, 2) not null,
		created_at datetime default getdate (),
		foreign key (product_item_id) references product_items (id) ON DELETE CASCADE
	);
GO
create table
	price_histories (
		id int identity (1, 1) primary key,
		product_item_id int not null,
		price decimal(18, 2) not null,
		created_at datetime default getdate (),
		foreign key (product_item_id) references product_items (id) ON DELETE CASCADE
	);
GO
create table
	reviews (
		id int identity (1, 1) primary key,
		product_item_id int not null,
		account_id int not null,
		rating int not null,
		comment nvarchar (max),
		image_url1 nvarchar (255),
		image_url2 nvarchar (255),
		image_url3 nvarchar (255),
		video_url varchar(255),
		order_id int,
		created_at datetime default getdate (),
		foreign key (product_item_id) references product_items (id) ON DELETE CASCADE,
		foreign key (account_id) references accounts (id) ON DELETE CASCADE
	);
GO
create table
	cart_items (
		id int identity (1, 1) primary key,
		account_id int not null,
		product_item_id int,
		qty int, 
		combo_qty int null,--combo qty
		combo_id int null,
		combo_group varchar(30) null, -- dùng để nhận diện combo ví dụ 2-4-5 , 2-1-1
		combo_group_id UNIQUEIDENTIFIER  , -- để nhận diện combo nếu khác số lượng 
		is_gift bit null,
		created_at datetime default getdate (),
		updated_at datetime default getdate (),
		foreign key (product_item_id) references product_items (id),
		foreign key (account_id) references accounts (id) ON DELETE CASCADE,
		foreign key (combo_id) references promotions(id) ON DELETE CASCADE
	);

GO
create TABLE
	payment_methods (
		id INT identity (1, 1) PRIMARY KEY,
		name nVARCHAR (100) NOT NULL,
		description nvarchar (max),
		is_active bit DEFAULT 1,
		created_at DATETIME DEFAULT GETDATE (),
		updated_at DATETIME DEFAULT GETDATE ()
	);

GO
CREATE TABLE
	shipping_methods (
		id INT identity (1, 1) primary key,
		name nVARCHAR (100) NOT NULL,
		description TEXT,
		is_active bit DEFAULT 1,
		created_at DATETIME DEFAULT GETDATE (),
		updated_at DATETIME DEFAULT GETDATE ()
	);

GO
create table
	orders (
		id int identity (1, 1) primary key,
		account_id int not null,
		payment_method_id int not null,
		shipping_method_id int not null,
		shipping_status nvarchar (50)check(shipping_status in ('pending','ready_to_pick','picking','cancel','money_collect_picking',
		'picked','storing','transporting','sorting','delivering'
		,'money_collect_delivering','delivered','delivery_fail','waiting_to_return',
		'return','return_transporting','return_storing',
		'returning','return_fail','returned','exception','damage','lost')),
		estimated_shipping_fee decimal(18, 2) not null,
		freeship_coupon_code varchar(50),
		actual_shipping_fee decimal(18, 2) not null,
		discount_coupon_code varchar(50),
		discount_value decimal(18, 2),
		shipped_date date,
		payment_status nvarchar (50) check (payment_status in ('unpaid','paid','refunded')) ,
		note nvarchar (max) not null,
		point int not null,
		final_total decimal(18, 2) not null,
		order_infor nvarchar (max) not null,
		order_code VARCHAR(50),
		created_at datetime default getdate (),
		updated_at datetime default getdate (),
		foreign key (account_id) references accounts (id) ON DELETE CASCADE,
		foreign key (payment_method_id) references payment_methods (id) ON DELETE CASCADE,
		foreign key (shipping_method_id) references shipping_methods (id) ON DELETE CASCADE
	);

GO
create table
	cancels (
		id int identity (1, 1) primary key,
		order_id int not null,
		reason nvarchar (max) not null,
		is_paid bit not null,
		status nvarchar (50) not null,
		created_at datetime default getdate (),
		updated_at datetime default getdate (),
		foreign key (order_id) references orders (id) ON DELETE CASCADE
	);

GO
create table
	order_items (
		id int identity (1, 1) primary key,
		order_id int not null,
		product_item_id int not null,
		qty int not null,
		combo_qty int null, -- số lượng combo
		promotion_id int,-- kiểm tra xem combo nào đã đc áp dụng để trừ vào usage limit 
		combo_group varchar(30) null, -- dùng để nhận diện combo ví dụ 2-4-5 , 2-1-1
		combo_group_id UNIQUEIDENTIFIER, -- mỗi combo sẽ có id riêng, để dễ dàng thống kê
		price_at_buy decimal(18, 2) not null,
		cost_at_buy decimal(18,2) not null, 
		is_gift bit default 0,
		selling_price decimal(18, 2) not null,
		total AS (qty * selling_price) PERSISTED,
		created_at datetime default getdate (),
		updated_at datetime default getdate (),
		foreign key (order_id) references orders (id) ON DELETE CASCADE,
		foreign key (product_item_id) references product_items (id) ON DELETE CASCADE,
		foreign key (promotion_id) references promotions (id) ON DELETE CASCADE
	);

GO
create table
	customs (
		id int identity (1, 1) primary key,
		product_item_id int not null,
		account_id int not null, -- kh cần liên kết cũng đc
		canvas_json nvarchar (max) not null,
		image_url nvarchar (255) not null,
		design_name nvarchar (50) not null, -- mặt trước, mặt sau
		order_id int  null,
		created_at datetime default getdate (),
		updated_at datetime default getdate (),
		foreign key (product_item_id) references product_items (id) ON DELETE CASCADE,
		foreign key (order_id) references orders (id)
	);
GO
CREATE TABLE
	returns (
		id INT IDENTITY (1, 1) PRIMARY KEY,
		order_product_item_id INT NOT NULL,
		qty INT NOT NULL,
		reason NVARCHAR (MAX) NOT NULL,
		image_url1 VARCHAR(255) NOT NULL,
		image_url2 VARCHAR(255),
		image_url3 VARCHAR(255),
		video_url nvarchar (255) not null,
		is_returned_money BIT DEFAULT 0,
		refund_amount DECIMAL(18, 2) NOT NULL,
		return_type NVARCHAR (20) CHECK (return_type IN ('refund', 'exchange')) DEFAULT 'refund',
		refund_type NVARCHAR (50) CHECK (refund_type IN ('wallet', 'bank', 'point')),
		status NVARCHAR (50) CHECK (
			status IN ('pending', 'approved', 'rejected', 'done')
		) DEFAULT 'pending',
		processed_at DATETIME NULL,
		created_at DATETIME DEFAULT GETDATE (),
		updated_at DATETIME DEFAULT GETDATE (),
		FOREIGN KEY (order_product_item_id) REFERENCES order_items (id) ON DELETE CASCADE
	);

GO
CREATE TABLE
	coupons (
		id int identity (1, 1) primary key,
		code NVARCHAR (50) NOT NULL UNIQUE,
		description NVARCHAR (255),
		discount_type VARCHAR(20) CHECK (discount_type IN ('FREESHIP', 'G-DISCOUNT')),
		discount_value DECIMAL(10, 2) NOT NULL,
		min_order_amount DECIMAL(10, 2),
		max_discount_amount DECIMAL(10, 2),
		qty INT,
		usage_per_customer INT, -- số lần sử dụng coupon của khách hàng
		is_allow_voucher bit default 0,
		is_active BIT NOT NULL DEFAULT 1,
		customer_group NVARCHAR (50),
		start_at datetime default getdate (),
		end_at datetime default getdate (),
		created_at DATETIME DEFAULT GETDATE (),
		updated_at datetime default getdate ()
	);

GO
create table
	variants (
		id int identity (1, 1) primary key,
		name nvarchar (50) unique,
		created_at datetime default getdate (),
		updated_at datetime default getdate ()
	);

GO
create table
	variant_values (
		id int identity (1, 1) primary key,
		variant_id int not null,
		signal_sku nvarchar (50) unique,
		description nvarchar (max) not null,
		created_at datetime default getdate (),
		updated_at datetime default getdate (),
		foreign key (variant_id) references variants (id) ON DELETE CASCADE
	);


GO
CREATE TABLE
	e_wallets (
		id nvarchar(40) PRIMARY KEY,
		account_id INT NOT NULL UNIQUE,
		balance DECIMAL(18, 2) DEFAULT 0 CHECK (balance >= 0),
		wallet_type NVARCHAR (10) CHECK (wallet_type IN ('REAL', 'VIRTUAL')),
		is_active BIT DEFAULT 1,
		code_activce nvarchar(max),
		created_at DATETIME DEFAULT GETDATE (),
		FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE CASCADE
	);
GO
CREATE TABLE
	e_wallet_transactions (
		id INT IDENTITY (1, 1) PRIMARY KEY,
		wallet_id nvarchar(40) NOT NULL,
		amount DECIMAL(18, 2) NOT NULL,
		transaction_type NVARCHAR (50) CHECK (
			transaction_type IN ('TOP_UP', 'PURCHASE', 'WITHDRAW', 'RECEIVE', 'MOMOPAY')
		),
		order_id NVARCHAR(max) NOT NULL,
		status NVARCHAR(max) NOT NULL,
		related_wallet_id INT NULL,
		description NVARCHAR (255),
		created_at DATETIME DEFAULT GETDATE (),
		FOREIGN KEY (wallet_id) REFERENCES e_wallets (id) ON DELETE CASCADE
	);
	go
	
create table message(
	id int identity (1,1) primary key ,
	[user_id] int not null,
	key_message nvarchar(max),
	[notification] nvarchar(max),
	created_at DATETIME DEFAULT GETDATE (),
	FOREIGN KEY ([user_id]) REFERENCES accounts (id) ON DELETE CASCADE
)
	go 


INSERT INTO
	accounts (
		email,
		password,
		gender,
		birthday,
		fullname,
		avatar_url,
		phone,
		average_order_value,
		user_rank,
		total_spent,
		total_order,
		loyalty_point,
		created_at,
		updated_at
	)
VALUES
	(
		'adminCUDE@gmail.com',
		'$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a',
		1,
		'2005-06-05',
		'admin',
		NULL,
		'0866843926',
		0.00,
		N'Bạc',
		20.00,
		20,
		20,
		'2025-06-05 00:00:00.000',
		'2025-06-05 00:00:00.000'
	),(
		'nkha79323@gmail.com',
		'$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a',
		1,
		'2005-06-05',
		'admin',
		NULL,
		'0866843926',
		0.00,
		N'Bạc',
		20.00,
		20,
		20,
		'2025-06-05 00:00:00.000',
		'2025-06-05 00:00:00.000'
	),(
		'nnkha.lop10c1.c3songdoc@gmail.com',
		'$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a',
		1,
		'2005-06-05',
		'AdminToCountUserCatchEvent',
		NULL,
		'0866843926',
		0.00,
		N'Platinum',
		20.00,
		20,
		20,
		'2025-06-05 00:00:00.000',
		'2025-06-05 00:00:00.000'
	);

INSERT INTO
	roles (name, created_at, updated_at)
VALUES
	(
		'GUEST',
		'2025-06-05 00:00:00.000',
		'2025-06-05 00:00:00.000'
	),
	(
		'USER',
		'2025-06-05 00:00:00.000',
		'2025-06-05 00:00:00.000'
	),
	(
		'ADMIN',
		'2025-06-05 00:00:00.000',
		'2025-06-05 00:00:00.000'
	);

INSERT INTO
	authorities (account_id, role_id, created_at, updated_at)
VALUES
	(
		1,
		5,
		'1970-01-01 00:00:00.000',
		'1970-01-01 00:00:00.000'
	),
	(
		2,
		5,
		'1970-01-01 00:00:00.000',
		'1970-01-01 00:00:00.000'
	);



-- create TRIGGER trgg_auto_insert_history_cost_and_price
--   ON product_items
--  FOR INSERT, UPDATE
--  AS
--     BEGIN
--         SET NOCOUNT ON;

--        -- Chỉ chạy khi INSERT hoặc UPDATE cost/price
--        IF NOT EXISTS (SELECT 1 FROM deleted) OR UPDATE(cost) OR UPDATE(price)
--         BEGIN
--            INSERT INTO cost_histories(product_item_id, cost)
--             SELECT id, cost FROM inserted;

--             INSERT INTO price_histories(product_item_id, price)
--             SELECT id, price FROM inserted;

--             PRINT N'Đã thêm lịch sử thay đổi giá';
--       END
--    END

--UPDATE orders
--SET order_code = 'L3BXXN'
--WHERE id = 2;

--/**CREATE TRIGGER trg_update_user_address
--ON user_addresses
--AFTER UPDATE
--AS
--BEGIN
--  SET NOCOUNT ON;
--  UPDATE user_addresses
--  SET updated_at = GETDATE()
--  FROM inserted
--  WHERE user_addresses.id = inserted.id;
--END;**\

--USE master;
--ALTER DATABASE all_in_store SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
--DROP DATABASE all_in_store;
