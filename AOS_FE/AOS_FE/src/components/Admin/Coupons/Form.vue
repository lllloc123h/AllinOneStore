<template>
  <div class="container-fluid row">
    <div class="aside col-3">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    <div class="article col-9">
      <form @submit.prevent="props.action === 'create' ? submitForm() : submitUpdateForm()">
        <div class="mb-3" :style="props.action === 'view' || props.action === 'create' ? ' display:none;' : ''
          ">
          <label :for="id" class="form-label text-capitalize"></label>
          <input :id="id" v-model="formData.id" v-if="props.action !== 'create'" :hidden="props.action === 'view'"
            type="number" class="form-control" :placeholder="`Enter id`" />
        </div>
        <div class="mb-3">
          <label for="code" class="form-label text-capitalize">code</label>
          <input id="code" v-model="formData.code" type="text" class="form-control"
            :class="{ 'is-invalid': errors.code }" placeholder="Enter code" />
          <div v-if="errors.code" class="invalid-feedback">
            {{ errors.code }}
          </div>
        </div>


        <div class="mb-3">
          <label :for="description" class="form-label text-capitalize">description</label>
          <input id="description" v-model="formData.description" type="text" class="form-control"
            :class="{ 'is-invalid': errors.description }" placeholder="Enter description" />
          <div v-if="errors.description" class="invalid-feedback">
            {{ errors.description }}
          </div>
        </div>

        <div class="mb-3">
          <label :for="discountType" class="form-label text-capitalize">discountType</label>
          <select :id="discountType" v-model="formData.discountType" class="form-control">
            <option value="G-DISCOUNT">Giảm giá trên tổng hóa đơn</option>
            <option value="FREESHIP">Giảm phí vận chuyển</option>
          </select>
        </div>

        <div class="mb-3">
          <label :for="discountValue" class="form-label text-capitalize">discountValue</label>
          <input id="discountValue" v-model="formData.discountValue" type="number" class="form-control"
            :class="{ 'is-invalid': errors.discountValue }" placeholder="Enter discountValue" />
          <div v-if="errors.discountValue" class="invalid-feedback">
            {{ errors.discountValue }}
          </div>
        </div>
        <div class="mb-3">
          <label :for="minOrderAmount" class="form-label text-capitalize">minOrderAmount</label>
          <input id="minOrderAmount" v-model="formData.minOrderAmount" type="number" class="form-control"
            :class="{ 'is-invalid': errors.minOrderAmount }" placeholder="Enter minOrderAmount" />
          <div v-if="errors.minOrderAmount" class="invalid-feedback">
            {{ errors.minOrderAmount }}
          </div>
        </div>

        <!-- <div class="mb-3">
          <label :for="maxDiscountAmount" class="form-label text-capitalize"
            >maxDiscountAmount</label
          >
          <input
            :id="maxDiscountAmount"
            v-model="formData.maxDiscountAmount"
            type="number"
            class="form-control"
            :placeholder="`Enter maxDiscountAmount`"
          />
        </div> -->

        <div class="mb-3">
          <label for="qty" class="form-label text-capitalize">qty</label>
          <input id="qty" v-model="formData.qty" type="number" class="form-control" placeholder="Enter qty"
            :class="{ 'is-invalid': errors.qty }" />
          <div v-if="errors.qty" class="invalid-feedback">
            {{ errors.qty }}
          </div>
        </div>

        <div class="mb-3">
          <label for="usagePerCustomer" class="form-label text-capitalize">usagePerCustomer</label>
          <input id="usagePerCustomer" v-model="formData.usagePerCustomer" type="number" class="form-control"
            placeholder="Enter usagePerCustomer" :class="{ 'is-invalid': errors.usagePerCustomer }" />
          <div v-if="errors.usagePerCustomer" class="invalid-feedback">
            {{ errors.usagePerCustomer }}
          </div>
        </div>

        <div class="mb-3">
          <label :for="formData.active" class="form-label text-capitalize">is Active</label>
          <input type="radio" name="active" v-model="formData.active" value="true" />
          Active
          <input type="radio" name="active" v-model="formData.active" value="false" />
          Inactive
        </div>

        <div class="mb-3">
          <label :for="formData.allowVoucher" class="form-label text-capitalize">is Voucher</label>
          <input type="radio" name="vouhcher" v-model="formData.allowVoucher" value="true" />
          Cho phép chồng khuyến mãi
          <input type="radio" name="voucher" v-model="formData.allowVoucher" value="false" />
          Không cho phép chồng khuyến mãi
        </div>

        <div class="mb-3">
          <label :for="customerGroup" class="form-label text-capitalize">customerGroup</label>
          <select :id="customerGroup" v-model="formData.customerGroup" type="text" class="form-control"
            :placeholder="`Enter customerGroup`">
            <option value="all">Tất cả nhóm khách hàng</option>
            <option value="Bạc">Từ nhóm khách hàng hạng Bạc trở lên</option>
            <option value="Vàng">Từ nhóm khách hàng hạng Vàng trở lên</option>
            <option value="Bạch Kim">Từ nhóm khách hàng hạng Bạch Kim trở lên</option>
            <option value="Kim Cương">Từ nhóm khách hàng hạng Kim Cương trở lên</option>
          </select>
        </div>

        <div class="mb-3">
          <label for="startAt" class="form-label text-capitalize">startAt</label>
          <input id="startAt" v-model="formData.startAt" type="datetime-local" class="form-control"
            :class="{ 'is-invalid': errors.startAt }" placeholder="Enter startAt" />
          <div v-if="errors.startAt" class="invalid-feedback">
            {{ errors.startAt }}
          </div>
        </div>

        <div class="mb-3">
          <label for="endAt" class="form-label text-capitalize">endAt</label>
          <input id="endAt" v-model="formData.endAt" type="datetime-local" class="form-control"
            :class="{ 'is-invalid': errors.endAt }" :min="formData.startAt || undefined" placeholder="Enter endAt" />
          <div v-if="errors.endAt" class="invalid-feedback">
            {{ errors.endAt }}
          </div>
        </div>


        <button type="submit" :disable="props.action == 'view'" class="btn btn-primary">
          <span v-if="props.action === 'create'">Create</span>
          <span v-else-if="props.action === 'create'">Create</span>
          <span v-else>Update</span>
        </button>
      </form>
    </div>
  </div>
</template>
<style></style>

<script setup>
import { reactive, ref, onMounted, watch } from "vue";
import { formatDate, toISOStringWithTimezone } from "../../Module/CommonsFunctions.js";
import Dashboard from "../../Module/DashBoard.vue";
import createCrudService from "../../../Configs/reusableCRUDService.js";
import { useRouter } from "vue-router";
const router = useRouter();
import axios from "axios";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "bootstrap/dist/css/bootstrap.min.css";
import { notification } from "ant-design-vue";
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

const formData = reactive({
  id: "",
  code: "CHAOMUNG30THANG4",
  description:
    "Ưu đãi giảm 250k với hóa đơn trên 1000k. Áp dụng từ ngày 30/04/2025 - 30/10/2025",
  discountType: "G-DISCOUNT",
  discountValue: "250000",
  minOrderAmount: "1000000",
  // maxDiscountAmount: "",
  qty: "200",
  usagePerCustomer: "3",
  allowVoucher: true,
  active: true,
  customerGroup: "all",
  startAt: "",
  endAt: "",
  createdAt: "",
  updatedAt: "",
});

const errors = reactive({
  code: "",
  description: "",
  discountValue: "",
  minOrderAmount: "",
});


async function submitUpdateForm() {
  console.log(formData);
  formData.createdAt = null;
  formData.updatedAt = null;
  if (checkTime() === false) {
    return;
  }
  try {
    const response = await formTableService.update(props.id, formData);
    console.log("Insert successful:", response.data);
    router.push(`/Admin/${props.TableName}`);
    notification.success({
      message: "Cập nhật thành công",
      description: `Dữ liệu đã được cập nhật thành công.`,
      duration: 3,
    });
  } catch (error) {
    notification.error({
      message: "Cập nhật thất bại",
      description: `Không thể cập nhật coupon.`,
      duration: 3,
    });
  }
}
function checkTime() {
  if (formData.startAt > formData.endAt) {
    notification.error({
      message: "Cập nhật thất bại",
      description: `Ngày bắt đầu không được lớn hơn ngày kết thúc !`,
      duration: 3,
    });
    return false;
  }
  return true;
}

async function submitForm() {
  if (!validateForm() || !checkTime()) return;
  try {
    const response = await formTableService.create(formData);
    router.push(`/Admin/${props.TableName}`);
    notification.success({
      message: "Tạo thành công",
      description: "Coupon đã được thêm.",
    });
  } catch (error) {
    if (error.response?.status === 409) {
      errors.code = "Code đã tồn tại!";
      document.getElementById("code")?.focus();
    } else {
      notification.error({
        message: "Tạo thất bại",
        description: error.response?.data?.message || "Có lỗi xảy ra",
      });
    }
  }
}

const fetchData = async () => {
  if (!props.TableName && props.action === "create") return;
  try {
    const response = await formTableService.getById(props.id);
    response.data.createdAt = formatDate(response.data.createdAt);
    response.data.updatedAt = formatDate(response.data.updatedAt);
    Object.assign(formData, response.data);
  } catch (err) {
    console.error("Get failed:", err);
  }
};

const validateForm = () => {
  let isValid = true;

  // reset tất cả error về ""
  Object.keys(errors).forEach((key) => {
    errors[key] = "";
  });

  // validate code
  if (!formData.code) {
    errors.code = "Code không được để trống";
    isValid = false;
  }

  if (!formData.description) {
    errors.description = "Description không được để trống";
    isValid = false;
  }

  if (!formData.discountValue || formData.discountValue <= 0) {
    errors.discountValue = "Discount value phải lớn hơn 0";
    isValid = false;
  }

  if (formData.minOrderAmount === "" || formData.minOrderAmount === null) {
    errors.minOrderAmount = "Min order amount không được để trống";
    isValid = false;
  } else if (Number(formData.minOrderAmount) < 0) {
    errors.minOrderAmount = "Min order amount không được nhỏ hơn 0";
    isValid = false;
  }

  if (formData.qty === "" || formData.qty === null) {
    errors.qty = "Qty không được để trống";
    isValid = false;
  } else if (Number(formData.qty) <= 0) {
    errors.qty = "Qty phải lớn hơn 0";
    isValid = false;
  }

  if (formData.usagePerCustomer === "" || formData.usagePerCustomer === null) {
    errors.usagePerCustomer = "Usage per customer không được để trống";
    isValid = false;
  } else if (Number(formData.usagePerCustomer) <= 0) {
    errors.usagePerCustomer = "Usage per customer phải lớn hơn 0";
    isValid = false;
  }

  if (!formData.startAt) {
    errors.startAt = "StartAt không được để trống";
    isValid = false;
  }

  if (!formData.endAt) {
    errors.endAt = "EndAt không được để trống";
    isValid = false;
  }

  return isValid;
};

onMounted(fetchData);
watch(() => props.id, fetchData);
</script>
