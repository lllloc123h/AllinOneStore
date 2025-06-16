<template>
  <div class="container-fluid px-4">
    <div class="row g-4 mt-4 mb-4">
      <!-- Canvas wrapper -->
      <div class="col-6">
        <div class="border rounded h-100">
          <canvas
            ref="canvasRef"
            width="810"
            height="500"
            class="w-100 rounded-2"
            style="display: block"
          />
        </div>
      </div>

      <!-- Control panel -->
      <div class="col-6">
        <div class="d-flex flex-column h-100">
          <!-- Tabs -->
          <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
              <button
                class="nav-link active"
                id="home-tab"
                data-bs-toggle="tab"
                data-bs-target="#home-tab-pane"
                type="button"
                role="tab"
              >
                Văn bản
              </button>
            </li>
            <li class="nav-item" role="presentation">
              <button
                class="nav-link"
                id="profile-tab"
                data-bs-toggle="tab"
                data-bs-target="#profile-tab-pane"
                type="button"
                role="tab"
              >
                Vẽ
              </button>
            </li>
            <li class="nav-item" role="presentation">
              <button
                class="nav-link"
                id="contact-tab"
                data-bs-toggle="tab"
                data-bs-target="#contact-tab-pane"
                type="button"
                role="tab"
              >
                Contact
              </button>
            </li>
          </ul>

          <!-- Tab content -->
          <div class="tab-content border rounded-bottom p-3 flex-grow-1 overflow-auto">
            <!-- Tab: Văn bản -->
            <div
              class="tab-pane fade show active"
              id="home-tab-pane"
              role="tabpanel"
              tabindex="0"
            >
              <button @click="addTextbox">➕ Thêm textbox</button>
              <div class="form-group mt-2">
                <label
                  >Màu chữ:
                  <input type="color" v-model="textColor" @input="updateActiveTextbox"
                /></label>
              </div>
              <div class="form-group mt-2">
                <label
                  >Background:
                  <input type="color" v-model="bgColor" @input="updateActiveTextbox" />
                  <input
                    type="checkbox"
                    v-model="isTransparent"
                    @input="updateActiveTextbox"
                  />
                  Trong suốt
                </label>
              </div>
              <div class="form-group mt-2">
                <label>Căn chỉnh:</label><br />
                <input type="checkbox" v-model="bold" @change="updateActiveTextbox" /> B
                <input type="checkbox" v-model="italic" @change="updateActiveTextbox" /> I
                <input
                  type="checkbox"
                  v-model="underline"
                  @change="updateActiveTextbox"
                />
                U
              </div>
              <div class="form-group mt-2">
                <label
                  >Font:
                  <select v-model="fontFamily" @change="updateActiveTextbox">
                    <option>Helvetica</option>
                    <option>Arial</option>
                    <option>Courier</option>
                    <option>Georgia</option>
                    <option>Verdana</option>
                    <option>Impact</option>
                    <option>VT323</option>
                  </select>
                </label>
              </div>
              <div class="form-group mt-2">
                <label
                  >Cỡ chữ:
                  <input
                    type="number"
                    v-model.number="fontSize"
                    min="10"
                    max="120"
                    @input="updateActiveTextbox"
                  />
                </label>
              </div>
              <div class="form-group mt-2">
                <label
                  >Căn lề:
                  <select v-model="textAlign" @change="updateActiveTextbox">
                    <option value="left">Trái</option>
                    <option value="center">Giữa</option>
                    <option value="right">Phải</option>
                    <option value="justify">Canh đều</option>
                  </select>
                </label>
              </div>
              <button class="mt-2" @click="exportJSON">💾 Xuất JSON</button>
            </div>

            <!-- Tab: Vẽ -->
            <div class="tab-pane fade" id="profile-tab-pane" role="tabpanel" tabindex="0">
              <button @click="startDrawingMode">
                {{ btnDraw ? "❌ Hủy chế độ vẽ" : "✏️ Bật chế độ vẽ" }}
              </button>
              <div class="form-group mt-2">
                <label
                  >Chế độ vẽ:
                  <select v-model="drawingMode">
                    <option value="Circle">Circle</option>
                    <option value="Pencil">Pencil</option>
                    <option value="Spray">Spray</option>
                    <option value="Pattern">Pattern</option>
                    <option value="hLine">hline</option>
                    <option value="vLine">vline</option>
                    <option value="square">Square</option>
                    <option value="diamond">Diamond</option>
                    <option value="texture">Texture</option>
                  </select>
                </label>
              </div>
              <div class="form-group mt-2">
                <label
                  >Màu vẽ:
                  <input type="color" v-model="drawingColor" />
                </label>
              </div>
              <div class="form-group mt-2">
                <label
                  >Độ dày:
                  <input type="number" v-model="drawingLineWidth" min="1" max="50" />
                </label>
              </div>
              <div class="form-group mt-2">
                <label
                  >Đổ bóng:
                  <input type="number" v-model="drawingShadowWidth" min="0" max="50" />
                </label>
              </div>
              <div class="form-group mt-2">
                <label
                  >Màu bóng:
                  <input type="color" v-model="drawingShadowColor" />
                </label>
              </div>
            </div>

            <!-- Tab: Contact -->
            <div class="tab-pane fade" id="contact-tab-pane" role="tabpanel" tabindex="0">
              <input type="file" @change="handleImageUpload" />
              <button class="mt-2" @click="exportImage">Xuất ảnh</button>
              <button class="mt-2" @click="saveCanvas">💾 Lưu tạm</button>
              <button class="mt-2" @click="loadCanvas">🔁 Tải lại</button>
              <img
                v-if="exportedImage"
                :src="exportedImage"
                alt="Ảnh xuất"
                class="mt-3 border"
                style="width: 410px; height: 250px"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, watch, computed } from "vue";
import { fabric } from "fabric";
import komiImage from "../../assets/imgs/komi.jpg";

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
  console.log("test 2", brush);

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

onMounted(() => {
  canvas = new fabric.Canvas(canvasRef.value);
  canvas.hoverCursor = "pointer";

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
  const dataURL = canvas.toDataURL({
    format: "png",
    quality: 1.0, // chất lượng 1.0 là cao nhất
    multiplier: 2,
  });
  exportedImage.value = dataURL;

  // 👉 Nếu muốn tải ảnh về luôn:
  const link = document.createElement("a");
  link.href = dataURL;
  link.download = "canvas-image.png";
  link.click();
}
function saveCanvas() {
  const json = canvas.toJSON();
  localStorage.setItem("savedCanvas", JSON.stringify(json));
}

function loadCanvas() {
  const saved = localStorage.getItem("savedCanvas");
  if (!saved) return;

  canvas.loadFromJSON(JSON.parse(saved), () => {
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

// Xuất JSON
function exportJSON() {
  exportedJson.value = JSON.stringify(canvas.toJSON(), null, 2);
}
</script>

<style scoped>
canvas {
  border: 1px solid #ccc;
  margin-bottom: 1rem;
}
</style>
