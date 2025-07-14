use all_in_store
INSERT INTO accounts (
    email, password, fullname, avatar_url, phone, average_order_value,
    user_rank, total_spent, total_order, loyalty_point, created_at, updated_at
)
VALUES
-- 1. Người dùng thường
('user1@example.com', '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a', N'Nguyễn Văn A', NULL, '0909123456', 100.00, N'Đồng', 500.00, 5, 50, GETDATE(), GETDATE()),

-- 2. Người dùng VIP
('user2@example.com', '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a', N'Lê Thị B', NULL, '0909988776', 500.00, N'Vàng', 3000.00, 10, 300, GETDATE(), GETDATE()),

-- 3. Người dùng mới đăng ký
('user3@example.com', '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a', N'Trần Văn C', NULL, '0909456123', 0.00, N'Đồng', 0.00, 0, 0, GETDATE(), GETDATE()),

-- 4. Người dùng có nhiều đơn hàng
('user4@example.com', '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a', N'Phạm Thị D', NULL, '0909777755', 300.00, N'Bạc', 6000.00, 20, 500, GETDATE(), GETDATE()),

-- 5. Người dùng Platinum
('user5@example.com', '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a', N'Lý Văn E', NULL, '0909555321', 800.00, N'Platinum', 12000.00, 15, 800, GETDATE(), GETDATE()),

-- 6. Người dùng có tên nước ngoài
('foreign.user@example.com', '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a', N'Anna Smith', NULL, '0911002200', 350.00, N'Vàng', 4200.00, 12, 200, GETDATE(), GETDATE());
GO
-- XÓA DỮ LIỆU CŨ
DELETE FROM product_items;
DELETE FROM base_products;
DELETE FROM categories;
DELETE FROM catalogs;

-- CATALOGS
INSERT INTO catalogs (name, created_at, updated_at)
VALUES
(N'Thời trang nam', GETDATE(), GETDATE()),
(N'Thời trang nữ', GETDATE(), GETDATE());

-- CATEGORIES
INSERT INTO categories (catalog_id, name, created_at, updated_at)
VALUES
-- Nam (1)
(1, N'Áo thun', GETDATE(), GETDATE()),
(1, N'Áo sơ mi', GETDATE(), GETDATE()),
(1, N'Quần jean', GETDATE(), GETDATE()),
(1, N'Áo khoác', GETDATE(), GETDATE()),
-- Nữ (2)
(2, N'Váy ngắn', GETDATE(), GETDATE()),
(2, N'Áo kiểu', GETDATE(), GETDATE()),
(2, N'Đầm công sở', GETDATE(), GETDATE()),
(2, N'Chân váy', GETDATE(), GETDATE());

-- BASE_PRODUCTS
INSERT INTO base_products (
    name, material, category_id, main_image_url, is_custom, turn_buy,
    rating, is_active, created_at, updated_at
)
VALUES
(N'Áo thun unisex mùa hè', N'Cotton 100%', 1, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 120, 4, 1, GETDATE(), GETDATE()),
(N'Áo thun trơn form rộng', N'Cotton lạnh', 1, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 95, 5, 1, GETDATE(), GETDATE()),
(N'Áo thun họa tiết Nhật Bản', N'Cotton lạnh', 1, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 110, 4, 1, GETDATE(), GETDATE()),
(N'Áo thun cổ tròn nam', N'Polyester', 1, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 75, 5, 1, GETDATE(), GETDATE()),

(N'Áo sơ mi trắng basic', N'Cotton', 2, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 90, 4, 1, GETDATE(), GETDATE()),
(N'Áo sơ mi caro đỏ đen', N'Flannel', 2, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 65, 5, 1, GETDATE(), GETDATE()),

(N'Quần jean ống rộng nam', N'Denim', 3, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 80, 4, 1, GETDATE(), GETDATE()),
(N'Quần jean rách gối', N'Denim co giãn', 3, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 60, 3, 1, GETDATE(), GETDATE()),
(N'Jean nam trơn basic', N'Denim', 3, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 100, 4, 1, GETDATE(), GETDATE()),
(N'Jean slim fit co giãn', N'Denim co giãn', 3, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 85, 5, 1, GETDATE(), GETDATE()),

(N'Áo khoác bomber nam', N'Polyester', 4, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 70, 4, 1, GETDATE(), GETDATE()),
(N'Áo khoác dù chống nước', N'Nylon', 4, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 40, 5, 1, GETDATE(), GETDATE()),

(N'Váy ngắn hoa nhí', N'Linen', 5, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 70, 5, 1, GETDATE(), GETDATE()),
(N'Váy tennis nữ', N'Polyester', 5, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 65, 4, 1, GETDATE(), GETDATE()),
(N'Váy caro chữ A', N'Kaki', 5, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 50, 4, 1, GETDATE(), GETDATE()),
(N'Váy xếp ly thời trang', N'Linen', 5, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 45, 5, 1, GETDATE(), GETDATE()),

(N'Áo croptop tay phồng', N'Voan', 6, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 60, 4, 1, GETDATE(), GETDATE()),
(N'Áo kiểu ren nữ tính', N'Ren', 6, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 35, 5, 1, GETDATE(), GETDATE()),

(N'Đầm body tay lỡ', N'Polyester', 7, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 40, 4, 1, GETDATE(), GETDATE()),
(N'Đầm xòe dự tiệc', N'Lụa', 7, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', 0, 55, 5, 1, GETDATE(), GETDATE());

-- PRODUCT_ITEMS (biến thể sản phẩm)
INSERT INTO product_items (
    base_id, cost, price, turn_buy, description, sku, safety_stock, qty,
    sell_start, sell_end, created_at, updated_at
)
VALUES
-- Áo thun
(1, 60000, 150000, 80, N'Áo thun M, trắng', 'TSHIRT1-M-WHITE', 10, 50, GETDATE(), NULL, GETDATE(), GETDATE()),
(1, 60000, 150000, 60, N'Áo thun L, đen', 'TSHIRT1-L-BLACK', 10, 40, GETDATE(), NULL, GETDATE(), GETDATE()),
(2, 65000, 160000, 70, N'Áo thun XL, xám', 'TSHIRT2-XL-RED', 10, 35, GETDATE(), NULL, GETDATE(), GETDATE()),
(3, 70000, 160000, 55, N'Áo Nhật Bản M', 'TS-JPN-M', 10, 40, GETDATE(), NULL, GETDATE(), GETDATE()),
(4, 68000, 155000, 60, N'Cổ tròn L', 'TS-ROUND-L', 10, 35, GETDATE(), NULL, GETDATE(), GETDATE()),

-- Áo sơ mi
(5, 80000, 180000, 90, N'Sơ mi trắng M', 'SM-WHITE-M', 10, 40, GETDATE(), NULL, GETDATE(), GETDATE()),
(6, 85000, 190000, 70, N'Sơ mi caro đỏ XL', 'SM-RED-XL', 10, 25, GETDATE(), NULL, GETDATE(), GETDATE()),

-- Quần jean
(7, 80000, 250000, 50, N'Jean size 30 xanh', 'JEAN1-30-BLUE', 5, 25, GETDATE(), NULL, GETDATE(), GETDATE()),
(8, 80000, 250000, 30, N'Jean size 32 đen', 'JEAN1-32-BLACK', 5, 20, GETDATE(), NULL, GETDATE(), GETDATE()),
(9, 85000, 200000, 75, N'Jean basic size 31', 'JN-BASIC-31', 10, 30, GETDATE(), NULL, GETDATE(), GETDATE()),
(10, 85000, 220000, 65, N'Jean slim fit 30', 'JN-SLIM-30', 10, 20, GETDATE(), NULL, GETDATE(), GETDATE()),

-- Áo khoác
(11, 120000, 300000, 60, N'Khoác bomber M', 'JK-BOMBER-M', 5, 20, GETDATE(), NULL, GETDATE(), GETDATE()),
(12, 130000, 310000, 45, N'Khoác dù XL', 'JK-DU-XL', 5, 18, GETDATE(), NULL, GETDATE(), GETDATE()),

-- Váy ngắn
(13, 70000, 200000, 45, N'Váy S, hồng', 'DRESS1-S-PINK', 5, 20, GETDATE(), NULL, GETDATE(), GETDATE()),
(14, 72000, 210000, 30, N'Váy tennis L', 'DRESS2-L-WHITE', 5, 18, GETDATE(), NULL, GETDATE(), GETDATE()),
(15, 68000, 185000, 38, N'Váy caro S', 'SK-CARO-S', 5, 15, GETDATE(), NULL, GETDATE(), GETDATE()),

-- Áo kiểu
(17, 74000, 220000, 33, N'Croptop M', 'TOP-CROP-M', 5, 22, GETDATE(), NULL, GETDATE(), GETDATE()),

-- Đầm
(19, 95000, 250000, 40, N'Đầm body S', 'DRESS-BODY-S', 5, 20, GETDATE(), NULL, GETDATE(), GETDATE()),
(20, 99000, 270000, 50, N'Đầm xòe M', 'DRESS-XOE-M', 5, 25, GETDATE(), NULL, GETDATE(), GETDATE());
GO
-- Giả sử product_item_id từ 1 đến 10 đã tồn tại
INSERT INTO product_images (product_item_id, image_url, created_at, updated_at)
VALUES
(1, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', GETDATE(), GETDATE()),
(1, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', GETDATE(), GETDATE()),
(2, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', GETDATE(), GETDATE()),
(3, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', GETDATE(), GETDATE()),
(4, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', GETDATE(), GETDATE()),
(5, 'https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png', GETDATE(), GETDATE());
GO
-- Giả sử account_id = 1 đến 3 đã tồn tại
INSERT INTO user_addresses (
    account_id, recipient_name, phone, province, district, ward,
    street, label, is_default, note, created_at, updated_at
)
VALUES
(1, N'Nguyễn Văn A', '0909123456', N'Hồ Chí Minh', N'Quận 1', N'Phường Bến Nghé', N'123 Lê Lợi', N'Nhà riêng', 1, NULL, GETDATE(), GETDATE()),
(2, N'Trần Thị B', '0912345678', N'Hà Nội', N'Hoàn Kiếm', N'Phường Hàng Trống', N'88 Tràng Tiền', N'Văn phòng', 1, NULL, GETDATE(), GETDATE()),
(3, N'Lê Văn C', '0987654321', N'Đà Nẵng', N'Hải Châu', N'Phường Thạch Thang', N'45 Hùng Vương', N'Nhà bố mẹ', 1, NULL, GETDATE(), GETDATE());
Go
INSERT INTO e_wallets (account_id, balance, wallet_type, is_active, created_at)
VALUES
(1, 500000, 'REAL', 1, GETDATE()),
(2, 0, 'REAL', 1, GETDATE()),
(3, 100000, 'VIRTUAL', 1, GETDATE());
GO
INSERT INTO e_wallet_transactions (
    wallet_id, amount, transaction_type, related_wallet_id, description, created_at
)
VALUES
(1, 500000, 'TOP_UP', NULL, N'Nạp tiền lần đầu', GETDATE()),
(3, 100000, 'RECEIVE', 1, N'Tặng thưởng từ hệ thống', GETDATE());
Go
INSERT INTO cart_items (
    account_id,combo_id,combo_group, product_item_id, qty,combo_qty,combo_group_id, created_at, updated_at
)
VALUES
(1,4,'1-2', 1, 1,1,'31D1386F-E158-4361-A106-234978CF856C', GETDATE(), GETDATE()),
(1,4,'1-2', 2, 1,1,'31D1386F-E158-4361-A106-234978CF856C', GETDATE(), GETDATE()),
(1,10,null,3, 1,null,'490CAE2E-873A-43F0-94A3-985C83D5C3FE', GETDATE(), GETDATE()
);
GO
-- INSERT payment_methods
INSERT INTO payment_methods (name, description, is_active, created_at, updated_at)
VALUES 
(N'Thanh toán khi nhận hàng (COD)', N'Thanh toán khi nhận hàng', 1, GETDATE(), GETDATE()),
(N'Thanh toán qua ví điện tử', N'Sử dụng ví tích hợp trong hệ thống', 1, GETDATE(), GETDATE());

-- INSERT shipping_methods
INSERT INTO shipping_methods (name, description, is_active, created_at, updated_at)
VALUES 
(N'Giao hàng tiêu chuẩn', N'Giao trong 2-5 ngày làm việc', 1, GETDATE(), GETDATE()),
(N'Giao hàng nhanh', N'Giao trong 24h nội thành', 1, GETDATE(), GETDATE());
GO
INSERT INTO orders (
    account_id, payment_method_id, shipping_method_id, shipping_status,
    estimated_shipping_fee, freeship_coupon_code, actual_shipping_fee,
    discount_coupon_code, discount_value, shipped_date,
    payment_status, note, point, final_total, order_infor,
    created_at, updated_at
)
VALUES
(1, 1, 1, N'Đang giao', 30000, NULL, 30000, 'SALE20', 20000, '2025-06-21',
 N'Đã thanh toán qua ví', N'Giao giờ hành chính', 20, 250000, N'[{"item":"Áo thun M","qty":2}]', GETDATE(), GETDATE()),

(2, 2, 1, N'Chờ xác nhận', 25000, NULL, 25000, NULL, 0, NULL,
 N'Chờ thanh toán', N'Giao sau 18h', 0, 180000, N'[{"item":"Áo sơ mi XL","qty":1}]', GETDATE(), GETDATE()),

(3, 1, 2, N'Hoàn tất', 20000, NULL, 0, 'FREESHIP', 0, '2025-06-19',
 N'Đã thanh toán', N'Giao sớm càng tốt', 30, 320000, N'[{"item":"Jean size 32","qty":1}, {"item":"Váy xếp ly","qty":1}]', GETDATE(), GETDATE());
GO
INSERT INTO promotions (
    name, description, type, discount_value,
    combo_price, qty, start_at, end_at,
    is_active, created_at, updated_at
)
VALUES
(N'Giảm 10% toàn site', N'Áp dụng cho tất cả đơn hàng từ 200K', 'DISCOUNT', 10.00, NULL, 200, GETDATE(), DATEADD(DAY, 30, GETDATE()), 1, GETDATE(), GETDATE()),
(N'Giảm 50K cho đơn trên 500K', N'Giảm giá cố định', 'DISCOUNT', 50000, NULL, 100, GETDATE(), DATEADD(DAY, 10, GETDATE()), 1, GETDATE(), GETDATE()),
(N'FREESHIP toàn quốc', N'Không cần nhập mã', 'DISCOUNT', 30000, NULL, 500, GETDATE(), DATEADD(DAY, 20, GETDATE()), 1, GETDATE(), GETDATE()),
(N'Mua 2 áo thun tặng 1', N'Áp dụng riêng cho áo thun mùa hè', 'COMBO', NULL, 210000, 50, GETDATE(), DATEADD(DAY, 7, GETDATE()), 1, GETDATE(), GETDATE()),
(N'Siêu combo 3 áo sơ mi', N'3 áo sơ mi giá chỉ 399K', 'COMBO', NULL, 399000, 30, GETDATE(), DATEADD(DAY, 14, GETDATE()), 1, GETDATE(), GETDATE()),
(N'Voucher sinh nhật', N'Chỉ áp dụng khi có ngày sinh nhật', 'DISCOUNT', 15.00, NULL, 100, GETDATE(), DATEADD(DAY, 15, GETDATE()), 1, GETDATE(), GETDATE()),
(N'Khách hàng mới', N'Ưu đãi lần đầu mua', 'DISCOUNT', 20.00, NULL, 150, GETDATE(), DATEADD(DAY, 10, GETDATE()), 1, GETDATE(), GETDATE()),
(N'Mua 1 tặng 1 khăn cổ', N'Khuyến mãi phụ kiện kèm áo sơ mi', 'COMBO', NULL, 0, 80, GETDATE(), DATEADD(DAY, 20, GETDATE()), 1, GETDATE(), GETDATE()),
(N'Flash sale cuối tuần', N'Tối đa 30%', 'DISCOUNT', 30.00, NULL, 50, GETDATE(), DATEADD(DAY, 3, GETDATE()), 1, GETDATE(), GETDATE()),
(N'Giảm 100K đơn trên 1 triệu', N'Tặng thêm voucher khi mua nhiều', 'DISCOUNT', 100000, NULL, 80, GETDATE(), DATEADD(DAY, 20, GETDATE()), 1, GETDATE(), GETDATE());
INSERT INTO promotions (
    name, description, type, discount_value,
    combo_price, qty, start_at, end_at,
    is_active, created_at, updated_at
)
VALUES
(N'Combo mùa thu', N'Mua 2 áo khoác giá chỉ 599K', 'COMBO', NULL, NULL, 599000, 40, GETDATE(), DATEADD(DAY, 25, GETDATE()), 1, GETDATE(), GETDATE()),
(N'Ưu đãi ví điện tử', N'Thanh toán bằng ví được giảm thêm 5%', 'DISCOUNT', 'PERCENT', 5.00, NULL, 300, GETDATE(), DATEADD(DAY, 10, GETDATE()), 1, GETDATE(), GETDATE()),
(N'Giảm 25% áo nữ', N'Chỉ áp dụng danh mục Áo nữ', 'DISCOUNT', 'PERCENT', 25.00, NULL, 120, GETDATE(), DATEADD(DAY, 15, GETDATE()), 1, GETDATE(), GETDATE()),
(N'Mua càng nhiều giảm càng sâu', N'Khuyến mãi theo cấp số nhân', 'DISCOUNT', 'PERCENT', 15.00, NULL, 200, GETDATE(), DATEADD(DAY, 20, GETDATE()), 1, GETDATE(), GETDATE()),
(N'Siêu khuyến mãi cho thành viên VIP', N'Giảm 20% mọi đơn hàng cho VIP', 'DISCOUNT', 'PERCENT', 20.00, NULL, 50, GETDATE(), DATEADD(DAY, 30, GETDATE()), 1, GETDATE(), GETDATE()),
(N'Quà tặng khách hàng trung thành', N'Mua đủ 5 đơn tặng 1 áo', 'COMBO', NULL, NULL, 0, 20, GETDATE(), DATEADD(DAY, 60, GETDATE()), 1, GETDATE(), GETDATE()),
(N'Tặng tất cả voucher 30K', N'Sự kiện ngày hội thành viên', 'DISCOUNT', 'AMOUNT', 30000, NULL, 300, GETDATE(), DATEADD(DAY, 5, GETDATE()), 1, GETDATE(), GETDATE()),
(N'Ưu đãi mùa lễ hội', N'Giảm 10% toàn bộ dịp lễ', 'DISCOUNT', 'PERCENT', 10.00, NULL, 150, GETDATE(), DATEADD(DAY, 15, GETDATE()), 1, GETDATE(), GETDATE()),
(N'Gói combo Áo + Quần giá sốc', N'Trọn gói chỉ 350K', 'COMBO', NULL, NULL, 350000, 30, GETDATE(), DATEADD(DAY, 15, GETDATE()), 1, GETDATE(), GETDATE()),
(N'Ưu đãi cuối tháng', N'Chốt sale giảm 12%', 'DISCOUNT', 'PERCENT', 12.00, NULL, 100, GETDATE(), DATEADD(DAY, 7, GETDATE()), 1, GETDATE(), GETDATE());
GO
INSERT INTO coupons (
    code, description, discount_type, discount_value,
    min_order_amount, max_discount_amount, qty,
    usage_per_customer, is_allow_voucher, is_active,
    customer_group, start_at, end_at, created_at, updated_at
)
VALUES
('COUPON001', N'Mô tả coupon số 1', 'FREESHIP', 30000, 200000, 30000, 100, 1, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 10, GETDATE()), GETDATE(), GETDATE()),
('COUPON002', N'Mô tả coupon số 2', 'G-DISCOUNT', 31000, NULL, 31000, 101, 2, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 11, GETDATE()), GETDATE(), GETDATE()),
('COUPON003', N'Mô tả coupon số 3', 'G-DISCOUNT', 32000, 200000, 32000, 102, 1, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 12, GETDATE()), GETDATE(), GETDATE()),
('COUPON004', N'Mô tả coupon số 4', 'FREESHIP', 33000, NULL, 33000, 103, 2, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 13, GETDATE()), GETDATE(), GETDATE()),
('COUPON005', N'Mô tả coupon số 5', 'G-DISCOUNT', 34000, 200000, 34000, 104, 1, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 14, GETDATE()), GETDATE(), GETDATE()),
('COUPON006', N'Mô tả coupon số 6', 'G-DISCOUNT', 35000, NULL, 35000, 105, 2, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 15, GETDATE()), GETDATE(), GETDATE()),
('COUPON007', N'Mô tả coupon số 7', 'FREESHIP', 36000, 200000, 36000, 106, 1, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 16, GETDATE()), GETDATE(), GETDATE()),
('COUPON008', N'Mô tả coupon số 8', 'G-DISCOUNT', 37000, NULL, 37000, 107, 2, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 17, GETDATE()), GETDATE(), GETDATE()),
('COUPON009', N'Mô tả coupon số 9', 'G-DISCOUNT', 38000, 200000, 38000, 108, 1, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 18, GETDATE()), GETDATE(), GETDATE()),
('COUPON010', N'Mô tả coupon số 10', 'FREESHIP', 39000, NULL, 39000, 109, 2, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 19, GETDATE()), GETDATE(), GETDATE()),
('COUPON011', N'Mô tả coupon số 11', 'G-DISCOUNT', 40000, 200000, 40000, 110, 1, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 20, GETDATE()), GETDATE(), GETDATE()),
('COUPON012', N'Mô tả coupon số 12', 'G-DISCOUNT', 41000, NULL, 41000, 111, 2, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 21, GETDATE()), GETDATE(), GETDATE()),
('COUPON013', N'Mô tả coupon số 13', 'FREESHIP', 42000, 200000, 42000, 112, 1, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 22, GETDATE()), GETDATE(), GETDATE()),
('COUPON014', N'Mô tả coupon số 14', 'G-DISCOUNT', 43000, NULL, 43000, 113, 2, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 23, GETDATE()), GETDATE(), GETDATE()),
('COUPON015', N'Mô tả coupon số 15', 'G-DISCOUNT', 44000, 200000, 44000, 114, 1, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 24, GETDATE()), GETDATE(), GETDATE()),
('COUPON016', N'Mô tả coupon số 16', 'FREESHIP', 45000, NULL, 45000, 115, 2, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 25, GETDATE()), GETDATE(), GETDATE()),
('COUPON017', N'Mô tả coupon số 17', 'G-DISCOUNT', 46000, 200000, 46000, 116, 1, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 26, GETDATE()), GETDATE(), GETDATE()),
('COUPON018', N'Mô tả coupon số 18', 'G-DISCOUNT', 47000, NULL, 47000, 117, 2, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 27, GETDATE()), GETDATE(), GETDATE()),
('COUPON019', N'Mô tả coupon số 19', 'FREESHIP', 48000, 200000, 48000, 118, 1, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 28, GETDATE()), GETDATE(), GETDATE()),
('COUPON020', N'Mô tả coupon số 20', 'G-DISCOUNT', 49000, NULL, 49000, 119, 2, 0, 1, N'ALL', GETDATE(), DATEADD(DAY, 29, GETDATE()), GETDATE(), GETDATE());
GO
INSERT INTO variants (name)
VALUES 
    (N'Màu sắc'),
    (N'Kích thước');
GO
INSERT INTO variant_values (variant_id, signal_sku, description)
VALUES 
    (1, N'RED', N'Màu đỏ'),
    (1, N'BLUE', N'Màu xanh dương'),
    (1, N'BLACK', N'Màu đen'),
    (1, N'WHITE', N'Màu trắng'),
    (2, N'S', N'Kích thước nhỏ'),
    (2, N'M', N'Kích thước vừa'),
    (2, N'L', N'Kích thước lớn'),
    (2, N'XL', N'Kích thước rất lớn');

	GO
-- Chèn đơn hàng mẫu
INSERT INTO orders (
    account_id, payment_method_id, shipping_method_id, shipping_status,
    estimated_shipping_fee, freeship_coupon_code, actual_shipping_fee,
    discount_coupon_code, discount_value, shipped_date, payment_status,
    note, point, final_total, order_infor, created_at, updated_at
)
VALUES
(1, 1, 1, N'Đang giao', 30000, NULL, 0, NULL, 0, GETDATE(), N'Đã thanh toán',
 N'Khách muốn giao giờ hành chính', 10, 479000, N'Áo thun đen x1, Áo sơ mi trắng x1', GETDATE(), GETDATE()),

(2, 2, 1, N'Đang xử lý', 30000, NULL, 30000, N'MEMBER30', 30000, NULL, N'Chưa thanh toán',
 N'Giao tận nơi trước 6h tối', 0, 269000, N'Áo thun trắng x1', GETDATE(), GETDATE());

 -- Giả sử product_item_id từ 1 → 10 và promotion_id từ 1 → 5 (nếu có)
INSERT INTO order_items (
    order_id, product_item_id, promotion_id, qty, cost, is_gift,
    selling_price, coupon_code, created_at, updated_at
)
VALUES
(1, 1, 1, 1, 100000, 0, 199000, NULL, GETDATE(), GETDATE()),
(1, 2, NULL, 1, 150000, 0, 280000, NULL, GETDATE(), GETDATE()),
(2, 3, 2, 1, 100000, 0, 269000, N'MEMBER30', GETDATE(), GETDATE());
-- Thêm dữ liệu cho bảng UserLogs
INSERT INTO UserLogs (user_id, action, description, ip_address, user_agent, created_at, module)
VALUES
(1, N'Đăng nhập', N'Đăng nhập thành công vào hệ thống', '192.168.1.10', N'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', GETDATE(), N'Authentication'),
(2, N'Xem sản phẩm', N'Xem chi tiết sản phẩm Áo thun unisex mùa hè', '192.168.1.11', N'Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1', GETDATE(), N'Product'),
(3, N'Thêm vào giỏ hàng', N'Thêm Áo thun XL, xám vào giỏ hàng', '192.168.1.12', N'Mozilla/5.0 (Linux; Android 10; SM-G973F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Mobile Safari/537.36', GETDATE(), N'Cart'),
(1, N'Cập nhật thông tin cá nhân', N'Thay đổi số điện thoại', '192.168.1.10', N'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', GETDATE(), N'Account Management'),
(4, N'Tìm kiếm sản phẩm', N'Tìm kiếm "quần jean nam"', '192.168.1.13', N'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', GETDATE(), N'Search');
GO

-- Thêm dữ liệu cho bảng news
INSERT INTO news (image_url, is_home, description, created_at, updated_at)
VALUES
('https://example.com/news/sale_he.jpg', 1, N'Khuyến mãi hè bùng nổ, giảm giá lên đến 50% toàn bộ sản phẩm!', GETDATE(), GETDATE()),
('https://example.com/news/bst_moi.jpg', 1, N'Ra mắt bộ sưu tập thời trang thu đông mới nhất 2025. Đừng bỏ lỡ!', GETDATE(), GETDATE()),
('https://example.com/news/event.jpg', 0, N'Sự kiện "Ngày hội thành viên" với hàng ngàn ưu đãi độc quyền.', GETDATE(), GETDATE());
GO

-- Thêm dữ liệu cho bảng reviews
-- Giả sử account_id 1, 2, 3 và product_item_id 1, 5, 7, 13 tồn tại
INSERT INTO reviews (product_item_id, account_id, rating, comment, image_url1, image_url2, image_url3, video_url, created_at)
VALUES
(1, 1, 5, N'Áo chất cotton rất mát, form đẹp, mình rất ưng ý!', 'https://example.com/reviews/rev1-1.jpg', NULL, NULL, NULL, GETDATE()),
(5, 2, 4, N'Áo sơ mi khá đẹp, vải dày dặn. Giao hàng nhanh.', 'https://example.com/reviews/rev2-1.jpg', 'https://example.com/reviews/rev2-2.jpg', NULL, NULL, GETDATE()),
(7, 3, 5, N'Quần jean ống rộng mặc thoải mái, đúng size. Rất đáng tiền.', NULL, NULL, NULL, NULL, GETDATE()),
(13, 1, 3, N'Váy hoa nhí đẹp nhưng hơi ngắn so với mình. Chất vải tốt.', 'https://example.com/reviews/rev4-1.jpg', NULL, NULL, NULL, GETDATE()),
(1, 4, 5, N'Mặc rất thoải mái, sẽ mua thêm màu khác!', 'https://example.com/reviews/rev5-1.jpg', NULL, NULL, NULL, GETDATE());
GO

-- Thêm dữ liệu cho bảng cancels (một đơn hàng bị hủy)
-- Giả sử order_id = 2 đã tồn tại và đang ở trạng thái 'Chờ xác nhận'
INSERT INTO cancels (order_id, reason, is_paid, status, created_at, updated_at)
VALUES
(2, N'Thay đổi ý định mua hàng', 0, N'Đã hủy', GETDATE(), GETDATE());
GO

-- Thêm dữ liệu cho bảng cost_histories
-- Giả sử product_item_id 1, 2, 5 đã tồn tại
INSERT INTO cost_histories (product_item_id, cost, created_at)
VALUES
(1, 55000, DATEADD(MONTH, -2, GETDATE())), -- Giá nhập cũ của sản phẩm 1
(1, 60000, GETDATE()),                     -- Giá nhập mới của sản phẩm 1
(2, 62000, DATEADD(MONTH, -1, GETDATE())),
(5, 75000, DATEADD(MONTH, -3, GETDATE()));
GO

-- Thêm dữ liệu cho bảng price_histories
-- Giả sử product_item_id 1, 2, 5 đã tồn tại
INSERT INTO price_histories (product_item_id, price, created_at)
VALUES
(1, 140000, DATEADD(MONTH, -2, GETDATE())), -- Giá bán cũ của sản phẩm 1
(1, 150000, GETDATE()),                     -- Giá bán mới của sản phẩm 1
(2, 155000, DATEADD(MONTH, -1, GETDATE())),
(5, 175000, DATEADD(MONTH, -3, GETDATE()));
GO

-- Thêm dữ liệu cho bảng purchase_orders
INSERT INTO purchase_orders (order_date, expected_date, received_date, total, status, note, created_at, updated_at)
VALUES
(GETDATE(), DATEADD(DAY, 7, GETDATE()), NULL, 2500000.00, N'Đang chờ nhập', N'Đơn hàng nhập kho áo thun và quần jean', GETDATE(), GETDATE()),
(DATEADD(MONTH, -1, GETDATE()), DATEADD(DAY, -20, GETDATE()), DATEADD(DAY, -25, GETDATE()), 1200000.00, N'Đã hoàn tất', N'Nhập kho bổ sung áo sơ mi', GETDATE(), GETDATE());
GO

-- Thêm dữ liệu cho bảng purchase_order_items
-- Giả sử purchase_order_id 1, 2 và product_item_id 1, 7, 5 đã tồn tại
INSERT INTO purchase_order_items (purchase_order_id, product_item_id, qty, cost, created_at, updated_at)
VALUES
(1, 1, 20, 60000, GETDATE(), GETDATE()), -- 20 Áo thun M, trắng
(1, 7, 10, 80000, GETDATE(), GETDATE()), -- 10 Jean size 30 xanh
(2, 5, 15, 80000, GETDATE(), GETDATE()); -- 15 Sơ mi trắng M
GO

-- Thêm dữ liệu cho bảng promotion_products
-- Liên kết một số sản phẩm cụ thể với các khuyến mãi đã có
-- Giả sử product_item_id 1 (Áo thun M, trắng) và promotion_id 4 (Mua 2 áo thun tặng 1)
-- Giả sử product_item_id 5 (Sơ mi trắng M) và promotion_id 5 (Siêu combo 3 áo sơ mi)
INSERT INTO promotion_products ( promotion_id, product_item_id, require_qty, is_gift, cost_share, created_at, updated_at)
VALUES
( 4, 1, 2, 0, 0.00, GETDATE(), GETDATE()), -- Áo thun unisex mùa hè (item 1) tham gia KM 'Mua 2 áo thun tặng 1'
( 4, 2, 2, 0, 0.00, GETDATE(), GETDATE()), -- Áo thun L, đen (item 2) cũng tham gia KM 'Mua 2 áo thun tặng 1'
( 5, 5, 3, 0, 0.00, GETDATE(), GETDATE()), -- Áo sơ mi trắng basic (item 5) tham gia KM 'Siêu combo 3 áo sơ mi'
( 5, 6, 3, 0, 0.00, GETDATE(), GETDATE()), -- Áo sơ mi caro đỏ đen (item 6) cũng tham gia KM 'Siêu combo 3 áo sơ mi'
( 8, 2, 1, 0, 0.00, GETDATE(), GETDATE()); -- Áo sơ mi trắng basic (item 5) tham gia KM 'Mua 1 tặng 1 khăn cổ'
GO

-- Thêm dữ liệu cho bảng returns
-- Giả sử order_item_id = 1 (Áo thun M trong đơn 1) đã tồn tại
INSERT INTO returns (order_product_item_id, qty, reason, image_url1, image_url2, image_url3, video_url, is_returned_money, refund_amount, return_type, refund_type, status, processed_at, created_at, updated_at)
VALUES
(1, 1, N'Không vừa size', 'https://example.com/returns/return1-1.jpg', NULL, NULL, 'https://example.com/returns/return1.mp4', 0, 199000.00, 'EXCHANGE', NULL, N'PENDING', NULL, GETDATE(), GETDATE());
GO



INSERT INTO news (image, is_home, description, created_at, updated_at)
VALUES
-- Tin khuyến mãi & sự kiện
('https://example.com/news/newyear_sale.jpg', 1, N'Tưng bừng đón năm mới – giảm giá 30% toàn bộ sản phẩm!', GETDATE(), GETDATE()),
('https://example.com/news/black_friday.jpg', 1, N'Black Friday - săn deal sốc giảm đến 70% chỉ trong 3 ngày!', GETDATE(), GETDATE()),
('https://example.com/news/flash_sale.jpg', 0, N'Flash Sale mỗi ngày lúc 12h trưa – Sản phẩm giá hủy diệt!', GETDATE(), GETDATE()),

-- Tin thời trang & sản phẩm mới
('https://example.com/news/summer_collection.jpg', 1, N'Khám phá BST mùa hè trẻ trung, năng động với nhiều mẫu mã mới.', GETDATE(), GETDATE()),
('https://example.com/news/fashion_tips.jpg', 0, N'5 mẹo phối đồ cực chất bạn không thể bỏ qua mùa này.', GETDATE(), GETDATE()),
('https://example.com/news/new_arrivals.jpg', 1, N'Hàng mới về mỗi tuần – Cập nhật xu hướng nhanh nhất!', GETDATE(), GETDATE()),

-- Tin thương hiệu & cộng đồng
('https://example.com/news/charity.jpg', 0, N'Thương hiệu đồng hành cùng chiến dịch "Áo ấm đến trường".', GETDATE(), GETDATE()),
('https://example.com/news/celebrity_collab.jpg', 1, N'Kết hợp cùng người nổi tiếng: BST độc quyền chỉ có tại đây.', GETDATE(), GETDATE()),
('https://example.com/news/recycle_program.jpg', 0, N'Chương trình tái chế đồ cũ – Góp phần bảo vệ môi trường.', GETDATE(), GETDATE());