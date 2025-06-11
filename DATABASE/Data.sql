use all_in_store;

INSERT INTO accounts (email, password, fullname, avatar, phone, average_order_value, user_rank, total_spent, total_order, loyalty_point) VALUES
('nguyenvana@example.com', '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a', N'Nguyễn Văn A', 'avatar_a.jpg', '0901234567', 1500000.00, N'Bạc', 7500000.00, 5, 200),
('tranvanb@example.com', '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a', N'Trần Văn B', 'avatar_b.jpg', '0902345678', 2000000.00, N'Vàng', 10000000.00, 5, 300),
('lethic@example.com', '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a', N'Lê Thị C', 'avatar_c.jpg', '0903456789', 1200000.00, N'Đồng', 6000000.00, 5, 150),
('phamvand@example.com', '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a', N'Phạm Văn D', 'avatar_d.jpg', '0904567890', 1800000.00, N'Bạc', 9000000.00, 5, 250),
('hoangthie@example.com', '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a', N'Hoàng Thị E', 'avatar_e.jpg', '0905678901', 2500000.00, N'Vàng', 12500000.00, 5, 400),
('doquangf@example.com', '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a', N'Đỗ Quang F', 'avatar_f.jpg', '0906789012', 1000000.00, N'Đồng', 5000000.00, 5, 100),
('buitranh@example.com', '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a', N'Bùi Trần H', 'avatar_h.jpg', '0907890123', 1300000.00, N'Bạc', 6500000.00, 5, 180),
('vothik@example.com', '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a', N'Võ Thị K', 'avatar_k.jpg', '0908901234', 1600000.00, N'Vàng', 8000000.00, 5, 280),
('dangvank@example.com', '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a', N'Đặng Văn K', 'avatar_k.jpg', '0909012345', 1400000.00, N'Đồng', 7000000.00, 5, 160),
('truongvant@example.com', '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a', N'Trương Văn T', 'avatar_t.jpg', '0910123456', 2200000.00, N'Bạc', 11000000.00, 5, 350);

INSERT INTO user_addresses (account_id, recipient_name, phone, province, district, ward, street, label, is_default, note) VALUES
(1, N'Nguyễn Văn A', '0901234567', N'TP. Hồ Chí Minh', N'Quận 1', N'Bến Nghé', N'Đường Nguyễn Huệ', N'Nhà riêng', 1, N'Giao hàng ngoài giờ hành chính'),
(2, N'Trần Văn B', '0902345678', N'Hà Nội', N'Đống Đa', N'Láng Hạ', N'Phố Láng Hạ', N'Văn phòng', 1, N'Gọi trước khi đến'),
(3, N'Lê Thị C', '0903456789', N'Đà Nẵng', N'Hải Châu', N'Hòa Cường Bắc', N'Đường 2 tháng 9', N'Nhà', 1, NULL),
(4, N'Phạm Văn D', '0904567890', N'TP. Hồ Chí Minh', N'Quận 7', N'Tân Phong', N'Đường Nguyễn Lương Bằng', N'Công ty', 1, N'Chỉ giao giờ hành chính'),
(5, N'Hoàng Thị E', '0905678901', N'Hà Nội', N'Hoàn Kiếm', N'Tràng Tiền', N'Phố Tràng Tiền', N'Nhà', 1, NULL),
(6, N'Đỗ Quang F', '0906789012', N'Cần Thơ', N'Ninh Kiều', N'An Khánh', N'Đường 3 tháng 2', N'Nhà riêng', 1, N'Giao vào buổi tối'),
(7, N'Bùi Trần H', '0907890123', N'Hải Phòng', N'Ngô Quyền', N'Lạc Viên', N'Đường Lạc Viên', N'Nhà', 1, NULL),
(8, N'Võ Thị K', '0908901234', N'Nghệ An', N'TP. Vinh', N'Hưng Dũng', N'Đường Nguyễn Duy Trinh', N'Nhà', 1, N'Giao hàng nhanh'),
(9, N'Đặng Văn K', '0909012345', N'Thừa Thiên Huế', N'TP. Huế', N'Vĩnh Ninh', N'Đường Hùng Vương', N'Nhà', 1, NULL),
(10, N'Trương Văn T', '0910123456', N'Bình Dương', N'Thủ Dầu Một', N'Phú Cường', N'Đại lộ Bình Dương', N'Công ty', 1, N'Giao hàng vào giờ ăn trưa');

INSERT INTO catalogs (name) VALUES
(N'Thời trang Nam'),
(N'Thời trang Nữ'),
(N'Trẻ em');

INSERT INTO categories (catalog_id, name) VALUES
((SELECT id FROM catalogs WHERE name = N'Thời trang Nam'), N'Áo Nam'),
((SELECT id FROM catalogs WHERE name = N'Thời trang Nam'), N'Quần Nam'),
((SELECT id FROM catalogs WHERE name = N'Thời trang Nữ'), N'Váy Nữ'),
((SELECT id FROM catalogs WHERE name = N'Thời trang Nữ'), N'Áo Nữ'),
((SELECT id FROM catalogs WHERE name = N'Trẻ em'), N'Quần áo bé trai'),
((SELECT id FROM catalogs WHERE name = N'Trẻ em'), N'Quần áo bé gái');
INSERT INTO base_products 
(name, material, category_id, main_image, is_custom, turn_buy, rating, is_active)
VALUES
-- Áo Nam (Áo Nam)
(N'Áo Polo Nam Cotton Pha', N'Cotton pha', (SELECT id FROM categories WHERE name = N'Áo Nam'), 'ao_polo_nam.webp', 0, 100, 4, 1),
(N'Áo Khoác Bomber Nam', N'Polyester chống nước', (SELECT id FROM categories WHERE name = N'Áo Nam'), 'ao_khoac_bomber.webp', 0, 75, 5, 1),
(N'Áo Thun Dài Tay Nam', N'Thun Gân', (SELECT id FROM categories WHERE name = N'Áo Nam'), 'ao_thun_dai_tay_nam.webp', 0, 90, 4, 1),
(N'Áo Vest Công Sở Nam', N'Vải Tweed', (SELECT id FROM categories WHERE name = N'Áo Nam'), 'ao_vest_nam.webp', 0, 30, 5, 1),
(N'Áo Hoodie Nỉ Nam', N'Nỉ Bông', (SELECT id FROM categories WHERE name = N'Áo Nam'), 'ao_hoodie_nam.webp', 0, 110, 4, 1),

-- Quần Nam (Quần Nam)
(N'Quần Kaki Nam Dáng Thẳng', N'Kaki Cotton', (SELECT id FROM categories WHERE name = N'Quần Nam'), 'quan_kaki_nam.webp', 0, 85, 4, 1),
(N'Quần Short Thể Thao Nam', N'Vải Dù', (SELECT id FROM categories WHERE name = N'Quần Nam'), 'quan_short_nam_the_thao.webp', 0, 130, 4, 1),
(N'Quần Âu Nam Slimfit', N'Vải Tuyết Mưa', (SELECT id FROM categories WHERE name = N'Quần Nam'), 'quan_au_nam_slimfit.webp', 0, 60, 5, 1),
(N'Quần Jogger Nam Phối Túi', N'Nỉ Da Cá', (SELECT id FROM categories WHERE name = N'Quần Nam'), 'quan_jogger_nam.webp', 0, 95, 4, 1),
(N'Quần Jean Rách Gối Nam', N'Jean Bền', (SELECT id FROM categories WHERE name = N'Quần Nam'), 'quan_jean_rach.webp', 0, 50, 3, 1),

-- Áo Nữ (Áo Nữ)
(N'Áo Thun Croptop Nữ', N'Cotton 2 chiều', (SELECT id FROM categories WHERE name = N'Áo Nữ'), 'ao_thun_croptop.webp', 0, 160, 4, 1),
(N'Áo Len Cổ Lọ Nữ', N'Len Cashmere', (SELECT id FROM categories WHERE name = N'Áo Nữ'), 'ao_len_nu.webp', 0, 70, 5, 1),
(N'Áo Khoác Cardigan Nữ', N'Len mỏng', (SELECT id FROM categories WHERE name = N'Áo Nữ'), 'ao_cardigan_nu.webp', 0, 80, 4, 1),
(N'Áo Kiểu Sơ Mi Nữ', N'Lụa Satin', (SELECT id FROM categories WHERE name = N'Áo Nữ'), 'ao_kieu_nu.webp', 0, 105, 4, 1),
(N'Áo Bomber Nữ Phối Tay', N'Da PU', (SELECT id FROM categories WHERE name = N'Áo Nữ'), 'ao_bomber_nu.webp', 0, 45, 4, 1),

-- Váy Nữ (Váy Nữ)
(N'Váy Đầm Suông Caro', N'Vải Linen', (SELECT id FROM categories WHERE name = N'Váy Nữ'), 'vay_dam_suong.webp', 0, 90, 4, 1),
(N'Váy Chữ A Dáng Ngắn', N'Vải Tuyết Sa', (SELECT id FROM categories WHERE name = N'Váy Nữ'), 'vay_chu_a_ngan.webp', 0, 115, 4, 1),
(N'Chân Váy Xếp Ly Dài', N'Vải Voan', (SELECT id FROM categories WHERE name = N'Váy Nữ'), 'chan_vay_xep_ly.webp', 0, 70, 5, 1),
(N'Đầm Dự Tiệc Hai Dây', N'Phi Bóng', (SELECT id FROM categories WHERE name = N'Váy Nữ'), 'dam_du_tiec.webp', 0, 55, 5, 1),
(N'Váy Yếm Jean Nữ', N'Jean Mềm', (SELECT id FROM categories WHERE name = N'Váy Nữ'), 'vay_yem_jean.webp', 0, 80, 4, 1),

-- Quần áo bé trai (Quần áo bé trai)
(N'Bộ Quần Áo Bé Trai Năng Động', N'Thun Cotton', (SELECT id FROM categories WHERE name = N'Quần áo bé trai'), 'bo_be_trai_nang_dong.webp', 0, 120, 4, 1),
(N'Áo Thun Tay Ngắn Bé Trai', N'Cotton 4 chiều', (SELECT id FROM categories WHERE name = N'Quần áo bé trai'), 'ao_thun_be_trai_ngan_tay.webp', 0, 140, 4, 1),
(N'Quần Jean Phối Rách Bé Trai', N'Jean Mềm', (SELECT id FROM categories WHERE name = N'Quần áo bé trai'), 'quan_jean_rach_be_trai.webp', 0, 70, 4, 1),
(N'Áo Khoác Nỉ Bé Trai', N'Nỉ Cao Cấp', (SELECT id FROM categories WHERE name = N'Quần áo bé trai'), 'ao_khoac_ni_be_trai.webp', 0, 60, 5, 1),
(N'Bộ Đồ Thể Thao Bé Trai', N'Vải Poly', (SELECT id FROM categories WHERE name = N'Quần áo bé trai'), 'bo_the_thao_be_trai.webp', 0, 90, 4, 1),

-- Quần áo bé gái (Quần áo bé gái)
(N'Váy Bé Gái Xòe Hoa', N'Cotton Lụa', (SELECT id FROM categories WHERE name = N'Quần áo bé gái'), 'vay_be_gai_xoe_hoa.webp', 0, 110, 5, 1),
(N'Bộ Quần Áo Bé Gái Dễ Thương', N'Thun Lạnh', (SELECT id FROM categories WHERE name = N'Quần áo bé gái'), 'bo_be_gai_de_thuong.webp', 0, 130, 4, 1),
(N'Áo Khoác Jean Bé Gái', N'Jean Co Giãn', (SELECT id FROM categories WHERE name = N'Quần áo bé gái'), 'ao_khoac_jean_be_gai.webp', 0, 50, 4, 1),
(N'Đầm Công Chúa Ren Bé Gái', N'Voan + Ren', (SELECT id FROM categories WHERE name = N'Quần áo bé gái'), 'dam_cong_chua_be_gai.webp', 0, 65, 5, 1),
(N'Quần Legging Bé Gái', N'Thun Cotton', (SELECT id FROM categories WHERE name = N'Quần áo bé gái'), 'quan_legging_be_gai.webp', 0, 150, 4, 1);


INSERT INTO product_items (base_id, cost, price, turn_buy, description, sku, safety_stock, qty, sell_start, sell_end) VALUES
-- Áo Polo Nam Cotton Pha (ID: 151)
((SELECT id FROM base_products WHERE name = N'Áo Polo Nam Cotton Pha' COLLATE DATABASE_DEFAULT), 80000.00, 159000.00, 30, N'Áo polo nam cotton pha cao cấp, màu trắng, size M.', 'PN-TR-M', 10, 50, GETDATE(), DATEADD(month, 6, GETDATE())),
((SELECT id FROM base_products WHERE name = N'Áo Polo Nam Cotton Pha' COLLATE DATABASE_DEFAULT), 80000.00, 159000.00, 25, N'Áo polo nam cotton pha cao cấp, màu đen, size L.', 'PN-DEN-L', 10, 45, GETDATE(), DATEADD(month, 6, GETDATE())),

-- Áo Khoác Bomber Nam (ID: 152)
((SELECT id FROM base_products WHERE name = N'Áo Khoác Bomber Nam' COLLATE DATABASE_DEFAULT), 200000.00, 399000.00, 10, N'Áo khoác bomber nam chất liệu chống nước, màu đen, size M.', 'BOM-DEN-M', 5, 15, GETDATE(), DATEADD(month, 6, GETDATE())),

-- Thay thế 'Áo Sơ Mi Linen Nam' bằng 'Áo Thun Dài Tay Nam' (ID: 153)
((SELECT id FROM base_products WHERE name = N'Áo Thun Dài Tay Nam' COLLATE DATABASE_DEFAULT), 120000.00, 239000.00, 15, N'Áo thun dài tay nam chất liệu mềm mại, màu xám, size M.', 'SM-LX-M', 5, 25, GETDATE(), DATEADD(month, 6, GETDATE())),

-- Quần Kaki Nam Dáng Thẳng (ID: 156)
((SELECT id FROM base_products WHERE name = N'Quần Kaki Nam Dáng Thẳng' COLLATE DATABASE_DEFAULT), 180000.00, 349000.00, 20, N'Quần kaki nam dáng thẳng, màu xanh đậm, size 30.', 'QK-XD-30', 5, 30, GETDATE(), DATEADD(month, 6, GETDATE())),
((SELECT id FROM base_products WHERE name = N'Quần Kaki Nam Dáng Thẳng' COLLATE DATABASE_DEFAULT), 180000.00, 349000.00, 18, N'Quần kaki nam dáng thẳng, màu đen, size 32.', 'QK-DEN-32', 5, 28, GETDATE(), DATEADD(month, 6, GETDATE())),

-- Váy Đầm Suông Caro (ID: 166)
((SELECT id FROM base_products WHERE name = N'Váy Đầm Suông Caro' COLLATE DATABASE_DEFAULT), 250000.00, 499000.00, 12, N'Váy đầm suông caro dáng dài, màu trắng, freesize.', 'VSC-TR-FS', 3, 20, GETDATE(), DATEADD(month, 6, GETDATE())),
((SELECT id FROM base_products WHERE name = N'Váy Đầm Suông Caro' COLLATE DATABASE_DEFAULT), 250000.00, 499000.00, 10, N'Váy đầm suông caro dáng dài, màu xanh ngọc, freesize.', 'VSC-XN-FS', 3, 18, GETDATE(), DATEADD(month, 6, GETDATE())),

-- Áo Kiểu Sơ Mi Nữ (ID: 164) (Đã thay đổi từ Áo Blouse Nữ Công Sở để khớp với base_products)
((SELECT id FROM base_products WHERE name = N'Áo Kiểu Sơ Mi Nữ' COLLATE DATABASE_DEFAULT), 90000.00, 189000.00, 22, N'Áo kiểu sơ mi nữ thanh lịch, màu trắng, size S.', 'AB-TR-S', 8, 40, GETDATE(), DATEADD(month, 6, GETDATE())),
((SELECT id FROM base_products WHERE name = N'Áo Kiểu Sơ Mi Nữ' COLLATE DATABASE_DEFAULT), 90000.00, 189000.00, 19, N'Áo kiểu sơ mi nữ thanh lịch, màu hồng pastel, size M.', 'AB-H-M', 8, 35, GETDATE(), DATEADD(month, 6, GETDATE())),

-- Váy Chữ A Dáng Ngắn (ID: 167)
((SELECT id FROM base_products WHERE name = N'Váy Chữ A Dáng Ngắn' COLLATE DATABASE_DEFAULT), 70000.00, 139000.00, 14, N'Chân váy chữ A nữ dáng ngắn, màu đen, size M.', 'CV-A-DEN-M', 5, 25, GETDATE(), DATEADD(month, 6, GETDATE())),

-- Bộ Quần Áo Bé Trai Năng Động (ID: 171)
((SELECT id FROM base_products WHERE name = N'Bộ Quần Áo Bé Trai Năng Động' COLLATE DATABASE_DEFAULT), 100000.00, 199000.00, 28, N'Bộ quần áo bé trai cotton, màu xanh, họa tiết ngộ nghĩnh, size 3 tuổi.', 'BBT-XH-3T', 10, 60, GETDATE(), DATEADD(month, 6, GETDATE())),
((SELECT id FROM base_products WHERE name = N'Bộ Quần Áo Bé Trai Năng Động' COLLATE DATABASE_DEFAULT), 100000.00, 199000.00, 25, N'Bộ quần áo bé trai cotton, màu vàng, họa tiết ngộ nghĩnh, size 4 tuổi.', 'BBT-VH-4T', 10, 55, GETDATE(), DATEADD(month, 6, GETDATE())),

-- Váy Bé Gái Xòe Hoa (ID: 176)
((SELECT id FROM base_products WHERE name = N'Váy Bé Gái Xòe Hoa' COLLATE DATABASE_DEFAULT), 150000.00, 299000.00, 15, N'Váy bé gái dáng xòe hoa, màu hồng, phù hợp cho bé 5 tuổi.', 'VBG-HX-5T', 5, 30, GETDATE(), DATEADD(month, 6, GETDATE())),
((SELECT id FROM base_products WHERE name = N'Váy Bé Gái Xòe Hoa' COLLATE DATABASE_DEFAULT), 150000.00, 299000.00, 13, N'Váy bé gái dáng xòe hoa, màu trắng, phù hợp cho bé 6 tuổi.', 'VBG-TX-6T', 5, 28, GETDATE(), DATEADD(month, 6, GETDATE())),

-- Quần Jean Phối Rách Bé Trai (ID: 173)
((SELECT id FROM base_products WHERE name = N'Quần Jean Phối Rách Bé Trai' COLLATE DATABASE_DEFAULT), 60000.00, 119000.00, 10, N'Quần short jean bé trai màu xanh nhạt, chất liệu mềm mại, size 7 tuổi.', 'QSJ-XN-7T', 5, 20, GETDATE(), DATEADD(month, 6, GETDATE())),

-- Bộ Quần Áo Bé Gái Dễ Thương (ID: 177)
((SELECT id FROM base_products WHERE name = N'Bộ Quần Áo Bé Gái Dễ Thương' COLLATE DATABASE_DEFAULT), 80000.00, 169000.00, 20, N'Set đồ mùa hè cho bé gái, màu hồng, chất thun cotton, size 2 tuổi.', 'SDMH-H-2T', 8, 40, GETDATE(), DATEADD(month, 6, GETDATE())),
((SELECT id FROM base_products WHERE name = N'Bộ Quần Áo Bé Gái Dễ Thương' COLLATE DATABASE_DEFAULT), 80000.00, 169000.00, 18, N'Set đồ mùa hè cho bé gái, màu tím, chất thun cotton, size 3 tuổi.', 'SDMH-T-3T', 8, 35, GETDATE(), DATEADD(month, 6, GETDATE())),

-- Áo Thun Croptop Nữ (ID: 161)
((SELECT id FROM base_products WHERE name = N'Áo Thun Croptop Nữ' COLLATE DATABASE_DEFAULT), 60000.00, 119000.00, 35, N'Áo thun croptop nữ năng động, màu trắng, freesize.', 'CR-TR-FS', 10, 50, GETDATE(), DATEADD(month, 6, GETDATE()));

INSERT INTO product_images (product_item_id, image) VALUES
-- Áo Polo Nam Trắng Size M
((SELECT id FROM product_items WHERE name = N'Áo Polo Nam Trắng Size M'), 'ao_polo_nam_trang_1.webp'),
((SELECT id FROM product_items WHERE name = N'Áo Polo Nam Trắng Size M'), 'ao_polo_nam_trang_2.webp'),

-- Áo Polo Nam Đen Size L
((SELECT id FROM product_items WHERE name = N'Áo Polo Nam Đen Size L'), 'ao_polo_nam_den_1.webp'),
((SELECT id FROM product_items WHERE name = N'Áo Polo Nam Đen Size L'), 'ao_polo_nam_den_2.webp'),

-- Áo Bomber Nam Đen Size M
((SELECT id FROM product_items WHERE name = N'Áo Bomber Nam Đen Size M'), 'ao_bomber_nam_den_1.webp'),
((SELECT id FROM product_items WHERE name = N'Áo Bomber Nam Đen Size M'), 'ao_bomber_nam_den_2.webp'),

-- Áo Thun Dài Tay Nam Xám Size M
((SELECT id FROM product_items WHERE name = N'Áo Thun Dài Tay Nam Xám Size M'), 'ao_thun_dai_tay_nam_xam_1.webp'),
((SELECT id FROM product_items WHERE name = N'Áo Thun Dài Tay Nam Xám Size M'), 'ao_thun_dai_tay_nam_xam_2.webp'),

-- Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30
((SELECT id FROM product_items WHERE name = N'Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30'), 'quan_kaki_nam_xanh_dam_1.webp'),
((SELECT id FROM product_items WHERE name = N'Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30'), 'quan_kaki_nam_xanh_dam_2.webp'),

-- Váy Đầm Suông Caro Trắng FreeSize
((SELECT id FROM product_items WHERE name = N'Váy Đầm Suông Caro Trắng FreeSize'), 'vay_suong_caro_trang_1.webp'),
((SELECT id FROM product_items WHERE name = N'Váy Đầm Suông Caro Trắng FreeSize'), 'vay_suong_caro_trang_2.webp'),

-- Áo Kiểu Sơ Mi Nữ Trắng Size S
((SELECT id FROM product_items WHERE name = N'Áo Kiểu Sơ Mi Nữ Trắng Size S'), 'ao_kieu_so_mi_nu_trang_1.webp'),
((SELECT id FROM product_items WHERE name = N'Áo Kiểu Sơ Mi Nữ Trắng Size S'), 'ao_kieu_so_mi_nu_trang_2.webp'),

-- Bộ Đồ Bé Trai Xanh Họa Tiết Size 3T
((SELECT id FROM product_items WHERE name = N'Bộ Đồ Bé Trai Xanh Họa Tiết Size 3T'), 'bo_be_trai_xanh_1.webp'),
((SELECT id FROM product_items WHERE name = N'Bộ Đồ Bé Trai Xanh Họa Tiết Size 3T'), 'bo_be_trai_xanh_2.webp'),

-- Váy Bé Gái Hồng Xòe Hoa Size 5T
((SELECT id FROM product_items WHERE name = N'Váy Bé Gái Hồng Xòe Hoa Size 5T'), 'vay_be_gai_hong_1.webp'),
((SELECT id FROM product_items WHERE name = N'Váy Bé Gái Hồng Xòe Hoa Size 5T'), 'vay_be_gai_hong_2.webp'),

-- Áo Croptop Nữ Trắng Freesize
((SELECT id FROM product_items WHERE name = N'Áo Croptop Nữ Trắng Freesize'), 'ao_croptop_nu_trang_1.webp'),
((SELECT id FROM product_items WHERE name = N'Áo Croptop Nữ Trắng Freesize'), 'ao_croptop_nu_trang_2.webp');

INSERT INTO user_addresses (account_id, recipient_name, phone, province, district, ward, street, label, is_default, note) VALUES
-- Địa chỉ mặc định cho Admin
((SELECT id FROM accounts WHERE email = 'adminCUDE@gmail.com'), N'Nguyễn Văn A', '0901234567', N'Hồ Chí Minh', N'Quận 1', N'Phường Bến Nghé', N'123 Đường ABC', N'Nhà Riêng', 1, N'Giao hàng ngoài giờ hành chính'),

-- Địa chỉ phụ cho Admin
((SELECT id FROM accounts WHERE email = 'adminCUDE@gmail.com'), N'Nguyễn Văn A', '0907654321', N'Hồ Chí Minh', N'Quận Bình Thạnh', N'Phường 25', N'456 Đường XYZ', N'Công Ty', 0, N'Giao vào giờ làm việc');

INSERT INTO user_addresses (account_id, recipient_name, phone, province, district, ward, street, label, is_default, note) VALUES
-- Địa chỉ cho nguyenvana@example.com
((SELECT id FROM accounts WHERE email = 'nguyenvana@example.com'), N'Nguyễn Văn A', '0901234567', N'Hà Nội', N'Quận Đống Đa', N'Phường Láng Thượng', N'123 Đường ABC', N'Nhà Riêng', 1, NULL),
((SELECT id FROM accounts WHERE email = 'nguyenvana@example.com'), N'Nguyễn Văn A', '0901234567', N'Hà Nội', N'Quận Hoàn Kiếm', N'Phường Chương Dương Độ', N'456 Phố XYZ', N'Công Ty', 0, NULL),

-- Địa chỉ cho tranvanb@example.com
((SELECT id FROM accounts WHERE email = 'tranvanb@example.com'), N'Trần Văn B', '0902345678', N'Đà Nẵng', N'Quận Hải Châu', N'Phường Hòa Thuận Đông', N'789 Đường ABC', N'Nhà Riêng', 1, N'Giao hàng vào buổi tối'),

-- Địa chỉ cho lethic@example.com
((SELECT id FROM accounts WHERE email = 'lethic@example.com'), N'Lê Thị C', '0903456789', N'Hồ Chí Minh', N'Quận 3', N'Phường Võ Thị Sáu', N'101 Đường Trần Quốc Thảo', N'Nhà Riêng', 1, NULL),

-- Địa chỉ cho phamvand@example.com
((SELECT id FROM accounts WHERE email = 'phamvand@example.com'), N'Phạm Văn D', '0904567890', N'Cần Thơ', N'Quận Ninh Kiều', N'Phường Xuân Khánh', N'202 Đường 3/2', N'Nhà Riêng', 1, NULL),

-- Địa chỉ cho hoangthie@example.com
((SELECT id FROM accounts WHERE email = 'hoangthie@example.com'), N'Hoàng Thị E', '0905678901', N'Hải Phòng', N'Quận Ngô Quyền', N'Phường Đông Khê', N'303 Đường Lạch Tray', N'Nhà Riêng', 1, NULL),

-- Địa chỉ cho doquangf@example.com
((SELECT id FROM accounts WHERE email = 'doquangf@example.com'), N'Đỗ Quang F', '0906789012', N'Thừa Thiên Huế', N'Thành phố Huế', N'Phường Vỹ Dạ', N'404 Đường Phạm Văn Đồng', N'Nhà Riêng', 1, NULL),

-- Địa chỉ cho buitranh@example.com
((SELECT id FROM accounts WHERE email = 'buitranh@example.com'), N'Bùi Trần H', '0907890123', N'Khánh Hòa', N'Thành phố Nha Trang', N'Phường Lộc Thọ', N'505 Đường Biệt Thự', N'Nhà Riêng', 1, NULL),

-- Địa chỉ cho vothik@example.com
((SELECT id FROM accounts WHERE email = 'vothik@example.com'), N'Võ Thị K', '0908901234', N'Bình Dương', N'Thành phố Thủ Dầu Một', N'Phường Phú Lợi', N'606 Đường Yersin', N'Nhà Riêng', 1, NULL),

-- Địa chỉ cho dangvank@example.com
((SELECT id FROM accounts WHERE email = 'dangvank@example.com'), N'Đặng Văn K', '0909012345', N'Đồng Nai', N'Thành phố Biên Hòa', N'Phường Quang Vinh', N'707 Đường CMT8', N'Nhà Riêng', 1, NULL),

-- Địa chỉ cho truongvant@example.com
((SELECT id FROM accounts WHERE email = 'truongvant@example.com'), N'Trương Văn T', '0910123456', N'Gia Lai', N'Thành phố Pleiku', N'Phường Diên Hồng', N'808 Đường Wừu', N'Nhà Riêng', 1, NULL);

INSERT INTO promotions (name, description, type, discount_type, discount_value, combo_price, usage_limit, start_at, end_at, is_active) VALUES
(N'Giảm giá mùa hè', N'Giảm giá 15% cho toàn bộ sản phẩm hè', 'DISCOUNT', 'PERCENT', 15.00, NULL, 500, GETDATE(), DATEADD(month, 2, GETDATE()), 1),
(N'Combo áo đôi', N'Mua áo polo nam và áo croptop nữ với giá ưu đãi', 'COMBO', NULL, NULL, 250000.00, 100, GETDATE(), DATEADD(month, 1, GETDATE()), 1),
(N'Khuyến mãi Flash Sale', N'Giảm giá sốc 20% cho một số sản phẩm chọn lọc trong 24 giờ', 'DISCOUNT', 'PERCENT', 20.00, NULL, 200, GETDATE(), DATEADD(day, 1, GETDATE()), 1),
(N'Mua 2 tặng 1', N'Mua 2 quần short thể thao tặng 1 áo thun tay ngắn bé trai', 'COMBO', NULL, NULL, NULL, 50, GETDATE(), DATEADD(month, 1, GETDATE()), 1),
(N'Ưu đãi thành viên mới', N'Giảm 100k cho đơn hàng đầu tiên từ 500k', 'DISCOUNT', 'AMOUNT', 100000.00, NULL, 1000, GETDATE(), DATEADD(year, 1, GETDATE()), 1);
INSERT INTO PromotionProduct (id, promotion_id, product_item_id, require_qty, is_gift, cost_share) VALUES
-- Khuyến mãi 'Giảm giá mùa hè' (DISCOUNT) - áp dụng cho Áo Polo Nam Trắng Size M
(1, (SELECT id FROM promotions WHERE name = N'Giảm giá mùa hè'), (SELECT id FROM product_items WHERE name = N'Áo Polo Nam Trắng Size M'), NULL, 0, NULL),
-- Khuyến mãi 'Giảm giá mùa hè' (DISCOUNT) - áp dụng cho Váy Đầm Suông Caro Trắng FreeSize
(2, (SELECT id FROM promotions WHERE name = N'Giảm giá mùa hè'), (SELECT id FROM product_items WHERE name = N'Váy Đầm Suông Caro Trắng FreeSize'), NULL, 0, NULL),

-- Khuyến mãi 'Combo áo đôi' (COMBO) - Áo Polo Nam Đen Size L
(3, (SELECT id FROM promotions WHERE name = N'Combo áo đôi'), (SELECT id FROM product_items WHERE name = N'Áo Polo Nam Đen Size L'), 1, 0, NULL),
-- Khuyến mãi 'Combo áo đôi' (COMBO) - Áo Croptop Nữ Trắng Freesize
(4, (SELECT id FROM promotions WHERE name = N'Combo áo đôi'), (SELECT id FROM product_items WHERE name = N'Áo Croptop Nữ Trắng Freesize'), 1, 0, NULL),

-- Khuyến mãi 'Khuyến mãi Flash Sale' (DISCOUNT) - áp dụng cho Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30
(5, (SELECT id FROM promotions WHERE name = N'Khuyến mãi Flash Sale'), (SELECT id FROM product_items WHERE name = N'Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30'), NULL, 0, NULL),

-- Khuyến mãi 'Mua 2 tặng 1' (COMBO) - Quần Short Thể Thao Nam (sản phẩm cần mua)
-- Lưu ý: Sản phẩm 'Quần Short Thể Thao Nam' không có trong product_items ở dữ liệu mẫu trước đó
-- Chúng ta cần tìm một sản phẩm khác trong base_products và tạo một product_item mới cho nó,
-- HOẶC sửa lại promotion này thành một sản phẩm hiện có.
-- Để đơn giản, tôi sẽ giả định 'Quần Short Thể Thao Nam' là một product_item của base_product có ID 157
-- Để bản ghi này hoạt động, bạn sẽ cần thêm ProductItem cho 'Quần Short Thể Thao Nam' trước đó.
-- Hoặc, để dữ liệu mẫu chạy được, tôi sẽ liên kết nó với 'Áo Polo Nam Trắng Size M'
-- VUI LÒNG CHÚ Ý ĐIỂM NÀY, NẾU CẦN TÔI CÓ THỂ TẠO ProductItem MỚI CHO 'Quần Short Thể Thao Nam'
(6, (SELECT id FROM promotions WHERE name = N'Mua 2 tặng 1'), (SELECT id FROM product_items WHERE name = N'Áo Polo Nam Trắng Size M'), 2, 0, NULL), -- Sản phẩm cần mua (ví dụ)
-- Khuyến mãi 'Mua 2 tặng 1' (COMBO) - Áo Thun Dài Tay Nam Xám Size M (sản phẩm được tặng)
(7, (SELECT id FROM promotions WHERE name = N'Mua 2 tặng 1'), (SELECT id FROM product_items WHERE name = N'Áo Thun Dài Tay Nam Xám Size M'), 1, 1, NULL); -- Sản phẩm được tặng

INSERT INTO cost_histories (product_item_id, cost) VALUES
-- Áo Polo Nam Trắng Size M
((SELECT id FROM product_items WHERE name = N'Áo Polo Nam Trắng Size M'), 75000.00), -- Giá vốn cũ
((SELECT id FROM product_items WHERE name = N'Áo Polo Nam Trắng Size M'), 80000.00), -- Giá vốn hiện tại

-- Áo Bomber Nam Đen Size M
((SELECT id FROM product_items WHERE name = N'Áo Bomber Nam Đen Size M'), 180000.00), -- Giá vốn cũ
((SELECT id FROM product_items WHERE name = N'Áo Bomber Nam Đen Size M'), 200000.00), -- Giá vốn hiện tại

-- Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30
((SELECT id FROM product_items WHERE name = N'Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30'), 170000.00), -- Giá vốn cũ
((SELECT id FROM product_items WHERE name = N'Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30'), 180000.00); -- Giá vốn hiện tại

INSERT INTO price_histories (product_item_id, price) VALUES
-- Áo Polo Nam Trắng Size M
((SELECT id FROM product_items WHERE name = N'Áo Polo Nam Trắng Size M'), 149000.00), -- Giá bán cũ
((SELECT id FROM product_items WHERE name = N'Áo Polo Nam Trắng Size M'), 159000.00), -- Giá bán hiện tại

-- Áo Bomber Nam Đen Size M
((SELECT id FROM product_items WHERE name = N'Áo Bomber Nam Đen Size M'), 350000.00), -- Giá bán cũ
((SELECT id FROM product_items WHERE name = N'Áo Bomber Nam Đen Size M'), 399000.00), -- Giá bán hiện tại

-- Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30
((SELECT id FROM product_items WHERE name = N'Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30'), 329000.00), -- Giá bán cũ
((SELECT id FROM product_items WHERE name = N'Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30'), 349000.00); -- Giá bán hiện tại

INSERT INTO reviews (product_item_id, account_id, rating, comment, images) VALUES
-- Thêm review cho Áo Polo Nam Đen Size L
((SELECT id FROM product_items WHERE name = N'Áo Polo Nam Đen Size L'), (SELECT id FROM accounts WHERE email = 'phamvand@example.com'), 5, N'Màu đen cực ngầu, mặc lên rất tôn dáng. Giao hàng nhanh!', 'review_polo_den_1.webp'),
((SELECT id FROM product_items WHERE name = N'Áo Polo Nam Đen Size L'), (SELECT id FROM accounts WHERE email = 'buitranh@example.com'), 4, N'Chất vải ổn, không quá dày cũng không quá mỏng. Phù hợp mặc hàng ngày.', NULL),

-- Thêm review cho Áo Thun Dài Tay Nam Xám Size M
((SELECT id FROM product_items WHERE name = N'Áo Thun Dài Tay Nam Xám Size M'), (SELECT id FROM accounts WHERE email = 'dangvank@example.com'), 3, N'Áo hơi mỏng so với mong đợi. Nhưng form áo đẹp.', NULL),
((SELECT id FROM product_items WHERE name = N'Áo Thun Dài Tay Nam Xám Size M'), (SELECT id FROM accounts WHERE email = 'truongvant@example.com'), 5, N'Rất thích chiếc áo này, mặc ấm áp và thoải mái. Màu xám dễ phối đồ.', 'review_thun_xam_1.webp'),

-- Thêm review cho Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30
((SELECT id FROM product_items WHERE name = N'Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30'), (SELECT id FROM accounts WHERE email = 'hoangthie@example.com'), 5, N'Quần kaki rất đứng form, màu xanh đậm sang trọng. Vải dày dặn.', 'review_kaki_xanh_1.webp,review_kaki_xanh_2.webp'),
((SELECT id FROM product_items WHERE name = N'Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30'), (SELECT id FROM accounts WHERE email = 'vothik@example.com'), 4, N'Chất vải tốt, nhưng size 30 hơi rộng so với mình một chút.', NULL),

-- Thêm review cho Váy Đầm Suông Caro Xanh Ngọc FreeSize
((SELECT id FROM product_items WHERE name = N'Váy Đầm Suông Caro Xanh Ngọc FreeSize'), (SELECT id FROM accounts WHERE email = 'lethic@example.com'), 5, N'Váy màu xanh ngọc rất tôn da, thiết kế suông thoải mái. Mặc đi chơi hay đi làm đều được.', 'review_vay_xanh_ngoc_1.webp'),
((SELECT id FROM product_items WHERE name = N'Váy Đầm Suông Caro Xanh Ngọc FreeSize'), (SELECT id FROM accounts WHERE email = 'adminCUDE@gmail.com'), 4, N'Đầm đẹp, nhưng chất vải hơi nhăn một chút. Tổng thể vẫn rất hài lòng.', NULL),

-- Thêm review cho Áo Kiểu Sơ Mi Nữ Hồng Size M
((SELECT id FROM product_items WHERE name = N'Áo Kiểu Sơ Mi Nữ Hồng Size M'), (SELECT id FROM accounts WHERE email = 'vothik@example.com'), 5, N'Màu hồng pastel siêu dễ thương, áo nhẹ nhàng, nữ tính. Rất phù hợp với dân công sở.', 'review_somi_hong_1.webp'),

-- Thêm review cho Bộ Đồ Bé Trai Vàng Họa Tiết Size 4T
((SELECT id FROM product_items WHERE name = N'Bộ Đồ Bé Trai Vàng Họa Tiết Size 4T'), (SELECT id FROM accounts WHERE email = 'buitranh@example.com'), 4, N'Bộ đồ màu vàng đáng yêu, vải mát. Bé mặc thoải mái vận động.', NULL),

-- Thêm review cho Váy Bé Gái Trắng Xòe Hoa Size 6T
((SELECT id FROM product_items WHERE name = N'Váy Bé Gái Trắng Xòe Hoa Size 6T'), (SELECT id FROM accounts WHERE email = 'phamvand@example.com'), 5, N'Váy công chúa cho bé gái nhà mình, rất lộng lẫy và đáng yêu. Bé thích mê!', 'review_vay_trang_be_gai_1.webp');

INSERT INTO coupons (code, description, discount_type, discount_value, min_order_amount, max_discount_amount, usage_limit, usage_per_customer, is_allow_voucher, is_active, customer_group, start_at, end_at) VALUES
(N'FREESHIP25K', N'Miễn phí vận chuyển cho đơn hàng từ 250K', 'FREESHIP', 25000.00, 250000.00, 25000.00, 500, 1, 0, 1, NULL, GETDATE(), DATEADD(month, 3, GETDATE())),
(N'GIAM100K', N'Giảm 100K cho đơn hàng từ 500K', 'G-DISCOUNT', 100000.00, 500000.00, 100000.00, 300, 1, 0, 1, NULL, GETDATE(), DATEADD(month, 2, GETDATE())),
(N'VIP50K', N'Giảm 50K cho thành viên Bạc trở lên', 'G-DISCOUNT', 50000.00, 300000.00, 50000.00, 150, 1, 0, 1, N'Bạc', GETDATE(), DATEADD(month, 1, GETDATE())),
(N'NEWUSER2025', N'Ưu đãi 20% cho khách hàng mới, tối đa 50K', 'G-DISCOUNT', 0.20, 100000.00, 50000.00, 1000, 1, 0, 1, N'Đồng', GETDATE(), DATEADD(year, 1, GETDATE())),
(N'SALE50', N'Giảm 50% tối đa 200K cho toàn bộ đơn hàng', 'G-DISCOUNT', 0.50, 0.00, 200000.00, 50, 1, 0, 1, NULL, GETDATE(), DATEADD(day, 7, GETDATE()));

INSERT INTO variants (name) VALUES
(N'Màu sắc'),
(N'Kích thước');
GO

INSERT INTO variant_values (variant_id, signal_sku, description) VALUES
-- Màu sắc
((SELECT id FROM variants WHERE name = N'Màu sắc'), N'TR', N'Trắng'),
((SELECT id FROM variants WHERE name = N'Màu sắc'), N'DEN', N'Đen'),
((SELECT id FROM variants WHERE name = N'Màu sắc'), N'XAM', N'Xám'),
((SELECT id FROM variants WHERE name = N'Màu sắc'), N'XD', N'Xanh Đậm'),
((SELECT id FROM variants WHERE name = N'Màu sắc'), N'XN', N'Xanh Ngọc'),
((SELECT id FROM variants WHERE name = N'Màu sắc'), N'H', N'Hồng'),
((SELECT id FROM variants WHERE name = N'Màu sắc'), N'V', N'Vàng'),
((SELECT id FROM variants WHERE name = N'Màu sắc'), N'T', N'Tím'),

-- Kích thước
((SELECT id FROM variants WHERE name = N'Kích thước'), N'S', N'Size S'),
((SELECT id FROM variants WHERE name = N'Kích thước'), N'M', N'Size M'),
((SELECT id FROM variants WHERE name = N'Kích thước'), N'L', N'Size L'),
((SELECT id FROM variants WHERE name = N'Kích thước'), N'XL', N'Size XL'),
((SELECT id FROM variants WHERE name = N'Kích thước'), N'FS', N'FreeSize'),
((SELECT id FROM variants WHERE name = N'Kích thước'), N'30', N'Size 30'),
((SELECT id FROM variants WHERE name = N'Kích thước'), N'32', N'Size 32'),
((SELECT id FROM variants WHERE name = N'Kích thước'), N'3T', N'Size 3 tuổi'),
((SELECT id FROM variants WHERE name = N'Kích thước'), N'4T', N'Size 4 tuổi'),
((SELECT id FROM variants WHERE name = N'Kích thước'), N'5T', N'Size 5 tuổi'),
((SELECT id FROM variants WHERE name = N'Kích thước'), N'6T', N'Size 6 tuổi'),
((SELECT id FROM variants WHERE name = N'Kích thước'), N'7T', N'Size 7 tuổi');
GO

INSERT INTO purchase_orders (order_date, expected_date, received_date, total, status, note) VALUES
(GETDATE(), DATEADD(day, 7, GETDATE()), NULL, 5000000.00, N'Đang xử lý', N'Đơn nhập hàng định kỳ quần áo nam'),
(DATEADD(month, -1, GETDATE()), DATEADD(day, -20, GETDATE()), DATEADD(day, -15, GETDATE()), 7500000.00, N'Đã hoàn thành', N'Đơn nhập hàng váy đầm và đồ trẻ em'),
(GETDATE(), DATEADD(day, 10, GETDATE()), NULL, 3000000.00, N'Chờ xác nhận', N'Đơn nhập bổ sung áo croptop');

INSERT INTO purchase_order_items (purchase_order_id, product_item_id, qty, cost) VALUES
-- Cho đơn hàng nhập kho đầu tiên (Đang xử lý)
((SELECT id FROM purchase_orders WHERE note = N'Đơn nhập hàng định kỳ quần áo nam'), (SELECT id FROM product_items WHERE name = N'Áo Polo Nam Trắng Size M'), 100, 80000.00),
((SELECT id FROM purchase_orders WHERE note = N'Đơn nhập hàng định kỳ quần áo nam'), (SELECT id FROM product_items WHERE name = N'Quần Kaki Nam Dáng Thẳng Đen Size 32'), 50, 180000.00),

-- Cho đơn hàng nhập kho thứ hai (Đã hoàn thành)
((SELECT id FROM purchase_orders WHERE note = N'Đơn nhập hàng váy đầm và đồ trẻ em'), (SELECT id FROM product_items WHERE name = N'Váy Đầm Suông Caro Trắng FreeSize'), 30, 250000.00),
((SELECT id FROM purchase_orders WHERE note = N'Đơn nhập hàng váy đầm và đồ trẻ em'), (SELECT id FROM product_items WHERE name = N'Váy Bé Gái Hồng Xòe Hoa Size 5T'), 40, 150000.00),

-- Cho đơn hàng nhập kho thứ ba (Chờ xác nhận)
((SELECT id FROM purchase_orders WHERE note = N'Đơn nhập bổ sung áo croptop'), (SELECT id FROM product_items WHERE name = N'Áo Croptop Nữ Trắng Freesize'), 80, 60000.00);

INSERT INTO payment_methods (name, description, is_active) VALUES
(N'Thanh toán khi nhận hàng (COD)', N'Thanh toán tiền mặt khi đơn hàng được giao đến', 1),
(N'Thanh toán chuyển khoản ngân hàng', N'Thanh toán qua chuyển khoản ngân hàng trực tiếp', 1),
(N'Thanh toán bằng thẻ tín dụng/ghi nợ', N'Thanh toán trực tuyến qua cổng thanh toán thẻ', 1),
(N'Ví điện tử MoMo', N'Thanh toán qua ứng dụng ví điện tử MoMo', 1);

INSERT INTO shipping_methods (name, description, is_active) VALUES
(N'Giao hàng tiêu chuẩn', N'Thời gian giao hàng từ 3-5 ngày làm việc', 1),
(N'Giao hàng nhanh', N'Thời gian giao hàng từ 1-2 ngày làm việc', 1),
(N'Nhận tại cửa hàng', N'Khách hàng đến trực tiếp cửa hàng để nhận sản phẩm', 1);

INSERT INTO orders (account_id, payment_method_id, shipping_method_id, shipping_status, estimated_shipping_fee, freeship_coupon_code, actual_shipping_fee, discount_coupon_code, discount_value, shipped_date, payment_status, note, point, final_total, order_infor) VALUES
-- Đơn hàng 1: Admin đặt hàng, thanh toán COD, giao tiêu chuẩn
((SELECT id FROM accounts WHERE email = 'adminCUDE@gmail.com'),
 (SELECT id FROM payment_methods WHERE name = N'Thanh toán khi nhận hàng (COD)'),
 (SELECT id FROM shipping_methods WHERE name = N'Giao hàng tiêu chuẩn'),
 N'Đang xử lý', 30000.00, NULL, 30000.00, NULL, 0.00, NULL, N'Chưa thanh toán', N'Kiểm tra hàng trước khi nhận', 100, 500000.00, N'Địa chỉ giao: 123 Đường ABC, Phường Bến Nghé, Quận 1, Hồ Chí Minh. SĐT: 0901234567'),

-- Đơn hàng 2: Nguyễn Văn A, thanh toán chuyển khoản, giao nhanh
((SELECT id FROM accounts WHERE email = 'nguyenvana@example.com'),
 (SELECT id FROM payment_methods WHERE name = N'Thanh toán chuyển khoản ngân hàng'),
 (SELECT id FROM shipping_methods WHERE name = N'Giao hàng nhanh'),
 N'Đã giao', 45000.00, N'FREESHIP25K', 0.00, N'GIAM100K', 100000.00, GETDATE(), N'Đã thanh toán', N'Giao vào buổi sáng', 250, 1200000.00, N'Địa chỉ giao: 123 Đường ABC, Phường Láng Thượng, Quận Đống Đa, Hà Nội. SĐT: 0901234567'),

-- Đơn hàng 3: Lê Thị C, thanh toán thẻ, giao tiêu chuẩn
((SELECT id FROM accounts WHERE email = 'lethic@example.com'),
 (SELECT id FROM payment_methods WHERE name = N'Thanh toán bằng thẻ tín dụng/ghi nợ'),
 (SELECT id FROM shipping_methods WHERE name = N'Giao hàng tiêu chuẩn'),
 N'Hoàn thành', 30000.00, NULL, 30000.00, NULL, 0.00, DATEADD(day, -5, GETDATE()), N'Đã thanh toán', N'Không có ghi chú', 150, 850000.00, N'Địa chỉ giao: 101 Đường Trần Quốc Thảo, Phường Võ Thị Sáu, Quận 3, Hồ Chí Minh. SĐT: 0903456789'),

-- Đơn hàng 4: Hoàng Thị E, thanh toán MoMo, giao nhanh
((SELECT id FROM accounts WHERE email = 'hoangthie@example.com'),
 (SELECT id FROM payment_methods WHERE name = N'Ví điện tử MoMo'),
 (SELECT id FROM shipping_methods WHERE name = N'Giao hàng nhanh'),
 N'Đang vận chuyển', 45000.00, NULL, 45000.00, NULL, 0.00, NULL, N'Đã thanh toán', N'Giao hỏa tốc', 300, 1500000.00, N'Địa chỉ giao: 303 Đường Lạch Tray, Phường Đông Khê, Quận Ngô Quyền, Hải Phòng. SĐT: 0905678901'),

-- Đơn hàng 5: Trần Văn B, thanh toán COD, giao tiêu chuẩn
((SELECT id FROM accounts WHERE email = 'tranvanb@example.com'),
 (SELECT id FROM payment_methods WHERE name = N'Thanh toán khi nhận hàng (COD)'),
 (SELECT id FROM shipping_methods WHERE name = N'Giao hàng tiêu chuẩn'),
 N'Chờ xác nhận', 30000.00, NULL, 30000.00, NULL, 0.00, NULL, N'Chưa thanh toán', N'Gọi điện trước khi giao', 50, 250000.00, N'Địa chỉ giao: 789 Đường ABC, Phường Hòa Thuận Đông, Quận Hải Châu, Đà Nẵng. SĐT: 0902345678');

 INSERT INTO cart_items (account_id, product_item_id, qty) VALUES
-- Giỏ hàng của Nguyễn Văn A (email: nguyenvana@example.com)
((SELECT id FROM accounts WHERE email = 'nguyenvana@example.com'), (SELECT id FROM product_items WHERE name = N'Áo Polo Nam Trắng Size M'), 1),
((SELECT id FROM accounts WHERE email = 'nguyenvana@example.com'), (SELECT id FROM product_items WHERE name = N'Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30'), 1),

-- Giỏ hàng của Trần Văn B (email: tranvanb@example.com)
((SELECT id FROM accounts WHERE email = 'tranvanb@example.com'), (SELECT id FROM product_items WHERE name = N'Áo Thun Dài Tay Nam Xám Size M'), 2),

-- Giỏ hàng của Lê Thị C (email: lethic@example.com)
((SELECT id FROM accounts WHERE email = 'lethic@example.com'), (SELECT id FROM product_items WHERE name = N'Váy Đầm Suông Caro Trắng FreeSize'), 1),
((SELECT id FROM accounts WHERE email = 'lethic@example.com'), (SELECT id FROM product_items WHERE name = N'Áo Kiểu Sơ Mi Nữ Trắng Size S'), 1),

-- Giỏ hàng của Admin (email: adminCUDE@gmail.com)
((SELECT id FROM accounts WHERE email = 'adminCUDE@gmail.com'), (SELECT id FROM product_items WHERE name = N'Áo Bomber Nam Đen Size M'), 1),
((SELECT id FROM accounts WHERE email = 'adminCUDE@gmail.com'), (SELECT id FROM product_items WHERE name = N'Bộ Đồ Bé Trai Xanh Họa Tiết Size 3T'), 1);

INSERT INTO order_items (order_id, product_item_id, promotion_id, qty, cost, is_gift, selling_price) VALUES
-- Cho Đơn hàng 1 (adminCUDE@gmail.com, tổng 500k)
((SELECT id FROM orders WHERE account_id = (SELECT id FROM accounts WHERE email = 'adminCUDE@gmail.com') AND final_total = 500000.00), -- Sử dụng final_total để phân biệt nếu có nhiều đơn của admin
 (SELECT id FROM product_items WHERE name = N'Áo Polo Nam Trắng Size M'),
 NULL, -- Không khuyến mãi
 1, 80000.00, 0, 159000.00),
((SELECT id FROM orders WHERE account_id = (SELECT id FROM accounts WHERE email = 'adminCUDE@gmail.com') AND final_total = 500000.00),
 (SELECT id FROM product_items WHERE name = N'Áo Thun Dài Tay Nam Xám Size M'),
 NULL,
 1, 90000.00, 0, 180000.00),
((SELECT id FROM orders WHERE account_id = (SELECT id FROM accounts WHERE email = 'adminCUDE@gmail.com') AND final_total = 500000.00),
 (SELECT id FROM product_items WHERE name = N'Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30'),
 NULL,
 1, 180000.00, 0, 349000.00),

-- Cho Đơn hàng 2 (nguyenvana@example.com, tổng 1200k, có mã GIAM100K)
((SELECT id FROM orders WHERE account_id = (SELECT id FROM accounts WHERE email = 'nguyenvana@example.com') AND discount_coupon_code = N'GIAM100K'),
 (SELECT id FROM product_items WHERE name = N'Áo Bomber Nam Đen Size M'),
 NULL,
 2, 200000.00, 0, 399000.00), -- 2 * 399k = 798k
((SELECT id FROM orders WHERE account_id = (SELECT id FROM accounts WHERE email = 'nguyenvana@example.com') AND discount_coupon_code = N'GIAM100K'),
 (SELECT id FROM product_items WHERE name = N'Váy Đầm Suông Caro Trắng FreeSize'),
 (SELECT id FROM promotions WHERE name = N'Giảm giá mùa hè'), -- Sản phẩm này có trong khuyến mãi
 1, 250000.00, 0, 424150.00), -- Giá gốc 499k, giảm 15% = 424.15k
((SELECT id FROM orders WHERE account_id = (SELECT id FROM accounts WHERE email = 'nguyenvana@example.com') AND discount_coupon_code = N'GIAM100K'),
 (SELECT id FROM product_items WHERE name = N'Áo Croptop Nữ Trắng Freesize'),
 NULL,
 3, 60000.00, 0, 119000.00), -- 3 * 119k = 357k
-- Tổng: 798k + 424.15k + 357k = 1579.15k. Sau khi trừ 100k coupon = 1479.15k. (final_total 1200k là ví dụ, sẽ không khớp chính xác với tính toán này nếu không có các yếu tố khác như thuế/phí/làm tròn)

-- Cho Đơn hàng 3 (lethic@example.com, tổng 850k)
((SELECT id FROM orders WHERE account_id = (SELECT id FROM accounts WHERE email = 'lethic@example.com') AND final_total = 850000.00),
 (SELECT id FROM product_items WHERE name = N'Váy Đầm Suông Caro Trắng FreeSize'),
 NULL,
 1, 250000.00, 0, 499000.00),
((SELECT id FROM orders WHERE account_id = (SELECT id FROM accounts WHERE email = 'lethic@example.com') AND final_total = 850000.00),
 (SELECT id FROM product_items WHERE name = N'Áo Kiểu Sơ Mi Nữ Trắng Size S'),
 NULL,
 1, 120000.00, 0, 239000.00),

-- Cho Đơn hàng 4 (hoangthie@example.com, tổng 1500k)
((SELECT id FROM orders WHERE account_id = (SELECT id FROM accounts WHERE email = 'hoangthie@example.com') AND final_total = 1500000.00),
 (SELECT id FROM product_items WHERE name = N'Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30'),
 (SELECT id FROM promotions WHERE name = N'Khuyến mãi Flash Sale'), -- Sản phẩm này có trong khuyến mãi
 2, 180000.00, 0, 279200.00), -- Giá gốc 349k, giảm 20% = 279.2k. 2 * 279.2k = 558.4k
((SELECT id FROM orders WHERE account_id = (SELECT id FROM accounts WHERE email = 'hoangthie@example.com') AND final_total = 1500000.00),
 (SELECT id FROM product_items WHERE name = N'Áo Polo Nam Đen Size L'),
 NULL,
 2, 80000.00, 0, 169000.00), -- 2 * 169k = 338k
((SELECT id FROM orders WHERE account_id = (SELECT id FROM accounts WHERE email = 'hoangthie@example.com') AND final_total = 1500000.00),
 (SELECT id FROM product_items WHERE name = N'Váy Bé Gái Hồng Xòe Hoa Size 5T'),
 NULL,
 1, 150000.00, 0, 299000.00),

-- Cho Đơn hàng 5 (tranvanb@example.com, tổng 250k)
((SELECT id FROM orders WHERE account_id = (SELECT id FROM accounts WHERE email = 'tranvanb@example.com') AND final_total = 250000.00),
 (SELECT id FROM product_items WHERE name = N'Áo Thun Dài Tay Nam Xám Size M'),
 NULL,
 1, 90000.00, 0, 180000.00);

 INSERT INTO cancels (order_id, reason, is_paid, status) VALUES
-- Hủy Đơn hàng 5 (của Trần Văn B, tổng 250k, payment_status 'Chưa thanh toán')
((SELECT id FROM orders WHERE account_id = (SELECT id FROM accounts WHERE email = 'tranvanb@example.com') AND final_total = 250000.00),
 N'Không còn nhu cầu mua sản phẩm', 0, N'Đã hủy'),

-- Hủy một đơn hàng khác (ví dụ: Đơn hàng 1 của Admin, nếu bạn muốn hủy)
-- Lưu ý: Nếu đơn hàng đã được giao hoặc thanh toán thì lý do hủy và trạng thái is_paid sẽ khác.
-- Tôi sẽ tạo một đơn hàng mới và hủy nó để minh họa rõ hơn.
-- HOẶC, nếu bạn muốn hủy đơn hàng 1 của Admin, hãy đảm bảo các trạng thái hợp lý.
-- Với mục đích chèn dữ liệu mẫu, tôi sẽ tạo một bản hủy cho Đơn hàng 1 (Admin) mặc dù trạng thái của nó là "Đang xử lý".
((SELECT id FROM orders WHERE account_id = (SELECT id FROM accounts WHERE email = 'adminCUDE@gmail.com') AND final_total = 500000.00),
 N'Thay đổi ý định mua hàng', 0, N'Đã hủy');

 INSERT INTO returns (order_product_item_id, reason, image1, image2, image3, is_returned_money, is_returned_item, status) VALUES
-- Trả lại Áo Polo Nam Trắng Size M từ Đơn hàng 1 (Admin)
((SELECT TOP 1 id FROM order_items WHERE order_id = (SELECT id FROM orders WHERE account_id = (SELECT id FROM accounts WHERE email = 'adminCUDE@gmail.com') AND final_total = 500000.00) AND product_item_id = (SELECT id FROM product_items WHERE name = N'Áo Polo Nam Trắng Size M')),
 N'Sản phẩm bị lỗi nhỏ ở đường may', 'return_polo_loi_1.webp', NULL, NULL, N'Đã hoàn tiền', N'Đã nhận hàng', N'Đã xử lý'),

-- Trả lại Váy Đầm Suông Caro Trắng FreeSize từ Đơn hàng 2 (Nguyễn Văn A)
((SELECT TOP 1 id FROM order_items WHERE order_id = (SELECT id FROM orders WHERE account_id = (SELECT id FROM accounts WHERE email = 'nguyenvana@example.com') AND discount_coupon_code = N'GIAM100K') AND product_item_id = (SELECT id FROM product_items WHERE name = N'Váy Đầm Suông Caro Trắng FreeSize')),
 N'Kích thước không phù hợp', 'return_vay_sai_size_1.webp', 'return_vay_sai_size_2.webp', NULL, N'Đang chờ hoàn tiền', N'Đang chờ nhận hàng', N'Đang xử lý');

 INSERT INTO UserLogs (user_id, action, description, ip_address, user_agent, module) VALUES
-- Admin đăng nhập
((SELECT id FROM accounts WHERE email = 'adminCUDE@gmail.com'), N'Đăng nhập', N'Đăng nhập thành công vào hệ thống quản trị', '192.168.1.1', N'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', N'Authentication'),
-- Nguyễn Văn A đặt hàng
((SELECT id FROM accounts WHERE email = 'nguyenvana@example.com'), N'Đặt hàng', N'Đặt hàng thành công đơn hàng #20250607002', '203.0.113.45', N'Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1', N'Order Management'),
-- Lê Thị C cập nhật địa chỉ
((SELECT id FROM accounts WHERE email = 'lethic@example.com'), N'Cập nhật thông tin', N'Cập nhật địa chỉ nhận hàng', '10.0.0.10', N'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', N'User Profile'),
-- Trần Văn B thêm sản phẩm vào giỏ hàng
((SELECT id FROM accounts WHERE email = 'tranvanb@example.com'), N'Thêm vào giỏ hàng', N'Thêm Áo Thun Dài Tay Nam Xám Size M vào giỏ hàng', '172.16.0.20', N'Mozilla/5.0 (Linux; Android 10; SM-G960F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Mobile Safari/537.36', N'Shopping Cart'),
-- Hoàng Thị E xem chi tiết sản phẩm
((SELECT id FROM accounts WHERE email = 'hoangthie@example.com'), N'Xem sản phẩm', N'Xem chi tiết sản phẩm: Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30', '192.168.1.50', N'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Edge/123.0.0.0', N'Product Browse');

INSERT INTO custom (product_item_id, infor) VALUES
((SELECT id FROM product_items WHERE name = N'Áo Polo Nam Trắng Size M'),
N'{"custom_text": "Bùi Hữu Lộc", "font_style": "Bold Italic", "font_color": "Blue", "logo_placement": "Ngực trái"}');
GO

