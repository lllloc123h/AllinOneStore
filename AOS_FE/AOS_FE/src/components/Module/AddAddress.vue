<template>
  <div class="overlay">
    <div class="form-container">
      <h3>Thêm địa chỉ</h3>
      <div class="input-group">
        <input type="text" placeholder="Họ và tên" />
        <input type="text" placeholder="Số điện thoại" />
      </div>
      <select>
        <option selected disabled>Tỉnh/ Thành phố, Quận/Huyện, Phường/Xã</option>
      </select>
      <input type="text" placeholder="Địa chỉ cụ thể" />
      <div class="map-box">
        <button class="add-location">+ Thêm vị trí</button>
      </div>
      <div class="address-type">
        <label>Loại địa chỉ:</label>
        <button class="type-btn">Nhà Riêng</button>
        <button class="type-btn">Nơi làm việc</button>
      </div>
      <div class="actions">
        <button class="cancel-btn">Quay lại</button>
        <button class="submit-btn">Xác nhận</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0,0,0,0.4);
  display: flex;
  align-items: center;
  justify-content: center;
}

.form-container {
  background: #fff;
  width: 400px;
  padding: 20px 25px;
  border-radius: 6px;
  box-shadow: 0 0 10px rgba(0,0,0,0.2);
}

h3 {
  margin-bottom: 20px;
}

.input-group {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
}

input, select {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.map-box {
  background: #f7f7f7;
  height: 100px;
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.add-location {
  border: 1px solid #ccc;
  background: white;
  padding: 8px 15px;
  cursor: pointer;
  border-radius: 4px;
}

.address-type {
  margin: 15px 0;
}

.address-type label {
  display: block;
  margin-bottom: 5px;
}

.type-btn {
  padding: 8px 15px;
  margin-right: 10px;
  border: 1px solid #ccc;
  background: #fff;
  cursor: pointer;
  border-radius: 4px;
}

.actions {
  display: flex;
  justify-content: space-between;
}

.cancel-btn {
  background: #f2f2f2;
  border: none;
  padding: 10px 15px;
  border-radius: 4px;
  cursor: pointer;
}

.submit-btn {
  background: #ee4d2d;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 4px;
  cursor: pointer;
}
</style>
