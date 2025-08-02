<template>
  <div class="upload-products-container">
    <div class="upload-header">
      <h4 class="upload-title">
        <i class="bi bi-images me-2"></i>
        Tải lên ảnh {{ props.titleUpload }}
        <span class="info-icon" :title="'Tải lên ảnh của ' + props.titleUpload">
          <i class="bi bi-info-circle"></i>
        </span>
      </h4>
      <p class="upload-subtitle">
        Kéo thả để sắp xếp ảnh. Ảnh đầu tiên sẽ là ảnh chính hiển thị.
      </p>
    </div>

    <div class="upload-area">
      <!-- Drop Zone -->
      <div
        class="drop-zone"
        @drop="handleDrop"
        @dragover="handleDragOver"
        @dragleave="handleDragLeave"
        @dragenter="handleDragEnter"
        :class="{ 'drag-over': isDragOver }"
        v-if="images.length === 0"
      >
        <div class="drop-content">
          <i class="bi bi-cloud-upload drop-icon"></i>
          <h5>Kéo thả ảnh vào đây</h5>
          <p>hoặc</p>
          <button type="button" class="browse-btn" @click.prevent="triggerFileInput">
            <i class="bi bi-folder2-open me-2"></i>
            Chọn ảnh từ máy tính
          </button>
          <input
            ref="fileInput"
            type="file"
            multiple
            accept="image/*"
            @change="handleFileSelect"
            style="display: none"
          />
          <div class="file-info">
            <small>Hỗ trợ: JPG, PNG, GIF. Tối đa {{ props.maxFiles }} ảnh.</small>
          </div>
        </div>
      </div>

      <!-- Image Gallery -->
      <div v-if="images.length > 0" class="image-gallery">
        <div class="gallery-header">
          <div class="image-count">
            <span class="count-badge">{{ images.length }}/{{ props.maxFiles }}</span>
            <span class="primary-label">Ảnh chính</span>
          </div>
          <div class="header-actions">
            <button
              type="button"
              class="upload-all-btn"
              @click.prevent="uploadAllImages"
              v-if="hasUnuploadedImages"
              :disabled="isUploading"
            >
              <i class="bi bi-cloud-upload me-1"></i>
              {{ isUploading ? "Đang tải..." : "Tải lên tất cả" }}
            </button>

            <button
              type="button"
              class="add-more-btn"
              @click.prevent="triggerFileInput"
              v-if="images.length < props.maxFiles"
            >
              <i class="bi bi-plus-circle me-1"></i>
              Thêm ảnh
            </button>
          </div>
        </div>

        <div class="sortable-grid" ref="sortableContainer">
          <div
            v-for="(image, index) in images"
            :key="image.id"
            class="image-item"
            :class="{ 'is-primary': index === 0, 'is-dragging': draggedIndex === index }"
            :draggable="true"
            @dragstart="handleDragStart($event, index)"
            @dragend="handleDragEnd"
            @dragover="handleImageDragOver($event, index)"
            @drop="handleImageDrop($event, index)"
          >
            <!-- Primary Badge -->
            <div v-if="index === 0" class="primary-badge">
              <i class="bi bi-star-fill"></i>
              Ảnh chính
            </div>

            <!-- Image -->
            <div class="image-wrapper" :style="{ paddingBottom: aspectRatioPadding }">
              <img :src="image.url" :alt="`Ảnh ${index + 1}`" />

              <!-- Overlay Controls -->
              <div class="image-overlay">
                <button
                  type="button"
                  class="control-btn preview-btn"
                  @click.prevent="previewImage(image)"
                  title="Xem trước"
                >
                  <i class="bi bi-eye"></i>
                </button>
                <button
                  type="button"
                  class="control-btn upload-btn"
                  @click.prevent="uploadSingleImage(index)"
                  v-if="!image.uploading && !image.cloudinaryUrl && !image.isUrl"
                  title="Tải lên"
                >
                  <i class="bi bi-cloud-upload"></i>
                </button>
                <button
                  type="button"
                  class="control-btn delete-btn"
                  @click.prevent="removeImage(index)"
                  title="Xóa ảnh"
                >
                  <i class="bi bi-trash"></i>
                </button>
              </div>

              <!-- Drag Handle -->
              <div class="drag-handle">
                <i class="bi bi-grip-vertical"></i>
              </div>

              <!-- Loading Overlay -->
              <div v-if="image.uploading" class="loading-overlay">
                <div class="spinner"></div>
                <span>Đang tải...</span>
              </div>
            </div>

            <!-- Image Info -->
            <div class="image-info">
              <span class="image-name">{{ image.name }}</span>
              <span class="image-size">{{ formatFileSize(image.size) }}</span>
              <span v-if="image.uploading" class="upload-status uploading">
                <i class="bi bi-cloud-arrow-up"></i> Đang tải lên...
              </span>
              <span v-else-if="image.cloudinaryUrl" class="upload-status success">
                <i class="bi bi-cloud-check"></i> Đã tải lên
              </span>
              <span v-else-if="image.isUrl" class="upload-status url">
                <i class="bi bi-link-45deg"></i> Từ URL
              </span>
              <span v-else class="upload-status pending">
                <i class="bi bi-clock"></i> Chờ tải lên
              </span>
            </div>
          </div>

          <!-- Add More Slot -->
          <div
            v-if="images.length < props.maxFiles"
            class="add-more-slot"
            :style="{ paddingBottom: aspectRatioPadding }"
            @click="triggerFileInput"
          >
            <div class="add-content">
              <i class="bi bi-plus-lg"></i>
              <span>Thêm ảnh</span>
            </div>
          </div>
        </div>

        <input
          ref="fileInput"
          type="file"
          multiple
          accept="image/*"
          @change="handleFileSelect"
          style="display: none"
        />
      </div>
    </div>

    <!-- URL Upload Section -->
    <div class="url-upload-section">
      <h5 class="url-title">
        <i class="bi bi-link-45deg me-2"></i>
        Thêm từ URL
      </h5>
      <div class="url-input-group">
        <input
          type="url"
          v-model="imageUrl"
          placeholder="Nhập URL ảnh..."
          class="url-input"
          @keyup.enter="addImageFromUrl"
        />
        <button
          type="button"
          class="url-add-btn"
          @click.prevent="addImageFromUrl"
          :disabled="!imageUrl || images.length >= 10"
        >
          <i class="bi bi-plus"></i>
        </button>
      </div>
    </div>

    <!-- Preview Modal -->
    <div v-if="previewModal.show" class="preview-modal mt-5" @click="closePreview">
      <div class="preview-content" @click.stop>
        <button type="button" class="close-btn" @click.prevent="closePreview">
          <i class="bi bi-x-lg"></i>
        </button>
        <img :src="previewModal.image?.url" :alt="previewModal.image?.name" />
        <div class="preview-info">
          <h6>{{ previewModal.image?.name }}</h6>
          <p>{{ formatFileSize(previewModal.image?.size) }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick, computed } from "vue";
import uploadAPI from "../../Configs/upload-api";

// Props & Emits
const props = defineProps({
  maxFiles: {
    type: Number,
    default: 10,
  },
  acceptedTypes: {
    type: Array,
    default: () => ["image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"],
  },
  maxFileSize: {
    type: Number,
    default: 5 * 1024 * 1024, // 5MB
  },
  folderName: {
    type: String,
    default: "products", // Thư mục upload trên Cloudinary
  },
  aspectRatio: {
    type: String,
    default: "4:5", // Tỷ lệ khung hình: "1:1", "4:5", "16:9", "3:4", v.v.
    validator: (value) => {
      // Kiểm tra format "width:height"
      return /^\d+:\d+$/.test(value);
    },
  },
  titleUpload: {
    type: String,
    default: " người dùng",
  },
});

const emit = defineEmits(["update:images", "primary-changed", "delete-image"]);

// Reactive Data
const images = ref([]);
const isDragOver = ref(false);
const draggedIndex = ref(null);
const imageUrl = ref("");
const fileInput = ref(null);
const sortableContainer = ref(null);
const isUploading = ref(false);

const previewModal = ref({
  show: false,
  image: null,
});

// Computed
const hasUnuploadedImages = computed(() => {
  return images.value.some((img) => !img.cloudinaryUrl && !img.isUrl && !img.uploading);
});

// Tính toán padding-bottom dựa vào aspectRatio
const aspectRatioPadding = computed(() => {
  const [width, height] = props.aspectRatio.split(":").map(Number);
  return `${(height / width) * 100}%`;
});

// Methods
const triggerFileInput = () => {
  fileInput.value?.click();
};

const handleFileSelect = (event) => {
  const files = Array.from(event.target.files);
  addFiles(files);
  // Reset input
  event.target.value = "";
};

const handleDrop = (event) => {
  event.preventDefault();
  isDragOver.value = false;

  const files = Array.from(event.dataTransfer.files);
  addFiles(files);
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
  // Only set to false if we're leaving the drop zone entirely
  if (!event.currentTarget.contains(event.relatedTarget)) {
    isDragOver.value = false;
  }
};

const addFiles = async (files) => {
  const validFiles = files.filter((file) => {
    // Kiểm tra loại file
    if (!props.acceptedTypes.includes(file.type)) {
      alert(`Loại file không được hỗ trợ: ${file.name}`);
      return false;
    }

    // Kiểm tra kích thước file
    if (file.size > props.maxFileSize) {
      alert(`File quá lớn: ${file.name}. Tối đa ${formatFileSize(props.maxFileSize)}`);
      return false;
    }

    return true;
  });

  // Kiểm tra giới hạn tổng số file
  const remainingSlots = props.maxFiles - images.value.length;
  const filesToAdd = validFiles.slice(0, remainingSlots);

  if (filesToAdd.length < validFiles.length) {
    alert(`Chỉ có thể thêm ${remainingSlots} ảnh nữa`);
  }

  // Thêm từng file vào danh sách KHÔNG upload ngay
  for (const file of filesToAdd) {
    const imageObj = {
      id: Date.now() + Math.random(),
      file,
      name: file.name,
      size: file.size,
      url: URL.createObjectURL(file), // URL tạm thời để preview
      uploading: false, // Chưa upload
      cloudinaryUrl: null,
      publicId: null,
    };

    images.value.push(imageObj);
  }
};

// Upload functions
const uploadSingleImage = async (index) => {
  const image = images.value[index];
  if (!image || image.uploading || image.cloudinaryUrl || image.isUrl || !image.file) {
    return;
  }

  // Đặt trạng thái uploading
  images.value[index].uploading = true;

  try {
    const uploadResult = await uploadAPI.upload.uploadSingleImageOrVideo(
      image.file,
      props.folderName
    );

    // Giải phóng URL tạm thời
    URL.revokeObjectURL(images.value[index].url);

    // Cập nhật với URL từ Cloudinary
    images.value[index] = {
      ...images.value[index],
      url: uploadResult.url,
      cloudinaryUrl: uploadResult.url,
      publicId: uploadResult.public_id,
      uploading: false,
    };

    updateParent();
  } catch (error) {
    console.error(`Upload failed for ${image.name}:`, error);
    images.value[index].uploading = false;
    alert(`Upload thất bại: ${image.name}. Vui lòng thử lại.`);
  }
};

const uploadAllImages = async () => {
  const unuploadedImages = images.value
    .map((img, index) => ({ img, index }))
    .filter(({ img }) => !img.cloudinaryUrl && !img.isUrl && !img.uploading && img.file);

  if (unuploadedImages.length === 0) {
    return;
  }

  isUploading.value = true;

  // Đặt tất cả ảnh chưa upload về trạng thái uploading
  unuploadedImages.forEach(({ index }) => {
    images.value[index].uploading = true;
  });

  try {
    // Upload từng ảnh một cách tuần tự để tránh quá tải
    for (const { img, index } of unuploadedImages) {
      try {
        const uploadResult = await uploadAPI.upload.uploadSingleImageOrVideo(
          img.file,
          props.folderName
        );

        // Giải phóng URL tạm thời
        URL.revokeObjectURL(images.value[index].url);

        // Cập nhật với URL từ Cloudinary
        images.value[index] = {
          ...images.value[index],
          url: uploadResult.url,
          cloudinaryUrl: uploadResult.url,
          publicId: uploadResult.public_id,
          uploading: false,
        };
      } catch (error) {
        console.error(`Upload failed for ${img.name}:`, error);
        images.value[index].uploading = false;
        // Không dừng quá trình upload, tiếp tục với ảnh tiếp theo
      }
    }

    updateParent();
  } finally {
    isUploading.value = false;
  }
};

const loadImagesFromProduct = async (productId) => {
  try {
    isUploading.value = true;
    const loadedImages = await uploadAPI.upload.loadImagesByProductId(productId);
    // Thay thế danh sách images hiện tại
    images.value = loadedImages;
    console.log(`Đã load ${loadedImages.length} ảnh của sản phẩm ${productId}`);
    return loadedImages;
  } catch (error) {
    console.error("Load images from product failed:", error);
    alert("Không thể tải ảnh sản phẩm. Vui lòng thử lại.");
    return [];
  } finally {
    isUploading.value = false;
  }
};

const loadImagesAndReplace = async (imageUrls) => {
  try {
    // Convert URLs thành image objects
    const loadedImages = imageUrls.map((url, index) => ({
      id: Date.now() + index,
      name: url.split("/").pop() || `Image ${index + 1}`,
      size: 0,
      url: url,
      cloudinaryUrl: url,
      publicId: url.includes("cloudinary") ? url.split("/").pop().split(".")[0] : null,
      isUrl: !url.includes("cloudinary"),
      uploading: false,
    }));

    // Thay thế danh sách images hiện tại
    images.value = loadedImages;

    console.log(`Đã load ${loadedImages.length} ảnh từ danh sách URLs`);
    return loadedImages;
  } catch (error) {
    console.error("Load images from URLs failed:", error);
    alert("Không thể tải ảnh từ danh sách URLs.");
    return [];
  }
};

const addImageFromUrl = async () => {
  if (!imageUrl.value || images.value.length >= props.maxFiles) return;

  try {
    // Validate URL
    new URL(imageUrl.value);

    const imageObj = {
      id: Date.now() + Math.random(),
      name: imageUrl.value.split("/").pop() || "URL Image",
      size: 0,
      url: imageUrl.value,
      cloudinaryUrl: imageUrl.value, // URL từ bên ngoài
      publicId: null, // Không có publicId vì không upload lên Cloudinary
      isUrl: true,
      uploading: false,
    };

    images.value.push(imageObj);
    imageUrl.value = "";
    updateParent();
  } catch (error) {
    alert("URL không hợp lệ");
  }
};

const removeImage = (index) => {
  const image = images.value[index];

  // Giải phóng object URL để tránh memory leak
  if (image.url && !image.isUrl && !image.cloudinaryUrl) {
    URL.revokeObjectURL(image.url);
  }

  // Lưu ý: Trong thực tế, bạn có thể muốn xóa ảnh khỏi Cloudinary
  // Tuy nhiên, việc này cần API key secret nên thường được thực hiện ở backend
  if (image.publicId) {
    console.log(`TODO: Delete image from Cloudinary with publicId: ${image.publicId}`);
    // Gọi API backend để xóa ảnh khỏi Cloudinary
    // await deleteFromCloudinary(image.publicId);
  }

  images.value.splice(index, 1);
  emit("delete-image", index); // Emit sự kiện xóa ảnh
};

// Drag and Drop Sorting
const handleDragStart = (event, index) => {
  draggedIndex.value = index;
  event.dataTransfer.effectAllowed = "move";
  event.dataTransfer.setData("text/html", event.target);
};

const handleDragEnd = () => {
  draggedIndex.value = null;
};

const handleImageDragOver = (event, index) => {
  event.preventDefault();
  event.dataTransfer.dropEffect = "move";
};

const handleImageDrop = (event, targetIndex) => {
  event.preventDefault();

  if (draggedIndex.value === null || draggedIndex.value === targetIndex) return;

  // Reorder images
  const draggedImage = images.value[draggedIndex.value];
  images.value.splice(draggedIndex.value, 1);
  images.value.splice(targetIndex, 0, draggedImage);
  console.log("kéo từ  ", draggedIndex.value, "qua ", targetIndex);

  draggedIndex.value = null;
  updateParent();
  // Emit primary changed if first image changed
  emit("primary-changed", images.value[0]);
};

// Preview
const previewImage = (image) => {
  previewModal.value = {
    show: true,
    image,
  };
};

const closePreview = () => {
  previewModal.value.show = false;
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
  emit("update:images", images.value);
  if (images.value.length > 0) {
    emit("primary-changed", images.value[0]);
  }
};

// Load từ danh sách URLs
const loadFromUrls = (urlList) => {
  if (!Array.isArray(urlList) || urlList.length === 0) {
    console.warn("Không có ảnh để tải !");
    return;
  }

  try {
    // Convert URLs thành image objects
    const loadedImages = urlList.map((url, index) => ({
      id: Date.now() + index + Math.random(),
      name: url.split("/").pop() || `Image ${index + 1}`,
      size: 0,
      url: url,
      cloudinaryUrl: url,
      publicId: url.includes("cloudinary") ? url.split("/").pop().split(".")[0] : null,
      isUrl: !url.includes("cloudinary"),
      uploading: false,
    }));

    // Thay thế danh sách images hiện tại
    images.value = loadedImages;
    console.log(`Đã load ${loadedImages.length} ảnh từ danh sách URLs`);
    return loadedImages;
  } catch (error) {
    console.error("Load images from URLs failed:", error);
    alert("Không thể tải ảnh từ danh sách URLs.");
    return [];
  }
};

// Expose methods for parent component
defineExpose({
  addFiles,
  clearImages: () => {
    images.value.forEach((image) => {
      // Giải phóng object URL nếu có
      if (image.url && !image.isUrl && !image.cloudinaryUrl) {
        URL.revokeObjectURL(image.url);
      }
    });
    images.value = [];
    updateParent();
  },
  getImages: () => images.value,
  getCloudinaryImages: () => images.value.filter((img) => img.cloudinaryUrl), // Chỉ lấy ảnh đã upload lên Cloudinary
  getImageUrls: () => images.value.map((img) => img.cloudinaryUrl || img.url), // Lấy tất cả URL

  // Load methods
  loadFromUrls, // Load từ danh sách URLs đơn giản
  loadImagesFromProduct, // Load ảnh từ backend theo productId
  loadImagesAndReplace, // Load ảnh từ danh sách URLs và thay thế
  uploadSingleImage, // Upload ảnh đơn lẻ
  uploadAllImages, // Upload tất cả ảnh chưa upload
});
</script>

<style scoped>
.upload-products-container {
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

.file-info {
  margin-top: 16px;
  color: #9ca3af;
}

/* Image Gallery */
.image-gallery {
  width: 100%;
}

.gallery-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px; /* Tăng khoảng cách dưới header */
}

.image-count {
  display: flex;
  align-items: center;
  gap: 12px;
}

.count-badge {
  background: #667eea;
  color: white;
  padding: 4px 8px;
  border-radius: 6px;
  font-size: 12px;
  font-weight: 600;
}

.primary-label {
  color: #6b7280;
  font-size: 14px;
}

.add-more-btn {
  background: #f3f4f6;
  color: #374151;
  border: 1px solid #d1d5db;
  padding: 8px 16px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
}

.add-more-btn:hover {
  background: #e5e7eb;
  border-color: #9ca3af;
}

.header-actions {
  display: flex;
  gap: 8px;
  align-items: center;
}

.upload-all-btn {
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
}

.upload-all-btn:hover:not(:disabled) {
  background: linear-gradient(135deg, #059669, #047857);
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(16, 185, 129, 0.4);
}

.upload-all-btn:disabled {
  background: #9ca3af;
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

.load-images-btn {
  background: linear-gradient(135deg, #3b82f6, #1d4ed8);
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  font-weight: 500;
}

.load-images-btn:hover:not(:disabled) {
  background: linear-gradient(135deg, #1d4ed8, #1e40af);
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.4);
}

.load-images-btn:disabled {
  background: #9ca3af;
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

/* Sortable Grid */
.sortable-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 45px; /* Tăng khoảng cách để ảnh không bị dính */
}

.image-item {
  position: relative;
  border: 2px solid transparent;
  border-radius: 12px;
  overflow: hidden;
  transition: all 0.3s ease;
  cursor: grab;
  background: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  height: auto; /* Để khung tự động điều chỉnh */
}

.image-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

.image-item.is-primary {
  border-color: #fbbf24;
  box-shadow: 0 0 0 2px rgba(251, 191, 36, 0.2);
}

.image-item.is-dragging {
  opacity: 0.5;
  transform: rotate(5deg);
}

.image-item:active {
  cursor: grabbing;
}

.primary-badge {
  position: absolute;
  top: 8px;
  left: 8px;
  background: linear-gradient(135deg, #fbbf24, #f59e0b);
  color: #92400e;
  padding: 4px 8px;
  border-radius: 6px;
  font-size: 11px;
  font-weight: 600;
  z-index: 5;
  display: flex;
  align-items: center;
  gap: 4px;
}

.image-wrapper {
  position: relative;
  width: 100%;
  height: 0;
  /* padding-bottom được set động qua :style binding */
  overflow: hidden;
  background: #f3f4f6;
  border-radius: 8px 8px 0 0; /* Chỉ bo góc trên */
  flex-shrink: 0; /* Không cho phép co lại */
}

.image-wrapper img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
  transition: transform 0.3s ease;
}

.image-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
  opacity: 0;
  transition: opacity 0.3s ease;
  z-index: 2;
}

.image-item:hover .image-overlay {
  opacity: 1;
}

.control-btn {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
  font-size: 14px;
}

.preview-btn {
  background: rgba(255, 255, 255, 0.9);
  color: #374151;
}

.preview-btn:hover {
  background: white;
  transform: scale(1.1);
}

.delete-btn {
  background: rgba(239, 68, 68, 0.9);
  color: white;
}

.delete-btn:hover {
  background: #ef4444;
  transform: scale(1.1);
}

.upload-btn {
  background: rgba(16, 185, 129, 0.9);
  color: white;
}

.upload-btn:hover {
  background: #10b981;
  transform: scale(1.1);
}

.drag-handle {
  position: absolute;
  top: 8px;
  right: 8px;
  color: rgba(255, 255, 255, 0.8);
  font-size: 16px;
  cursor: grab;
  padding: 4px;
  border-radius: 4px;
  background: rgba(0, 0, 0, 0.3);
  z-index: 3;
}

.drag-handle:active {
  cursor: grabbing;
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
  gap: 8px;
  color: #6b7280;
  font-size: 14px;
  z-index: 4;
}

.spinner {
  width: 24px;
  height: 24px;
  border: 2px solid #e5e7eb;
  border-top: 2px solid #667eea;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.image-info {
  padding: 12px;
  background: #f9fafb;
  display: flex;
  flex-direction: column;
  gap: 4px;
  border-radius: 0 0 8px 8px; /* Chỉ bo góc dưới */
  flex: 1; /* Chiếm không gian còn lại */
}

.image-name {
  font-weight: 500;
  color: #374151;
  font-size: 14px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.image-size {
  color: #6b7280;
  font-size: 12px;
}

.upload-status {
  font-size: 11px;
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 4px;
  margin-top: 4px;
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

/* Add More Slot */
.add-more-slot {
  width: 100%;
  height: 0;
  /* padding-bottom được set động qua :style binding */
  border: 2px dashed #d1d5db;
  border-radius: 12px;
  position: relative;
  cursor: pointer;
  transition: all 0.3s ease;
  background: #f9fafb;
  display: flex;
  align-items: center;
  justify-content: center;
}

.add-more-slot::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}

.add-more-slot .add-content {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  color: #9ca3af;
  z-index: 1;
}

.add-more-slot:hover {
  border-color: #667eea;
  background: #eef2ff;
}

.add-more-slot:hover .add-content {
  color: #667eea;
}

.add-more-slot .add-content i {
  font-size: 24px;
}

/* URL Upload */
.url-upload-section {
  margin-top: 32px;
  padding-top: 24px;
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
  max-width: 80vw;
  max-height: 80vh;
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
  max-height: 70vh;
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
  .sortable-grid {
    grid-template-columns: repeat(auto-fill, minmax(140px, 1fr));
    gap: 34px; /* Khoảng cách thoáng cho tablet */
  }

  .upload-products-container {
    padding: 16px;
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

  /* Aspect ratio được set động qua :style binding - không cần CSS cố định */
}

@media (max-width: 480px) {
  .sortable-grid {
    grid-template-columns: repeat(2, 1fr);
    gap: 24px; /* Khoảng cách đủ cho mobile */
  }

  .gallery-header {
    flex-direction: column;
    gap: 12px;
    align-items: stretch;
  }

  .header-actions {
    flex-direction: column;
    gap: 8px;
  }

  .add-more-btn,
  .upload-all-btn,
  .load-images-btn {
    justify-content: center;
  }

  /* Aspect ratio được set động qua :style binding - không cần CSS cố định */

  .drop-zone {
    padding: 20px 12px;
  }

  .upload-products-container {
    padding: 12px;
  }
}
</style>
