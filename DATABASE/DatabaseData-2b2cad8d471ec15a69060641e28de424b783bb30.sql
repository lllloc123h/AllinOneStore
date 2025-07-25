use all_in_store;

INSERT INTO
  accounts (
    email,
    password,
    fullname,
    avatar_url,
    phone,
    average_order_value,
    user_rank,
    total_spent,
    total_order,
    loyalty_point
  )
VALUES
  (
    'nguyenvana@example.com',
    '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a',
    N'Nguyễn Văn A',
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1752902314/hynx7jvtbmz8k7vfyomk.jpg',
    '0901234567',
    1500000.00,
    N'Bạc',
    7500000.00,
    5,
    200
  ),
  (
    'tranvanb@example.com',
    '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a',
    N'Trần Văn B',
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1752902314/hynx7jvtbmz8k7vfyomk.jpg',
    '0902345678',
    2000000.00,
    N'Vàng',
    10000000.00,
    5,
    300
  ),
  (
    'lethic@example.com',
    '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a',
    N'Lê Thị C',
    'avatar_c.jpg',
    '0903456789',
    1200000.00,
    N'Đồng',
    6000000.00,
    5,
    150
  ),
  (
    'phamvand@example.com',
    '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a',
    N'Phạm Văn D',
    'avatar_d.jpg',
    '0904567890',
    1800000.00,
    N'Bạc',
    9000000.00,
    5,
    250
  ),
  (
    'hoangthie@example.com',
    '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a',
    N'Hoàng Thị E',
    'avatar_e.jpg',
    '0905678901',
    2500000.00,
    N'Vàng',
    12500000.00,
    5,
    400
  ),
  (
    'doquangf@example.com',
    '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a',
    N'Đỗ Quang F',
    'avatar_f.jpg',
    '0906789012',
    1000000.00,
    N'Đồng',
    5000000.00,
    5,
    100
  ),
  (
    'buitranh@example.com',
    '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a',
    N'Bùi Trần H',
    'avatar_h.jpg',
    '0907890123',
    1300000.00,
    N'Bạc',
    6500000.00,
    5,
    180
  ),
  (
    'vothik@example.com',
    '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a',
    N'Võ Thị K',
    'avatar_k.jpg',
    '0908901234',
    1600000.00,
    N'Vàng',
    8000000.00,
    5,
    280
  ),
  (
    'dangvank@example.com',
    '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a',
    N'Đặng Văn K',
    'avatar_k.jpg',
    '0909012345',
    1400000.00,
    N'Đồng',
    7000000.00,
    5,
    160
  ),
  (
    'truongvant@example.com',
    '$2a$10$YDQtz.cHyKDlwqG1Rzky7.WdaHWbMWBUDXmRAqiMSqsRp7jcUCj9a',
    N'Trương Văn T',
    'avatar_t.jpg',
    '0910123456',
    2200000.00,
    N'Bạc',
    11000000.00,
    5,
    350
  );

INSERT INTO
  user_addresses (
    account_id,
    recipient_name,
    phone,
    province,
    district,
    ward,
    street,
    label,
    is_default,
    note
  )
VALUES
  (
    1,
    N'Nguyễn Văn A',
    '0901234567',
    N'TP. Hồ Chí Minh',
    N'Quận 1',
    N'Bến Nghé',
    N'Đường Nguyễn Huệ',
    N'Nhà riêng',
    1,
    N'Giao hàng ngoài giờ hành chính'
  ),
  (
    2,
    N'Trần Văn B',
    '0902345678',
    N'Hà Nội',
    N'Đống Đa',
    N'Láng Hạ',
    N'Phố Láng Hạ',
    N'Văn phòng',
    1,
    N'Gọi trước khi đến'
  ),
  (
    3,
    N'Lê Thị C',
    '0903456789',
    N'Đà Nẵng',
    N'Hải Châu',
    N'Hòa Cường Bắc',
    N'Đường 2 tháng 9',
    N'Nhà',
    1,
    NULL
  ),
  (
    4,
    N'Phạm Văn D',
    '0904567890',
    N'TP. Hồ Chí Minh',
    N'Quận 7',
    N'Tân Phong',
    N'Đường Nguyễn Lương Bằng',
    N'Công ty',
    1,
    N'Chỉ giao giờ hành chính'
  ),
  (
    5,
    N'Hoàng Thị E',
    '0905678901',
    N'Hà Nội',
    N'Hoàn Kiếm',
    N'Tràng Tiền',
    N'Phố Tràng Tiền',
    N'Nhà',
    1,
    NULL
  ),
  (
    6,
    N'Đỗ Quang F',
    '0906789012',
    N'Cần Thơ',
    N'Ninh Kiều',
    N'An Khánh',
    N'Đường 3 tháng 2',
    N'Nhà riêng',
    1,
    N'Giao vào buổi tối'
  ),
  (
    7,
    N'Bùi Trần H',
    '0907890123',
    N'Hải Phòng',
    N'Ngô Quyền',
    N'Lạc Viên',
    N'Đường Lạc Viên',
    N'Nhà',
    1,
    NULL
  ),
  (
    8,
    N'Võ Thị K',
    '0908901234',
    N'Nghệ An',
    N'TP. Vinh',
    N'Hưng Dũng',
    N'Đường Nguyễn Duy Trinh',
    N'Nhà',
    1,
    N'Giao hàng nhanh'
  ),
  (
    9,
    N'Đặng Văn K',
    '0909012345',
    N'Thừa Thiên Huế',
    N'TP. Huế',
    N'Vĩnh Ninh',
    N'Đường Hùng Vương',
    N'Nhà',
    1,
    NULL
  ),
  (
    10,
    N'Trương Văn T',
    '0910123456',
    N'Bình Dương',
    N'Thủ Dầu Một',
    N'Phú Cường',
    N'Đại lộ Bình Dương',
    N'Công ty',
    1,
    N'Giao hàng vào giờ ăn trưa'
  );

INSERT INTO
  catalogs (name)
VALUES
  (N'Thời trang Nam'),
  (N'Thời trang Nữ'),
  (N'Trẻ em');

INSERT INTO
  categories (catalog_id, name)
VALUES
  (
    (
      SELECT
        id
      FROM
        catalogs
      WHERE
        name = N'Thời trang Nam'
    ),
    N'Áo Nam'
  ),
  (
    (
      SELECT
        id
      FROM
        catalogs
      WHERE
        name = N'Thời trang Nam'
    ),
    N'Quần Nam'
  ),
  (
    (
      SELECT
        id
      FROM
        catalogs
      WHERE
        name = N'Thời trang Nữ'
    ),
    N'Váy Nữ'
  ),
  (
    (
      SELECT
        id
      FROM
        catalogs
      WHERE
        name = N'Thời trang Nữ'
    ),
    N'Áo Nữ'
  ),
  (
    (
      SELECT
        id
      FROM
        catalogs
      WHERE
        name = N'Trẻ em'
    ),
    N'Quần áo bé trai'
  ),
  (
    (
      SELECT
        id
      FROM
        catalogs
      WHERE
        name = N'Trẻ em'
    ),
    N'Quần áo bé gái'
  );

INSERT INTO
  base_products (
    name,
    material,
    category_id,
    main_image_url,
    is_custom,
    turn_buy,
    rating,
    is_active
  )
VALUES
  -- Áo Nam (Áo Nam)
  (
    N'Áo Polo Nam Cotton Pha',
    N'Cotton pha',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Áo Nam'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    100,
    4,
    1
  ),
  (
    N'Áo Khoác Bomber Nam',
    N'Polyester chống nước',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Áo Nam'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    75,
    5,
    1
  ),
  (
    N'Áo Thun Dài Tay Nam',
    N'Thun Gân',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Áo Nam'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    90,
    4,
    1
  ),
  (
    N'Áo Vest Công Sở Nam',
    N'Vải Tweed',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Áo Nam'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    30,
    5,
    1
  ),
  (
    N'Áo Hoodie Nỉ Nam',
    N'Nỉ Bông',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Áo Nam'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    110,
    4,
    1
  ),
  -- Quần Nam (Quần Nam)
  (
    N'Quần Kaki Nam Dáng Thẳng',
    N'Kaki Cotton',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Quần Nam'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    85,
    4,
    1
  ),
  (
    N'Quần Short Thể Thao Nam',
    N'Vải Dù',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Quần Nam'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    130,
    4,
    1
  ),
  (
    N'Quần Âu Nam Slimfit',
    N'Vải Tuyết Mưa',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Quần Nam'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    60,
    5,
    1
  ),
  (
    N'Quần Jogger Nam Phối Túi',
    N'Nỉ Da Cá',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Quần Nam'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    95,
    4,
    1
  ),
  (
    N'Quần Jean Rách Gối Nam',
    N'Jean Bền',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Quần Nam'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    50,
    3,
    1
  ),
  -- Áo Nữ (Áo Nữ)
  (
    N'Áo Thun Croptop Nữ',
    N'Cotton 2 chiều',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Áo Nữ'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    160,
    4,
    1
  ),
  (
    N'Áo Len Cổ Lọ Nữ',
    N'Len Cashmere',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Áo Nữ'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    70,
    5,
    1
  ),
  (
    N'Áo Khoác Cardigan Nữ',
    N'Len mỏng',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Áo Nữ'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    80,
    4,
    1
  ),
  (
    N'Áo Kiểu Sơ Mi Nữ',
    N'Lụa Satin',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Áo Nữ'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    105,
    4,
    1
  ),
  (
    N'Áo Bomber Nữ Phối Tay',
    N'Da PU',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Áo Nữ'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    45,
    4,
    1
  ),
  -- Váy Nữ (Váy Nữ)
  (
    N'Váy Đầm Suông Caro',
    N'Vải Linen',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Váy Nữ'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    90,
    4,
    1
  ),
  (
    N'Váy Chữ A Dáng Ngắn',
    N'Vải Tuyết Sa',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Váy Nữ'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    115,
    4,
    1
  ),
  (
    N'Chân Váy Xếp Ly Dài',
    N'Vải Voan',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Váy Nữ'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    70,
    5,
    1
  ),
  (
    N'Đầm Dự Tiệc Hai Dây',
    N'Phi Bóng',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Váy Nữ'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    55,
    5,
    1
  ),
  (
    N'Váy Yếm Jean Nữ',
    N'Jean Mềm',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Váy Nữ'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    80,
    4,
    1
  ),
  -- Quần áo bé trai (Quần áo bé trai)
  (
    N'Bộ Quần Áo Bé Trai Năng Động',
    N'Thun Cotton',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Quần áo bé trai'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    120,
    4,
    1
  ),
  (
    N'Áo Thun Tay Ngắn Bé Trai',
    N'Cotton 4 chiều',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Quần áo bé trai'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    140,
    4,
    1
  ),
  (
    N'Quần Jean Phối Rách Bé Trai',
    N'Jean Mềm',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Quần áo bé trai'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    70,
    4,
    1
  ),
  (
    N'Áo Khoác Nỉ Bé Trai',
    N'Nỉ Cao Cấp',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Quần áo bé trai'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    60,
    5,
    1
  ),
  (
    N'Bộ Đồ Thể Thao Bé Trai',
    N'Vải Poly',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Quần áo bé trai'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    90,
    4,
    1
  ),
  -- Quần áo bé gái (Quần áo bé gái)
  (
    N'Váy Bé Gái Xòe Hoa',
    N'Cotton Lụa',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Quần áo bé gái'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    110,
    5,
    1
  ),
  (
    N'Bộ Quần Áo Bé Gái Dễ Thương',
    N'Thun Lạnh',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Quần áo bé gái'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    130,
    4,
    1
  ),
  (
    N'Áo Khoác Jean Bé Gái',
    N'Jean Co Giãn',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Quần áo bé gái'
    ),
    'ao_khoac_jean_be_gai.webp',
    0,
    50,
    4,
    1
  ),
  (
    N'Đầm Công Chúa Ren Bé Gái',
    N'Voan + Ren',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Quần áo bé gái'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    65,
    5,
    1
  ),
  (
    N'Quần Legging Bé Gái',
    N'Thun Cotton',
    (
      SELECT
        id
      FROM
        categories
      WHERE
        name = N'Quần áo bé gái'
    ),
    'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png',
    0,
    150,
    4,
    1
  );

--delete from product_items
INSERT INTO
  product_items (
    base_id,
    cost,
    price,
    turn_buy,
    description,
    sku,
    safety_stock,
    qty,
    sell_start,
    sell_end
  )
VALUES
  -- Áo Polo Nam Cotton Pha (ID: 151)
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Áo Polo Nam Cotton Pha' COLLATE DATABASE_DEFAULT
    ),
    80000.00,
    159000.00,
    30,
    N'Áo polo nam cotton pha cao cấp, màu trắng, size M.',
    'PN-TR-M',
    10,
    50,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  ),
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Áo Polo Nam Cotton Pha' COLLATE DATABASE_DEFAULT
    ),
    80000.00,
    159000.00,
    25,
    N'Áo polo nam cotton pha cao cấp, màu đen, size L.',
    'PN-DEN-L',
    10,
    45,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  ),
  -- Áo Khoác Bomber Nam (ID: 152)
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Áo Khoác Bomber Nam' COLLATE DATABASE_DEFAULT
    ),
    200000.00,
    399000.00,
    10,
    N'Áo khoác bomber nam chất liệu chống nước, màu đen, size M.',
    'BOM-DEN-M',
    5,
    15,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  ),
  -- Thay thế 'Áo Sơ Mi Linen Nam' bằng 'Áo Thun Dài Tay Nam' (ID: 153)
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Áo Thun Dài Tay Nam' COLLATE DATABASE_DEFAULT
    ),
    120000.00,
    239000.00,
    15,
    N'Áo thun dài tay nam chất liệu mềm mại, màu xám, size M.',
    'SM-LX-M',
    5,
    25,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  ),
  -- Quần Kaki Nam Dáng Thẳng (ID: 156)
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Quần Kaki Nam Dáng Thẳng' COLLATE DATABASE_DEFAULT
    ),
    180000.00,
    349000.00,
    20,
    N'Quần kaki nam dáng thẳng, màu xanh đậm, size 30.',
    'QK-XD-30',
    5,
    30,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  ),
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Quần Kaki Nam Dáng Thẳng' COLLATE DATABASE_DEFAULT
    ),
    180000.00,
    349000.00,
    18,
    N'Quần kaki nam dáng thẳng, màu đen, size 32.',
    'QK-DEN-32',
    5,
    28,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  ),
  -- Váy Đầm Suông Caro (ID: 166)
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Váy Đầm Suông Caro' COLLATE DATABASE_DEFAULT
    ),
    250000.00,
    499000.00,
    12,
    N'Váy đầm suông caro dáng dài, màu trắng, freesize.',
    'VSC-TR-FS',
    3,
    20,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  ),
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Váy Đầm Suông Caro' COLLATE DATABASE_DEFAULT
    ),
    250000.00,
    499000.00,
    10,
    N'Váy đầm suông caro dáng dài, màu xanh ngọc, freesize.',
    'VSC-XN-FS',
    3,
    18,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  ),
  -- Áo Kiểu Sơ Mi Nữ (ID: 164) (Đã thay đổi từ Áo Blouse Nữ Công Sở để khớp với base_products)
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Áo Kiểu Sơ Mi Nữ' COLLATE DATABASE_DEFAULT
    ),
    90000.00,
    189000.00,
    22,
    N'Áo kiểu sơ mi nữ thanh lịch, màu trắng, size S.',
    'AB-TR-S',
    8,
    40,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  ),
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Áo Kiểu Sơ Mi Nữ' COLLATE DATABASE_DEFAULT
    ),
    90000.00,
    189000.00,
    19,
    N'Áo kiểu sơ mi nữ thanh lịch, màu hồng pastel, size M.',
    'AB-H-M',
    8,
    35,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  ),
  -- Váy Chữ A Dáng Ngắn (ID: 167)
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Váy Chữ A Dáng Ngắn' COLLATE DATABASE_DEFAULT
    ),
    70000.00,
    139000.00,
    14,
    N'Chân váy chữ A nữ dáng ngắn, màu đen, size M.',
    'CV-A-DEN-M',
    5,
    25,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  ),
  -- Bộ Quần Áo Bé Trai Năng Động (ID: 171)
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Bộ Quần Áo Bé Trai Năng Động' COLLATE DATABASE_DEFAULT
    ),
    100000.00,
    199000.00,
    28,
    N'Bộ quần áo bé trai cotton, màu xanh, họa tiết ngộ nghĩnh, size 3 tuổi.',
    'BBT-XH-3T',
    10,
    60,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  ),
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Bộ Quần Áo Bé Trai Năng Động' COLLATE DATABASE_DEFAULT
    ),
    100000.00,
    199000.00,
    25,
    N'Bộ quần áo bé trai cotton, màu vàng, họa tiết ngộ nghĩnh, size 4 tuổi.',
    'BBT-VH-4T',
    10,
    55,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  ),
  -- Váy Bé Gái Xòe Hoa (ID: 176)
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Váy Bé Gái Xòe Hoa' COLLATE DATABASE_DEFAULT
    ),
    150000.00,
    299000.00,
    15,
    N'Váy bé gái dáng xòe hoa, màu hồng, phù hợp cho bé 5 tuổi.',
    'VBG-HX-5T',
    5,
    30,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  ),
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Váy Bé Gái Xòe Hoa' COLLATE DATABASE_DEFAULT
    ),
    150000.00,
    299000.00,
    13,
    N'Váy bé gái dáng xòe hoa, màu trắng, phù hợp cho bé 6 tuổi.',
    'VBG-TX-6T',
    5,
    28,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  ),
  -- Quần Jean Phối Rách Bé Trai (ID: 173)
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Quần Jean Phối Rách Bé Trai' COLLATE DATABASE_DEFAULT
    ),
    60000.00,
    119000.00,
    10,
    N'Quần short jean bé trai màu xanh nhạt, chất liệu mềm mại, size 7 tuổi.',
    'QSJ-XN-7T',
    5,
    20,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  ),
  -- Bộ Quần Áo Bé Gái Dễ Thương (ID: 177)
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Bộ Quần Áo Bé Gái Dễ Thương' COLLATE DATABASE_DEFAULT
    ),
    80000.00,
    169000.00,
    20,
    N'Set đồ mùa hè cho bé gái, màu hồng, chất thun cotton, size 2 tuổi.',
    'SDMH-H-2T',
    8,
    40,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  ),
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Bộ Quần Áo Bé Gái Dễ Thương' COLLATE DATABASE_DEFAULT
    ),
    80000.00,
    169000.00,
    18,
    N'Set đồ mùa hè cho bé gái, màu tím, chất thun cotton, size 3 tuổi.',
    'SDMH-T-3T',
    8,
    35,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  ),
  -- Áo Thun Croptop Nữ (ID: 161)
  (
    (
      SELECT
        id
      FROM
        base_products
      WHERE
        name = N'Áo Thun Croptop Nữ' COLLATE DATABASE_DEFAULT
    ),
    60000.00,
    119000.00,
    35,
    N'Áo thun croptop nữ năng động, màu trắng, freesize.',
    'CR-TR-FS',
    10,
    50,
    GETDATE (),
    DATEADD (month, 6, GETDATE ())
  );

INSERT INTO
  product_images (product_item_id, image_url)
SELECT
  id,
  'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png'
FROM
  product_items
WHERE
  description = N'Áo polo nam cotton pha cao cấp, màu trắng, size M.'
UNION ALL
SELECT
  id,
  'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png'
FROM
  product_items
WHERE
  description = N'Áo polo nam cotton pha cao cấp, màu đen, size M.'
UNION ALL
SELECT
  id,
  'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png'
FROM
  product_items
WHERE
  description = N'Áo polo nam cotton pha cao cấp, màu đen, size L.'
UNION ALL
SELECT
  id,
  'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png'
FROM
  product_items
WHERE
  description = N'Áo polo nam cotton pha cao cấp, màu đen, size L.'
UNION ALL
SELECT
  id,
  'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png'
FROM
  product_items
WHERE
  description = N'Áo khoác bomber nam chất liệu chống nước, màu đen, size M.'
UNION ALL
SELECT
  id,
  'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png'
FROM
  product_items
WHERE
  description = N'Áo khoác bomber nam chất liệu chống nước, màu đen, size M.'
UNION ALL
SELECT
  id,
  'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png'
FROM
  product_items
WHERE
  description = N'Áo thun dài tay nam chất liệu mềm mại, màu xám, size M.'
UNION ALL
SELECT
  id,
  'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png'
FROM
  product_items
WHERE
  description = N'Áo thun dài tay nam chất liệu mềm mại, màu xám, size M.'
UNION ALL
SELECT
  id,
  'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png'
FROM
  product_items
WHERE
  description = N'Quần kaki nam dáng thẳng, màu xanh đậm, size 30.'
UNION ALL
SELECT
  id,
  'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png'
FROM
  product_items
WHERE
  description = N'Quần kaki nam dáng thẳng, màu xanh đậm, size 30.'
UNION ALL
SELECT
  id,
  'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png'
FROM
  product_items
WHERE
  description = N'Váy đầm suông caro dáng dài, màu trắng, freesize.'
UNION ALL
SELECT
  id,
  'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png'
FROM
  product_items
WHERE
  description = N'Váy đầm suông caro dáng dài, màu trắng, freesize.'
UNION ALL
SELECT
  id,
  'ao_kieu_so_mi_nu_trang_1.webp'
FROM
  product_items
WHERE
  description = N'Bộ quần áo bé trai cotton, màu xanh, họa tiết ngộ nghĩnh, size 3 tuổi.'
UNION ALL
SELECT
  id,
  'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png'
FROM
  product_items
WHERE
  description = N'Bộ quần áo bé trai cotton, màu xanh, họa tiết ngộ nghĩnh, size 3 tuổi.'
UNION ALL
SELECT
  id,
  'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png'
FROM
  product_items
WHERE
  description = N'Bộ quần áo bé trai cotton, màu xanh, họa tiết ngộ nghĩnh, size 3 tuổi.'
UNION ALL
SELECT
  id,
  'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png'
FROM
  product_items
WHERE
  description = N'Bộ quần áo bé trai cotton, màu xanh, họa tiết ngộ nghĩnh, size 3 tuổi.'
UNION ALL
SELECT
  id,
  'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png'
FROM
  product_items
WHERE
  description = N'Váy bé gái dáng xòe hoa, màu hồng, phù hợp cho bé 5 tuổi.'
UNION ALL
SELECT
  id,
  'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png'
FROM
  product_items
WHERE
  description = N'Váy bé gái dáng xòe hoa, màu hồng, phù hợp cho bé 5 tuổi.'
UNION ALL
SELECT
  id,
  'ao_croptop_nu_trang_1.webp'
FROM
  product_items
WHERE
  description = N'Áo thun croptop nữ năng động, màu trắng, freesize.'
UNION ALL
SELECT
  id,
  'https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png'
FROM
  product_items
WHERE
  description = N'Áo thun croptop nữ năng động, màu trắng, freesize.';

--không lỗi
INSERT INTO
  user_addresses (
    account_id,
    recipient_name,
    phone,
    province,
    district,
    ward,
    street,
    label,
    is_default,
    note
  )
VALUES
  -- Địa chỉ mặc định cho Admin
  (
    (
      SELECT
        id
      FROM
        accounts
      WHERE
        email = 'adminCUDE@gmail.com'
    ),
    N'Nguyễn Văn A',
    '0901234567',
    N'Hồ Chí Minh',
    N'Quận 1',
    N'Phường Bến Nghé',
    N'123 Đường ABC',
    N'Nhà Riêng',
    1,
    N'Giao hàng ngoài giờ hành chính'
  ),
  -- Địa chỉ phụ cho Admin
  (
    (
      SELECT
        id
      FROM
        accounts
      WHERE
        email = 'adminCUDE@gmail.com'
    ),
    N'Nguyễn Văn A',
    '0907654321',
    N'Hồ Chí Minh',
    N'Quận Bình Thạnh',
    N'Phường 25',
    N'456 Đường XYZ',
    N'Công Ty',
    0,
    N'Giao vào giờ làm việc'
  );

INSERT INTO
  user_addresses (
    account_id,
    recipient_name,
    phone,
    province,
    district,
    ward,
    street,
    label,
    is_default,
    note
  )
VALUES
  -- Địa chỉ cho nguyenvana@example.com
  (
    (
      SELECT
        id
      FROM
        accounts
      WHERE
        email = 'nguyenvana@example.com'
    ),
    N'Nguyễn Văn A',
    '0901234567',
    N'Hà Nội',
    N'Quận Đống Đa',
    N'Phường Láng Thượng',
    N'123 Đường ABC',
    N'Nhà Riêng',
    1,
    NULL
  ),
  (
    (
      SELECT
        id
      FROM
        accounts
      WHERE
        email = 'nguyenvana@example.com'
    ),
    N'Nguyễn Văn A',
    '0901234567',
    N'Hà Nội',
    N'Quận Hoàn Kiếm',
    N'Phường Chương Dương Độ',
    N'456 Phố XYZ',
    N'Công Ty',
    0,
    NULL
  ),
  -- Địa chỉ cho tranvanb@example.com
  (
    (
      SELECT
        id
      FROM
        accounts
      WHERE
        email = 'tranvanb@example.com'
    ),
    N'Trần Văn B',
    '0902345678',
    N'Đà Nẵng',
    N'Quận Hải Châu',
    N'Phường Hòa Thuận Đông',
    N'789 Đường ABC',
    N'Nhà Riêng',
    1,
    N'Giao hàng vào buổi tối'
  ),
  -- Địa chỉ cho lethic@example.com
  (
    (
      SELECT
        id
      FROM
        accounts
      WHERE
        email = 'lethic@example.com'
    ),
    N'Lê Thị C',
    '0903456789',
    N'Hồ Chí Minh',
    N'Quận 3',
    N'Phường Võ Thị Sáu',
    N'101 Đường Trần Quốc Thảo',
    N'Nhà Riêng',
    1,
    NULL
  ),
  -- Địa chỉ cho phamvand@example.com
  (
    (
      SELECT
        id
      FROM
        accounts
      WHERE
        email = 'phamvand@example.com'
    ),
    N'Phạm Văn D',
    '0904567890',
    N'Cần Thơ',
    N'Quận Ninh Kiều',
    N'Phường Xuân Khánh',
    N'202 Đường 3/2',
    N'Nhà Riêng',
    1,
    NULL
  ),
  -- Địa chỉ cho hoangthie@example.com
  (
    (
      SELECT
        id
      FROM
        accounts
      WHERE
        email = 'hoangthie@example.com'
    ),
    N'Hoàng Thị E',
    '0905678901',
    N'Hải Phòng',
    N'Quận Ngô Quyền',
    N'Phường Đông Khê',
    N'303 Đường Lạch Tray',
    N'Nhà Riêng',
    1,
    NULL
  ),
  -- Địa chỉ cho doquangf@example.com
  (
    (
      SELECT
        id
      FROM
        accounts
      WHERE
        email = 'doquangf@example.com'
    ),
    N'Đỗ Quang F',
    '0906789012',
    N'Thừa Thiên Huế',
    N'Thành phố Huế',
    N'Phường Vỹ Dạ',
    N'404 Đường Phạm Văn Đồng',
    N'Nhà Riêng',
    1,
    NULL
  ),
  -- Địa chỉ cho buitranh@example.com
  (
    (
      SELECT
        id
      FROM
        accounts
      WHERE
        email = 'buitranh@example.com'
    ),
    N'Bùi Trần H',
    '0907890123',
    N'Khánh Hòa',
    N'Thành phố Nha Trang',
    N'Phường Lộc Thọ',
    N'505 Đường Biệt Thự',
    N'Nhà Riêng',
    1,
    NULL
  ),
  -- Địa chỉ cho vothik@example.com
  (
    (
      SELECT
        id
      FROM
        accounts
      WHERE
        email = 'vothik@example.com'
    ),
    N'Võ Thị K',
    '0908901234',
    N'Bình Dương',
    N'Thành phố Thủ Dầu Một',
    N'Phường Phú Lợi',
    N'606 Đường Yersin',
    N'Nhà Riêng',
    1,
    NULL
  ),
  -- Địa chỉ cho dangvank@example.com
  (
    (
      SELECT
        id
      FROM
        accounts
      WHERE
        email = 'dangvank@example.com'
    ),
    N'Đặng Văn K',
    '0909012345',
    N'Đồng Nai',
    N'Thành phố Biên Hòa',
    N'Phường Quang Vinh',
    N'707 Đường CMT8',
    N'Nhà Riêng',
    1,
    NULL
  ),
  -- Địa chỉ cho truongvant@example.com
  (
    (
      SELECT
        id
      FROM
        accounts
      WHERE
        email = 'truongvant@example.com'
    ),
    N'Trương Văn T',
    '0910123456',
    N'Gia Lai',
    N'Thành phố Pleiku',
    N'Phường Diên Hồng',
    N'808 Đường Wừu',
    N'Nhà Riêng',
    1,
    NULL
  );
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
--delete from promotions
--INSERT INTO
--  promotions (
--    name,
--    description,
--    type,
--    discount_type,
--    discount_value,
--    combo_price,
--    usage_limit,
--    start_at,
--    end_at,
--    is_active
--  )
--VALUES
--  (
--    N'Giảm giá mùa hè',
--    N'Giảm giá 15% cho toàn bộ sản phẩm hè',
--    'DISCOUNT',
--    'PERCENT',
--    15.00,
--    NULL,
--    500,
--    GETDATE (),
--    DATEADD (month, 2, GETDATE ()),
--    1
--  ),
--  (
--    N'Combo áo đôi',
--    N'Mua áo polo nam và áo croptop nữ với giá ưu đãi',
--    'COMBO',
--    NULL,
--    NULL,
--    250000.00,
--    100,
--    GETDATE (),
--    DATEADD (month, 1, GETDATE ()),
--    1
--  ),
--  (
--    N'Khuyến mãi Flash Sale',
--    N'Giảm giá sốc 20% cho một số sản phẩm chọn lọc trong 24 giờ',
--    'DISCOUNT',
--    'PERCENT',
--    20.00,
--    NULL,
--    200,
--    GETDATE (),
--    DATEADD (day, 1, GETDATE ()),
--    1
--  ),
--  (
--    N'Mua 2 tặng 1',
--    N'Mua 2 quần short thể thao tặng 1 áo thun tay ngắn bé trai',
--    'COMBO',
--    NULL,
--    NULL,
--    NULL,
--    50,
--    GETDATE (),
--    DATEADD (month, 1, GETDATE ()),
--    1
--  ),
--  (
--    N'Ưu đãi thành viên mới',
--    N'Giảm 100k cho đơn hàng đầu tiên từ 500k',
--    'DISCOUNT',
--    'AMOUNT',
--    100000.00,
--    NULL,
--    1000,
--    GETDATE (),
--    DATEADD (year, 1, GETDATE ()),
--    1
--  );

--INSERT INTO Promotion_Products (id, promotion_id, product_item_id, require_qty, is_gift, cost_share)
--SELECT 1, p.id, pi.id, NULL, 0, NULL
--FROM promotions p, product_items pi
--WHERE p.name = N'Giảm giá mùa hè'
--  AND pi.description = N'Áo polo nam cotton pha cao cấp, màu trắng, size M.'
--UNION ALL
--SELECT
--  2,
--  p.id,
--  pi.id,
--  NULL,
--  0,
--  NULL
--FROM
--  promotions p,
--  product_items pi
--WHERE
--  p.name = N'Giảm giá mùa hè'
--  AND pi.description = N'Váy đầm suông caro dáng dài, màu trắng, freesize.'
--UNION ALL
--SELECT
--  3,
--  p.id,
--  pi.id,
--  1,
--  0,
--  NULL
--FROM
--  promotions p,
--  product_items pi
--WHERE
--  p.name = N'Combo áo đôi'
--  AND pi.description = N'Áo polo nam cotton pha cao cấp, màu đen, size L.'
--UNION ALL
--SELECT
--  4,
--  p.id,
--  pi.id,
--  1,
--  0,
--  NULL
--FROM
--  promotions p,
--  product_items pi
--WHERE
--  p.name = N'Combo áo đôi'
--  AND pi.description = N'Áo thun croptop nữ năng động, màu trắng, freesize.'
--UNION ALL
--SELECT
--  5,
--  p.id,
--  pi.id,
--  NULL,
--  0,
--  NULL
--FROM
--  promotions p,
--  product_items pi
--WHERE
--  p.name = N'Khuyến mãi Flash Sale'
--  AND pi.description = N'Quần kaki nam dáng thẳng, màu xanh đậm, size 30.'
--UNION ALL
--SELECT
--  6,
--  p.id,
--  pi.id,
--  2,
--  0,
--  NULL
--FROM
--  promotions p,
--  product_items pi
--WHERE
--  p.name = N'Mua 2 tặng 1'
--  AND pi.description = N'Áo polo nam cotton pha cao cấp, màu trắng, size M.'
--UNION ALL
--SELECT
--  7,
--  p.id,
--  pi.id,
--  1,
--  1,
--  NULL
--FROM
--  promotions p,
--  product_items pi
--WHERE
--  p.name = N'Mua 2 tặng 1'
--  AND pi.description = N'Áo thun dài tay nam chất liệu mềm mại, màu xám, size M.';

--INSERT INTO
--  cost_histories (product_item_id, cost)
--SELECT
--  pi.id,
--  75000.00
--FROM
--  product_items pi
--WHERE
--  pi.description = N'Áo polo nam cotton pha cao cấp, màu trắng, size M.'
--UNION ALL
--SELECT
--  pi.id,
--  80000.00
--FROM
--  product_items pi
--WHERE
--  pi.description = N'Áo polo nam cotton pha cao cấp, màu trắng, size M.'
--UNION ALL
--SELECT
--  pi.id,
--  180000.00
--FROM
--  product_items pi
--WHERE
--  pi.description = N'Áo khoác bomber nam chất liệu chống nước, màu đen, size M.'
--UNION ALL
--SELECT
--  pi.id,
--  200000.00
--FROM
--  product_items pi
--WHERE
--  pi.description = N'Áo khoác bomber nam chất liệu chống nước, màu đen, size M.'
--UNION ALL
--SELECT
--  pi.id,
--  170000.00
--FROM
--  product_items pi
--WHERE
--  pi.description = N'Quần kaki nam dáng thẳng, màu xanh đậm, size 30.'
--UNION ALL
--SELECT
--  pi.id,
--  180000.00
--FROM
--  product_items pi
--WHERE
--  pi.description = N'Quần kaki nam dáng thẳng, màu xanh đậm, size 30.';

--INSERT INTO
--  price_histories (product_item_id, price)
--VALUES
--  -- Áo Polo Nam Trắng Size M
--  (
--    (
--      SELECT
--        id
--      FROM
--        product_items
--      WHERE
--        description = N'Áo polo nam cotton pha cao cấp, màu trắng, size M.'
--    ),
--    149000.00
--  ), -- Giá bán cũ
--  (
--    (
--      SELECT
--        id
--      FROM
--        product_items
--      WHERE
--        description = N'Áo polo nam cotton pha cao cấp, màu trắng, size M.'
--    ),
--    159000.00
--  ), -- Giá bán hiện tại
--  -- Áo Bomber Nam Đen Size M
--  (
--    (
--      SELECT
--        id
--      FROM
--        product_items
--      WHERE
--        description = N'Áo khoác bomber nam chất liệu chống nước, màu đen, size M.'
--    ),
--    350000.00
--  ), -- Giá bán cũ
--  (
--    (
--      SELECT
--        id
--      FROM
--        product_items
--      WHERE
--        description = N'Áo khoác bomber nam chất liệu chống nước, màu đen, size M.'
--    ),
--    399000.00
--  ), -- Giá bán hiện tại
--  -- Quần Kaki Nam Dáng Thẳng Xanh Đậm Size 30
--  (
--    (
--      SELECT
--        id
--      FROM
--        product_items
--      WHERE
--        description = N'Quần kaki nam dáng thẳng, màu xanh đậm, size 30.'
--    ),
--    329000.00
--  ), -- Giá bán cũ
--  (
--    (
--      SELECT
--        id
--      FROM
--        product_items
--      WHERE
--        description = N'Quần kaki nam dáng thẳng, màu xanh đậm, size 30.'
--    ),
--    349000.00
--  );

---- Giá bán hiện tại
---- select description from  product_items
--INSERT INTO
--  price_histories (product_item_id, price)
--SELECT
--  pi.id,
--  149000.00
--FROM
--  product_items pi
--WHERE
--  pi.description = N'Áo polo nam cotton pha cao cấp, màu trắng, size M.'
--UNION ALL
--SELECT
--  pi.id,
--  159000.00
--FROM
--  product_items pi
--WHERE
--  pi.description = N'Áo polo nam cotton pha cao cấp, màu trắng, size M.'
--UNION ALL
--SELECT
--  pi.id,
--  350000.00
--FROM
--  product_items pi
--WHERE
--  pi.description = N'Áo khoác bomber nam chất liệu chống nước, màu đen, size M.'
--UNION ALL
--SELECT
--  pi.id,
--  399000.00
--FROM
--  product_items pi
--WHERE
--  pi.description = N'Áo khoác bomber nam chất liệu chống nước, màu đen, size M.'
--UNION ALL
--SELECT
--  pi.id,
--  329000.00
--FROM
--  product_items pi
--WHERE
--  pi.description = N'Quần kaki nam dáng thẳng, màu xanh đậm, size 30.'
--UNION ALL
--SELECT
--  pi.id,
--  349000.00
--FROM
--  product_items pi
--WHERE
--  pi.description = N'Quần kaki nam dáng thẳng, màu xanh đậm, size 30.';

--INSERT INTO
--  coupons (
--    code,
--    description,
--    discount_type,
--    discount_value,
--    min_order_amount,
--    max_discount_amount,
--    usage_limit,
--    usage_per_customer,
--    is_allow_voucher,
--    is_active,
--    customer_group,
--    start_at,
--    end_at
--  )
--VALUES
--  (
--    N'FREESHIP25K',
--    N'Miễn phí vận chuyển cho đơn hàng từ 250K',
--    'FREESHIP',
--    25000.00,
--    250000.00,
--    25000.00,
--    500,
--    1,
--    0,
--    1,
--    NULL,
--    GETDATE (),
--    DATEADD (month, 3, GETDATE ())
--  ),
--  (
--    N'GIAM100K',
--    N'Giảm 100K cho đơn hàng từ 500K',
--    'G-DISCOUNT',
--    100000.00,
--    500000.00,
--    100000.00,
--    300,
--    1,
--    0,
--    1,
--    NULL,
--    GETDATE (),
--    DATEADD (month, 2, GETDATE ())
--  ),
--  (
--    N'VIP50K',
--    N'Giảm 50K cho thành viên Bạc trở lên',
--    'G-DISCOUNT',
--    50000.00,
--    300000.00,
--    50000.00,
--    150,
--    1,
--    0,
--    1,
--    N'Bạc',
--    GETDATE (),
--    DATEADD (month, 1, GETDATE ())
--  ),
--  (
--    N'NEWUSER2025',
--    N'Ưu đãi 20% cho khách hàng mới, tối đa 50K',
--    'G-DISCOUNT',
--    0.20,
--    100000.00,
--    50000.00,
--    1000,
--    1,
--    0,
--    1,
--    N'Đồng',
--    GETDATE (),
--    DATEADD (year, 1, GETDATE ())
--  ),
--  (
--    N'SALE50',
--    N'Giảm 50% tối đa 200K cho toàn bộ đơn hàng',
--    'G-DISCOUNT',
--    0.50,
--    0.00,
--    200000.00,
--    50,
--    1,
--    0,
--    1,
--    NULL,
--    GETDATE (),
--    DATEADD (day, 7, GETDATE ())
--  );

INSERT INTO
  variants (name)
VALUES
  (N'Màu sắc'),
  (N'Kích thước');

GO
INSERT INTO
  variant_values (variant_id, signal_sku, description)
VALUES
  -- Màu sắc
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Màu sắc'
    ),
    N'TR',
    N'Trắng'
  ),
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Màu sắc'
    ),
    N'DEN',
    N'Đen'
  ),
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Màu sắc'
    ),
    N'XAM',
    N'Xám'
  ),
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Màu sắc'
    ),
    N'XD',
    N'Xanh Đậm'
  ),
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Màu sắc'
    ),
    N'XN',
    N'Xanh Ngọc'
  ),
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Màu sắc'
    ),
    N'H',
    N'Hồng'
  ),
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Màu sắc'
    ),
    N'V',
    N'Vàng'
  ),
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Màu sắc'
    ),
    N'T',
    N'Tím'
  ),
  -- Kích thước
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Kích thước'
    ),
    N'S',
    N'Size S'
  ),
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Kích thước'
    ),
    N'M',
    N'Size M'
  ),
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Kích thước'
    ),
    N'L',
    N'Size L'
  ),
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Kích thước'
    ),
    N'XL',
    N'Size XL'
  ),
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Kích thước'
    ),
    N'FS',
    N'FreeSize'
  ),
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Kích thước'
    ),
    N'30',
    N'Size 30'
  ),
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Kích thước'
    ),
    N'32',
    N'Size 32'
  ),
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Kích thước'
    ),
    N'3T',
    N'Size 3 tuổi'
  ),
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Kích thước'
    ),
    N'4T',
    N'Size 4 tuổi'
  ),
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Kích thước'
    ),
    N'5T',
    N'Size 5 tuổi'
  ),
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Kích thước'
    ),
    N'6T',
    N'Size 6 tuổi'
  ),
  (
    (
      SELECT
        id
      FROM
        variants
      WHERE
        name = N'Kích thước'
    ),
    N'7T',
    N'Size 7 tuổi'
  );

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
	name,
	description,
	type,
	discount_value,
	combo_price,
	qty,
	start_at,
	end_at,
	is_active,
	created_at,
	updated_at
)
VALUES
(N'Khuyến mãi hè', N'Giảm giá 20% toàn bộ sản phẩm trong mùa hè', 'DISCOUNT', 20.00, NULL, 100, GETDATE(), DATEADD(DAY, 30, GETDATE()), 1, GETDATE(), GETDATE()),

(N'Mua 2 tặng 1', N'Khi mua 2 sản phẩm sẽ được tặng 1 sản phẩm cùng loại', 'COMBO', NULL, 0.00, 50, GETDATE(), DATEADD(DAY, 15, GETDATE()), 1, GETDATE(), GETDATE()),

(N'Giảm 100k đơn từ 500k', N'Áp dụng cho đơn hàng từ 500,000 VNĐ trở lên', 'DISCOUNT', 100000, NULL, 200, GETDATE(), DATEADD(DAY, 10, GETDATE()), 1, GETDATE(), GETDATE()),

(N'Combo Café Tháng 7', N'Combo 3 gói café chỉ 199,000 VNĐ', 'COMBO', NULL, 199000, 30, GETDATE(), DATEADD(DAY, 7, GETDATE()), 1, GETDATE(), GETDATE());

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


INSERT INTO promotion_products (
    promotion_id,
    product_item_id,
    require_qty,
    is_gift,
    created_at,
    updated_at
) VALUES
(1, 1, 1, 0, GETDATE(), GETDATE()), -- Giảm giá cho sản phẩm 1
(1, 2, 2, 0, GETDATE(), GETDATE()), -- Mua 2 sản phẩm 2 mới áp dụng khuyến mãi
(2, 3, 1, 1, GETDATE(), GETDATE()); -- Sản phẩm 3 là quà tặng trong combo

-- Thêm combo khuyến mãi
INSERT INTO promotion_products (
    promotion_id,
    product_item_id,
    require_qty,
    is_gift,
    created_at,
    updated_at
) VALUES
(2, 1, 1, 0, GETDATE(), GETDATE()), -- Sản phẩm 1 là bắt buộc trong combo
(2, 2, 1, 0, GETDATE(), GETDATE()); -- Sản phẩm 2 cũng bắt buộc

-- INSERT 30 orders
DECLARE @i INT = 1;
WHILE @i <= 30
BEGIN
  INSERT INTO orders (
    account_id,
    payment_method_id,
    shipping_method_id,
    shipping_status,
    estimated_shipping_fee,
    actual_shipping_fee,
    payment_status,
    note,
    point,
    final_total,
    order_infor,
    order_code,
    created_at,
    updated_at
  )
  VALUES (
    (SELECT TOP 1 id FROM accounts ORDER BY NEWID()),
    (SELECT TOP 1 id FROM payment_methods ORDER BY NEWID()),
    (SELECT TOP 1 id FROM shipping_methods ORDER BY NEWID()),
    N'Đang xử lý',
    25000.00,
    25000.00,
    N'Chưa thanh toán',
    N'Giao trước 6 giờ tối',
    10,
    100000.00 + (@i * 1000),
    N'Họ tên người nhận, địa chỉ đầy đủ, số điện thoại',
    CONCAT('ORD', FORMAT(@i, '0000')),
    GETDATE(),
    GETDATE()
  );

  SET @i = @i + 1;
END;



-- INSERT order_items for each order
DECLARE @order_id INT, @max_id INT, @pid INT, @j INT;

SELECT @order_id = MIN(id), @max_id = MAX(id) FROM orders;

WHILE @order_id <= @max_id
BEGIN
  SET @j = 1;

  WHILE @j <= (1 + ABS(CHECKSUM(NEWID()) % 3)) -- mỗi đơn có 1-3 sản phẩm
  BEGIN
    SELECT TOP 1 @pid = id FROM product_items ORDER BY NEWID();

    INSERT INTO order_items (
      order_id,
      product_item_id,
      qty,
      price_at_buy,
      selling_price,
      coupon_code,
      created_at,
      updated_at
    )
    VALUES (
      @order_id,
      @pid,
      1 + ABS(CHECKSUM(NEWID()) % 5),  -- qty từ 1-5
      (SELECT price FROM product_items WHERE id = @pid),
      (SELECT price FROM product_items WHERE id = @pid),
      NULL,
      GETDATE(),
      GETDATE()
    );

    SET @j = @j + 1;
  END;

  SET @order_id = @order_id + 1;
END;
DECLARE @i INT = 1;
DECLARE @order_item_id INT;

WHILE @i <= 10
BEGIN
  -- Lấy ngẫu nhiên 1 id từ order_items
  SELECT TOP 1 @order_item_id = id FROM order_items ORDER BY NEWID();

  INSERT INTO returns (
    order_product_item_id,
    qty,
    reason,
    image_url1,
    image_url2,
    image_url3,
    video_url,
    is_returned_money,
    refund_amount,
    return_type,
    refund_type,
    status,
    processed_at,
    created_at,
    updated_at
  )
  VALUES (
    @order_item_id,
    1 + ABS(CHECKSUM(NEWID()) % 3), -- qty từ 1–3
    N'Sản phẩm bị lỗi, cần đổi trả',
    'https://example.com/image1.jpg',
    NULL,
    NULL,
    'https://example.com/video.mp4',
    0,
    50000.00,
    'REFUND',
    'WALLET',
    'PENDING',
    NULL,
    GETDATE(),
    GETDATE()
  );

  SET @i = @i + 1;
END;

DECLARE @i INT = 1;
WHILE @i <= 20
BEGIN
    INSERT INTO reviews (
        product_item_id,
        account_id,
        rating,
        comment,
        image_url1,
        image_url2,
        image_url3,
        video_url,
        created_at
    )
    SELECT TOP 1 
        id AS product_item_id,
        1 + ABS(CHECKSUM(NEWID()) % 10),  -- Giả sử có 10 tài khoản
        1 + ABS(CHECKSUM(NEWID()) % 5),   -- Rating từ 1–5
        N'Sản phẩm dùng rất tốt, sẽ mua lại!',
        'https://example.com/image1.jpg',
        'https://example.com/image2.jpg',
        'https://example.com/image3.jpg',
        'https://example.com/video.mp4',
        DATEADD(DAY, -ABS(CHECKSUM(NEWID()) % 30), GETDATE())
    FROM product_items
    ORDER BY NEWID();

    SET @i = @i + 1;
END;
select * from cancels
INSERT INTO cancels (order_id, reason, is_paid, status, created_at, updated_at)
VALUES 
(1, N'Khách thay đổi quyết định', 0, N'PENDING', GETDATE(), GETDATE()),
(2, N'Không thể liên hệ với người nhận', 1, N'APPROVED', GETDATE(), GETDATE()),
(3, N'Trùng đơn', 0, N'REJECTED', GETDATE(), GETDATE()),
(4, N'Sản phẩm không đúng mô tả', 1, N'DONE', GETDATE(), GETDATE()),
(5, N'Địa chỉ giao hàng không hợp lệ', 0, N'PENDING', GETDATE(), GETDATE()),
(6, N'Khách đặt nhầm', 0, N'DONE', GETDATE(), GETDATE()),
(7, N'Hệ thống báo lỗi đơn hàng', 1, N'APPROVED', GETDATE(), GETDATE()),
(8, N'Khách hủy đơn khi chưa thanh toán', 0, N'REJECTED', GETDATE(), GETDATE()),
(9, N'Giao hàng trễ so với cam kết', 1, N'APPROVED', GETDATE(), GETDATE()),
(10, N'Không cần nữa', 0, N'PENDING', GETDATE(), GETDATE());
