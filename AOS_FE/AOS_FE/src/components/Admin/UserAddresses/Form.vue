<template>
  <div class="container-fluid row">
    <div class="aside col-3">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    <div class="article col-9">
      <form
        @submit.prevent="props.action === 'create' ? submitForm() : submitUpdateForm()"
      >
        <div
          class="mb-3"
          :style="
            props.action === 'view' || props.action === 'create' ? ' display:none;' : ''
          "
        >
          <label :for="id" class="form-label text-capitalize"></label>
          <input
            :id="id"
            v-model="formData.id"
            v-if="props.action !== 'create'"
            :hidden="props.action === 'view'"
            type="number"
            class="form-control"
            :placeholder="`Enter id`"
          />
        </div>
        <div class="mb-3">
          <label :for="recipientName" class="form-label text-capitalize"
            >recipientName</label
          >
          <input
            :id="recipientName"
            v-model="formData.recipientName"
            type="text"
            class="form-control"
            :placeholder="`Enter recipientName`"
          />
        </div>

        <div class="mb-3">
          <label :for="phone" class="form-label text-capitalize">phone</label>
          <input
            :id="phone"
            v-model="formData.phone"
            type="text"
            class="form-control"
            :placeholder="`Enter phone`"
          />
        </div>

        <div class="mb-3">
          <label :for="province" class="form-label text-capitalize">province</label>
          <input
            :id="province"
            v-model="formData.province"
            type="text"
            class="form-control"
            :placeholder="`Enter province`"
          />
        </div>

        <div class="mb-3">
          <label :for="district" class="form-label text-capitalize">district</label>
          <input
            :id="district"
            v-model="formData.district"
            type="text"
            class="form-control"
            :placeholder="`Enter district`"
          />
        </div>

        <div class="mb-3">
          <label :for="ward" class="form-label text-capitalize">ward</label>
          <input
            :id="ward"
            v-model="formData.ward"
            type="text"
            class="form-control"
            :placeholder="`Enter ward`"
          />
        </div>

        <div class="mb-3">
          <label :for="street" class="form-label text-capitalize">street</label>
          <input
            :id="street"
            v-model="formData.street"
            type="text"
            class="form-control"
            :placeholder="`Enter street`"
          />
        </div>

        <div class="mb-3">
          <label :for="label" class="form-label text-capitalize">label</label>
          <input
            :id="label"
            v-model="formData.label"
            type="text"
            class="form-control"
            :placeholder="`Enter label`"
          />
        </div>

        <div class="mb-3">
          <label :for="isDefault" class="form-label text-capitalize">isDefault</label>
          <input
            :id="isDefault"
            v-model="formData.isDefault"
            type="text"
            class="form-control"
            :placeholder="`Enter isDefault`"
          />
        </div>

        <div class="mb-3">
          <label :for="note" class="form-label text-capitalize">note</label>
          <input
            :id="note"
            v-model="formData.note"
            type="text"
            class="form-control"
            :placeholder="`Enter note`"
          />
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
<style scoped>
/* Form Card */
.form-card {
  background: white;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  border: 1px solid rgba(102, 126, 234, 0.1);
  margin-bottom: 2rem;
}

.form-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 2rem;
  text-align: center;
}

.form-title {
  font-size: 1.75rem;
  font-weight: 700;
  margin: 0;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* Address Form */
.address-form {
  padding: 2.5rem;
}

.form-section {
  margin-bottom: 2.5rem;
  padding: 1.5rem;
  background: linear-gradient(135deg, #f8f9fa 0%, #ffffff 100%);
  border-radius: 15px;
  border: 1px solid #e9ecef;
}

.section-title {
  font-size: 1.2rem;
  font-weight: 700;
  color: #2d3748;
  margin-bottom: 1.5rem;
  display: flex;
  align-items: center;
  border-bottom: 2px solid #e9ecef;
  padding-bottom: 0.75rem;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1.5rem;
  margin-bottom: 1.5rem;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-label {
  font-weight: 600;
  color: #4a5568;
  margin-bottom: 0.5rem;
  display: flex;
  align-items: center;
  font-size: 0.95rem;
}

.form-input,
.form-select,
.form-textarea {
  padding: 12px 16px;
  border: 2px solid #e2e8f0;
  border-radius: 10px;
  font-size: 1rem;
  transition: all 0.3s ease;
  background: white;
}

.form-input:focus,
.form-select:focus,
.form-textarea:focus {
  outline: none;
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
  transform: translateY(-1px);
}

.form-input:disabled,
.form-select:disabled,
.form-textarea:disabled {
  background-color: #f7fafc;
  color: #a0aec0;
  cursor: not-allowed;
}

.form-select {
  background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 20 20'%3e%3cpath stroke='%236b7280' stroke-linecap='round' stroke-linejoin='round' stroke-width='1.5' d='m6 8 4 4 4-4'/%3e%3c/svg%3e");
  background-position: right 12px center;
  background-repeat: no-repeat;
  background-size: 16px;
  padding-right: 40px;
  appearance: none;
}

.form-textarea {
  resize: vertical;
  min-height: 80px;
}

/* Checkbox styling */
.form-check {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 1rem;
  background: linear-gradient(135deg, #f0f9ff 0%, #e0f2fe 100%);
  border-radius: 10px;
  border: 1px solid #bae6fd;
  margin-top: 1rem;
}

.form-check-input {
  width: 20px;
  height: 20px;
  border: 2px solid #0891b2;
  border-radius: 4px;
  margin: 0;
}

.form-check-input:checked {
  background-color: #0891b2;
  border-color: #0891b2;
}

.form-check-label {
  font-weight: 600;
  color: #164e63;
  margin: 0;
  display: flex;
  align-items: center;
  cursor: pointer;
}

/* Form Actions */
.form-actions {
  display: flex;
  gap: 1rem;
  justify-content: center;
  margin-top: 2rem;
  padding-top: 2rem;
  border-top: 2px solid #e9ecef;
}

.cancel-btn,
.submit-btn {
  padding: 12px 30px;
  border-radius: 10px;
  font-weight: 600;
  font-size: 1rem;
  border: none;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  cursor: pointer;
  min-width: 140px;
  justify-content: center;
}

.cancel-btn {
  background: transparent;
  color: #6c757d;
  border: 2px solid #e9ecef;
}

.cancel-btn:hover {
  border-color: #dc3545;
  color: #dc3545;
  transform: translateY(-2px);
  box-shadow: 0 4px 15px rgba(220, 53, 69, 0.2);
}

.submit-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: 2px solid transparent;
}

.submit-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

.submit-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

/* Responsive */
@media (max-width: 768px) {
  .form-row {
    grid-template-columns: 1fr;
    gap: 1rem;
  }

  .address-form {
    padding: 1.5rem;
  }

  .form-header {
    padding: 1.5rem;
  }

  .form-title {
    font-size: 1.5rem;
  }

  .form-actions {
    flex-direction: column;
  }

  .cancel-btn,
  .submit-btn {
    width: 100%;
  }
}

/* Bootstrap Icons fix */
.bi {
  font-size: 1.1em;
}
</style>

<script setup>
import { reactive, ref, onMounted, watch } from "vue";
import { formatDate } from "../../Module/CommonsFunctions.js";
import Dashboard from "../../Module/DashBoard.vue";
import createCrudService from "../../../Configs/reusableCRUDService.js";
import { useRouter } from "vue-router";
import axios from "axios";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "bootstrap/dist/css/bootstrap.min.css";

const router = useRouter();

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

// Form data
const formData = reactive({
  id: "",
  recipientName: "",
  phone: "",
  province: "",
  district: "",
  ward: "",
  street: "",
  label: "Nhà Riêng",
  isDefault: false,
  note: "",
  createdAt: "",
  updatedAt: "",
});

// Province/District/Ward handling - copied from ShippingAddress.vue
const provinces = ref([]);
const districts = ref([]);
const wards = ref([]);
const selectedProvince = ref("");
const selectedDistrict = ref("");
const selectedWard = ref("");

// Load provinces - using same logic as ShippingAddress.vue
const loadProvinces = async () => {
  try {
    const res = await fetch(
      "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province",
      {
        headers: {
          Token: "cc2fea72-5000-11f0-9b81-222185cb68c8",
          "Content-Type": "application/json",
        },
      }
    );

    const data = await res.json();
    provinces.value = data.data;
  } catch (err) {
    console.error("Error loading provinces:", err);
  }
};

// Load districts - using same logic as ShippingAddress.vue
const loadDistricts = async (keepSelected = false) => {
  if (!keepSelected) {
    selectedDistrict.value = "";
    selectedWard.value = "";
  }
  districts.value = [];
  wards.value = [];

  if (selectedProvince.value) {
    try {
      const res = await fetch(
        "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district",
        {
          method: "POST",
          headers: {
            Token: "cc2fea72-5000-11f0-9b81-222185cb68c8",
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            province_id: selectedProvince.value,
          }),
        }
      );

      const data = await res.json();
      districts.value = data.data;

      // Update form data
      const selectedProv = provinces.value.find(
        (p) => p.ProvinceID == selectedProvince.value
      );
      if (selectedProv) {
        formData.province = selectedProv.ProvinceName;
      }
    } catch (err) {
      console.error("Error loading districts:", err);
    }
  }
};

// Load wards - using same logic as ShippingAddress.vue
const loadWards = async (keepSelected = false) => {
  if (!keepSelected) {
    selectedWard.value = "";
  }
  wards.value = [];

  if (selectedDistrict.value) {
    try {
      const res = await fetch(
        "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id",
        {
          method: "POST",
          headers: {
            Token: "cc2fea72-5000-11f0-9b81-222185cb68c8",
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            district_id: selectedDistrict.value,
          }),
        }
      );

      const data = await res.json();
      wards.value = data.data;

      // Update form data
      const selectedDist = districts.value.find(
        (d) => d.DistrictID == selectedDistrict.value
      );
      if (selectedDist) {
        formData.district = selectedDist.DistrictName;
      }
    } catch (err) {
      console.error("Error loading wards:", err);
    }
  }
};

// Watch ward selection
watch(selectedWard, (newWard) => {
  if (newWard) {
    const selectedWardObj = wards.value.find((w) => w.WardCode == newWard);
    if (selectedWardObj) {
      formData.ward = selectedWardObj.WardName;
    }
  }
});

// Form submission methods
async function submitUpdateForm() {
  console.log(formData);
  try {
    const response = await formTableService.update(props.id, formData);
    console.log("Update successful:", response.data);
    router.push(`/Admin/${props.TableName}`);
  } catch (error) {
    console.error("Update failed:", error);
  }
}

async function submitForm() {
  console.log(formData);
  try {
    const response = await formTableService.create(formData);
    console.log("Create successful:", response.data);
    router.push(`/Admin/${props.TableName}`);
  } catch (error) {
    console.error("Create failed:", error);
  }
}

const fetchData = async () => {
  if (!props.TableName || props.action === "create") return;
  try {
    const response = await formTableService.getById(props.id);
    response.data.createdAt = formatDate(response.data.createdAt);
    response.data.updatedAt = formatDate(response.data.updatedAt);
    Object.assign(formData, response.data);

    console.log("📍 Form data loaded:", formData);

    // Load provinces first
    await loadProvinces();

    // Find and set province if exists
    if (formData.province) {
      const province = provinces.value.find((p) => p.ProvinceName === formData.province);
      console.log("🔍 Found province:", province);
      if (province) {
        selectedProvince.value = province.ProvinceID;

        // Load districts for selected province (keep selected district)
        await loadDistricts(true);

        // Find and set district if exists
        if (formData.district) {
          const district = districts.value.find(
            (d) => d.DistrictName === formData.district
          );
          console.log("🔍 Found district:", district);
          if (district) {
            selectedDistrict.value = district.DistrictID;

            // Load wards for selected district (keep selected ward)
            await loadWards(true);

            // Find and set ward if exists
            if (formData.ward) {
              const ward = wards.value.find((w) => w.WardName === formData.ward);
              console.log("🔍 Found ward:", ward);
              if (ward) {
                selectedWard.value = ward.WardCode;
              }
            }
          }
        }
      }
    }

    console.log("📋 Final selected values:", {
      province: selectedProvince.value,
      district: selectedDistrict.value,
      ward: selectedWard.value,
    });
  } catch (err) {
    console.error("Get failed:", err);
  }
};

onMounted(async () => {
  await loadProvinces();
  if (props.action !== "create") {
    await fetchData();
  }
});

watch(() => props.id, fetchData);
</script>
