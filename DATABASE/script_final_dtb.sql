--create database all_in_store;
--drop database all_in_store
-- use all_in_store
GO
-- Disable all constraints temporarily (optional safety)
EXEC sp_msforeachtable "ALTER TABLE ? NOCHECK CONSTRAINT ALL"

-- Drop constraints dynamically if they exist
-- You can use this pattern repeatedly for known constraints

IF OBJECT_ID('FK_roles_accounts', 'F') IS NOT NULL
    ALTER TABLE roles DROP CONSTRAINT FK_roles_accounts
	go
IF OBJECT_ID('FK_roles_authorities', 'F') IS NOT NULL
    ALTER TABLE roles DROP CONSTRAINT FK_roles_authorities
	go
-- Drop child tables first to avoid foreign key errors
IF OBJECT_ID('order_items', 'U') IS NOT NULL
    DROP TABLE order_items
	go
IF OBJECT_ID('returns', 'U') IS NOT NULL
    DROP TABLE returns
	go
IF OBJECT_ID('orders', 'U') IS NOT NULL
    DROP TABLE orders
	go
IF OBJECT_ID('cancels', 'U') IS NOT NULL
    DROP TABLE cancels
	go
IF OBJECT_ID('cart_items', 'U') IS NOT NULL
    DROP TABLE cart_items
	go
IF OBJECT_ID('reviews', 'U') IS NOT NULL
    DROP TABLE reviews
	go
IF OBJECT_ID('product_images', 'U') IS NOT NULL
    DROP TABLE product_images
	go
IF OBJECT_ID('PromotionProduct', 'U') IS NOT NULL
    DROP TABLE PromotionProduct
	go
IF OBJECT_ID('promotions', 'U') IS NOT NULL
    DROP TABLE promotions
	go
IF OBJECT_ID('price_histories', 'U') IS NOT NULL
    DROP TABLE price_histories
	go
IF OBJECT_ID('cost_histories', 'U') IS NOT NULL
    DROP TABLE cost_histories
	go
IF OBJECT_ID('product_items', 'U') IS NOT NULL
    DROP TABLE product_items
	go
IF OBJECT_ID('base_products', 'U') IS NOT NULL
    DROP TABLE base_products
	go
IF OBJECT_ID('categories', 'U') IS NOT NULL
    DROP TABLE categories
	go
IF OBJECT_ID('catalogs', 'U') IS NOT NULL
    DROP TABLE catalogs
	go
IF OBJECT_ID('custom', 'U') IS NOT NULL
    DROP TABLE custom
	go
IF OBJECT_ID('roles', 'U') IS NOT NULL
    DROP TABLE roles
	go
IF OBJECT_ID('authorities', 'U') IS NOT NULL
    DROP TABLE authorities
	go
IF OBJECT_ID('user_addresses', 'U') IS NOT NULL
    DROP TABLE user_addresses
	go
IF OBJECT_ID('accounts', 'U') IS NOT NULL
    DROP TABLE accounts
	go
IF OBJECT_ID('payment_methods', 'U') IS NOT NULL
    DROP TABLE payment_methods
	go
IF OBJECT_ID('shipping_methods', 'U') IS NOT NULL
    DROP TABLE shipping_methods
	go
IF OBJECT_ID('UserLogs', 'U') IS NOT NULL
    DROP TABLE UserLogs
	go
IF OBJECT_ID('news', 'U') IS NOT NULL
    DROP TABLE news
go
IF OBJECT_ID('coupons', 'U') IS NOT NULL
    DROP TABLE coupons
go
IF OBJECT_ID('variants', 'U') IS NOT NULL
    DROP TABLE variants
	go
IF OBJECT_ID('variant_values', 'U') IS NOT NULL
    DROP TABLE variant_values
go
IF OBJECT_ID('purchase_order_items', 'U') IS NOT NULL
    DROP TABLE purchase_order_items
go
IF OBJECT_ID('purchase_orders', 'U') IS NOT NULL
    DROP TABLE purchase_orders
go

create table accounts (
	id int identity(1,1) primary key,
	email varchar(100) unique not null,
	password varchar(100) not null,
	fullname nvarchar(100) not null,
	avatar varchar(255),
	phone varchar(15) null,
	average_order_value decimal(18,2) default 0,
	user_rank nvarchar(20) default N'Đồng',
	total_spent decimal(18,2) default 0,
	total_order int default 0,
	loyalty_point int default 0,
	created_at datetime default getdate(),
	updated_at datetime default getdate()
);
GO

create table roles (
	id int identity(1,2) primary key,
	name nvarchar(50) ,
	created_at datetime default getdate(),
	updated_at datetime default getdate()
)
create table authorities(
	id int identity(1,5) primary key,
	account_id int not null,
	role_id int not null,
	created_at datetime default getdate(),
	updated_at datetime default getdate(),
	foreign key (account_id) references accounts(id),
	foreign key (role_id) references roles(id)
)
GO

create table user_addresses (
	id int identity (1,1) primary key,
	account_id int not null,
	recipient_name nvarchar(100) not null,
	phone varchar(15) not null,
	province nvarchar(50) not null,
	district nvarchar(50) not null,
	ward nvarchar(50) not null,
	street nvarchar(50) not null, 
	label nvarchar(20),
	is_default bit default 1,
	note nvarchar(255),
	created_at datetime default getdate(),
	updated_at datetime default getdate(),
	foreign key (account_id) references accounts(id)
);
GO

create table catalogs(
	id int identity (1,1) primary key,
	name nvarchar(100) unique,
	created_at datetime default getdate(),
	updated_at datetime default getdate()
);
GO

create table categories (
	id int identity (1,1) primary key,
	catalog_id int not null,
	name nvarchar(100) unique,
	created_at datetime default getdate(),
	updated_at datetime default getdate(),
	foreign key (catalog_id) references catalogs(id)
);
GO

create table base_products(
	id int identity(1,1) primary key,
	name nvarchar(100) not null,
	material nvarchar(50) not null,
	category_id int not null,
	main_image varchar(100) not null,
	is_custom bit default 0,
	turn_buy int default 0,
	rating int default 0,
	is_promote bit default 0,
	is_active bit default 1,
	created_at datetime default getdate(),
	updated_at datetime default getdate(),
	foreign key (category_id) references categories(id)
);
GO

create table product_items(
	id int identity(1,1) primary key,
	base_id int not null,
	name nvarchar(100) not null,
	cost decimal(18,2) not null,
	price decimal(18,2) not null,
	is_promote bit default 0,
	turn_buy int default 0,
	description nvarchar(max),
	sku varchar(100),
	safety_stock int default 0,
	qty int default 0,
	sell_start datetime default getdate(),
	sell_end datetime default getdate(),
	created_at datetime default getdate(),
	updated_at datetime default getdate(),
	foreign key (base_id) references base_products(id)
);
GO

create table custom (
	id int identity(1,1) primary key,
	product_item_id int not null,
	infor nvarchar(max),
	foreign key (product_item_id) references product_items(id)
);
GO

CREATE TABLE UserLogs (
	id INT IDENTITY(1,1) PRIMARY KEY,
	user_id INT NOT NULL,
	action NVARCHAR(100) NOT NULL,
	description NVARCHAR(500),
	ip_address VARCHAR(45),
	user_agent NVARCHAR(255),
	created_at DATETIME DEFAULT GETDATE(),
	module NVARCHAR(100)
);
GO

create table product_images(
	id int identity(1,1) primary key,
	product_item_id int not null,
	image varchar(255) not null,
	created_at datetime default getdate(),
	updated_at datetime default getdate(),
	foreign key (product_item_id) references product_items(id)
);
GO

create table news (
	id int identity(1,1) primary key,
	image varchar(255) not null,
	is_home bit default 1,
	description nvarchar(max) not null,
	created_at datetime default getdate(),
	updated_at datetime default getdate()
);
GO

CREATE TABLE promotions (
	id INT identity(1,1) PRIMARY KEY,
	name NVARCHAR(100),
	description NVARCHAR(255),
	type VARCHAR(30) CHECK (type IN ('DISCOUNT','COMBO')),
	discount_type VARCHAR(20) CHECK (discount_type IN ('PERCENT','AMOUNT')),
	discount_value DECIMAL(10,2),
	combo_price decimal(18,2),
	usage_limit int not null,
	start_at datetime default getdate(),
	end_at datetime default getdate(),
	is_active bit default 1,
	created_at datetime default getdate(),
	updated_at datetime default getdate()
);
GO

CREATE TABLE PromotionProduct (
	id INT PRIMARY KEY,
	promotion_id INT NOT NULL,
	product_item_id INT NOT NULL,
	require_qty int,
	is_gift bit default 0,
	cost_share decimal(3,2),
	created_at datetime default getdate(),
	updated_at datetime default getdate(),
	foreign key (product_item_id) references product_items(id),
	foreign key (promotion_id) references promotions(id)
);
GO

create table cost_histories (
	id int identity(1,1) primary key,
	product_item_id int not null,
	cost decimal(18,2) not null,
	created_at datetime default getdate(),
	foreign key (product_item_id) references product_items(id)
);
GO

create table price_histories (
	id int identity(1,1) primary key,
	product_item_id int not null,
	price decimal(18,2) not null,
	created_at datetime default getdate(),
	foreign key (product_item_id) references product_items(id)
);
GO

create table reviews (
	id int identity(1,1) primary key,
	product_item_id int not null,
	account_id int not null,
	rating int not null,
	comment nvarchar(max),
	images varchar(max),
	created_at datetime default getdate(),
	foreign key (product_item_id) references product_items(id),
	foreign key (account_id) references accounts(id)
);
GO

create table cart_items (
	id int identity(1,1) primary key,
	account_id int not null,
	product_item_id int,
	qty int,
	created_at datetime default getdate(),
	updated_at datetime default getdate(),
	foreign key (product_item_id) references product_items(id),
	foreign key (account_id) references accounts(id)
);
GO

create TABLE payment_methods (
	id INT identity(1,1) PRIMARY KEY,
	name nVARCHAR(100) NOT NULL,
	description nvarchar(max),
	is_active bit DEFAULT 1,
	created_at DATETIME DEFAULT GETDATE(),
	updated_at DATETIME DEFAULT GETDATE()
);
GO

CREATE TABLE shipping_methods (
	id INT identity(1,1) primary key,
	name nVARCHAR(100) NOT NULL,
	description TEXT,
	is_active bit DEFAULT 1,
	created_at DATETIME DEFAULT GETDATE(),
	updated_at DATETIME DEFAULT GETDATE()
);
GO

create table orders (
	id int identity(1,1) primary key,
	account_id int not null,
	payment_method_id int not null,
	shipping_method_id int not null,
	shipping_status nvarchar(50) not null,
	estimated_shipping_fee decimal(18,2) not null,
	freeship_coupon_code varchar(50),
	actual_shipping_fee decimal(18,2) not null,
	discount_coupon_code varchar(50),
	discount_value decimal(18,2),
	shipped_date date,
	payment_status nvarchar(50) not null,
	note nvarchar(max) not null,
	point int not null,
	final_total decimal(18,2) not null,
	order_infor nvarchar(max) not null,
	created_at datetime default getdate(),
	updated_at datetime default getdate(),
	foreign key (account_id) references accounts(id),
	foreign key (payment_method_id) references payment_methods(id),
	foreign key (shipping_method_id) references shipping_methods(id)
);
GO

create table cancels (
	id int identity(1,1) primary key,
	order_id int not null,
	reason nvarchar(max) not null,
	is_paid bit not null,
	status nvarchar(50) not null,
	created_at datetime default getdate(),
	updated_at datetime default getdate(),
	foreign key (order_id) references orders(id)
);
GO

create table order_items (
	id int identity(1,1) primary key,
	order_id int not null,
	product_item_id int not null,
	promotion_id int,
	qty int not null,
	cost decimal(18,2) not null,
	is_gift bit default 0,
	selling_price decimal(18,2) not null,
	total AS (qty * selling_price) PERSISTED,
	coupon_code int,
	created_at datetime default getdate(),
	updated_at datetime default getdate(),
	foreign key (order_id) references orders(id),
	foreign key (product_item_id) references product_items(id),
	foreign key (promotion_id) references promotions(id)
);
GO

create table returns (
	id int identity(1,1) primary key,
	order_product_item_id int not null,
	reason nvarchar(max) not null,
	image1 varchar(255) not null,
	image2 varchar(255),
	image3 varchar(255),
	is_returned_money nvarchar(50) not null,
	is_returned_item nvarchar(50) not null,
	status nvarchar(50) not null,
	created_at datetime default getdate(),
	updated_at datetime default getdate(),
	foreign key (order_product_item_id) references order_items(id)
);
GO

CREATE TABLE coupons (
	id int identity(1,1) primary key,
	code NVARCHAR(50) NOT NULL UNIQUE,
	description NVARCHAR(255),
	discount_type VARCHAR(20) CHECK (discount_type IN ('FREESHIP','G-DISCOUNT')),
	discount_value DECIMAL(10,2) NOT NULL,
	min_order_amount DECIMAL(10,2),
	max_discount_amount DECIMAL(10,2),
	usage_limit INT,
	usage_per_customer INT,
	is_allow_voucher bit default 0,
	is_active BIT NOT NULL DEFAULT 1,
	customer_group NVARCHAR(50),
	start_at datetime default getdate(),
	end_at datetime default getdate(),
	created_at DATETIME DEFAULT GETDATE(),
	updated_at datetime default getdate()
);
GO

create table variants(
	id int identity(1,1) primary key,
	name nvarchar(50) unique,
	created_at datetime default getdate(),
	updated_at datetime default getdate()
);
GO

create table variant_values(
	id int identity(1,1) primary key,
	variant_id int not null,
	signal_sku nvarchar(50) unique,
	description nvarchar(max) not null,
	created_at datetime default getdate(),
	updated_at datetime default getdate(),
	foreign key (variant_id) references variants(id)
);
GO

CREATE TABLE purchase_orders (
	id INT PRIMARY KEY IDENTITY(1,1),
	order_date DATETIME DEFAULT GETDATE(),
	expected_date DATETIME,
	received_date DATETIME,
	total decimal(18,2) not null,
	status NVARCHAR(50),
	note NVARCHAR(500),
	created_at DATETIME DEFAULT GETDATE(),
	updated_at datetime default getdate()
);
GO

create TABLE purchase_order_items (
	id INT PRIMARY KEY IDENTITY(1,1),
	purchase_order_id INT NOT NULL,
	product_item_id INT NOT NULL,
	qty INT NOT NULL,
	cost DECIMAL(18, 2) NOT NULL,
	total_cost AS (qty * cost) PERSISTED,
	created_at DATETIME DEFAULT GETDATE(),
	updated_at DATETIME DEFAULT GETDATE(),
	FOREIGN KEY (product_item_id) REFERENCES product_items(id),
	FOREIGN KEY (purchase_order_id) REFERENCES purchase_orders(id)
);
GO
INSERT INTO accounts(
    email,
    password,
    fullname,
    avatar,
    phone,
    average_order_value,
    user_rank,
    total_spent,
    total_order,
    loyalty_point,
    created_at,
    updated_at
) VALUES (
    'adminCUDE@gmail.com',
    '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a',
    'admin',
    NULL,
    '0866843926',
    0.00,
    'Bạc',
    20.00,
    20,
    20,
    '2025-06-05 00:00:00.000',
    '2025-06-05 00:00:00.000'
);

INSERT INTO roles (
    name,
    created_at,
    updated_at
) VALUES
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
INSERT INTO authorities (
    account_id,
    role_id,
    created_at,
    updated_at
) VALUES (
    1,
    1,
    '1970-01-01 00:00:00.000',
    '1970-01-01 00:00:00.000'
);


