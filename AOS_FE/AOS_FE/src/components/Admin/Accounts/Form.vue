<template>
  <div class="container-fluid row">
    <div class="aside col-3">
      <Dashboard></Dashboard>
    </div>
    <div class="article col-9">
      <form
        class="account-form shadow-lg p-4 rounded bg-white"
        @submit.prevent="props.action === 'create' ? submitForm() : submitUpdateForm()"
      >
        <div
          class="mb-4"
          :style="
            props.action === 'view' || props.action === 'create' ? 'display:none;' : ''
          "
        >
          <label
            for="id"
            v-if="props.action !== 'create'"
            :hidden="props.action === 'view'"
            class="form-label text-capitalize"
            >Id</label
          >
          <input
            id="id"
            v-model="formData.id"
            readonly
            v-if="props.action !== 'create'"
            :hidden="props.action === 'view'"
            type="number"
            class="form-control custom-input"
            placeholder="Enter id"
          />
        </div>
        <div class="mb-4">
          <label for="email" class="form-label text-capitalize fw-semibold">Email</label>
          <input
            id="email"
            v-model="formData.email"
            type="email"
            class="form-control custom-input"
            placeholder="Enter email"
          />
        </div>

        <div class="mb-4">
          <label for="password" class="form-label text-capitalize fw-semibold"
            >Password</label
          >
          <input
            id="password"
            v-model="formData.password"
            type="password"
            class="form-control custom-input"
            placeholder="Enter password"
          />
        </div>

        <div class="mb-4">
          <label for="fullname" class="form-label text-capitalize fw-semibold"
            >Full Name</label
          >
          <input
            id="fullname"
            v-model="formData.fullname"
            type="text"
            class="form-control custom-input"
            placeholder="Enter fullname"
          />
        </div>
        <div class="mb-4">
          <label for="phone" class="form-label text-capitalize fw-semibold">Phone</label>
          <input
            id="phone"
            v-model="formData.phone"
            type="text"
            class="form-control custom-input"
            placeholder="Enter phone"
          />
        </div>

        <div class="mb-4">
          <label class="form-label text-capitalize fw-semibold">Gender</label>
          <div>
            <label class="form-check-label me-3">
              <input
                type="radio"
                class="form-check-input"
                v-model="formData.gender"
                :value="true"
              />
              Male
            </label>
            <label class="form-check-label me-3">
              <input
                type="radio"
                class="form-check-input"
                v-model="formData.gender"
                :value="false"
              />
              Female
            </label>
          </div>
        </div>

        <div class="mb-4">
          <label class="form-label text-capitalize fw-semibold">Active</label>
          <div>
            <label class="form-check-label me-3">
              <input
                type="radio"
                class="form-check-input"
                v-model="formData.active"
                :value="true"
              />
              Active
            </label>
            <label class="form-check-label me-3">
              <input
                type="radio"
                class="form-check-input"
                v-model="formData.active"
                :value="false"
              />
              Inactive
            </label>
          </div>
        </div>
        <div class="mb-4">
          <label for="birthday" class="form-label text-capitalize fw-semibold"
            >Birthday</label
          >
          <input
            id="birthday"
            v-model="formData.birthday"
            type="datetime-local"
            class="form-control custom-input"
            placeholder="Select your birthday and time"
          />
        </div>
        <div class="mb-4">
          <label class="form-label text-capitalize fw-semibold">Avatar</label>
          <uploadProducts
            ref="uploadRef"
            :maxFiles="1"
            :aspectRatio="'1:1'"
            @update:images="handleImagesUpdate"
            @delete-image="handleImagesDelete"
          />
        </div>

        <div class="row mb-4">
          <div class="col-md-6">
            <label for="averageOrderValue" class="form-label text-capitalize fw-semibold"
              >Avg. Order Value</label
            >
            <input
              id="averageOrderValue"
              v-model="formData.averageOrderValue"
              type="number"
              class="form-control custom-input"
              placeholder="Auto-calculated from Total Spent ÷ Total Orders"
              disabled
              :style="{ backgroundColor: '#f8f9fa', cursor: 'not-allowed' }"
            />
            <small class="text-muted">
              ⚡ Tự động tính: Total Spent ÷ Total Orders
            </small>
          </div>
          <div class="col-md-6">
            <label for="userRank" class="form-label text-capitalize fw-semibold"
              >User Rank</label
            >
            <select
              id="userRank"
              v-model="formData.userRank"
              type="text"
              class="form-control"
              disabled
              :style="{ backgroundColor: '#f8f9fa', cursor: 'not-allowed' }"
            >
              <option value="Đồng">Đồng</option>
              <option value="Bạc">Bạc</option>
              <option value="Vàng">Vàng</option>
              <option value="Bạch Kim">Bạch Kim</option>
              <option value="Kim Cương">Kim Cương</option>
            </select>
            <small class="text-muted"> ⚡ Tự động tính dựa trên Loyalty Points </small>
          </div>
        </div>

        <!-- Rank Information Table -->
        <div class="mb-4 p-4 rounded bg-light border">
          <h6 class="fw-semibold mb-3 text-primary">
            📊 Bảng Thông Tin Xếp Hạng Khách Hàng
          </h6>
          <div class="table-responsive">
            <table class="table table-bordered table-sm">
              <thead class="table-primary">
                <tr>
                  <th class="text-center">Hạng</th>
                  <th class="text-center">Điều Kiện Loyalty Points</th>
                  <th class="text-center">Mô Tả</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="rule in userRankRules"
                  :key="rule.rank"
                  :class="{ 'table-warning': autoCalculatedRank === rule.rank }"
                >
                  <td class="text-center">
                    <span
                      class="badge"
                      :style="{ backgroundColor: rule.color, color: '#000' }"
                    >
                      {{ rule.rank }}
                    </span>
                  </td>
                  <td class="text-center">{{ rule.description }}</td>
                  <td class="text-center">
                    <span v-if="rule.rank === 'Đồng'">Khách hàng mới</span>
                    <span v-else-if="rule.rank === 'Bạc'">Khách hàng thân thiết</span>
                    <span v-else-if="rule.rank === 'Vàng'">Khách hàng VIP</span>
                    <span v-else-if="rule.rank === 'Bạch Kim'">Khách hàng cao cấp</span>
                    <span v-else-if="rule.rank === 'Kim Cương'">Khách hàng đặc biệt</span>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="mt-2">
            <small class="text-info">
              💡 <strong>Hiện tại:</strong>
              <span v-if="formData.loyaltyPoint">
                {{ formatNumber(formData.loyaltyPoint) }} điểm →
                <span class="fw-bold text-primary">{{ autoCalculatedRank }}</span>
              </span>
              <span v-else class="text-muted">Nhập loyalty points để xem rank</span>
            </small>
          </div>
        </div>

        <div class="row mb-4">
          <div class="col-md-4">
            <label for="totalSpent" class="form-label text-capitalize fw-semibold"
              >Total Spent</label
            >
            <input
              id="totalSpent"
              v-model="formData.totalSpent"
              type="number"
              class="form-control custom-input"
              placeholder="Enter total spent"
            />
          </div>

          <div class="col-md-4">
            <label for="totalOrder" class="form-label text-capitalize fw-semibold"
              >Total Orders</label
            >
            <input
              id="totalOrder"
              v-model="formData.totalOrder"
              type="number"
              class="form-control custom-input"
              placeholder="Enter total orders"
            />
            <small class="text-info" v-if="formData.totalSpent && formData.totalOrder">
              💡 Avg. Order Value: {{ formatCurrency(autoCalculatedAverageOrderValue) }}
            </small>
          </div>

          <div class="col-md-4">
            <label for="loyaltyPoint" class="form-label text-capitalize fw-semibold"
              >Loyalty Points</label
            >
            <input
              id="loyaltyPoint"
              v-model="formData.loyaltyPoint"
              type="number"
              class="form-control custom-input"
              placeholder="Enter loyalty points"
            />
          </div>
        </div>

        <!-- Average Order Value Calculation Info -->
        <div class="mb-4 p-3 rounded bg-info bg-opacity-10 border border-info">
          <h6 class="fw-semibold mb-2 text-info">
            🧮 Tính Toán Trung Bình Đơn Hàng (Average Order Value)
          </h6>
          <div class="row">
            <div class="col-md-12">
              <small class="text-muted">
                <strong>Công thức:</strong>
                <code>Average Order Value = Total Spent ÷ Total Orders</code>
              </small>
              <br />
              <small class="text-info">
                <strong>Ví dụ:</strong>
                <span
                  v-if="
                    formData.totalSpent && formData.totalOrder && formData.totalOrder > 0
                  "
                >
                  {{ formatCurrency(formData.totalSpent) }} ÷ {{ formData.totalOrder }} =
                  <strong class="text-primary">{{
                    formatCurrency(autoCalculatedAverageOrderValue)
                  }}</strong>
                </span>
                <span v-else class="text-muted">
                  1,000,000 VND ÷ 5 đơn hàng = 200,000 VND/đơn
                </span>
              </small>
            </div>
          </div>
        </div>

        <div class="role-list mb-4 p-4 rounded bg-light border">
          <label class="form-label mb-3 fw-semibold">Roles</label>
          <div class="row">
            <div v-for="role in dropDownListRoles" :key="role.id" class="col-md-6 mb-2">
              <label class="role-item w-100">
                <input
                  type="checkbox"
                  :value="role.id"
                  v-model="selectedRoles"
                  class="form-check-input me-2"
                />
                <div class="role-info">
                  <span class="role-name">{{ role.name }}</span>
                  <span class="role-date">Created: {{ formatDate(role.createdAt) }}</span>
                </div>
              </label>
            </div>
          </div>
        </div>

        <div class="text-end">
          <button
            type="submit"
            :disabled="props.action == 'view'"
            class="btn btn-primary px-5 py-2 rounded-pill"
          >
            <span v-if="props.action === 'create'">Create</span>
            <span v-else>Update</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted, watch, nextTick, computed } from "vue";
import { formatDate } from "../../Module/CommonsFunctions.js";
import Dashboard from "../../Module/DashBoard.vue";
import createCrudService from "../../../Configs/reusableCRUDService.js";
import { useRouter } from "vue-router";
import ImageUpload from "../../Module/ImageUpload.vue";
const router = useRouter();
import axios from "axios";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "bootstrap/dist/css/bootstrap.min.css";
import { dropDown } from "../../../Configs/DropDownList.js";
import api from "../../../Configs/api.js";
import uploadProducts from "../../Module/upload-images.vue";
import { notification } from "ant-design-vue";
const uploadRef = ref(null);
const props = defineProps({
  TableName: {
    type: String,
    required: true,
  },
  action: {
    type: String,
    required: true,
  },
  id: {
    type: String,
    required: true,
  },
});
const formTableService = createCrudService(props.TableName);
const dropDownListRoles = ref([]);
const resultUpload = ref([]);
const formData = reactive({
  id: "",
  email: "",
  password: "",
  gender: true,
  birthday: "",
  fullname: "",
  avatarUrl: "",
  phone: "",
  averageOrderValue: "",
  userRank: "",
  totalSpent: "",
  totalOrder: "",
  loyaltyPoint: "",
  createdAt: "",
  updatedAt: "",
  active: false,
});

// User Rank Rules based on Loyalty Points
const userRankRules = [
  { min: 0, max: 10000, rank: "Đồng", color: "#CD7F32", description: "0 - 10,000 điểm" },
  {
    min: 10000,
    max: 20000,
    rank: "Bạc",
    color: "#C0C0C0",
    description: "10,000 - 20,000 điểm",
  },
  {
    min: 20000,
    max: 30000,
    rank: "Vàng",
    color: "#FFD700",
    description: "20,000 - 30,000 điểm",
  },
  {
    min: 30000,
    max: 50000,
    rank: "Bạch Kim",
    color: "#E5E4E2",
    description: "30,000 - 50,000 điểm",
  },
  {
    min: 50000,
    max: Infinity,
    rank: "Kim Cương",
    color: "#B9F2FF",
    description: "Trên 50,000 điểm",
  },
];

// Format number with thousand separators
const formatNumber = (num) => {
  if (!num) return "0";
  return parseInt(num).toLocaleString("vi-VN");
};

// Format currency with VND
const formatCurrency = (amount) => {
  if (!amount || amount === 0) return "0 VND";
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
    minimumFractionDigits: 0,
    maximumFractionDigits: 0,
  }).format(amount);
};

// Function to calculate user rank based on loyalty points
const calculateUserRank = (loyaltyPoints) => {
  const points = parseInt(loyaltyPoints) || 0;
  const rule = userRankRules.find((rule) => points >= rule.min && points < rule.max);
  return rule ? rule.rank : "Đồng";
};

// Computed property for auto-calculated rank
const autoCalculatedRank = computed(() => {
  return calculateUserRank(formData.loyaltyPoint);
});

// Watch loyalty points changes to auto-update rank
watch(
  () => formData.loyaltyPoint,
  (newPoints) => {
    if (newPoints !== undefined && newPoints !== null && newPoints !== "") {
      formData.userRank = calculateUserRank(newPoints);
    }
  },
  { immediate: true }
);

// Function to calculate average order value
const calculateAverageOrderValue = (totalSpent, totalOrder) => {
  const spent = parseFloat(totalSpent) || 0;
  const orders = parseInt(totalOrder) || 0;

  if (orders === 0) return 0;
  return Math.round((spent / orders) * 100) / 100; // Round to 2 decimal places
};

// Computed property for auto-calculated average order value
const autoCalculatedAverageOrderValue = computed(() => {
  return calculateAverageOrderValue(formData.totalSpent, formData.totalOrder);
});

// Watch totalSpent and totalOrder changes to auto-update average order value
watch(
  [() => formData.totalSpent, () => formData.totalOrder],
  ([newSpent, newOrder]) => {
    const calculatedAverage = calculateAverageOrderValue(newSpent, newOrder);
    formData.averageOrderValue = calculatedAverage;
  },
  { immediate: true }
);

const selectedRoles = ref([]);
async function submitUpdateForm() {
  console.log("Debug update: ", formData);
  try {
    const payload = {
      ...formData, // Add this if your backend needs role IDs
    };
    const response = await api.put(`/admin/${props.TableName}/` + props.id, payload);
    console.log("Form updated successfully:", response.data);
    await updateRoles(formData.id);
    router.push(`/Admin/${props.TableName}`);
    notification.success({
      message: "Cập nhật thành công",
      description: `Người dùng ${formData.id} đã được cập nhật thành công.`,
      duration: 3,
    });
  } catch (error) {
    console.error("Insert failed:", error);
  }
}
async function submitForm() {
  console.log("Debug submit: ", formData);
  console.log(formData);
  try {
    const response = await formTableService.create({ ...formData });
    console.log("Form submitted successfully:", response.data);
    await updateRoles(response.data.id); // Pass the created ID if needed
    router.push(`/Admin/${props.TableName}`);
  } catch (error) {
    console.error("Insert failed:", error);
  }
}
const fetchData = async () => {
  if (props.action === "create") return;
  try {
    dropDownListRoles.value = (await dropDown("admin/Roles")).content;
    const responseAuthorities = await api.get(`/admin/Authorities/` + props.id);
    selectedRoles.value = responseAuthorities.data.map((role) => role.roles.id);
    const response = await formTableService.getById(props.id);
    Object.assign(formData, response.data);

    // Kiểm tra uploadRef có tồn tại và có avatarUrl trước khi load
    if (uploadRef.value && response.data.avatarUrl) {
      uploadRef.value.loadFromUrls([response.data.avatarUrl]);
    }
  } catch (err) {
    console.error("Get failed:", err);
  }
};
// Xử lý khi danh sách ảnh thay đổi
const handleImagesUpdate = (images) => {
  console.log("Images updated:", images);
  // Cập nhật avatarUrl với ảnh đầu tiên (nếu có)
  if (images.length > 0) {
    formData.avatarUrl = images[0].cloudinaryUrl || images[0].url;
    if (images[0].cloudinaryUrl) {
      formData.avatarUrl = images[0].cloudinaryUrl;
      const payload = {
        ...formData, // Add this if your backend needs role IDs
      };
      if (props.action === "update") {
        api
          .put(`/admin/${props.TableName}/` + props.id, payload)
          .then(() => {
            notification.success({
              message: "Cập nhật thành công",
              description: `Avatar đã được cập nhật thành công.`,
              duration: 3,
            });
          })
          .catch((error) => {
            notification.error({
              message: "Cập nhật thất bại",
              description: `Không thể cập nhật avatar.`,
              duration: 3,
            });
            console.error("Failed to update avatar:", error);
          });
      }
    }
  } else {
    formData.avatarUrl = null;
  }

  // Hiển thị thông báo khi có thay đổi
  if (images.length > 0) {
    console.log("Avatar updated:", formData.avatarUrl);
  }
};
const handleImagesDelete = (index) => {
  console.log("Deleting image at index:", index);
  // Xử lý xóa ảnh tại index
  formData.avatarUrl = null; // Xóa ảnh khỏi formData
  const payload = {
    ...formData, // Add this if your backend needs role IDs
  };
  api
    .put(`/admin/${props.TableName}/` + props.id, payload)
    .then(() => {
      notification.success({
        message: "Xóa thành công",
        description: `Ảnh đã được xóa thành công.`,
        duration: 3,
      });
      console.log("Image deleted successfully");
    })
    .catch((error) => {
      notification.error({
        message: "Xóa thất bại",
        description: `Không thể xóa ảnh.`,
        duration: 3,
      });
      console.error("Failed to delete image:", error);
    });
};

// Xử lý khi ảnh chính thay đổi
// const handlePrimaryImageChanged = (primaryImage) => {
//   console.log("Primary image changed:", primaryImage);

//   if (primaryImage) {
//     formData.avatarUrl = primaryImage.cloudinaryUrl || primaryImage.url;
//     console.log("New primary avatar:", formData.avatarUrl);
//   }
// };
async function updateRoles(id) {
  try {
    console.log("Roles need successfully:", {
      accounts: formData.email,
      listRoles: selectedRoles.value,
      roles: "",
      updatedAt: "",
      createdAt: "",
      id: "",
    });

    const response = await api.put(`/admin/Authorities/${id}`, {
      accounts: formData.email,
      listRoles: selectedRoles.value,
      roles: "",
      updatedAt: "",
      createdAt: "",
      id: "",
    });
    console.log("Roles updated successfully:", response.data);
  } catch (error) {
    console.error("Failed to update roles:", error);
  }
}
onMounted(async () => {
  await nextTick(); // Đợi DOM được render
  fetchData();
});
watch(() => props.id, fetchData);
watch(
  () => selectedRoles.value,
  (newValue) => {
    console.log("Selected roles updated:", newValue);
    // Here you can handle the selected roles if needed
  }
);
</script>
<style scoped>
.custom-input {
  border-radius: 8px;
  border: 1px solid #dee2e6;
  padding: 0.75rem 1rem;
  transition: all 0.3s ease;
}

.custom-input:focus {
  border-color: #0d6efd;
  box-shadow: 0 0 0 0.2rem rgba(13, 110, 253, 0.25);
}

.account-form {
  max-width: 1200px;
  margin: 2rem auto;
  border-radius: 15px;
}

.form-label {
  color: #495057;
  margin-bottom: 0.5rem;
}

.upload-container {
  border: 2px dashed #dee2e6;
  border-radius: 8px;
  padding: 1rem;
}

.btn-primary {
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  transition: all 0.3s ease;
}

.btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(13, 110, 253, 0.15);
}

.role-list {
  background: #f8f9fa !important;
}

.form-check-input {
  cursor: pointer;
}

.role-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 10px;
}

.role-item {
  display: flex;
  align-items: center;
  gap: 10px;
  border: 1px solid #ccc;
  padding: 8px 12px;
  border-radius: 8px;
  transition: background 0.2s ease;
  cursor: pointer;
}

.role-item:hover {
  background-color: #f9f9f9;
}

.role-info {
  display: flex;
  flex-direction: column;
}

.role-name {
  font-weight: bold;
  color: #333;
}

.role-date {
  font-size: 0.85rem;
  color: #777;
}

/* Rank Table Styles */
.table-warning {
  background-color: #fff3cd !important;
  border: 2px solid #ffc107 !important;
}

.badge {
  font-size: 0.875rem;
  font-weight: 600;
  padding: 0.375rem 0.75rem;
  border-radius: 0.375rem;
}

.table th {
  font-weight: 600;
  font-size: 0.875rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.table td {
  vertical-align: middle;
  font-size: 0.9rem;
}

.text-primary {
  color: #0d6efd !important;
}

.bg-light {
  background-color: #f8f9fa !important;
}

.border {
  border: 1px solid #dee2e6 !important;
  border-radius: 8px !important;
}

/* User Rank Select Styling */
select:disabled {
  background-color: #f8f9fa !important;
  opacity: 0.8;
  cursor: not-allowed !important;
}
</style>
