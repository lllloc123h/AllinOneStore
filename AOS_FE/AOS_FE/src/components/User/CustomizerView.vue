<template>
  <div class="customizer-container">
    <!-- Header -->
    <div class="customizer-header">
      <h2 class="header-title">
        <i class="bi bi-palette2"></i>
        Thiết kế sản phẩm
      </h2>
      <p class="header-subtitle">Tạo ra thiết kế độc đáo của riêng bạn</p>
    </div>

    <!-- Main Content -->
    <div class="customizer-content">
      <!-- Canvas Section -->
      <div class="canvas-section">
        <div class="canvas-container">
          <div class="canvas-wrapper">
            <canvas ref="canvasRef" width="560" height="700" class="design-canvas" />
          </div>
        </div>
      </div>

      <!-- Control Panel -->
      <div class="control-panel">
        <div class="panel-header">
          <h3>Bảng điều khiển</h3>
        </div>

        <!-- All Controls in One Panel -->
        <div class="controls-container">
          <!-- Text Controls Section -->
          <div class="control-section">
            <h4 class="section-title">
              <i class="bi bi-type"></i>
              Văn bản
            </h4>

            <button class="primary-btn" @click="addTextbox">
              <i class="bi bi-plus-circle"></i>
              Thêm textbox
            </button>

            <div class="form-grid">
              <div class="form-group">
                <label class="form-label">Màu chữ</label>
                <input
                  type="color"
                  v-model="textColor"
                  @input="updateActiveTextbox"
                  class="color-picker"
                />
              </div>

              <div class="form-group">
                <label class="form-label">Background</label>
                <div class="bg-controls">
                  <input
                    type="color"
                    v-model="bgColor"
                    @input="updateActiveTextbox"
                    class="color-picker small"
                    :disabled="isTransparent"
                  />
                  <label class="checkbox-wrapper">
                    <input
                      type="checkbox"
                      v-model="isTransparent"
                      @input="updateActiveTextbox"
                    />
                    <span class="checkmark"></span>
                    Trong suốt
                  </label>
                </div>
              </div>

              <div class="form-group">
                <label class="form-label">Định dạng</label>
                <div class="format-controls">
                  <button
                    class="format-btn"
                    :class="{ active: bold }"
                    @click="
                      bold = !bold;
                      updateActiveTextbox();
                    "
                  >
                    <i class="bi bi-type-bold"></i>
                  </button>
                  <button
                    class="format-btn"
                    :class="{ active: italic }"
                    @click="
                      italic = !italic;
                      updateActiveTextbox();
                    "
                  >
                    <i class="bi bi-type-italic"></i>
                  </button>
                  <button
                    class="format-btn"
                    :class="{ active: underline }"
                    @click="
                      underline = !underline;
                      updateActiveTextbox();
                    "
                  >
                    <i class="bi bi-type-underline"></i>
                  </button>
                </div>
              </div>

              <div class="form-group">
                <label class="form-label">Font chữ</label>
                <select
                  v-model="fontFamily"
                  @change="updateActiveTextbox"
                  class="form-select"
                >
                  <option>Helvetica</option>
                  <option>Arial</option>
                  <option>Courier</option>
                  <option>Georgia</option>
                  <option>Verdana</option>
                  <option>Impact</option>
                  <option>VT323</option>
                </select>
              </div>

              <div class="form-group">
                <label class="form-label">Cỡ chữ</label>
                <input
                  type="number"
                  v-model.number="fontSize"
                  min="10"
                  max="120"
                  @input="updateActiveTextbox"
                  class="form-input"
                />
              </div>

              <div class="form-group">
                <label class="form-label">Căn lề</label>
                <select
                  v-model="textAlign"
                  @change="updateActiveTextbox"
                  class="form-select"
                >
                  <option value="left">Trái</option>
                  <option value="center">Giữa</option>
                  <option value="right">Phải</option>
                  <option value="justify">Canh đều</option>
                </select>
              </div>
            </div>
          </div>

          <!-- Drawing Controls Section -->
          <div class="control-section">
            <h4 class="section-title">
              <i class="bi bi-brush"></i>
              Vẽ
            </h4>

            <button class="primary-btn" @click="startDrawingMode">
              <i class="bi bi-pencil"></i>
              {{ btnDraw ? "Hủy chế độ vẽ" : "Bật chế độ vẽ" }}
            </button>

            <button class="secondary-btn" @click="toggleEraser">
              <i class="bi bi-eraser"></i>
              {{ isErasing ? "Vẽ lại" : "Gôm" }}
            </button>

            <div class="form-grid">
              <div class="form-group">
                <label class="form-label">Chế độ vẽ</label>
                <select v-model="drawingMode" class="form-select">
                  <option value="Circle">Circle</option>
                  <option value="Pencil">Pencil</option>
                  <option value="Spray">Spray</option>
                  <option value="Pattern">Pattern</option>
                  <option value="hLine">Đường ngang</option>
                  <option value="vLine">Đường dọc</option>
                  <option value="square">Vuông</option>
                  <option value="diamond">Kim cương</option>
                  <option value="texture">Texture</option>
                </select>
              </div>

              <div class="form-group">
                <label class="form-label">Màu vẽ</label>
                <input type="color" v-model="drawingColor" class="color-picker" />
              </div>

              <div class="form-group">
                <label class="form-label">Độ dày</label>
                <input
                  type="number"
                  v-model="drawingLineWidth"
                  min="1"
                  max="50"
                  class="form-input"
                />
              </div>

              <div class="form-group">
                <label class="form-label">Đổ bóng</label>
                <input
                  type="number"
                  v-model="drawingShadowWidth"
                  min="0"
                  max="50"
                  class="form-input"
                />
              </div>

              <div class="form-group">
                <label class="form-label">Màu bóng</label>
                <input type="color" v-model="drawingShadowColor" class="color-picker" />
              </div>
            </div>
          </div>

          <!-- Action Controls Section -->
          <div class="control-section">
            <h4 class="section-title">
              <i class="bi bi-gear"></i>
              Hành động
            </h4>

            <div class="upload-section">
              <label for="image-upload" class="upload-btn">
                <i class="bi bi-cloud-upload"></i>
                Tải ảnh lên
              </label>
              <input
                id="image-upload"
                type="file"
                @change="handleImageUpload"
                class="file-input"
              />
            </div>

            <div class="form-group">
              <label class="form-label">Tên phác thảo</label>
              <input
                type="text"
                v-model="rawCanvasData.designName"
                class="form-input"
                placeholder="Nhập tên phác thảo..."
              />
            </div>

            <button class="success-btn" @click="saveCanvas">
              <i class="bi bi-save"></i>
              Lưu thiết kế
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, watch, computed, reactive } from "vue";
import { fabric } from "fabric";
import komiImage from "../../assets/imgs/komi.jpg";
import api from "../../Configs/api";
import uploadAPI from "../../Configs/upload-api";
import { notification } from "ant-design-vue";
import router from "../../router";

const props = defineProps({
  productItemId: {
    type: Number, // For create mode
  }, // For create mode
  customId: {
    type: Number, // For create mode
  }, // For update mode
  action: {
    type: String, // For create mode
  }, // 'create' or 'update'
});
const rawCanvasData = ref({
  id: null,
  accountId: null,
  canvasJson: "",
  imageUrl: null,
  createdAt: null,
  updatedAt: null,
  designName: "phác thảo 1",
  productItems: { id: props.productItemId },
  orderItem: null,
});
if (props.action === "create") {
  console.log("isCreateMode");
} else if (props.action === "update") {
  console.log("isUpdateMode");
} else {
  console.error("Invalid action prop:", props.action);
}

// Notification helpers
const showNotification = (type, message, description = "", duration = 4.5) => {
  notification[type]({
    message,
    description,
    duration,
    placement: "topRight",
  });
};

const showSuccess = (message, description = "") =>
  showNotification("success", message, description);
const showError = (message, description = "") =>
  showNotification("error", message, description);
const showInfo = (message, description = "") =>
  showNotification("info", message, description);

// Canvas
const canvasRef = ref();
let canvas;
let vLinePatternBrush,
  hLinePatternBrush,
  squarePatternBrush,
  diamondPatternBrush,
  texturePatternBrush,
  patternBrush;

// Control values
const textColor = ref("#000000");
const fontFamily = ref("Helvetica");
const fontSize = ref(24);
const textAlign = ref("left");
const exportedJson = ref("");
const bold = ref(false);
const italic = ref(false);
const underline = ref(false);
const bgColor = ref("#10C6C3");
const isTransparent = ref(true);
const drawingMode = ref("Pencil");
const drawingColor = ref("#000000");
const drawingLineWidth = ref(15);
const drawingShadowWidth = ref(0);
const drawingShadowColor = ref("#000000");
const btnDraw = ref(false);
const exportedImage = ref(null);
const isErasing = ref(false);
const isUploading = ref(false);
const cloudinaryUrl = ref(null);

function startDrawingMode() {
  canvas.isDrawingMode = !canvas.isDrawingMode;
  btnDraw.value = canvas.isDrawingMode;
}

watch(btnDraw, (val) => {
  canvas.isDrawingMode = val;
  if (val) mode();
});
watch(
  [drawingMode, drawingColor, drawingLineWidth, drawingShadowWidth, drawingShadowColor],
  () => {
    if (canvas.isDrawingMode) {
      mode();
    }
  }
);

function mode() {
  const brushName = drawingMode.value;
  const patternBrushMap = {
    Pattern: patternBrush, // 👈 Thêm dòng này
    hLine: hLinePatternBrush,
    vLine: vLinePatternBrush,
    square: squarePatternBrush,
    diamond: diamondPatternBrush,
    texture: texturePatternBrush,
  };

  let brush;

  if (brushName === "texture" && texturePatternBrush) {
    brush = texturePatternBrush;
  } else if (patternBrushMap[brushName]) {
    brush = patternBrushMap[brushName];

    if (brushName !== "texture" && typeof brush.getPatternSrcFunction === "function") {
      brush.source = brush.getPatternSrcFunction();
    }
  } else if (fabric[`${brushName}Brush`]) {
    brush = new fabric[`${brushName}Brush`](canvas);
  } else {
    console.warn(`Không tìm thấy brush phù hợp cho: ${brushName}`);
    return;
  }

  brush.color = drawingColor.value;
  brush.width = drawingLineWidth.value;
  brush.shadow = new fabric.Shadow({
    blur: drawingShadowWidth.value,
    offsetX: 0,
    offsetY: 0,
    affectStroke: true,
    color: drawingShadowColor.value,
  });

  canvas.freeDrawingBrush = brush;
  if (
    drawingMode.value !== "texture" &&
    patternBrushMap[brushName] &&
    typeof patternBrushMap[brushName].getPatternSrcFunction === "function"
  ) {
    patternBrushMap[brushName].source = patternBrushMap[
      brushName
    ].getPatternSrcFunction();
  }
}
5;
onMounted(() => {
  canvas = new fabric.Canvas(canvasRef.value);
  canvas.hoverCursor = "pointer";
  if (props.action === "create") {
    api.get(`/ProductItems/image/default/${props.productItemId}`).then((response) => {
      const productItem = response.data;
      console.log("productItem", productItem.content);
      console.log("productItem    ", productItem.content[0].imageUrl);
      if (productItem) {
        fabric.Image.fromURL(
          productItem.content[0].imageUrl,
          (img) => {
            // Tính scale để không méo ảnh
            const canvasW = canvas.getWidth();
            const canvasH = canvas.getHeight();
            const scale = Math.min(canvasW / img.width, canvasH / img.height);
            img.scale(scale);

            // Căn giữa ảnh trong canvas
            img.set({
              left: (canvasW - img.width * scale) / 2,
              top: (canvasH - img.height * scale) / 2,
              originX: "left",
              originY: "top",
            });

            canvas.setBackgroundImage(img, canvas.renderAll.bind(canvas));
          },
          { crossOrigin: "anonymous" }
        );
      }
    });
  } else if (props.action === "update") {
    api.get(`/customs/${props.customId}`).then((response) => {
      rawCanvasData.value = response.data;
      console.log(" load rawCanvasData", rawCanvasData.value);
      if (rawCanvasData.value.canvasJson) {
        loadCanvas(rawCanvasData.value.canvasJson);
      } else {
        console.error("Không có dữ liệu để tải canvas");
      }
    });
  }

  // fabric.Image.fromURL("/src/assets/imgs/ao_bomber_nu.webp", (img) => {
  //   // Tính scale để không méo ảnh
  //   const canvasW = canvas.getWidth();
  //   const canvasH = canvas.getHeight();
  //   const scale = Math.min(canvasW / img.width, canvasH / img.height);
  //   img.scale(scale);

  //   // Căn giữa ảnh trong canvas
  //   img.set({
  //     left: (canvasW - img.width * scale) / 2,
  //     top: (canvasH - img.height * scale) / 2,
  //     originX: "left",
  //     originY: "top",
  //   });

  //   canvas.setBackgroundImage(img, canvas.renderAll.bind(canvas));
  // });
  fabric.Object.prototype.set({
    transparentCorners: false,
    cornerColor: "blue",
    cornerStrokeColor: "#fff",
    cornerSize: 8,
    cornerStyle: "circle",
    borderColor: "#3f51b5",
    rotatingPointOffset: 30,
  });
  fabric.Object.prototype.setControlsVisibility({});
  // PatternBrushes
  if (fabric.PatternBrush) {
    patternBrush = new fabric.PatternBrush(canvas);
    patternBrush.getPatternSrcFunction = function () {
      const dotWidth = 20;
      const dotDistance = 5;
      const patternCanvas = fabric.util.createCanvasElement();
      const patternCtx = patternCanvas.getContext("2d");

      patternCanvas.width = patternCanvas.height = dotWidth + dotDistance;
      patternCtx.fillStyle = this.color;
      patternCtx.beginPath();
      patternCtx.arc(dotWidth / 2, dotWidth / 2, dotWidth / 2, 0, Math.PI * 2, false);
      patternCtx.closePath();
      patternCtx.fill();
      return patternCanvas;
    };
    // HORIZONTAL LINE (hline)
    hLinePatternBrush = new fabric.PatternBrush(canvas);
    hLinePatternBrush.getPatternSrcFunction = function () {
      const patternCanvas = document.createElement("canvas");
      patternCanvas.width = patternCanvas.height = 10;
      const ctx = patternCanvas.getContext("2d");
      ctx.strokeStyle = this.color;
      ctx.lineWidth = 5;
      ctx.beginPath();
      ctx.moveTo(0, 5);
      ctx.lineTo(10, 5);
      ctx.stroke();
      return patternCanvas;
    };

    // VERTICAL LINE (vline)
    vLinePatternBrush = new fabric.PatternBrush(canvas);
    vLinePatternBrush.getPatternSrcFunction = function () {
      const patternCanvas = document.createElement("canvas");
      patternCanvas.width = patternCanvas.height = 10;
      const ctx = patternCanvas.getContext("2d");
      ctx.strokeStyle = this.color;
      ctx.lineWidth = 5;
      ctx.beginPath();
      ctx.moveTo(5, 0);
      ctx.lineTo(5, 10);
      ctx.stroke();
      return patternCanvas;
    };

    // SQUARE
    squarePatternBrush = new fabric.PatternBrush(canvas);
    squarePatternBrush.getPatternSrcFunction = function () {
      const squareWidth = 10;
      const squareDistance = 2;
      const patternCanvas = document.createElement("canvas");
      patternCanvas.width = patternCanvas.height = squareWidth + squareDistance;
      const ctx = patternCanvas.getContext("2d");
      ctx.fillStyle = this.color;
      ctx.fillRect(0, 0, squareWidth, squareWidth);
      return patternCanvas;
    };

    // DIAMOND
    diamondPatternBrush = new fabric.PatternBrush(canvas);
    diamondPatternBrush.getPatternSrcFunction = function () {
      const squareWidth = 10;
      const squareDistance = 5;
      const rect = new fabric.Rect({
        width: squareWidth,
        height: squareWidth,
        angle: 45,
        fill: this.color,
      });
      const canvasWidth = rect.getBoundingRect().width;
      const patternCanvas = document.createElement("canvas");
      patternCanvas.width = patternCanvas.height = canvasWidth + squareDistance;
      rect.set({
        left: canvasWidth / 2,
        top: canvasWidth / 2,
        originX: "center",
        originY: "center",
      });
      const ctx = patternCanvas.getContext("2d");
      const tempCanvas = new fabric.StaticCanvas(null, {
        width: patternCanvas.width,
        height: patternCanvas.height,
      });
      tempCanvas.add(rect);
      tempCanvas.renderAll();
      ctx.drawImage(tempCanvas.lowerCanvasEl, 0, 0);
      return patternCanvas;
    };
    // TEXTURE
    const img = new Image();
    img.onload = () => {
      texturePatternBrush = new fabric.PatternBrush(canvas);
      texturePatternBrush.source = img;
      if (drawingMode.value === "texture" && canvas.isDrawingMode) {
        mode();
      }
    };

    img.src = komiImage;
  }

  // Add default shape
  const rect = new fabric.Rect({
    left: 100,
    top: 100,
    width: 150,
    height: 100,
    fill: "#4caf50",
  });
  canvas.add(rect);

  // Events
  canvas.on("text:changed", updateFormFromObject);
  canvas.on("text:selection:changed", updateFormFromObject);
  // canvas.on("selection:updated", updateFormFromObject); // 👈 Thêm dòng này

  window.addEventListener("keydown", handleDeleteKey);
});

onBeforeUnmount(() => {
  window.removeEventListener("keydown", handleDeleteKey);
});

function handleDeleteKey(e) {
  // Không xử lý nếu đang focus trong input hoặc textarea (HTML)
  const tag = document.activeElement.tagName;
  if (tag === "INPUT" || tag === "TEXTAREA") return;

  // Nếu đang sửa văn bản trong fabric.Textbox (đang focus)
  const activeObj = canvas.getActiveObject();
  if (
    activeObj &&
    activeObj.isEditing && // đang sửa nội dung textbox
    activeObj.type === "textbox"
  ) {
    return;
  }

  if (e.key === "Delete" || e.key === "Backspace") {
    if (activeObj) {
      if (activeObj.type === "activeSelection") {
        activeObj.forEachObject((obj) => canvas.remove(obj));
      } else {
        canvas.remove(activeObj);
      }
      canvas.discardActiveObject().requestRenderAll();
    }
  }
}
// Hàm xử lý khi chọn ảnh
function handleImageUpload(event) {
  const file = event.target.files[0];
  if (!file) return;

  const reader = new FileReader();
  reader.onload = (e) => {
    fabric.Image.fromURL(e.target.result, (img) => {
      const canvasWidth = canvas.getWidth();
      const targetWidth = canvasWidth * 0.5; // 50% chiều rộng canvas
      const scale = targetWidth / img.width;

      img.scale(scale);
      img.set({
        left: (canvasWidth - img.getScaledWidth()) / 2, // căn giữa
        top: 100,
        selectable: true,
      });

      canvas.add(img);
    });
  };
  reader.readAsDataURL(file);
}
// Xuất ảnh từ canvas
function exportImage() {
  return new Promise((resolve, reject) => {
    try {
      isUploading.value = true;

      const dataURL = canvas.toDataURL({
        format: "png",
        quality: 1.0, // chất lượng 1.0 là cao nhất
        multiplier: 2,
      });
      exportedImage.value = dataURL;

      // Chuyển dataURL thành File để upload
      const canvas2 = document.createElement("canvas");
      const ctx = canvas2.getContext("2d");
      const img = new Image();

      img.onload = async () => {
        canvas2.width = img.width;
        canvas2.height = img.height;
        ctx.drawImage(img, 0, 0);
        canvas2.toBlob(
          async (blob) => {
            try {
              // Tạo File từ blob
              const file = new File([blob], `canvas-design-${Date.now()}.png`, {
                type: "image/png",
              });
              // Upload lên Cloudinary
              const uploadResult = await uploadAPI.upload.uploadSingleImageOrVideo(
                file,
                "custom-designs" // folder name trên Cloudinary
              );
              cloudinaryUrl.value = uploadResult.url;
              console.log("✅ Đã upload ảnh lên Cloudinary:", uploadResult.url);
              resolve(uploadResult.url); // Resolve Promise với URL
            } catch (error) {
              console.error("❌ Lỗi upload lên Cloudinary:", error);
              showError(
                "Upload thất bại",
                "Không thể upload ảnh lên Cloudinary. Vui lòng thử lại."
              );
              reject(error); // Reject Promise nếu lỗi
            } finally {
              isUploading.value = false;
            }
          },
          "image/png",
          0.9
        );
      };

      img.onerror = () => {
        console.error("❌ Lỗi load ảnh từ canvas");
        showError("Xuất ảnh thất bại", "Không thể xuất ảnh. Vui lòng thử lại.");
        isUploading.value = false;
        reject(new Error("Lỗi load ảnh từ canvas"));
      };

      img.src = dataURL;
    } catch (error) {
      console.error("❌ Lỗi xuất ảnh:", error);
      showError(
        "Lỗi xuất ảnh",
        "Không thể xuất ảnh. Vui lòng thử lại hoặc tải lại trang."
      );
      isUploading.value = false;
      reject(error);
    }
  });
}

// Copy URL to clipboard
const copyToClipboard = async (text) => {
  try {
    if (navigator.clipboard && navigator.clipboard.writeText) {
      await navigator.clipboard.writeText(text);
      showSuccess("Copy thành công!", "URL đã được copy vào clipboard");
    } else {
      // Fallback cho browser cũ
      const textArea = document.createElement("textarea");
      textArea.value = text;
      document.body.appendChild(textArea);
      textArea.select();
      document.execCommand("copy");
      document.body.removeChild(textArea);
      showSuccess("Copy thành công!", "URL đã được copy vào clipboard");
    }
  } catch (error) {
    console.error("❌ Lỗi copy clipboard:", error);
    showError("Copy thất bại", "Không thể copy URL. Vui lòng copy thủ công.");
  }
};

function saveCanvas() {
  try {
    const json = canvas.toJSON();
    rawCanvasData.value.canvasJson = JSON.stringify(json);

    // Đợi exportImage() hoàn thành và có URL
    exportImage()
      .then((imageUrl) => {
        if (imageUrl) {
          rawCanvasData.value.imageUrl = imageUrl;
          console.log(" dữ liệu tải lên ", props.action, rawCanvasData.value);
          api
            .post("/customs/save", rawCanvasData.value)
            .then((response) => {
              console.log("Đã lưu canvas thành công:", response.data);
              if (props.action === "create") {
                showSuccess("Lưu thành công", "Canvas đã được lưu thành công.");
              } else if (props.action === "update") {
                showSuccess("Cập nhật thành công", "Canvas đã được cập nhật thành công.");
              }
              router.push({ name: "custom" });
            })
            .catch((error) => {
              console.error("Lỗi lưu canvas:", error);
              showError("Lỗi lưu canvas", "Không thể lưu canvas. Vui lòng thử lại.");
            });
        } else {
          showError("Lỗi lưu canvas", "Không thể upload ảnh. Vui lòng thử lại.");
        }
      })
      .catch((error) => {
        console.error("Lỗi export ảnh:", error);
        showError("Lỗi xuất ảnh", "Không thể xuất ảnh. Vui lòng thử lại.");
      });
  } catch (error) {
    console.error("Lỗi khi lưu canvas:", error);
    showError("Lỗi lưu canvas", "Không thể lưu canvas. Vui lòng thử lại.");
  }

  // exportedJson.value = JSON.stringify(json);
}

function loadCanvas(json) {
  canvas.loadFromJSON(JSON.parse(json), () => {
    canvas.renderAll();
  });
}
// Thêm textbox
function addTextbox() {
  const textbox = new fabric.Textbox("Nhập chữ ở đây", {
    left: 100,
    top: 100,
    width: 250,
    fill: textColor.value,
    fontFamily: fontFamily.value,
    fontSize: fontSize.value,
    textAlign: textAlign.value,
    fontWeight: bold.value ? "bold" : "",
    fontStyle: italic.value ? "italic" : "normal",
    underline: underline.value,
    textBackgroundColor: null,
    splitByGrapheme: true, // BẮT BUỘC để style từng ký tự
  });

  canvas.add(textbox).setActiveObject(textbox);
  var finalBgColor = "";
  if (isTransparent.value) {
    finalBgColor = null;
  } else {
    finalBgColor = bgColor.value;
  }

  // Set inline style cho toàn bộ ký tự
  const styles = {
    fill: textColor.value,
    fontFamily: fontFamily.value,
    fontSize: fontSize.value,
    fontWeight: bold.value ? "bold" : "",
    fontStyle: italic.value ? "italic" : "",
    underline: underline.value,
    textBackgroundColor: finalBgColor,
  };

  for (let i = 0; i < textbox.text.length; i++) {
    textbox.setSelectionStyles(styles, i, i + 1);
  }

  canvas.requestRenderAll();
}

// Cập nhật textbox đang chọn
function updateActiveTextbox() {
  const obj = canvas.getActiveObject();
  if (obj && obj.type === "textbox") {
    const start = obj.selectionStart;
    const end = obj.selectionEnd;
    var finalBgColor = "";
    if (isTransparent.value) {
      finalBgColor = null;
    } else {
      finalBgColor = bgColor.value;
    }
    const newStyle = {
      fill: textColor.value,
      fontFamily: fontFamily.value,
      fontSize: fontSize.value,
      fontWeight: bold.value ? "bold" : "",
      fontStyle: italic.value ? "italic" : "",
      underline: underline.value,
      textBackgroundColor: finalBgColor,
    };

    if (start === end) {
      // Không có vùng chọn → cập nhật từng ký tự + style mặc định
      const fullLength = obj.text.length;
      for (let i = 0; i < fullLength; i++) {
        obj.set({
          textAlign: textAlign.value,
        });
        obj.setSelectionStyles(newStyle, i, i + 1);
      }

      // 🔥 Cập nhật style mặc định cho ký tự mới gõ vào
      obj.set({
        fill: newStyle.fill,
        fontFamily: newStyle.fontFamily,
        fontSize: newStyle.fontSize,
        fontWeight: newStyle.fontWeight,
        fontStyle: newStyle.fontStyle,
        underline: newStyle.underline,
        textBackgroundColor: newStyle.textBackgroundColor,
      });
      console.log("requestRenderAll ", textAlign.value);
      canvas.requestRenderAll();
    } else {
      obj.set({
        textAlign: textAlign.value,
      });
      for (let i = start; i < end; i++) {
        obj.setSelectionStyles(newStyle, i, i + 1);
      }
    }
    obj.set({
      textAlign: textAlign.value,
    });
    canvas.requestRenderAll();
  }
}

// Khi chọn object thì cập nhật form control
function updateFormFromObject() {
  const obj = canvas.getActiveObject();
  if (!obj || obj.type !== "textbox") return;

  const start = obj.selectionStart ?? 0;
  const end = obj.selectionEnd ?? start;

  let styles;
  console.log("color ", textColor.value);
  console.log("family ", fontFamily.value);
  console.log("size ", fontSize.value);
  console.log("align ", textAlign.value);
  console.log("bold ", bold.value);
  console.log("updateFormFromObject styles", styles);
  if (start !== end) {
    // Trường hợp có vùng chọn
    const selectedStyles = obj.getSelectionStyles(start, end);
    console.log("start != end");

    styles = selectedStyles[0]; // lấy style của ký tự đầu tiên
  } else if (start > 0) {
    // Không có vùng chọn, nhưng con trỏ > 0
    styles = obj.getSelectionStyles(start - 1, start)[0];
    console.log("start > 0");
    console.error("kh co vung chon");
  } else {
    console.log("start = 0");

    // Nếu không có style riêng → fallback style toàn textbox
    styles = styles || {
      fill: obj.fill,
      fontFamily: obj.fontFamily,
      fontSize: obj.fontSize,
      fontWeight: obj.fontWeight,
      fontStyle: obj.fontStyle,
      underline: obj.underline,
      textBackgroundColor: obj.textBackgroundColor,
    };
  }
  console.log("start ", start);

  // Cập nhật UI
  textColor.value = styles.fill || "#000000";
  fontFamily.value = styles.fontFamily || "Helvetica";
  fontSize.value = styles.fontSize || 24;
  textAlign.value = obj.textAlign || "left";
  bold.value = styles.fontWeight === "bold";
  italic.value = styles.fontStyle === "italic";
  underline.value = !!styles.underline;
  if (styles.textBackgroundColor == null) {
    isTransparent.value = true;
  } else {
    isTransparent.value = false;
    bgColor.value = styles.textBackgroundColor;
  }
  console.error("color ", textColor.value);
  console.error("family ", fontFamily.value);
  console.error("size ", fontSize.value);
  console.error("align ", textAlign.value);
  console.error("bold ", bold.value);
  console.error("bg ", bgColor.value);
  console.error("updateFormFromObject styles", styles);
}
</script>

<style scoped>
/* Container */
.customizer-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
  padding-bottom: 100px;
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
}

/* Header */
.customizer-header {
  text-align: center;
  margin-bottom: 30px;
  color: white;
}

.header-title {
  font-size: 2.5rem;
  font-weight: 700;
  margin: 0 0 10px 0;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;
}

.header-subtitle {
  font-size: 1.2rem;
  opacity: 0.9;
  margin: 0;
}

/* Main Content */
.customizer-content {
  display: grid;
  grid-template-columns: 1fr 650px;
  gap: 15px;
  max-width: 1800px;
  margin: 0 auto;
}

/* Canvas Section */
.canvas-section {
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  padding-top: 10px;
}

.canvas-container {
  background: white;
  border-radius: 20px;
  padding: 20px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
}

.canvas-wrapper {
  position: relative;
  border-radius: 15px;
  overflow: hidden;
}

.design-canvas {
  display: block;
  border: 2px dashed #e2e8f0;
  border-radius: 15px;
  background: #f8fafc;
  transition: all 0.3s ease;
}

.design-canvas:hover {
  border-color: #667eea;
  box-shadow: 0 0 20px rgba(102, 126, 234, 0.2);
}

/* Control Panel */
.control-panel {
  background: white;
  border-radius: 15px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  height: fit-content;
  max-height: 85vh;
}

.panel-header {
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  padding: 22px;
  text-align: center;
}

.panel-header h3 {
  margin: 0;
  font-weight: 600;
  font-size: 1.3rem;
}

/* Controls Container */
.controls-container {
  padding: 25px;
  height: 100%;
  overflow-y: auto;
  display: flex;
  flex-direction: row;
  gap: 25px;
}

.control-section {
  background: #f8fafc;
  border-radius: 15px;
  padding: 25px;
  border: 1px solid #e2e8f0;
  flex: 1;
  min-width: 180px;
}

.section-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  padding-bottom: 10px;
  border-bottom: 2px solid #e2e8f0;
  text-align: center;
}

.tab-btn {
  flex: 1;
  padding: 15px 10px;
  border: none;
  background: transparent;
  color: #64748b;
  font-weight: 500;
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 5px;
  position: relative;
  cursor: pointer;
}

.tab-btn i {
  font-size: 1.2rem;
}

.tab-btn span {
  font-size: 0.85rem;
}

.tab-btn:hover {
  background: #e2e8f0;
  color: #334155;
}

.tab-btn.active {
  background: white;
  color: #667eea;
  font-weight: 600;
}

.tab-btn.active::after {
  content: "";
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 30px;
  height: 3px;
  background: linear-gradient(135deg, #667eea, #764ba2);
  border-radius: 3px 3px 0 0;
}

/* Tab Contents */
.tab-contents {
  flex: 1;
  position: relative;
}

.tab-content {
  display: none;
  padding: 25px;
  height: 100%;
  overflow-y: auto;
}

.tab-content.active {
  display: block;
}

.content-section {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* Buttons */
.primary-btn,
.secondary-btn,
.success-btn {
  padding: 12px 16px;
  border: none;
  border-radius: 8px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  font-size: 0.9rem;
}

.primary-btn {
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
}

.primary-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.4);
}

.secondary-btn {
  background: #f1f5f9;
  color: #475569;
  border: 2px solid #e2e8f0;
}

.secondary-btn:hover {
  background: #e2e8f0;
  border-color: #cbd5e1;
}

.success-btn {
  background: linear-gradient(135deg, #10b981, #059669);
  color: white;
}

.success-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(16, 185, 129, 0.4);
}

/* Form Elements */
.form-grid {
  display: grid;
  gap: 15px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.form-label {
  font-weight: 500;
  color: #374151;
  font-size: 0.9rem;
}

.form-input,
.form-select {
  padding: 10px 12px;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  font-size: 0.9rem;
  transition: border-color 0.3s ease;
}

.form-input:focus,
.form-select:focus {
  outline: none;
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.color-picker {
  width: 100%;
  height: 50px;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  cursor: pointer;
  transition: border-color 0.3s ease;
}

.color-picker:hover {
  border-color: #667eea;
}

.color-picker.small {
  width: 60px;
  height: 40px;
}

/* Background Controls */
.bg-controls {
  display: flex;
  align-items: center;
  gap: 15px;
}

.checkbox-wrapper {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 0.85rem;
  color: #64748b;
  cursor: pointer;
}

.checkbox-wrapper input[type="checkbox"] {
  appearance: none;
  width: 18px;
  height: 18px;
  border: 2px solid #cbd5e1;
  border-radius: 4px;
  position: relative;
  cursor: pointer;
  transition: all 0.3s ease;
}

.checkbox-wrapper input[type="checkbox"]:checked {
  background: linear-gradient(135deg, #667eea, #764ba2);
  border-color: #667eea;
}

.checkbox-wrapper input[type="checkbox"]:checked::after {
  content: "✓";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 12px;
  font-weight: bold;
}

/* Format Controls */
.format-controls {
  display: flex;
  gap: 8px;
}

.format-btn {
  width: 40px;
  height: 40px;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  background: #f8fafc;
  color: #475569;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.format-btn:hover {
  background: #e2e8f0;
  border-color: #cbd5e1;
}

.format-btn.active {
  background: linear-gradient(135deg, #667eea, #764ba2);
  border-color: #667eea;
  color: white;
}

/* Upload Section */
.upload-section {
  position: relative;
}

.upload-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 12px 20px;
  background: linear-gradient(135deg, #f59e0b, #d97706);
  color: white;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.3s ease;
  font-weight: 500;
}

.upload-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(245, 158, 11, 0.4);
}

.file-input {
  position: absolute;
  opacity: 0;
  pointer-events: none;
}

/* Responsive */
@media (max-width: 1200px) {
  .customizer-content {
    grid-template-columns: 1fr;
    gap: 20px;
  }

  .control-panel {
    order: -1;
  }

  .canvas-section {
    justify-content: center;
  }

  .controls-container {
    flex-direction: column;
    gap: 15px;
  }

  .control-section {
    min-width: auto;
    padding: 15px;
  }
}

@media (max-width: 768px) {
  .customizer-container {
    padding: 15px;
  }

  .header-title {
    font-size: 2rem;
    flex-direction: column;
    gap: 10px;
  }

  .canvas-container {
    padding: 15px;
  }

  .controls-container {
    padding: 12px;
    gap: 12px;
  }

  .control-section {
    padding: 12px;
  }
}

/* Animations */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.tab-content.active {
  animation: fadeIn 0.3s ease;
}

/* Scrollbar Styling */
.tab-content::-webkit-scrollbar {
  width: 6px;
}

.tab-content::-webkit-scrollbar-track {
  background: #f1f5f9;
}

.tab-content::-webkit-scrollbar-thumb {
  background: #cbd5e1;
  border-radius: 3px;
}

.tab-content::-webkit-scrollbar-thumb:hover {
  background: #94a3b8;
}
</style>
