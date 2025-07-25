<template>
  <h1>Uploads here</h1>
  <input type="file" accept="image/*" @change="checkImageSize" />
  <div v-if="previewUrl" class="preview-wrapper">
    <img :src="previewUrl" alt="Preview" class="preview-img" />
    <button class="remove-btn" @click="removeImage">✖</button>
  </div>
  <div v-if="errorMsg" style="color: red; margin-top: 8px">{{ errorMsg }}</div>
</template>

<script setup>
import axios from "axios";
import { ref } from "vue";
const canUpload = ref(false);
const errorMsg = ref("");
let lastFile = null;
const previewUrl = ref(null);
const emit = defineEmits(["uploaded"]);

function checkImageSize(e) {
  canUpload.value = false;
  errorMsg.value = "";
  const file = e.target.files[0];
  if (!file) {
    previewUrl.value = null;
    return;
  }
  const reader = new FileReader();
  reader.onload = function (evt) {
    const img = new window.Image();
    img.onload = function () {
      if (img.width === 600 && img.height === 900) {
        canUpload.value = true;
        lastFile = file;
        previewUrl.value = evt.target.result;
        openUploadWidget();
      } else {
        errorMsg.value = "Ảnh phải đúng 300x200px!";
        canUpload.value = false;
        lastFile = null;
        previewUrl.value = null;
      }
    };
    img.src = evt.target.result;
  };
  reader.readAsDataURL(file);
}

function openUploadWidget() {
  if (canUpload.value && lastFile) {
    const formData = new FormData();
    formData.append("file", lastFile);
    formData.append("upload_preset", "upload_preset"); // Thay bằng tên preset của bạn
    axios
      .post("https://api.cloudinary.com/v1_1/da2v8uqir/image/upload", formData)
      .then((response) => {
        // Xử lý kết quả upload thành công
        console.log("Upload thành công:", response.data.secure_url);
        errorMsg.value = "Upload thành công!";
        emit("uploaded", response.data.secure_url);
      })
      .catch((err) => {
        errorMsg.value = "Upload thất bại!";
      });
  }
}

function removeImage() {
  lastFile = null;
  previewUrl.value = null;
  canUpload.value = false;
  errorMsg.value = "";
}
</script>

<style scoped>
.preview-wrapper {
  position: relative;
  display: inline-block;
  margin: 12px 0;
}
.preview-img {
  max-width: 200px;
  max-height: 200px;
  border-radius: 8px;
  box-shadow: 0 2px 8px #0002;
  background: #f8f8f8;
}
.remove-btn {
  position: absolute;
  top: 4px;
  right: 4px;
  background: #ff4d4f;
  color: #fff;
  border: none;
  border-radius: 50%;
  width: 28px;
  height: 28px;
  font-size: 18px;
  cursor: pointer;
  box-shadow: 0 1px 4px #0002;
  transition: background 0.2s;
}
.remove-btn:hover {
  background: #d9363e;
}
</style>
