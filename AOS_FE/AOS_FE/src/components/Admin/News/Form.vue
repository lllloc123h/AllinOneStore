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
          <label :for="id" class="form-label text-capitalize">Id</label>
          <input
            :id="id"
            v-model="formData.id"
            v-if="props.action !== 'create'"
            :hidden="props.action === 'view'"
            :readonly="props.action === 'update'"
            type="number"
            class="form-control"
            :placeholder="`Enter id`"
          />
        </div>
        <div class="mb-3">
          <label :for="title" class="form-label text-capitalize">Tiêu đề</label>
          <input
            :id="title"
            v-model="formData.title"
            type="text"
            class="form-control"
            :placeholder="`Enter title`"
          />
        </div>
        <div class="mb-3">
          <uploadImages
            ref="uploadRef"
            :maxFiles="1"
            :aspectRatio="'4:5'"
            :titleUpload="'ảnh tin tức'"
            :folderName="'news'"
            @update:images="handleImagesUpdate"
            @delete-image="handleImagesDelete"
          />
        </div>

        <div class="mb-3">
          <label :for="home" class="form-label text-capitalize"
            >Hiển thị ở trang chủ</label
          >
          <br />
          <label class="form-label text-capitalize">Home</label>
          <input type="radio" value="true" v-model="formData.home" />
          <label class="form-label text-capitalize">Not Home</label>
          <input type="radio" value="false" v-model="formData.home" />
        </div>
        <div class="mb-3">
          <label for="description" class="form-label text-capitalize">
            <i class="bi bi-file-text me-2"></i>Mô tả tin tức
          </label>
          <QuillEditor
            v-model:content="formData.description"
            :content-type="'html'"
            :modules="modules"
            theme="snow"
            toolbar="full"
            placeholder="Nhập nội dung tin tức..."
            style="height: 400px"
          />
        </div>
        <div class="mb-3">
          <button @click="getHTMLContent" type="button" class="btn btn-info me-2">
            <i class="bi bi-eye me-1"></i>Xem HTML
          </button>
          <button @click="copyHTMLToClipboard" type="button" class="btn btn-secondary">
            <i class="bi bi-clipboard me-1"></i>Copy HTML
          </button>
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
/* HTML Preview Styling */
.html-preview {
  background: #f8f9fa;
  border: 1px solid #e9ecef;
  border-radius: 0.375rem;
  max-height: 200px;
  overflow-y: auto;
  padding: 1rem;
}

.html-preview pre {
  margin: 0;
  font-family: "Courier New", monospace;
  font-size: 0.875rem;
  line-height: 1.4;
  color: #495057;
  white-space: pre-wrap;
  word-wrap: break-word;
}

/* Button Styling */
.btn {
  border-radius: 0.375rem;
  font-weight: 500;
  transition: all 0.3s ease;
}

.btn-info {
  background: linear-gradient(45deg, #17a2b8, #20c997);
  border: none;
  color: white;
}

.btn-info:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(23, 162, 184, 0.3);
}

.btn-secondary {
  background: linear-gradient(45deg, #6c757d, #495057);
  border: none;
  color: white;
}

.btn-secondary:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(108, 117, 125, 0.3);
}

/* Quill Editor Basic Styling */
:deep(.ql-toolbar) {
  border-top: 1px solid #ced4da;
  border-left: 1px solid #ced4da;
  border-right: 1px solid #ced4da;
  border-bottom: none;
  border-radius: 0.375rem 0.375rem 0 0;
  background-color: #f8f9fa;
}

:deep(.ql-container) {
  border-left: 1px solid #ced4da;
  border-right: 1px solid #ced4da;
  border-bottom: 1px solid #ced4da;
  border-top: none;
  border-radius: 0 0 0.375rem 0.375rem;
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
}

:deep(.ql-editor) {
  min-height: 300px;
  font-size: 14px;
  line-height: 1.6;
}

:deep(.ql-editor.ql-blank::before) {
  color: #6c757d;
  font-style: italic;
}

/* Focus effects */
:deep(.ql-toolbar):focus-within + .ql-container,
:deep(.ql-container):focus-within {
  border-color: #86b7fe;
  box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
}

/* Responsive */
@media (max-width: 768px) {
  .html-preview {
    max-height: 150px;
    padding: 0.75rem;
  }

  .html-preview pre {
    font-size: 0.75rem;
  }

  :deep(.ql-editor) {
    min-height: 200px;
    font-size: 13px;
  }
}
</style>

<script setup>
import { reactive, ref, onMounted, watch, nextTick } from "vue";
import { formatDate } from "../../Module/CommonsFunctions.js";
import Dashboard from "../../Module/DashBoard.vue";
import createCrudService from "../../../Configs/reusableCRUDService.js";
import { useRouter } from "vue-router";
const router = useRouter();
import axios from "axios";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "bootstrap/dist/css/bootstrap.min.css";
import uploadImages from "../../Module/upload-images.vue";
import { notification } from "ant-design-vue";
import api from "../../../Configs/api.js";
import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
import "@vueup/vue-quill/dist/vue-quill.bubble.css";
import BlotFormatter from "quill-blot-formatter";
import ImageUploader from "quill-image-uploader";
import uploadApi from "../../../Configs/upload-api.js";
const modules = [
  {
    name: "imageUploader",
    module: ImageUploader,
    options: {
      upload: (file) => {
        return new Promise((resolve, reject) => {
          uploadApi.upload
            .uploadSingleImageOrVideo(file, "newContents")
            .then((res) => {
              console.log("url ảnh dc tải lên", res.url);
              resolve(res.url);
            })
            .catch((err) => {
              reject("Upload failed");
              console.error("Error:", err);
            });
        });
      },
    },
  },
  {
    name: "blotFormatter",
    module: BlotFormatter,
    options: {
      /* options */
    },
  },
];

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
const uploadRef = ref();
const formData = reactive({
  id: "",
  imageUrl: null,
  title: "",
  home: "true",
  description:
    "<h2>Khuyến mãi lớn tháng 8: Giảm giá 50% toàn bộ áo thun!</h2>" +
    "<p>Chào mừng bạn đến với chương trình <strong>Sale Tháng 8</strong> của AllInOneStore! Từ <span style='color:#e53e3e;font-weight:bold;'>15/8 đến 31/8</span>, toàn bộ sản phẩm áo thun sẽ được giảm giá <b>50%</b>.</p>" +
    "<ul><li>Áo thun nam, nữ, trẻ em</li><li>Chất liệu cotton cao cấp</li><li>Đổi trả miễn phí trong 7 ngày</li></ul>" +
    "<blockquote><em>“Mua càng nhiều, ưu đãi càng lớn!”</em></blockquote>",
  createdAt: "",
  updatedAt: "",
});
// Functions để xử lý HTML content
const getHTMLContent = () => {
  console.log("Current HTML content:", formData.description);
  notification.info({
    message: "HTML Content",
    description: "Check console để xem HTML content",
    duration: 3,
  });

  // Hiển thị HTML trong modal hoặc alert
  if (formData.description) {
    alert("HTML Content:\n" + formData.description);
  } else {
    alert("Chưa có nội dung!");
  }
};

const copyHTMLToClipboard = async () => {
  if (!formData.description) {
    notification.warning({
      message: "Không có nội dung",
      description: "Vui lòng nhập nội dung trước khi copy.",
      duration: 3,
    });
    return;
  }

  try {
    await navigator.clipboard.writeText(formData.description);
    notification.success({
      message: "Copy thành công",
      description: "HTML content đã được copy vào clipboard.",
      duration: 3,
    });
  } catch (err) {
    console.error("Failed to copy: ", err);
    notification.error({
      message: "Copy thất bại",
      description: "Không thể copy HTML content.",
      duration: 3,
    });
  }
};

const handleImagesUpdate = (images) => {
  console.log("Images updated:", images);
  // Cập nhật imageUrl với ảnh đầu tiên (nếu có)
  if (images.length > 0) {
    formData.imageUrl = images[0].cloudinaryUrl || images[0].url;
    if (images[0].cloudinaryUrl) {
      formData.imageUrl = images[0].cloudinaryUrl;
      formData.createdAt = null;
      formData.updatedAt = null;
      const payload = {
        ...formData, // Add this if your backend needs role IDs
      };
      if (props.action === "update") {
        api
          .put(`/admin/${props.TableName}/` + props.id, payload)
          .then(() => {
            notification.success({
              message: "Cập nhật thành công",
              description: `Ảnh đã được cập nhật thành công.`,
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
    formData.imageUrl = null;
  }

  // Hiển thị thông báo khi có thay đổi
  if (images.length > 0) {
    console.log("Image updated:", formData.imageUrl);
  }
};
const handleImagesDelete = (index) => {
  console.log("da xoa anh");

  formData.imageUrl = null;
};
async function submitUpdateForm() {
  console.log("dữ liệu gửi lên, ", formData);
  formData.updatedAt = null;
  formData.createdAt = null;
  if (formData.imageUrl === null) {
    notification.error({
      message: "Cập nhật thất bại",
      description: `Vui lòng tải lên ảnh trước khi cập nhật.`,
      duration: 3,
    });
    return;
  }
  try {
    const response = await formTableService.update(props.id, formData);
    notification.success({
      message: "Cập nhật thành công",
      description: `Dữ liệu đã được cập nhật thành công.`,
      duration: 3,
    });
    router.push(`/Admin/${props.TableName}`);
  } catch (error) {
    notification.error({
      message: "Cập nhật thất bại",
      description: `Không thể cập nhật dữ liệu.`,
      duration: 3,
    });
    console.error("Insert failed:", error);
  }
}

async function submitForm() {
  console.log(formData);
  if (formData.imageUrl == null) {
    notification.error({
      message: "Thêm thất bại",
      description: `Vui lòng tải lên ảnh trước khi thêm.`,
      duration: 3,
    });
    return;
  }
  try {
    const response = await formTableService.create(formData);
    console.log("Insert successful:", response.data);
    router.push(`/Admin/${props.TableName}`);
    notification.success({
      message: "Thêm thành công",
      description: `Dữ liệu đã được thêm thành công.`,
      duration: 3,
    });
  } catch (error) {
    console.error("Insert failed:", error);
  }
}
const fetchData = async () => {
  if (!props.TableName) return;
  try {
    const response = await formTableService.getById(props.id);
    response.data.createdAt = formatDate(response.data.createdAt);
    response.data.updatedAt = formatDate(response.data.updatedAt);
    Object.assign(formData, response.data);
    // Kiểm tra uploadRef có tồn tại và có imageUrl trước khi load
    if (uploadRef.value && response.data.imageUrl) {
      uploadRef.value.loadFromUrls([response.data.imageUrl]);
    }
  } catch (err) {
    console.error("Get failed:", err);
  }
};

onMounted(async () => {
  await nextTick(); // Đợi DOM được render
  fetchData();
});
watch(() => props.id, fetchData);
</script>
