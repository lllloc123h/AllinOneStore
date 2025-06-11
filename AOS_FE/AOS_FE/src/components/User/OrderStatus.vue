<template>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  <div class="container">
    <h2>Trạng thái đơn hàng</h2>

    <div class="section">
      <h3>Thông tin đơn hàng</h3>
      <p><strong>Mã đơn:</strong> ALLINONE123</p>
      <p><strong>Ngày đặt:</strong> 10/06/2025</p>
      <p><strong>Khách hàng:</strong> Nguyễn Văn A</p>
    </div>

    <div class="section order-status">
      <div class="step done">
        <div class="circle"><i class="fa-solid fa-receipt"></i></div>
        <div>Đã đặt</div>
      </div>
      <div class="step done">
        <div class="circle"><i class="fas fa-cogs"></i></div>
        <div>Đang xử lý</div>
      </div>
      <div class="step active">
        <div class="circle"><i class="fas fa-truck"></i></div>
        <div>Đang giao</div>
      </div>
      <div class="step">
        <div class="circle"><i class="fas fa-box-open"></i></div>
        <div>Hoàn tất</div>
      </div>
    </div>

    <div class="section products">
      <h3>Danh sách sản phẩm</h3>
      <table>
        <thead>
          <tr>
            <th>Ảnh</th>
            <th>Tên sản phẩm</th>
            <th>Số lượng</th>
            <th>Đơn giá</th>
            <th>Tổng</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td><img src="https://via.placeholder.com/50/CCCCCC/FFFFFF?text=Shirt" alt="Product Image"></td>
            <td>Áo thun trắng</td>
            <td>2</td>
            <td>150.000đ</td>
            <td>300.000đ</td>
          </tr>
          <tr>
            <td><img src="https://via.placeholder.com/50/CCCCCC/FFFFFF?text=Short" alt="Product Image"></td>
            <td>Quần short kaki</td>
            <td>1</td>
            <td>250.000đ</td>
            <td>250.000đ</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="section">
      <h3>Thông tin giao hàng</h3>
      <p><strong>Người nhận:</strong> Nguyễn Văn A</p>
      <p><strong>Địa chỉ:</strong> 123 Lê Lợi, Q.1, TP.HCM</p>
      <p><strong>Điện thoại:</strong> 0909 123 456</p>
      <p><strong>Đơn vị vận chuyển:</strong> Giao Hàng Nhanh</p>
      <p><strong>Mã vận đơn:</strong> GHN123456789 - <a href="#">Tra cứu</a></p>
    </div>

    <div class="section">
      <h3>Thông tin thanh toán</h3>
      <p><strong>Phương thức:</strong> COD</p>
      <p><strong>Trạng thái:</strong> Chưa thanh toán</p>
      <p><strong>Tổng cộng:</strong> 550.000đ</p>
    </div>

    <div class="section">
      <h3>Ghi chú đơn hàng</h3>
      <textarea readonly class="note">Khách hàng yêu cầu giao sau 17h.</textarea>
    </div>

    <div class="section">
      <h3>Lịch sử xử lý</h3>
      <table class="history-table">
        <thead>
          <tr>
            <th>Thời gian</th>
            <th>Nội dung</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>10/06/2025 08:15</td>
            <td>Đơn hàng được tạo</td>
          </tr>
          <tr>
            <td>10/06/2025 10:00</td>
            <td>Đã xác nhận và đang xử lý</td>
          </tr>
          <tr>
            <td>10/06/2025 15:30</td>
            <td>Đã bàn giao cho đơn vị vận chuyển</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="actions">
      <button class="cancel">Hủy đơn</button>
      <button class="review">Đánh giá</button>
      <button>Mua lại</button>
      <button>In hóa đơn</button>
    </div>
  </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Quicksand:wght@300..700&display=swap');

body {
  font-family: 'Quicksand', sans-serif;
  margin: 0;
  background-color: #f8f6f4; 
  color: #333;
}

.container {
  max-width: 1000px;
  margin: 40px auto;
  background-color: white;
  padding: 30px;
  border-radius: 15px; 
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.08); 
  border: none; 
}

.section {
  margin-bottom: 30px; 
  padding: 20px;
  border: 1px solid #e0e0e0; 
  border-radius: 12px;
  background-color: #ffffff; 
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.03); 
}

h2 {
  color: #5d4a3e; 
  margin-bottom: 25px; 
  text-align: center;
  font-weight: 700;
}

h3 {
  color: #6a5647;
  margin-bottom: 15px;
  font-weight: 600;
}

p {
  line-height: 1.6;
  color: #4a4a4a;
}

p strong {
  color: #333;
}

.order-status {
  display: flex;
  justify-content: space-between;
  position: relative;
  margin: 30px 0;
  padding: 30px 20px;
  background-color: #f9f8f7;
  border: 1px solid #eee;
}

.order-status::before {
  content: "";
  position: absolute;
  top: 50%;
  left: 0;
  height: 3px;
  width: 100%;
  background: #e7ded9;
  z-index: 0;
}

.step {
  position: relative;
  z-index: 1;
  text-align: center;
  flex: 1;
  color: #888;
  font-weight: 500;
}

.step .circle {
  width: 35px;
  height: 35px;
  border-radius: 50%;
  background: #d4c8c0;
  margin: 0 auto 10px;
  color: white;
  line-height: 35px;
  font-size: 16px;
  font-weight: bold;
  display: flex;
  justify-content: center;
  align-items: center;
}

.step .circle i {
  font-family: "Font Awesome 6 Free";
  font-weight: 900;
  font-size: 16px;
}

.step.active {
  color: #b59b8a;
}

.step.active .circle {
  background: #b59b8a;
}

.step.done {
  color: #7b6d61;
}

.step.done .circle {
  background: #7b6d61;
}

table,
.history-table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;
  margin-top: 15px;
  background-color: white;
  border-radius: 10px; 
  overflow: hidden; 
}

th,
td,
.history-table th,
.history-table td {
  border: 1px solid #f0f0f0;
  padding: 12px;
  text-align: center;
  font-size: 0.95rem;
  color: #4a4a4a;
}

th,
.history-table th {
  background-color: #fdfaf8;
  color: #6a5647;
  font-weight: 600;
  border-bottom: 2px solid #e7ded9;
}

table thead tr:first-child th:first-child {
  border-top-left-radius: 10px;
}
table thead tr:first-child th:last-child {
  border-top-right-radius: 10px;
}

table tbody tr:last-child td:first-child {
  border-bottom-left-radius: 10px;
}
table tbody tr:last-child td:last-child {
  border-bottom-right-radius: 10px;
}

.products img {
  width: 60px; 
  height: 60px;
  object-fit: cover;
  border-radius: 8px; 
  border: 1px solid #e0e0e0;
}

.actions {
  display: flex;
  gap: 15px; 
  flex-wrap: wrap;
  justify-content: center;
  margin-top: 40px;
}

.actions button {
  background-color: #b59b8a; 
  color: white;
  border: none;
  padding: 12px 25px; 
  border-radius: 25px; 
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  transition: background-color 0.3s ease, transform 0.2s ease;
  box-shadow: 0 3px 8px rgba(0, 0, 0, 0.1);
}

.actions button:hover {
  background-color: #a08c7e;
  transform: translateY(-2px); 
}

.actions button.cancel {
  background-color: #e28e8e; 
}

.actions button.cancel:hover {
  background-color: #d17b7b;
}

.actions button.review {
  background-color: #f7d6a5; 
  color: #8a6a4d; 
}

.actions button.review:hover {
  background-color: #f5c790;
}

textarea.note {
  width: 100%;
  min-height: 100px;
  padding: 15px;
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  resize: vertical;
  font-size: 1rem;
  font-family: 'Quicksand', sans-serif;
  background-color: whitewhite;
  color: #333;
  box-sizing: border-box;
}

@media (max-width: 600px) {
  .order-status {
    flex-direction: column;
    gap: 20px;
    padding: 20px 10px;
  }

  .order-status::before {
    display: none;
  }

  .actions {
    flex-direction: column;
    align-items: center;
  }

  th,
  td {
    padding: 8px;
    font-size: 0.85rem;
  }

  .products img {
    width: 40px;
    height: 40px;
  }

  .container {
    margin: 20px auto;
    padding: 20px;
  }
}
</style>