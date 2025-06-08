INSERT INTO accounts (email, password, fullname, avatar, phone, average_order_value, user_rank, total_spent, total_order, loyalty_point) VALUES
('nguyenvana@example.com', 'hashedpass1', N'Nguyễn Văn A', 'avatar_a.jpg', '0901234567', 1500000.00, N'Bạc', 7500000.00, 5, 200),
('tranvanb@example.com', 'hashedpass2', N'Trần Văn B', 'avatar_b.jpg', '0902345678', 2000000.00, N'Vàng', 10000000.00, 5, 300),
('lethic@example.com', 'hashedpass3', N'Lê Thị C', 'avatar_c.jpg', '0903456789', 1200000.00, N'Đồng', 6000000.00, 5, 150),
('phamvand@example.com', 'hashedpass4', N'Phạm Văn D', 'avatar_d.jpg', '0904567890', 1800000.00, N'Bạc', 9000000.00, 5, 250),
('hoangthie@example.com', 'hashedpass5', N'Hoàng Thị E', 'avatar_e.jpg', '0905678901', 2500000.00, N'Vàng', 12500000.00, 5, 400),
('doquangf@example.com', 'hashedpass6', N'Đỗ Quang F', 'avatar_f.jpg', '0906789012', 1000000.00, N'Đồng', 5000000.00, 5, 100),
('buitranh@example.com', 'hashedpass7', N'Bùi Trần H', 'avatar_h.jpg', '0907890123', 1300000.00, N'Bạc', 6500000.00, 5, 180),
('vothik@example.com', 'hashedpass8', N'Võ Thị K', 'avatar_k.jpg', '0908901234', 1600000.00, N'Vàng', 8000000.00, 5, 280),
('dangvank@example.com', 'hashedpass9', N'Đặng Văn K', 'avatar_k.jpg', '0909012345', 1400000.00, N'Đồng', 7000000.00, 5, 160),
('truongvant@example.com', 'hashedpass10', N'Trương Văn T', 'avatar_t.jpg', '0910123456', 2200000.00, N'Bạc', 11000000.00, 5, 350);
-- chỉ có 2 role ADMIN, USER
INSERT INTO roles (name) VALUES
(N'Admin'),
(N'Quản lý'),
(N'Nhân viên bán hàng'),
(N'Khách hàng'),
(N'Khách hàng thân thiết'),
(N'Người dùng mới'),
(N'Kế toán'),
(N'Kho vận'),
(N'Marketing'),
(N'Nhân sự');

-- ID của roles sẽ là:
-- Admin: 1
-- Quản lý: 3
-- Nhân viên bán hàng: 5
-- Khách hàng: 7
-- Khách hàng thân thiết: 9

INSERT INTO authorities (account_id, role_id) VALUES
(1, 1), -- nguyenvana@example.com là Admin
(2, 7), -- tranvanb@example.com là Khách hàng (role_id = 7)
(3, 7), -- lethic@example.com là Khách hàng (role_id = 7)
(4, 3), -- phamvand@example.com là Quản lý (role_id = 3)
(5, 9), -- hoangthie@example.com là Khách hàng thân thiết (role_id = 9)
(6, 7), -- doquangf@example.com là Khách hàng (role_id = 7)
(7, 7), -- buitranh@example.com là Khách hàng (role_id = 7)
(8, 9), -- vothik@example.com là Khách hàng thân thiết (role_id = 9)
(9, 7), -- dangvank@example.com là Khách hàng (role_id = 7)
(10, 5); -- truongvant@example.com là Nhân viên bán hàng (role_id = 5)-- truongvant@example.com là Nhân viên bán hàng

INSERT INTO user_addresses (account_id, recipient_name, phone, province, district, ward, street, label, is_default, note) VALUES
(1, N'Nguyễn Văn A', '0901234567', N'TP. Hồ Chí Minh', N'Quận 1', N'Bến Nghé', N'Đường Nguyễn Huệ', N'Nhà', 1, N'Giao hàng ngoài giờ hành chính'),
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
(N'Điện tử'),
(N'Gia dụng'),
(N'Đồ dùng trẻ em'),
(N'Sách'),
(N'Văn phòng phẩm'),
(N'Phụ kiện'),
(N'Mỹ phẩm'),
(N'Thực phẩm chức năng');

INSERT INTO categories (catalog_id, name) VALUES
(1, N'Áo Nam'),
(1, N'Quần Nam'),
(2, N'Váy Nữ'),
(2, N'Áo Nữ'),
(3, N'Điện thoại'),
(3, N'Máy tính bảng'),
(4, N'Thiết bị nhà bếp'),
(4, N'Đồ dùng phòng khách'),
(5, N'Đồ chơi trẻ em'),
(5, N'Quần áo trẻ em');

INSERT INTO base_products (name, material, category_id, main_image, is_custom, turn_buy, rating, is_promote, is_active) VALUES
(N'Áo Thun Cotton Basic', N'Cotton 100%', 1, 'basic_tshirt.jpg', 0, 150, 4, 1, 1),
(N'Quần Jean Slim Fit', N'Jean Denim', 2, 'jean_slimfit.jpg', 0, 120, 5, 1, 1),
(N'Váy Maxi Hoa', N'Lụa tổng hợp', 3, 'maxi_dress.jpg', 0, 90, 4, 0, 1),
(N'Áo Blouse Voan', N'Voan', 4, 'blouse_voan.jpg', 0, 110, 4, 1, 1),
(N'Điện thoại XYZ', N'Hợp kim nhôm', 5, 'phone_xyz.jpg', 0, 200, 5, 1, 1),
(N'Máy tính bảng ABC', N'Nhựa ABS', 6, 'tablet_abc.jpg', 0, 70, 4, 0, 1),
(N'Nồi chiên không dầu', N'Thép không gỉ', 7, 'air_fryer.jpg', 0, 80, 5, 1, 1),
(N'Ghế Sofa hiện đại', N'Vải bố', 8, 'sofa_modern.jpg', 0, 30, 4, 0, 1),
(N'Bộ xếp hình gỗ', N'Gỗ tự nhiên', 9, 'wooden_puzzle.jpg', 0, 100, 5, 1, 1),
(N'Quần áo sơ sinh', N'Vải cotton mềm', 10, 'baby_clothes.jpg', 0, 130, 4, 0, 1);

INSERT INTO product_items (base_id, name, cost, price, is_promote, turn_buy, description, sku, safety_stock, qty, sell_start, sell_end) VALUES
(1, N'Áo Thun Basic Đen - Size M', 50000.00, 99000.00, 1, 50, N'Áo thun cotton cao cấp màu đen, size M.', 'ATB-DEN-M', 10, 100, GETDATE(), DATEADD(month, 6, GETDATE())),
(1, N'Áo Thun Basic Trắng - Size L', 50000.00, 99000.00, 0, 40, N'Áo thun cotton cao cấp màu trắng, size L.', 'ATB-TRANG-L', 10, 90, GETDATE(), DATEADD(month, 6, GETDATE())),
(2, N'Quần Jean Slim Fit Xanh - Size 30', 150000.00, 299000.00, 1, 30, N'Quần jean co giãn, dáng ôm, màu xanh, size 30.', 'QJ-XANH-30', 5, 80, GETDATE(), DATEADD(month, 6, GETDATE())),
(3, N'Váy Maxi Hoa Đỏ - Freesize', 200000.00, 399000.00, 0, 20, N'Váy maxi họa tiết hoa đỏ, chất liệu lụa mềm mại.', 'VMX-HOA-DO-FS', 5, 70, GETDATE(), DATEADD(month, 6, GETDATE())),
(4, N'Áo Blouse Voan Trắng - Size S', 100000.00, 199000.00, 1, 25, N'Áo blouse voan mỏng, nhẹ nhàng, màu trắng, size S.', 'ABV-TRANG-S', 8, 60, GETDATE(), DATEADD(month, 6, GETDATE())),
(5, N'Điện thoại XYZ 128GB', 3000000.00, 5990000.00, 1, 15, N'Điện thoại thông minh hiệu năng cao, bộ nhớ 128GB.', 'DT-XYZ-128', 3, 50, GETDATE(), DATEADD(month, 12, GETDATE())),
(6, N'Máy tính bảng ABC 64GB', 1500000.00, 2990000.00, 0, 10, N'Máy tính bảng màn hình lớn, 64GB bộ nhớ.', 'MTB-ABC-64', 2, 40, GETDATE(), DATEADD(month, 12, GETDATE())),
(7, N'Nồi chiên không dầu 5L', 800000.00, 1599000.00, 1, 18, N'Nồi chiên không dầu dung tích 5 lít, công nghệ mới.', 'NCKD-5L', 4, 30, GETDATE(), DATEADD(month, 12, GETDATE())),
(8, N'Ghế Sofa Góc Lớn', 5000000.00, 9990000.00, 0, 5, N'Ghế sofa góc hiện đại, vải cao cấp, kích thước lớn.', 'SFA-GOC-LON', 1, 20, GETDATE(), DATEADD(month, 12, GETDATE())),
(9, N'Bộ Xếp Hình Gỗ 50 Chi Tiết', 70000.00, 149000.00, 1, 35, N'Bộ xếp hình gỗ an toàn cho trẻ em, 50 chi tiết.', 'XHG-50CT', 10, 80, GETDATE(), DATEADD(month, 6, GETDATE()));

INSERT INTO custom (product_item_id, infor) VALUES
(1, N'{"color_options": ["black", "white", "navy"], "size_guide_url": "tshirt_size.pdf"}'),
(2, N'{"color_options": ["white", "grey"], "size_guide_url": "tshirt_size.pdf"}'),
(3, N'{"wash_instructions": "cold water, no bleach", "fitting_notes": "stretchable material"}'),
(4, N'{"fabric_care": "hand wash recommended", "occasion": "summer, beach wear"}'),
(5, N'{"style_tips": "pair with skinny jeans", "material_composition": "100% polyester"}'),
(6, N'{"os": "Android 14", "camera_specs": "48MP", "battery_life": "2 days"}'),
(7, N'{"display_res": "2K", "storage_expandable": "up to 256GB SD card"}'),
(8, N'{"features": "timer, presets", "cleaning": "dishwasher safe basket"}'),
(9, N'{"assembly_required": "yes", "dimensions": "200x150x80 cm"}'),
(10, N'{"age_range": "3-6 years", "safety_cert": "ASTM"}');

INSERT INTO UserLogs (user_id, action, description, ip_address, user_agent, module) VALUES
(1, N'Đăng nhập thành công', N'Người dùng admin đăng nhập vào hệ thống.', '192.168.1.10', N'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.75 Safari/537.36', N'Authentication'),
(2, N'Xem chi tiết sản phẩm', N'Người dùng xem chi tiết sản phẩm Áo Thun Basic Đen.', '192.168.1.11', N'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Safari/605.1.15', N'Product'),
(3, N'Thêm sản phẩm vào giỏ hàng', N'Người dùng thêm Váy Maxi Hoa Đỏ vào giỏ hàng.', '192.168.1.12', N'Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.75 Mobile Safari/537.36', N'Cart'),
(4, N'Cập nhật thông tin cá nhân', N'Người dùng quản lý cập nhật số điện thoại.', '192.168.1.13', N'Edge/99.0.1150.55', N'Profile'),
(5, N'Đăng xuất', N'Người dùng khách hàng thân thiết đăng xuất khỏi hệ thống.', '192.168.1.14', N'Firefox/98.0', N'Authentication'),
(6, N'Tìm kiếm sản phẩm', N'Người dùng tìm kiếm "Nồi chiên không dầu".', '192.168.1.15', N'Chrome/99.0.4844.84', N'Search'),
(7, N'Xem tin tức', N'Người dùng xem bài viết tin tức mới.', '192.168.1.16', N'Safari/15.2', N'News'),
(8, N'Thay đổi mật khẩu', N'Người dùng khách hàng thân thiết thay đổi mật khẩu.', '192.168.1.17', N'Opera/83.0.4253.33', N'Security'),
(9, N'Thêm địa chỉ mới', N'Người dùng thêm địa chỉ giao hàng mới.', '192.168.1.18', N'Brave/1.37.111', N'Address Management'),
(10, N'Xem danh sách đơn hàng', N'Người dùng nhân viên bán hàng xem danh sách đơn hàng.', '192.168.1.19', N'Vivaldi/5.1.2567.49', N'Order Management');

INSERT INTO product_images (product_item_id, image) VALUES
(1, 'tshirt_black_front.jpg'),
(1, 'tshirt_black_back.jpg'),
(2, 'tshirt_white_front.jpg'),
(3, 'jean_blue_side.jpg'),
(4, 'maxi_dress_red_full.jpg'),
(5, 'blouse_white_detail.jpg'),
(6, 'phone_xyz_angle.jpg'),
(7, 'tablet_abc_screen.jpg'),
(8, 'air_fryer_open.jpg'),
(9, 'wooden_puzzle_box.jpg');

INSERT INTO news (image, is_home, description) VALUES
('news_sale_event.jpg', 1, N'Giảm giá lớn cuối mùa! Hàng ngàn sản phẩm đang chờ đón bạn với ưu đãi cực sốc. Nhanh tay săn ngay kẻo lỡ!'),
('news_new_collection.jpg', 1, N'Ra mắt bộ sưu tập thời trang Thu Đông mới nhất. Phong cách đẳng cấp, chất liệu cao cấp, đón đầu xu hướng.'),
('news_tech_update.jpg', 0, N'Cập nhật công nghệ: Top 5 điện thoại thông minh đáng mua nhất năm 2025. Đừng bỏ lỡ các siêu phẩm này!'),
('news_home_decor.jpg', 1, N'Trang trí nhà cửa: Bí quyết tạo không gian sống ấm cúng và hiện đại. Xem ngay để biến đổi tổ ấm của bạn!'),
('news_baby_care.jpg', 0, N'Chăm sóc bé yêu: Những sản phẩm thiết yếu cho trẻ sơ sinh mà mẹ nào cũng cần. An toàn và tiện lợi.'),
('news_book_release.jpg', 1, N'Sách mới: Khám phá những tựa sách bán chạy nhất và sắp ra mắt. Thỏa mãn đam mê đọc sách của bạn.'),
('news_office_supplies.jpg', 0, N'Văn phòng phẩm: Tối ưu không gian làm việc với các sản phẩm thông minh. Nâng cao hiệu suất công việc.'),
('news_accessories.jpg', 1, N'Phụ kiện thời trang: Top 10 phụ kiện không thể thiếu để nâng tầm phong cách. Đừng quên điểm nhấn cho trang phục của bạn!'),
('news_beauty_tips.jpg', 0, N'Bí quyết làm đẹp: Chăm sóc da toàn diện với các sản phẩm mỹ phẩm thiên nhiên. Làn da rạng rỡ mỗi ngày.'),
('news_health_care.jpg', 1, N'Sức khỏe và sắc đẹp: Lợi ích bất ngờ từ thực phẩm chức năng. Cải thiện sức khỏe từ bên trong.');

INSERT INTO promotions (name, description, type, discount_type, discount_value, combo_price, usage_limit, start_at, end_at, is_active) VALUES
(N'Giảm 20% toàn bộ đơn hàng', N'Áp dụng cho tất cả sản phẩm, tối đa 200.000 VNĐ', 'DISCOUNT', 'PERCENT', 20.00, NULL, 500, GETDATE(), DATEADD(month, 1, GETDATE()), 1),
(N'Mua 1 tặng 1 Áo Thun', N'Khi mua Áo Thun Basic Đen, tặng Áo Thun Basic Trắng', 'COMBO', NULL, NULL, 120000.00, 100, GETDATE(), DATEADD(week, 2, GETDATE()), 1),
(N'Giảm 100K cho đơn trên 1 triệu', N'Áp dụng cho đơn hàng có tổng giá trị trên 1.000.000 VNĐ', 'DISCOUNT', 'AMOUNT', 100000.00, NULL, 300, GETDATE(), DATEADD(month, 2, GETDATE()), 1),
(N'Miễn phí vận chuyển', N'Áp dụng cho tất cả đơn hàng, không giới hạn giá trị', 'DISCOUNT', 'AMOUNT', 0.00, NULL, 1000, GETDATE(), DATEADD(month, 3, GETDATE()), 1),
(N'Combo Điện thoại + Ốp lưng', N'Mua điện thoại XYZ kèm ốp lưng chỉ với giá combo', 'COMBO', NULL, NULL, 6000000.00, 50, GETDATE(), DATEADD(month, 1, GETDATE()), 1),
(N'Ưu đãi thành viên mới', N'Giảm 15% cho đơn hàng đầu tiên của thành viên mới', 'DISCOUNT', 'PERCENT', 15.00, NULL, 200, GETDATE(), DATEADD(month, 6, GETDATE()), 1),
(N'Flash Sale 12h-14h', N'Giảm sốc nhiều sản phẩm trong khung giờ vàng', 'DISCOUNT', 'PERCENT', 30.00, NULL, 400, GETDATE(), DATEADD(day, 7, GETDATE()), 1),
(N'Mua Nồi chiên không dầu tặng Sách nấu ăn', N'Tặng sách nấu ăn cho khách mua nồi chiên', 'COMBO', NULL, NULL, 1599000.00, 80, GETDATE(), DATEADD(month, 1, GETDATE()), 1),
(N'Giảm 50K khi thanh toán qua ví điện tử', N'Áp dụng cho các đơn hàng thanh toán qua ví', 'DISCOUNT', 'AMOUNT', 50000.00, NULL, 250, GETDATE(), DATEADD(month, 2, GETDATE()), 1),
(N'Ưu đãi cuối tuần', N'Giảm giá đặc biệt vào thứ 7 và chủ nhật', 'DISCOUNT', 'PERCENT', 10.00, NULL, 600, GETDATE(), DATEADD(week, 4, GETDATE()), 1);

INSERT INTO PromotionProduct (id, promotion_id, product_item_id, require_qty, is_gift, cost_share) VALUES
(1, 1, 1, 1, 0, 0.00), -- Giảm 20% cho Áo Thun Basic Đen
(2, 1, 3, 1, 0, 0.00), -- Giảm 20% cho Quần Jean Slim Fit Xanh
(3, 2, 1, 1, 0, 0.00), -- Mua Áo Thun Basic Đen (chính)
(4, 2, 2, 0, 1, 0.00), -- Tặng Áo Thun Basic Trắng (quà tặng)
(5, 3, 6, 1, 0, 0.00), -- Giảm 100K cho Điện thoại XYZ
(6, 4, 1, 1, 0, 0.00), -- Miễn phí VC cho Áo Thun
(7, 5, 6, 1, 0, 0.00), -- Combo Điện thoại XYZ (chính)
(8, 5, 7, 0, 0, 0.00), -- Combo Máy tính bảng ABC (kèm theo combo, ví dụ: ốp lưng, tai nghe)
(9, 7, 8, 1, 0, 0.00), -- Flash Sale cho Nồi chiên không dầu
(10, 8, 8, 1, 0, 0.00); -- Mua Nồi chiên không dầu (chính)

INSERT INTO cost_histories (product_item_id, cost) VALUES
(1, 48000.00),
(1, 50000.00),
(2, 49000.00),
(3, 145000.00),
(3, 150000.00),
(4, 190000.00),
(5, 98000.00),
(6, 2900000.00),
(6, 3000000.00),
(7, 1450000.00);

INSERT INTO price_histories (product_item_id, price) VALUES
(1, 99000.00),
(1, 95000.00),
(2, 99000.00),
(3, 299000.00),
(3, 280000.00),
(4, 399000.00),
(5, 199000.00),
(6, 5990000.00),
(6, 5800000.00),
(7, 2990000.00);

INSERT INTO reviews (product_item_id, account_id, rating, comment, images) VALUES
(1, 2, 5, N'Áo chất vải mát, mặc rất thoải mái, sẽ ủng hộ shop tiếp!', 'review_tshirt_1.jpg'),
(3, 2, 4, N'Quần jean đúng size, chất liệu tốt nhưng hơi lâu giao hàng.', NULL),
(6, 5, 5, N'Điện thoại dùng mượt, pin trâu, rất đáng tiền!', 'review_phone_1.jpg,review_phone_2.jpg'),
(8, 5, 5, N'Nồi chiên không dầu tiện lợi, dễ sử dụng, nướng gà rất ngon!', 'review_airfryer_1.jpg'),
(1, 3, 4, N'Áo đẹp, nhưng màu trắng hơi mỏng chút.', NULL),
(2, 4, 3, N'Áo ok, giao hàng nhanh.', NULL),
(4, 6, 5, N'Váy xinh lắm ạ, mặc đi chơi rất hợp.', 'review_dress_1.jpg'),
(5, 7, 4, N'Blouse đẹp, giao đúng mẫu.', NULL),
(9, 8, 5, N'Bộ xếp hình gỗ chất lượng, bé nhà mình rất thích.', 'review_puzzle_1.jpg'),
(10, 9, 4, N'Quần áo sơ sinh mềm mại, bé mặc không bị cộm.', NULL);

INSERT INTO cart_items (account_id, product_item_id, qty) VALUES
(2, 1, 2), -- tranvanb@example.com có 2 Áo Thun Basic Đen
(2, 3, 1), -- tranvanb@example.com có 1 Quần Jean Slim Fit Xanh
(3, 4, 1), -- lethic@example.com có 1 Váy Maxi Hoa Đỏ
(5, 6, 1), -- hoangthie@example.com có 1 Điện thoại XYZ
(5, 8, 1), -- hoangthie@example.com có 1 Nồi chiên không dầu
(6, 2, 3), -- doquangf@example.com có 3 Áo Thun Basic Trắng
(7, 5, 1), -- buitranh@example.com có 1 Áo Blouse Voan Trắng
(8, 9, 2), -- vothik@example.com có 2 Bộ Xếp Hình Gỗ
(9, 10, 1), -- dangvank@example.com có 1 Quần áo sơ sinh
(10, 7, 1); -- truongvant@example.com có 1 Máy tính bảng ABC

INSERT INTO variants (name) VALUES
(N'Kích thước'),
(N'Màu sắc'),
(N'Kiểu dáng'),
(N'Chất liệu'),
(N'Dung lượng'),
(N'Phiên bản'),
(N'Độ dày'),
(N'Hương vị'),
(N'Loại da'),
(N'Độ tuổi');

INSERT INTO variant_values (variant_id, signal_sku, description) VALUES
(1, 'S', N'Size Small'),
(1, 'M', N'Size Medium'),
(1, 'L', N'Size Large'),
(2, 'RED', N'Màu Đỏ'),
(2, 'BLUE', N'Màu Xanh'),
(3, 'SLIM', N'Dáng Slim Fit'),
(4, 'COTTON', N'Chất liệu Cotton'),
(5, '128GB', N'Dung lượng 128GB'),
(6, 'PRO', N'Phiên bản Pro'),
(7, 'THIN', N'Mỏng');

INSERT INTO purchase_orders (order_date, expected_date, received_date, total, status, note) VALUES
(GETDATE(), DATEADD(day, 7, GETDATE()), NULL, 5000000.00, N'Đang xử lý', N'Đơn hàng nhập kho Quần Jean'),
(GETDATE(), DATEADD(day, 10, GETDATE()), NULL, 1000000.00, N'Đang xử lý', N'Đơn hàng nhập kho Áo Thun'),
(GETDATE(), DATEADD(day, 5, GETDATE()), GETDATE(), 800000.00, N'Đã hoàn thành', N'Nhập thêm Nồi chiên không dầu'),
(GETDATE(), DATEADD(day, 14, GETDATE()), NULL, 15000000.00, N'Đang chờ duyệt', N'Đặt hàng Điện thoại XYZ số lượng lớn'),
(GETDATE(), DATEADD(day, 7, GETDATE()), NULL, 600000.00, N'Đang xử lý', N'Nhập hàng Váy Maxi'),
(GETDATE(), DATEADD(day, 3, GETDATE()), GETDATE(), 300000.00, N'Đã hoàn thành', N'Nhập bổ sung Áo Blouse'),
(GETDATE(), DATEADD(day, 21, GETDATE()), NULL, 7500000.00, N'Đang xử lý', N'Đặt hàng Máy tính bảng'),
(GETDATE(), DATEADD(day, 9, GETDATE()), NULL, 200000.00, N'Đang xử lý', N'Nhập thêm Bộ xếp hình gỗ'),
(GETDATE(), DATEADD(day, 12, GETDATE()), NULL, 1200000.00, N'Đang chờ duyệt', N'Đặt hàng Quần áo sơ sinh'),
(GETDATE(), DATEADD(day, 10, GETDATE()), NULL, 900000.00, N'Đang xử lý', N'Nhập thêm phụ kiện');

INSERT INTO purchase_order_items (purchase_order_id, product_item_id, qty, cost) VALUES
(1, 3, 20, 150000.00), -- Đơn nhập hàng 1: 20 Quần Jean
(2, 1, 10, 50000.00), -- Đơn nhập hàng 2: 10 Áo Thun Đen
(2, 2, 10, 50000.00), -- Đơn nhập hàng 2: 10 Áo Thun Trắng
(3, 8, 5, 800000.00), -- Đơn nhập hàng 3: 5 Nồi chiên không dầu
(4, 6, 5, 3000000.00), -- Đơn nhập hàng 4: 5 Điện thoại XYZ
(5, 4, 3, 200000.00), -- Đơn nhập hàng 5: 3 Váy Maxi
(6, 5, 3, 100000.00), -- Đơn nhập hàng 6: 3 Áo Blouse
(7, 7, 5, 1500000.00), -- Đơn nhập hàng 7: 5 Máy tính bảng ABC
(8, 9, 2, 70000.00), -- Đơn nhập hàng 8: 2 Bộ Xếp Hình Gỗ
(9, 10, 5, 120000.00); -- Đơn nhập hàng 9: 5 Quần áo sơ sinh

-- Chèn dữ liệu ban đầu cho payment_methods (sử dụng order_id tạm thời hoặc sẽ cập nhật sau)
INSERT INTO payment_methods (order_id, name, description) VALUES
(1, N'Thanh toán khi nhận hàng (COD)', N'Thanh toán trực tiếp cho nhân viên giao hàng.'),
(1, N'Thanh toán qua ví điện tử MoMo', N'Thanh toán qua ứng dụng ví điện tử MoMo.'),
(1, N'Chuyển khoản ngân hàng', N'Thanh toán bằng cách chuyển khoản vào tài khoản ngân hàng của cửa hàng.'),
(1, N'Thanh toán qua thẻ tín dụng/ghi nợ', N'Chấp nhận thanh toán bằng các loại thẻ Visa, Mastercard, JCB.'),
(1, N'Ví điện tử ZaloPay', N'Thanh toán qua ứng dụng ví điện tử ZaloPay.'),
(1, N'Thanh toán qua ví điện tử VNPay', N'Thanh toán qua ứng dụng ví điện tử VNPay.'),
(1, N'Thanh toán qua AirPay', N'Thanh toán qua ứng dụng ví điện tử AirPay.'),
(1, N'Thanh toán qua cổng thanh toán OnePay', N'Cổng thanh toán trực tuyến OnePay.'),
(1, N'Thanh toán tại cửa hàng', N'Thanh toán trực tiếp tại các cửa hàng của chúng tôi.'),
(1, N'Thanh toán trả góp', N'Thanh toán trả góp qua thẻ tín dụng.');

-- Chèn dữ liệu ban đầu cho shipping_methods (sử dụng order_id tạm thời hoặc sẽ cập nhật sau)
INSERT INTO shipping_methods (order_id, name, description) VALUES
(1, N'Giao hàng tiêu chuẩn', N'Thời gian giao hàng 3-5 ngày làm việc.'),
(1, N'Giao hàng nhanh', N'Thời gian giao hàng 1-2 ngày làm việc.'),
(1, N'Giao hàng tiết kiệm', N'Thời gian giao hàng 5-7 ngày làm việc, chi phí thấp.'),
(1, N'Giao hàng hỏa tốc trong 4 giờ', N'Chỉ áp dụng tại khu vực nội thành, giao trong 4 giờ.'),
(1, N'Tự đến lấy tại cửa hàng', N'Khách hàng đến trực tiếp cửa hàng để nhận hàng.'),
(1, N'Giao hàng quốc tế', N'Giao hàng ra nước ngoài (có thể mất phí và thời gian lâu hơn).'),
(1, N'Giao hàng COD toàn quốc', N'Giao hàng và thu tiền tận nơi trên toàn quốc.'),
(1, N'Giao hàng nội thành 24h', N'Giao hàng trong nội thành trong vòng 24 giờ.'),
(1, N'Giao hàng qua bưu điện', N'Vận chuyển qua dịch vụ bưu điện.'),
(1, N'Giao hàng theo lịch hẹn', N'Khách hàng có thể chọn thời gian giao hàng cụ thể.');

PRINT N'-- Inserting data into payment_methods...'
INSERT INTO payment_methods (order_id, name, description) VALUES
(1, N'Thanh toán khi nhận hàng (COD)', N'Thanh toán trực tiếp cho nhân viên giao hàng.'),
(1, 'MoMo', N'Thanh toán qua ví điện tử MoMo.'),
(1, N'Chuyển khoản ngân hàng', N'Thanh toán bằng cách chuyển khoản vào tài khoản ngân hàng.'),
(1, N'Thẻ tín dụng/ghi nợ', N'Thanh toán bằng Visa, Mastercard, JCB.'),
(1, 'ZaloPay', N'Thanh toán qua ví điện tử ZaloPay.'),
(1, 'VNPay', N'Thanh toán qua ví điện tử VNPay.'),
(1, 'AirPay', N'Thanh toán qua ví điện tử AirPay.'),
(1, 'OnePay', N'Cổng thanh toán trực tuyến OnePay.'),
(1, N'Tại cửa hàng', N'Thanh toán trực tiếp tại các cửa hàng.'),
(1, N'Trả góp', N'Thanh toán trả góp qua thẻ tín dụng.');
PRINT N'(10 rows affected)'
PRINT N'Finished inserting data into payment_methods.'
GO

PRINT N'-- Inserting data into shipping_methods...'
INSERT INTO shipping_methods (order_id, name, description) VALUES
(1, N'Giao hàng tiêu chuẩn', N'Thời gian giao hàng 3-5 ngày.'),
(1, N'Giao hàng nhanh', N'Thời gian giao hàng 1-2 ngày.'),
(1, N'Giao hàng tiết kiệm', N'Thời gian giao hàng 5-7 ngày, chi phí thấp.'),
(1, N'Hỏa tốc 4 giờ', N'Áp dụng nội thành, giao trong 4 giờ.'),
(1, N'Tự lấy tại cửa hàng', N'Khách hàng đến trực tiếp cửa hàng.'),
(1, N'Giao hàng quốc tế', N'Giao hàng ra nước ngoài.'),
(1, N'COD toàn quốc', N'Giao hàng và thu tiền tận nơi.'),
(1, N'Nội thành 24h', N'Giao hàng trong nội thành trong 24 giờ.'),
(1, N'Qua bưu điện', N'Vận chuyển qua dịch vụ bưu điện.'),
(1, N'Theo lịch hẹn', N'Khách hàng chọn thời gian giao hàng.');
PRINT N'(10 rows affected)'
PRINT N'Finished inserting data into shipping_methods.'
GO
PRINT N'-- Inserting data into temporary orders (placeholder for FK resolution)...'
INSERT INTO orders (account_id, payment_method_id, shipping_method_id, shipping_status, estimated_shipping_fee, freeship_coupon_code, actual_shipping_fee, discount_coupon_code, discount_value, shipped_date, payment_status, note, point, final_total, order_infor) VALUES
(1, 1, 1, N'Created', 0.00, NULL, 0.00, NULL, 0.00, NULL, N'Pending', N'Placeholder order for initial FK setup.', 0, 0.00, N'{"recipient": "Placeholder", "address": "N/A"}');
PRINT N'(1 row affected)'
PRINT N'Finished inserting data into temporary orders.'
GO

PRINT N'-- Inserting data into orders...'
INSERT INTO orders (account_id, payment_method_id, shipping_method_id, shipping_status, estimated_shipping_fee, freeship_coupon_code, actual_shipping_fee, discount_coupon_code, discount_value, shipped_date, payment_status, note, point, final_total, order_infor) VALUES
(2, 1, 1, N'Đang xử lý', 30000.00, NULL, 30000.00, NULL, 0.00, NULL, N'Chưa thanh toán', N'Khách hàng muốn kiểm tra hàng trước khi nhận.', 10, 329000.00, N'{"recipient": "Trần Văn B", "address": "Láng Hạ, Đống Đa, Hà Nội"}'),
(3, 2, 2, N'Đã giao hàng', 45000.00, NULL, 45000.00, NULL, 0.00, '2025-06-05', N'Đã thanh toán', N'Giao hàng nhanh giúp tôi.', 15, 444000.00, N'{"recipient": "Lê Thị C", "address": "Hòa Cường Bắc, Hải Châu, Đà Nẵng"}'),
(5, 3, 3, N'Đang xử lý', 30000.00, NULL, 30000.00, NULL, 0.00, NULL, N'Đã thanh toán', N'Giao giờ hành chính.', 20, 6020000.00, N'{"recipient": "Hoàng Thị E", "address": "Tràng Tiền, Hoàn Kiếm, Hà Nội"}'),
(6, 4, 4, N'Đã giao hàng', 25000.00, NULL, 25000.00, NULL, 0.00, '2025-06-04', N'Đã thanh toán', N'Không gọi điện thoại khi giao.', 8, 223000.00, N'{"recipient": "Đỗ Quang F", "address": "An Khánh, Ninh Kiều, Cần Thơ"}'),
(7, 5, 5, N'Đang xử lý', 40000.00, NULL, 40000.00, NULL, 0.00, NULL, N'Chưa thanh toán', N'Giao vào buổi tối.', 5, 239000.00, N'{"recipient": "Bùi Trần H", "address": "Lạc Viên, Ngô Quyền, Hải Phòng"}'),
(8, 6, 6, N'Đã giao hàng', 30000.00, NULL, 30000.00, NULL, 0.00, '2025-06-03', N'Đã thanh toán', N'Giao nhanh.', 12, 328000.00, N'{"recipient": "Võ Thị K", "address": "Hưng Dũng, TP. Vinh, Nghệ An"}'),
(9, 7, 7, N'Đang xử lý', 25000.00, NULL, 25000.00, NULL, 0.00, NULL, N'Đã thanh toán', N'Để ở cổng nếu không có người.', 6, 174000.00, N'{"recipient": "Đặng Văn K", "address": "Vĩnh Ninh, TP. Huế, Thừa Thiên Huế"}'),
(10, 8, 8, N'Đã giao hàng', 35000.00, NULL, 35000.00, NULL, 0.00, '2025-06-02', N'Đã thanh toán', N'Liên hệ trước khi đến.', 18, 3025000.00, N'{"recipient": "Trương Văn T", "address": "Phú Cường, Thủ Dầu Một, Bình Dương"}'),
(2, 9, 9, N'Đang xử lý', 30000.00, NULL, 30000.00, NULL, 0.00, NULL, N'Chưa thanh toán', N'Đơn hàng test.', 9, 129000.00, N'{"recipient": "Trần Văn B", "address": "Láng Hạ, Đống Đa, Hà Nội"}'),
(5, 10, 10, N'Đã giao hàng', 40000.00, NULL, 40000.00, NULL, 0.00, '2025-06-01', N'Đã thanh toán', N'Đơn hàng test 2.', 25, 750000.00, N'{"recipient": "Hoàng Thị E", "address": "Tràng Tiền, Hoàn Kiếm, Hà Nội"}');
PRINT N'(10 rows affected)'
PRINT N'Finished inserting data into orders.'
GO

PRINT N'-- Updating order_id in payment_methods...'
-- Cập nhật order_id cho các payment_method đã chèn
-- Ví dụ: payment_method_id = 1 sẽ liên kết với orders.id = 2
-- payment_method_id = 2 sẽ liên kết với orders.id = 3, v.v.
UPDATE pm SET pm.order_id = o.id
FROM payment_methods pm
JOIN (
    SELECT id, ROW_NUMBER() OVER (ORDER BY id) AS rn
    FROM orders
    WHERE id > 1 -- Bỏ qua bản ghi orders placeholder
) o ON pm.id = o.rn;

-- Đặc biệt: Cập nhật bản ghi payment_methods.id = 1 để trỏ đến orders.id = 1 (placeholder) nếu cần
-- Hoặc để nguyên nếu bản ghi placeholder đó không cần payment_method thực
-- Trong trường hợp này, chúng ta sẽ giữ pm.id=1 trỏ đến orders.id=1.

PRINT N'(10 rows affected)'
PRINT N'Finished updating order_id in payment_methods.'
GO

PRINT N'-- Updating order_id in shipping_methods...'
-- Tương tự cho shipping_methods
UPDATE sm SET sm.order_id = o.id
FROM shipping_methods sm
JOIN (
    SELECT id, ROW_NUMBER() OVER (ORDER BY id) AS rn
    FROM orders
    WHERE id > 1 -- Bỏ qua bản ghi orders placeholder
) o ON sm.id = o.rn;

-- Đặc biệt: Cập nhật bản ghi shipping_methods.id = 1 để trỏ đến orders.id = 1 (placeholder) nếu cần
-- Hoặc để nguyên nếu bản ghi placeholder đó không cần shipping_method thực

PRINT N'(10 rows affected)'
PRINT N'Finished updating order_id in shipping_methods.'
GO