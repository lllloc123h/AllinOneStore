<style scoped>
.custum-file-upload {
  height: 200px;
  width: 300px;
  display: flex;
  flex-direction: column;
  align-items: space-between;
  gap: 20px;
  cursor: pointer;
  align-items: center;
  justify-content: center;
  border: 2px dashed #cacaca;
  background-color: rgba(255, 255, 255, 1);
  padding: 1.5rem;
  border-radius: 10px;
  box-shadow: 0px 48px 35px -48px rgba(0, 0, 0, 0.1);
}

.custum-file-upload .icon {
  display: flex;
  align-items: center;
  justify-content: center;
}

.custum-file-upload .icon svg {
  height: 80px;
  fill: rgba(75, 85, 99, 1);
}

.custum-file-upload .text {
  display: flex;
  align-items: center;
  justify-content: center;
}

.custum-file-upload .text span {
  font-weight: 400;
  color: rgba(75, 85, 99, 1);
}

.custum-file-upload input {
  display: none;
}

.close-image {
  position: absolute;
  top: 12px;
  right: 12px;
  z-index: 10;
  background-color: rgba(255, 255, 255, 0.9);
  border: none;
  border-radius: 50%;
  width: 28px;
  height: 28px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 1px 4px #0002;
  font-size: 18px;
  padding: 0;
  transition: background 0.2s;
}
</style>

<template>
  <label class="custum-file-upload" for="file">
    <div class="icon">
      <svg xmlns="http://www.w3.org/2000/svg" fill="" viewBox="0 0 24 24">
        <g stroke-width="0" id="SVGRepo_bgCarrier"></g>
        <g stroke-linejoin="round" stroke-linecap="round" id="SVGRepo_tracerCarrier"></g>
        <g id="SVGRepo_iconCarrier">
          <path
            fill=""
            d="M10 1C9.73478 1 9.48043 1.10536 9.29289 1.29289L3.29289 7.29289C3.10536 7.48043 3 7.73478 3 8V20C3 21.6569 4.34315 23 6 23H7C7.55228 23 8 22.5523 8 22C8 21.4477 7.55228 21 7 21H6C5.44772 21 5 20.5523 5 20V9H10C10.5523 9 11 8.55228 11 8V3H18C18.5523 3 19 3.44772 19 4V9C19 9.55228 19.4477 10 20 10C20.5523 10 21 9.55228 21 9V4C21 2.34315 19.6569 1 18 1H10ZM9 7H6.41421L9 4.41421V7ZM14 15.5C14 14.1193 15.1193 13 16.5 13C17.8807 13 19 14.1193 19 15.5V16V17H20C21.1046 17 22 17.8954 22 19C22 20.1046 21.1046 21 20 21H13C11.8954 21 11 20.1046 11 19C11 17.8954 11.8954 17 13 17H14V16V15.5ZM16.5 11C14.142 11 12.2076 12.8136 12.0156 15.122C10.2825 15.5606 9 17.1305 9 19C9 21.2091 10.7909 23 13 23H20C22.2091 23 24 21.2091 24 19C24 17.1305 22.7175 15.5606 20.9844 15.122C20.7924 12.8136 18.858 11 16.5 11Z"
            clip-rule="evenodd"
            fill-rule="evenodd"
          ></path>
        </g>
      </svg>
    </div>
    <div class="text">
      <span>Click to upload image</span>
    </div>
    <input type="file" id="file" multiple @change="onFilesChange" />
  </label>
  <!-- ...existing code... -->
  <div class="row mt-4 g-4">
    <h3>Ảnh đã tải lên :</h3>
    <div class="col-3" v-for="(item, index) in fileView" :key="index">
      <div style="border: 0px" class="card position-relative overflow-hidden rounded-4">
        <button class="close-image" @click="removeItem(index)">x</button>
        <img
          v-if="item.type === 'image'"
          class="card-img rounded-4 custom-shadow"
          style="height: 450px; object-fit: cover"
          :src="item.url"
          alt="Card image"
        />
        <video
          v-else
          class="card-img rounded-4 custom-shadow"
          style="height: 220px; object-fit: cover"
          :src="item.url"
          controls
        ></video>
        <div
          v-if="!item.valid"
          style="
            color: red;
            position: absolute;
            bottom: 8px;
            left: 8px;
            padding: 2px 8px;
            border-radius: 6px;
            background: #fff8;
          "
        >
          Sai kích thước
        </div>
      </div>
    </div>
    <button
      @click="uploadAllFiles"
      :disabled="!canUpload || isUploading"
      style="min-width: 120px; position: relative"
    >
      <span v-if="!isUploading">Upload</span>
      <span v-else>
        <svg
          style="width: 20px; height: 20px; vertical-align: middle; margin-right: 6px"
          viewBox="0 0 50 50"
        >
          <circle
            cx="25"
            cy="25"
            r="20"
            fill="none"
            stroke="#10c6c3"
            stroke-width="5"
            stroke-linecap="round"
            stroke-dasharray="31.4 31.4"
          >
            <animateTransform
              attributeName="transform"
              type="rotate"
              from="0 25 25"
              to="360 25 25"
              dur="1s"
              repeatCount="indefinite"
            />
          </circle>
        </svg>
        Đang upload...
      </span>
    </button>
  </div>
  <div v-if="errorMsg" style="color: red; margin-top: 8px">{{ errorMsg }}</div>
  <!-- ...existing code... -->
</template>
<script setup>
import { ref } from "vue";
import axios from "axios";

const props = defineProps({
  maxImages: { type: Number, default: 5 },
  maxVideos: { type: Number, default: 2 },
  folder: { type: String, default: "profiles" },
  widthImg: { type: Number, default: 350 },
  heightImg: { type: Number, default: 450 },
  videoDuration: { type: Number, default: 60 }, // Giây
});

const fileView = ref([]); // [{file, url, valid, type}]
const canUpload = ref(false);
const errorMsg = ref("");
const emit = defineEmits(["update-avatar", "update-main-image-url"]);
const isUploading = ref(false);

const removeItem = (index) => {
  const removed = fileView.value.splice(index, 1);
  if (removed.length && removed[0].url) {
    URL.revokeObjectURL(removed[0].url);
  }
  checkCanUpload();
};

function onFilesChange(event) {
  errorMsg.value = "";
  const files = Array.from(event.target.files);

  // Đếm số ảnh và video đã có
  const currentImages = fileView.value.filter((f) => f.type === "image").length;
  const currentVideos = fileView.value.filter((f) => f.type === "video").length;

  // Đếm số file hợp lệ sẽ thêm
  let newImages = 0;
  let newVideos = 0;
  files.forEach((file) => {
    if (file.type.startsWith("image/")) newImages++;
    else if (file.type.startsWith("video/")) newVideos++;
  });

  if (currentImages + newImages > props.maxImages) {
    errorMsg.value = `Chỉ được chọn tối đa ${props.maxImages} ảnh!`;
    return;
  }
  if (currentVideos + newVideos > props.maxVideos) {
    errorMsg.value = `Chỉ được chọn tối đa ${props.maxVideos} video!`;
    return;
  }

  let checked = 0;

  files.forEach((file) => {
    const url = URL.createObjectURL(file);
    if (file.type.startsWith("image/")) {
      const img = new window.Image();
      img.onload = function () {
        checked++;
        if (props.widthImg === 0 && props.heightImg === 0) {
          // Nếu ảnh không cần tỷ lệ
          fileView.value.push({ file, url, valid: true, type: "image" });
        } else {
          if (img.width === props.widthImg && img.height === props.heightImg) {
            fileView.value.push({ file, url, valid: true, type: "image" });
          } else {
            fileView.value.push({ file, url, valid: false, type: "image" });
          }
        }
        if (checked === files.length) checkCanUpload();
      };
      img.onerror = function () {
        checked++;
        fileView.value.push({ file, url, valid: false, type: "image" });
        if (checked === files.length) checkCanUpload();
      };
      img.src = url;
    } else if (file.type.startsWith("video/")) {
      const video = document.createElement("video");
      video.preload = "metadata";
      video.onloadedmetadata = function () {
        checked++;
        if (video.duration <= props.videoDuration) {
          fileView.value.push({ file, url, valid: true, type: "video" });
        } else {
          fileView.value.push({ file, url, valid: false, type: "video" });
        }
        if (checked === files.length) checkCanUpload();
      };
      video.onerror = function () {
        checked++;
        fileView.value.push({ file, url, valid: false, type: "video" });
        if (checked === files.length) checkCanUpload();
      };
      video.src = url;
    } else {
      checked++;
      if (checked === files.length) checkCanUpload();
    }
  });
}

function checkCanUpload() {
  canUpload.value =
    fileView.value.length > 0 && fileView.value.every((item) => item.valid);
  if (!canUpload.value) {
    errorMsg.value = "Tất cả ảnh phải đúng 350x450px và video < 60s!";
  } else {
    errorMsg.value = "";
  }
}

async function uploadAllFiles() {
  errorMsg.value = "";
  isUploading.value = true;
  const validFiles = fileView.value.filter((item) => item.valid);
  if (!validFiles.length) {
    errorMsg.value = "Không có file hợp lệ để upload!";
    isUploading.value = false;
    return [];
  }
  const results = [];
  for (const item of validFiles) {
    const formData = new FormData();
    formData.append("file", item.file);
    formData.append("upload_preset", "upload_preset");
    formData.append("folder", props.folder); // dùng props.folder
    try {
      const endpoint =
        item.type === "image"
          ? "https://api.cloudinary.com/v1_1/da2v8uqir/image/upload"
          : "https://api.cloudinary.com/v1_1/da2v8uqir/video/upload";
      const response = await axios.post(endpoint, formData);
      results.push({
        type: item.type,
        url: response.data.secure_url,
        public_id: response.data.public_id,
      });
    } catch (err) {
      errorMsg.value = "Upload thất bại!";
      isUploading.value = false;
      return [];
    }
  }
  errorMsg.value = "Upload thành công!";
  fileView.value = [];
  canUpload.value = false;
  isUploading.value = false;
  return results;
}

// expose hàm cho cha gọi
defineExpose({ uploadAllFiles });

// import { ref } from "vue";
// import ImageUpload from "./ImageUpload.vue";
// const imageUploadRef = ref();

// async function handleCreateProduct() {
//   // Gọi upload ở component con
//   const uploadResults = await imageUploadRef.value.uploadAllFiles();
//   if (!uploadResults.length) {
//     // Báo lỗi hoặc return
//     return;
//   }
//   // Tiếp tục gửi API tạo sản phẩm, truyền kèm danh sách ảnh/video đã upload
//   // ...
// }

// component cha sẽ như thế này
// <ImageUpload :max-images="3" :max-videos="1" folder="products" heightImg= widthImg= videoDuration= ref="imageUploadRef" />
</script>
