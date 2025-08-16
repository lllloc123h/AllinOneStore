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
                  class="control-btn crop-btn"
                  @click.prevent="cropExistingImage(index)"
                  v-if="image.needsCrop && !image.uploading && !image.isUrl"
                  title="Crop ảnh"
                >
                  <i class="bi bi-crop"></i>
                </button>
                <button
                  type="button"
                  class="control-btn upload-btn"
                  @click.prevent="uploadSingleImage(index)"
                  v-if="
                    !image.uploading &&
                    !image.cloudinaryUrl &&
                    !image.isUrl &&
                    image.isReadyToUpload
                  "
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
              <span v-else-if="image.needsCrop" class="upload-status needs-crop">
                <i class="bi bi-crop"></i> Cần crop
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

    <!-- Crop Modal -->
    <div v-if="cropModal.show" class="crop-modal" @click="closeCropModal">
      <div class="crop-content" @click.stop>
        <div class="crop-header">
          <h4 class="crop-title">
            <i class="bi bi-crop me-2"></i>
            Crop ảnh theo tỷ lệ {{ props.aspectRatio }}
          </h4>
          <button type="button" class="close-btn" @click.prevent="closeCropModal">
            <i class="bi bi-x-lg"></i>
          </button>
        </div>

        <div class="crop-body">
          <div class="crop-container">
            <img
              ref="cropperRef"
              :src="cropModal.originalUrl"
              style="max-width: 100%; height: 400px"
            />
          </div>

          <div class="crop-info">
            <div class="info-item">
              <i class="bi bi-info-circle me-2"></i>
              <span
                >Kéo và thả để di chuyển ảnh, kéo góc để thay đổi kích thước vùng
                crop</span
              >
            </div>
            <div class="info-item">
              <i class="bi bi-aspect-ratio me-2"></i>
              <span>Tỷ lệ được cố định theo yêu cầu: {{ props.aspectRatio }}</span>
            </div>
          </div>
        </div>

        <div class="crop-footer">
          <button type="button" class="btn btn-secondary" @click.prevent="skipCrop">
            <i class="bi bi-x-lg me-2"></i>
            Hủy
          </button>
          <button type="button" class="btn btn-primary" @click.prevent="applyCrop">
            <i class="bi bi-check-lg me-2"></i>
            Áp dụng Crop
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick, computed } from "vue";
import uploadAPI from "../../Configs/upload-api";
import Cropper from "cropperjs";
import "cropperjs/dist/cropper.css";

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

const emit = defineEmits(["update:images", "primary-changed", "delete-image", "uploaded"]);

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

// Crop Modal Data
const cropModal = ref({
  show: false,
  originalFile: null,
  originalUrl: null,
  replacingIndex: undefined,
});

const cropperRef = ref(null);
const cropperInstance = ref(null);

// Computed
const hasUnuploadedImages = computed(() => {
  return images.value.some(
    (img) => !img.cloudinaryUrl && !img.isUrl && !img.uploading && img.isReadyToUpload
  );
});

// Tính toán padding-bottom dựa vào aspectRatio
const aspectRatioPadding = computed(() => {
  const [width, height] = props.aspectRatio.split(":").map(Number);
  return `${(height / width) * 100}%`;
});

// Tính toán aspect ratio number cho cropper
const aspectRatioNumber = computed(() => {
  const [width, height] = props.aspectRatio.split(":").map(Number);
  return width / height;
});

// Check image aspect ratio
const checkImageAspectRatio = (file) => {
  return new Promise((resolve) => {
    const img = new Image();
    img.onload = () => {
      const imageRatio = img.width / img.height;
      const expectedRatio = aspectRatioNumber.value;
      const tolerance = 0.1; // Cho phép sai số 10%

      const isCorrectRatio = Math.abs(imageRatio - expectedRatio) <= tolerance;
      resolve({
        isCorrect: isCorrectRatio,
        currentRatio: imageRatio,
        expectedRatio: expectedRatio,
        width: img.width,
        height: img.height,
      });
    };
    img.src = URL.createObjectURL(file);
  });
};

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
//1
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

  // Kiểm tra aspect ratio cho từng file và thêm vào danh sách
  for (const file of filesToAdd) {
    const aspectCheck = await checkImageAspectRatio(file);

    const imageObj = {
      id: Date.now() + Math.random(),
      file,
      name: file.name,
      size: file.size,
      url: URL.createObjectURL(file),
      uploading: false,
      cloudinaryUrl: null,
      publicId: null,
      needsCrop: !aspectCheck.isCorrect, // Đánh dấu ảnh cần crop
      isReadyToUpload: aspectCheck.isCorrect, // Chỉ ready khi đúng tỷ lệ
    };

    images.value.push(imageObj);
  }

  // updateParent();
};

// Upload functions
const uploadSingleImage = async (index) => {
  const image = images.value[index];
  if (
    !image ||
    image.uploading ||
    image.cloudinaryUrl ||
    image.isUrl ||
    !image.file ||
    !image.isReadyToUpload
  ) {
    if (!image.isReadyToUpload) {
      alert("Vui lòng crop ảnh trước khi upload!");
    }
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
  const uploadedUrls = images.value
    .filter((img) => img.cloudinaryUrl)
    .map((img) => img.cloudinaryUrl);

  emit("uploaded", uploadedUrls);
};

const uploadAllImages = async () => {
  const unuploadedImages = images.value
    .map((img, index) => ({ img, index }))
    .filter(
      ({ img }) =>
        !img.cloudinaryUrl &&
        !img.isUrl &&
        !img.uploading &&
        img.file &&
        img.isReadyToUpload
    );

  if (unuploadedImages.length === 0) {
    const needsCropCount = images.value.filter(
      (img) => img.needsCrop && !img.cloudinaryUrl && !img.isUrl
    ).length;
    if (needsCropCount > 0) {
      alert(`Có ${needsCropCount} ảnh cần crop trước khi upload!`);
    }
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
  const uploadedUrls = images.value
    .filter((img) => img.cloudinaryUrl)
    .map((img) => img.cloudinaryUrl);

  emit("uploaded", uploadedUrls);
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
      needsCrop: false, // URL images không cần crop
      isReadyToUpload: false, // URL images không cần upload
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
    const url = new URL(imageUrl.value);

    // Tạo một image element để kiểm tra aspect ratio
    const img = new Image();
    img.crossOrigin = "anonymous"; // Để tránh CORS issues

    await new Promise((resolve, reject) => {
      img.onload = resolve;
      img.onerror = reject;
      img.src = imageUrl.value;
    });

    // Kiểm tra aspect ratio
    const imageRatio = img.width / img.height;
    const expectedRatio = aspectRatioNumber.value;
    const tolerance = 0.1; // Cho phép sai số 10%
    const isCorrectRatio = Math.abs(imageRatio - expectedRatio) <= tolerance;

    const imageObj = {
      id: Date.now() + Math.random(),
      name: imageUrl.value.split("/").pop() || "URL Image",
      size: 0, // Không biết size từ URL
      url: imageUrl.value,
      cloudinaryUrl: imageUrl.value, // URL từ bên ngoài đã sẵn sàng
      publicId: null, // Không có publicId vì không upload lên Cloudinary
      isUrl: true,
      uploading: false,
      needsCrop: false, // URL images không cần crop vì đã có sẵn
      isReadyToUpload: false, // URL images không cần upload
    };

    images.value.push(imageObj);
    imageUrl.value = "";
    updateParent();

    // Thông báo nếu ảnh không đúng tỷ lệ
    if (!isCorrectRatio) {
      console.warn(
        `Ảnh từ URL có tỷ lệ ${imageRatio.toFixed(
          2
        )} khác với yêu cầu ${expectedRatio.toFixed(2)}`
      );
      // Có thể thêm notification ở đây nếu cần
    }
  } catch (error) {
    console.error("Lỗi khi thêm ảnh từ URL:", error);
    if (error.message.includes("Invalid URL")) {
      alert("URL không hợp lệ");
    } else {
      alert("Không thể tải ảnh từ URL này. Vui lòng kiểm tra URL và thử lại.");
    }
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
  // Emit primary changed if first image changed
  emit("primary-changed", images.value[0]);
};
//2
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

// Crop Functions
const showCropModal = (file, index) => {
  cropModal.value = {
    show: true,
    originalFile: file,
    originalUrl: URL.createObjectURL(file),
    replacingIndex: index,
  };

  // Wait for modal to render then init cropper
  nextTick(() => {
    initCropper();
  });
};

const closeCropModal = () => {
  // Destroy cropper instance
  if (cropperInstance.value) {
    cropperInstance.value.destroy();
    cropperInstance.value = null;
  }

  // Clean up URL
  if (cropModal.value.originalUrl) {
    URL.revokeObjectURL(cropModal.value.originalUrl);
  }

  cropModal.value = {
    show: false,
    originalFile: null,
    originalUrl: null,
    replacingIndex: undefined,
  };
};

const initCropper = () => {
  if (!cropperRef.value) return;

  const image = cropperRef.value;
  cropperInstance.value = new Cropper(image, {
    aspectRatio: aspectRatioNumber.value,
    viewMode: 1,
    dragMode: "move",
    autoCropArea: 0.8,
    restore: false,
    guides: true,
    center: true,
    highlight: false,
    cropBoxMovable: true,
    cropBoxResizable: true,
    toggleDragModeOnDblclick: false,
    responsive: true,
    background: false,
  });
};

const applyCrop = () => {
  if (!cropperInstance.value) return;

  const canvas = cropperInstance.value.getCroppedCanvas({
    width: 800,
    height: Math.round(800 / aspectRatioNumber.value),
    imageSmoothingEnabled: true,
    imageSmoothingQuality: "high",
  });

  canvas.toBlob(
    (blob) => {
      // Tạo file mới từ blob đã crop
      const croppedFile = new File([blob], cropModal.value.originalFile.name, {
        type: cropModal.value.originalFile.type,
      });

      // Thay thế ảnh đã crop
      replaceImageAfterCrop(cropModal.value.replacingIndex, croppedFile);

      // Đóng modal
      closeCropModal();
    },
    cropModal.value.originalFile.type,
    0.9
  );
};

const skipCrop = () => {
  // Chỉ đóng modal, không làm gì thêm
  closeCropModal();
};

// Replace image after crop
const replaceImageAfterCrop = (index, croppedFile) => {
  const oldImage = images.value[index];

  // Giải phóng URL cũ
  if (oldImage.url && !oldImage.isUrl && !oldImage.cloudinaryUrl) {
    URL.revokeObjectURL(oldImage.url);
  }

  // Thay thế bằng ảnh đã crop
  images.value[index] = {
    ...oldImage,
    file: croppedFile,
    name: croppedFile.name,
    size: croppedFile.size,
    url: URL.createObjectURL(croppedFile),
    cloudinaryUrl: null, // Reset upload status
    publicId: null,
    uploading: false,
    needsCrop: false, // Đã crop xong
    isReadyToUpload: true, // Sẵn sàng upload
  };
};

// Crop existing image
const cropExistingImage = (index) => {
  const image = images.value[index];
  if (!image || !image.file) return;

  showCropModal(image.file, index);
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
const loadFromUrls = async (urlList) => {
  if (!Array.isArray(urlList) || urlList.length === 0) {
    console.warn("Không có ảnh để tải !");
    return;
  }

  try {
    // Convert URLs thành image objects
    const loadedImages = await Promise.all(
      urlList.map(async (url, index) => {
        try {
          // Tạo image element để kiểm tra kích thước (optional, có thể skip cho performance)
          const img = new Image();
          img.crossOrigin = "anonymous";

          // Không await để không block, chỉ load để cache
          img.src = url;

          return {
            id: Date.now() + index + Math.random(),
            name: url.split("/").pop() || `Image ${index + 1}`,
            size: 0,
            url: url,
            cloudinaryUrl: url,
            publicId: url.includes("cloudinary")
              ? url.split("/").pop().split(".")[0]
              : null,
            isUrl: !url.includes("cloudinary"),
            uploading: false,
            needsCrop: false, // URL images không cần crop
            isReadyToUpload: false, // URL images không cần upload
          };
        } catch (error) {
          console.error(`Lỗi load ảnh từ URL ${url}:`, error);
          return null;
        }
      })
    );

    // Lọc bỏ các URL lỗi
    const validImages = loadedImages.filter((img) => img !== null);

    // Thay thế danh sách images hiện tại
    images.value = validImages;
    console.log(`Đã load ${validImages.length} ảnh từ danh sách URLs`);
    return validImages;
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

.crop-btn {
  background: rgba(245, 158, 11, 0.9);
  color: white;
}

.crop-btn:hover {
  background: #f59e0b;
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

.upload-status.needs-crop {
  color: #ef4444;
  font-weight: 600;
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

/* Crop Modal Styles */
.crop-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

.crop-content {
  background: white;
  border-radius: 12px;
  width: 90%;
  max-width: 800px;
  max-height: 90vh;
  overflow: hidden;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
  animation: slideUp 0.3s ease;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(20px) scale(0.95);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.crop-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  border-bottom: 1px solid #e9ecef;
  background: #f8f9fa;
}

.crop-title {
  margin: 0;
  color: #333;
  font-size: 1.2em;
  font-weight: 600;
}

.crop-body {
  padding: 20px;
  max-height: 60vh;
  overflow-y: auto;
}

.crop-container {
  margin-bottom: 20px;
  text-align: center;
  background: #f8f9fa;
  border-radius: 8px;
  padding: 20px;
}

.crop-info {
  background: #e7f3ff;
  border: 1px solid #b3d8ff;
  border-radius: 8px;
  padding: 15px;
}

.crop-info .info-item {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
  color: #0066cc;
  font-size: 0.9em;
}

.crop-info .info-item:last-child {
  margin-bottom: 0;
}

.crop-footer {
  padding: 20px;
  border-top: 1px solid #e9ecef;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  background: #f8f9fa;
}

.crop-footer .btn {
  padding: 10px 20px;
  border-radius: 6px;
  font-weight: 500;
  display: flex;
  align-items: center;
  transition: all 0.3s ease;
  border: none;
  cursor: pointer;
}

.crop-footer .btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

.crop-footer .btn-secondary {
  background: #6b7280;
  color: white;
}

.crop-footer .btn-secondary:hover {
  background: #4b5563;
}

.crop-footer .btn-primary {
  background: #3b82f6;
  color: white;
}

.crop-footer .btn-primary:hover {
  background: #2563eb;
}

/* Mobile Responsive for Crop Modal */
@media (max-width: 768px) {
  .crop-content {
    width: 95%;
    max-height: 95vh;
  }

  .crop-header {
    padding: 15px;
  }

  .crop-title {
    font-size: 1.1em;
  }

  .crop-body {
    padding: 15px;
    max-height: 50vh;
  }

  .crop-container {
    padding: 15px;
  }

  .crop-footer {
    padding: 15px;
    flex-direction: column;
    gap: 8px;
  }

  .crop-footer .btn {
    width: 100%;
    justify-content: center;
  }
}
</style>
