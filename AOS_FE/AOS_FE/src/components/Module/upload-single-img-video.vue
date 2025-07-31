<template>
  <div class="upload-single-container">
    <div class="upload-header">
      <h4 class="upload-title">
        <i :class="isVideo ? 'bi bi-camera-video me-2' : 'bi bi-person-circle me-2'"></i>
        {{ isVideo ? "Video" : "Avatar" }}
        <span
          class="info-icon"
          :title="isVideo ? 'Chọn video để tải lên' : 'Chọn ảnh avatar để tải lên'"
        >
          <i class="bi bi-info-circle"></i>
        </span>
      </h4>
      <p class="upload-subtitle">
        {{
          isVideo
            ? "Hỗ trợ MP4, WebM, AVI. Tối đa 50MB."
            : "Hỗ trợ JPG, PNG, GIF. Tối đa 5MB."
        }}
      </p>
    </div>

    <div class="upload-area">
      <!-- Drop Zone (when no file) -->
      <div
        v-if="!selectedFile"
        class="drop-zone"
        @drop="handleDrop"
        @dragover="handleDragOver"
        @dragleave="handleDragLeave"
        @dragenter="handleDragEnter"
        :class="{ 'drag-over': isDragOver }"
      >
        <div class="drop-content">
          <i
            :class="
              isVideo ? 'bi bi-camera-video drop-icon' : 'bi bi-person-circle drop-icon'
            "
          ></i>
          <h5>{{ isVideo ? "Kéo thả video vào đây" : "Kéo thả ảnh avatar vào đây" }}</h5>
          <p>hoặc</p>
          <button class="browse-btn" @click="triggerFileInput">
            <i class="bi bi-folder2-open me-2"></i>
            {{ isVideo ? "Chọn video từ máy tính" : "Chọn ảnh từ máy tính" }}
          </button>
          <input
            ref="fileInput"
            type="file"
            :accept="isVideo ? 'video/*' : 'image/*'"
            @change="handleFileSelect"
            style="display: none"
          />
        </div>
      </div>

      <!-- File Preview (when file selected) -->
      <div v-if="selectedFile" class="file-preview">
        <div class="preview-header">
          <div class="file-info">
            <span class="file-name">{{ selectedFile.name }}</span>
            <span class="file-size">{{ formatFileSize(selectedFile.size) }}</span>
          </div>
          <div class="preview-actions">
            <button
              class="upload-btn"
              @click="uploadFile"
              v-if="!selectedFile.uploading && !selectedFile.cloudinaryUrl"
              :disabled="isUploading"
            >
              <i class="bi bi-cloud-upload me-1"></i>
              {{ isUploading ? "Đang tải..." : "Tải lên" }}
            </button>
            <button class="replace-btn" @click="replaceFile" :disabled="isUploading">
              <i class="bi bi-arrow-repeat me-1"></i>
              Thay đổi
            </button>
            <button class="remove-btn" @click="removeFile" :disabled="isUploading">
              <i class="bi bi-trash me-1"></i>
              Xóa
            </button>
          </div>
        </div>

        <div class="media-container">
          <!-- Image Preview -->
          <div v-if="!isVideo" class="image-preview">
            <img :src="selectedFile.url" :alt="selectedFile.name" @click="previewMedia" />
            <div class="media-overlay" @click="previewMedia">
              <button class="preview-btn">
                <i class="bi bi-eye"></i>
              </button>
            </div>
          </div>

          <!-- Video Preview -->
          <div v-else class="video-preview">
            <video :src="selectedFile.url" @click="previewMedia" muted preload="metadata">
              Trình duyệt không hỗ trợ video
            </video>
            <div class="media-overlay" @click="previewMedia">
              <button class="preview-btn">
                <i class="bi bi-play-circle"></i>
              </button>
            </div>
          </div>

          <!-- Loading Overlay -->
          <div v-if="selectedFile.uploading" class="loading-overlay">
            <div class="spinner"></div>
            <span>Đang tải lên...</span>
          </div>
        </div>

        <!-- Upload Status -->
        <div class="upload-status-container">
          <span v-if="selectedFile.uploading" class="upload-status uploading">
            <i class="bi bi-cloud-arrow-up"></i> Đang tải lên...
          </span>
          <span v-else-if="selectedFile.cloudinaryUrl" class="upload-status success">
            <i class="bi bi-cloud-check"></i> Đã tải lên thành công
          </span>
          <span v-else-if="selectedFile.isUrl" class="upload-status url">
            <i class="bi bi-link-45deg"></i> Từ URL
          </span>
          <span v-else class="upload-status pending">
            <i class="bi bi-clock"></i> Chờ tải lên
          </span>
        </div>

        <input
          ref="fileInput"
          type="file"
          :accept="isVideo ? 'video/*' : 'image/*'"
          @change="handleFileSelect"
          style="display: none"
        />
      </div>
    </div>

    <!-- URL Upload Section -->
    <div class="url-upload-section">
      <h5 class="url-title">
        <i class="bi bi-link-45deg me-2"></i>
        {{ isVideo ? "Thêm video từ URL" : "Thêm ảnh từ URL" }}
      </h5>
      <div class="url-input-group">
        <input
          type="url"
          v-model="mediaUrl"
          :placeholder="isVideo ? 'Nhập URL video...' : 'Nhập URL ảnh...'"
          class="url-input"
          @keyup.enter="addMediaFromUrl"
        />
        <button
          class="url-add-btn"
          @click="addMediaFromUrl"
          :disabled="!mediaUrl || !!selectedFile"
        >
          <i class="bi bi-plus"></i>
        </button>
      </div>
    </div>

    <!-- Preview Modal -->
    <div v-if="previewModal.show" class="preview-modal" @click="closePreview">
      <div class="preview-content" @click.stop>
        <button class="close-btn" @click="closePreview">
          <i class="bi bi-x-lg"></i>
        </button>

        <!-- Image Modal -->
        <img
          v-if="!isVideo"
          :src="previewModal.file?.url"
          :alt="previewModal.file?.name"
        />

        <!-- Video Modal -->
        <video
          v-else
          :src="previewModal.file?.url"
          controls
          autoplay
          style="max-width: 100%; max-height: 80vh"
        >
          Trình duyệt không hỗ trợ video
        </video>

        <div class="preview-info">
          <h6>{{ previewModal.file?.name }}</h6>
          <p>{{ formatFileSize(previewModal.file?.size) }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import uploadAPI from "../../Configs/upload-api";

// Props & Emits
const props = defineProps({
  type: {
    type: String,
    default: "image", // "image" hoặc "video"
    validator: (value) => ["image", "video"].includes(value),
  },
  maxFileSize: {
    type: Number,
    default: null, // Sẽ được tính dựa trên type
  },
  folderName: {
    type: String,
    default: "uploads",
  },
  initialUrl: {
    type: String,
    default: null, // URL ban đầu nếu có
  },
});

const emit = defineEmits(["update:file", "upload-success", "upload-error"]);

// Computed
const isVideo = computed(() => props.type === "video");
const maxSize = computed(() => {
  if (props.maxFileSize) return props.maxFileSize;
  return isVideo.value ? 50 * 1024 * 1024 : 5 * 1024 * 1024; // 50MB cho video, 5MB cho image
});

const acceptedTypes = computed(() => {
  if (isVideo.value) {
    return ["video/mp4", "video/webm", "video/avi", "video/mov", "video/wmv"];
  }
  return ["image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"];
});

// Reactive Data
const selectedFile = ref(null);
const isDragOver = ref(false);
const mediaUrl = ref("");
const fileInput = ref(null);
const isUploading = ref(false);

const previewModal = ref({
  show: false,
  file: null,
});

// Initialize with initial URL if provided
if (props.initialUrl) {
  selectedFile.value = {
    id: Date.now(),
    name: props.initialUrl.split("/").pop() || `${props.type} file`,
    size: 0,
    url: props.initialUrl,
    cloudinaryUrl: props.initialUrl,
    publicId: null,
    isUrl: true,
    uploading: false,
  };
}

// Methods
const triggerFileInput = () => {
  fileInput.value?.click();
};

const handleFileSelect = (event) => {
  const file = event.target.files[0];
  if (file) {
    addFile(file);
  }
  // Reset input
  event.target.value = "";
};

const handleDrop = (event) => {
  event.preventDefault();
  isDragOver.value = false;

  const file = event.dataTransfer.files[0];
  if (file) {
    addFile(file);
  }
};

const handleDragOver = (event) => {
  event.preventDefault();
  isDragOver.value = true;
};

const handleDragEnter = (event) => {
  event.preventDefault();
  isDragOver.value = true;
};

const handleDragLeave = (event) => {
  event.preventDefault();
  if (!event.currentTarget.contains(event.relatedTarget)) {
    isDragOver.value = false;
  }
};

const addFile = (file) => {
  // Kiểm tra loại file
  if (!acceptedTypes.value.includes(file.type)) {
    alert(`Loại file không được hỗ trợ: ${file.name}`);
    return;
  }

  // Kiểm tra kích thước file
  if (file.size > maxSize.value) {
    alert(`File quá lớn: ${file.name}. Tối đa ${formatFileSize(maxSize.value)}`);
    return;
  }

  // Giải phóng URL cũ nếu có
  if (
    selectedFile.value?.url &&
    !selectedFile.value.isUrl &&
    !selectedFile.value.cloudinaryUrl
  ) {
    URL.revokeObjectURL(selectedFile.value.url);
  }

  selectedFile.value = {
    id: Date.now(),
    file,
    name: file.name,
    size: file.size,
    url: URL.createObjectURL(file),
    uploading: false,
    cloudinaryUrl: null,
    publicId: null,
    isUrl: false,
  };

  updateParent();
};

const uploadFile = async () => {
  if (
    !selectedFile.value?.file ||
    selectedFile.value.uploading ||
    selectedFile.value.cloudinaryUrl
  ) {
    return;
  }

  selectedFile.value.uploading = true;
  isUploading.value = true;

  try {
    const uploadResult = await uploadAPI.upload.uploadSingleImageOrVideo(
      selectedFile.value.file,
      props.folderName
    );

    // Giải phóng URL tạm thời
    URL.revokeObjectURL(selectedFile.value.url);

    // Cập nhật với URL từ Cloudinary
    selectedFile.value = {
      ...selectedFile.value,
      url: uploadResult.url,
      cloudinaryUrl: uploadResult.url,
      publicId: uploadResult.public_id,
      uploading: false,
    };

    updateParent();
    emit("upload-success", selectedFile.value);
  } catch (error) {
    console.error(`Upload failed:`, error);
    selectedFile.value.uploading = false;
    emit("upload-error", error);
    alert(`Upload thất bại: ${selectedFile.value.name}. Vui lòng thử lại.`);
  } finally {
    isUploading.value = false;
  }
};

const addMediaFromUrl = () => {
  if (!mediaUrl.value) return;

  try {
    // Validate URL
    new URL(mediaUrl.value);

    // Giải phóng file cũ nếu có
    if (
      selectedFile.value?.url &&
      !selectedFile.value.isUrl &&
      !selectedFile.value.cloudinaryUrl
    ) {
      URL.revokeObjectURL(selectedFile.value.url);
    }

    selectedFile.value = {
      id: Date.now(),
      name: mediaUrl.value.split("/").pop() || `${props.type} từ URL`,
      size: 0,
      url: mediaUrl.value,
      cloudinaryUrl: mediaUrl.value,
      publicId: null,
      isUrl: true,
      uploading: false,
    };

    mediaUrl.value = "";
    updateParent();
  } catch (error) {
    alert("URL không hợp lệ");
  }
};

const replaceFile = () => {
  triggerFileInput();
};

const removeFile = () => {
  if (
    selectedFile.value?.url &&
    !selectedFile.value.isUrl &&
    !selectedFile.value.cloudinaryUrl
  ) {
    URL.revokeObjectURL(selectedFile.value.url);
  }

  selectedFile.value = null;
  updateParent();
};

// Preview
const previewMedia = () => {
  previewModal.value = {
    show: true,
    file: selectedFile.value,
  };
};

const closePreview = () => {
  previewModal.value.show = false;
};

// Load functions
const loadFromUrl = (url) => {
  try {
    new URL(url);

    // Giải phóng file cũ nếu có
    if (
      selectedFile.value?.url &&
      !selectedFile.value.isUrl &&
      !selectedFile.value.cloudinaryUrl
    ) {
      URL.revokeObjectURL(selectedFile.value.url);
    }

    selectedFile.value = {
      id: Date.now(),
      name: url.split("/").pop() || `${props.type} file`,
      size: 0,
      url: url,
      cloudinaryUrl: url,
      publicId: url.includes("cloudinary") ? url.split("/").pop().split(".")[0] : null,
      isUrl: !url.includes("cloudinary"),
      uploading: false,
    };

    updateParent();
    return selectedFile.value;
  } catch (error) {
    console.error("Invalid URL:", error);
    throw new Error("URL không hợp lệ");
  }
};

// Utilities
const formatFileSize = (bytes) => {
  if (bytes === 0) return "0 B";
  const k = 1024;
  const sizes = ["B", "KB", "MB", "GB"];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + " " + sizes[i];
};

const updateParent = () => {
  emit("update:file", selectedFile.value);
};

// Expose methods for parent component
defineExpose({
  uploadFile,
  removeFile,
  loadFromUrl,
  getFile: () => selectedFile.value,
  getFileUrl: () => selectedFile.value?.cloudinaryUrl || selectedFile.value?.url,
  hasFile: () => !!selectedFile.value,
  isUploaded: () => !!selectedFile.value?.cloudinaryUrl,
  clearFile: () => {
    removeFile();
  },
});
</script>

<style scoped>
.upload-single-container {
  background: white;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.upload-header {
  margin-bottom: 24px;
}

.upload-title {
  color: #1a202c;
  font-weight: 600;
  margin: 0 0 8px 0;
  display: flex;
  align-items: center;
}

.info-icon {
  margin-left: 8px;
  color: #6b7280;
  cursor: help;
}

.upload-subtitle {
  color: #6b7280;
  margin: 0;
  font-size: 14px;
}

/* Drop Zone */
.drop-zone {
  border: 2px dashed #d1d5db;
  border-radius: 12px;
  padding: 40px 20px;
  text-align: center;
  transition: all 0.3s ease;
  background: #f9fafb;
}

.drop-zone.drag-over {
  border-color: #667eea;
  background: #eef2ff;
  transform: scale(1.02);
}

.drop-content {
  max-width: 300px;
  margin: 0 auto;
}

.drop-icon {
  font-size: 48px;
  color: #9ca3af;
  margin-bottom: 16px;
}

.drop-zone h5 {
  color: #374151;
  margin-bottom: 8px;
}

.drop-zone p {
  color: #6b7280;
  margin: 8px 0;
}

.browse-btn {
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 8px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  display: inline-flex;
  align-items: center;
}

.browse-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

/* File Preview */
.file-preview {
  background: #f9fafb;
  border-radius: 12px;
  overflow: hidden;
  border: 2px solid #e5e7eb;
}

.preview-header {
  padding: 16px;
  background: white;
  border-bottom: 1px solid #e5e7eb;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 12px;
}

.file-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.file-name {
  font-weight: 500;
  color: #374151;
  font-size: 14px;
}

.file-size {
  color: #6b7280;
  font-size: 12px;
}

.preview-actions {
  display: flex;
  gap: 8px;
  align-items: center;
}

.upload-btn {
  background: linear-gradient(135deg, #10b981, #059669);
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  font-weight: 500;
  font-size: 12px;
}

.upload-btn:hover:not(:disabled) {
  background: linear-gradient(135deg, #059669, #047857);
  transform: translateY(-1px);
}

.upload-btn:disabled {
  background: #9ca3af;
  cursor: not-allowed;
}

.replace-btn {
  background: #f3f4f6;
  color: #374151;
  border: 1px solid #d1d5db;
  padding: 8px 16px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  font-size: 12px;
}

.replace-btn:hover:not(:disabled) {
  background: #e5e7eb;
}

.replace-btn:disabled {
  background: #f9fafb;
  color: #9ca3af;
  cursor: not-allowed;
}

.remove-btn {
  background: #fef2f2;
  color: #dc2626;
  border: 1px solid #fecaca;
  padding: 8px 16px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  font-size: 12px;
}

.remove-btn:hover:not(:disabled) {
  background: #fee2e2;
  border-color: #fca5a5;
}

.remove-btn:disabled {
  background: #f9fafb;
  color: #9ca3af;
  cursor: not-allowed;
}

/* Media Container */
.media-container {
  position: relative;
  background: #f3f4f6;
}

.image-preview,
.video-preview {
  position: relative;
  width: 100%;
  height: 300px;
  overflow: hidden;
  cursor: pointer;
}

.image-preview img,
.video-preview video {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
}

.media-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.media-container:hover .media-overlay {
  opacity: 1;
}

.preview-btn {
  width: 60px;
  height: 60px;
  background: rgba(255, 255, 255, 0.9);
  color: #374151;
  border: none;
  border-radius: 50%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
  transition: all 0.3s ease;
}

.preview-btn:hover {
  background: white;
  transform: scale(1.1);
}

.loading-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.9);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 12px;
  color: #6b7280;
  font-size: 16px;
  z-index: 10;
}

.spinner {
  width: 32px;
  height: 32px;
  border: 3px solid #e5e7eb;
  border-top: 3px solid #667eea;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.upload-status-container {
  padding: 12px 16px;
  background: white;
}

.upload-status {
  font-size: 12px;
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 6px;
}

.upload-status.uploading {
  color: #f59e0b;
  animation: pulse 2s infinite;
}

.upload-status.success {
  color: #10b981;
}

.upload-status.url {
  color: #6366f1;
}

.upload-status.pending {
  color: #f59e0b;
}

@keyframes pulse {
  0%,
  100% {
    opacity: 1;
  }
  50% {
    opacity: 0.5;
  }
}

/* URL Upload */
.url-upload-section {
  margin-top: 24px;
  padding-top: 20px;
  border-top: 1px solid #e5e7eb;
}

.url-title {
  color: #374151;
  font-weight: 600;
  margin-bottom: 12px;
  display: flex;
  align-items: center;
}

.url-input-group {
  display: flex;
  gap: 8px;
}

.url-input {
  flex: 1;
  padding: 12px 16px;
  border: 2px solid #e5e7eb;
  border-radius: 8px;
  font-size: 14px;
  transition: border-color 0.3s ease;
}

.url-input:focus {
  outline: none;
  border-color: #667eea;
}

.url-add-btn {
  width: 44px;
  height: 44px;
  background: #667eea;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.url-add-btn:hover:not(:disabled) {
  background: #5a67d8;
  transform: scale(1.05);
}

.url-add-btn:disabled {
  background: #9ca3af;
  cursor: not-allowed;
}

/* Preview Modal */
.preview-modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  padding: 20px;
}

.preview-content {
  position: relative;
  max-width: 90vw;
  max-height: 90vh;
  background: white;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3);
}

.close-btn {
  position: absolute;
  top: 16px;
  right: 16px;
  width: 32px;
  height: 32px;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  border-radius: 50%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10;
  transition: background 0.3s ease;
}

.close-btn:hover {
  background: rgba(0, 0, 0, 0.7);
}

.preview-content img {
  width: 100%;
  height: auto;
  max-height: 80vh;
  object-fit: contain;
}

.preview-info {
  padding: 20px;
  text-align: center;
}

.preview-info h6 {
  margin: 0 0 8px 0;
  color: #1a202c;
}

.preview-info p {
  margin: 0;
  color: #6b7280;
  font-size: 14px;
}

/* Responsive */
@media (max-width: 768px) {
  .upload-single-container {
    padding: 16px;
  }

  .preview-header {
    flex-direction: column;
    align-items: stretch;
  }

  .preview-actions {
    justify-content: space-between;
  }

  .upload-btn,
  .replace-btn,
  .remove-btn {
    flex: 1;
    justify-content: center;
  }

  .drop-zone {
    padding: 24px 16px;
  }

  .url-input-group {
    flex-direction: column;
  }

  .url-add-btn {
    width: 100%;
    height: 44px;
  }
}

@media (max-width: 480px) {
  .upload-single-container {
    padding: 12px;
  }

  .drop-zone {
    padding: 20px 12px;
  }

  .image-preview,
  .video-preview {
    height: 200px;
  }

  .preview-actions {
    flex-direction: column;
    gap: 8px;
  }
}
</style>
