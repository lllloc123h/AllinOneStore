<template>
  <div>
    <!-- Canvas -->
    <canvas ref="canvasRef" width="800" height="600"></canvas>
    <div class="controls">
      <ul class="nav nav-tabs" id="myTab" role="tablist">
        <li class="nav-item" role="presentation">
          <button
            class="nav-link active"
            id="home-tab"
            data-bs-toggle="tab"
            data-bs-target="#home-tab-pane"
            type="button"
            role="tab"
            aria-controls="home-tab-pane"
            aria-selected="true"
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
            aria-controls="profile-tab-pane"
            aria-selected="false"
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
            aria-controls="contact-tab-pane"
            aria-selected="false"
          >
            Contact
          </button>
        </li>
        <li class="nav-item" role="presentation">
          <button
            class="nav-link"
            id="disabled-tab"
            data-bs-toggle="tab"
            data-bs-target="#disabled-tab-pane"
            type="button"
            role="tab"
            aria-controls="disabled-tab-pane"
            aria-selected="false"
            disabled
          >
            Disabled
          </button>
        </li>
      </ul>
      <div class="tab-content" id="myTabContent">
        <div
          class="tab-pane fade show active"
          id="home-tab-pane"
          role="tabpanel"
          aria-labelledby="home-tab"
          tabindex="0"
        >
          <!-- Tool panel -->
          <div class="controls">
            <button @click="addTextbox">➕ Thêm textbox</button>

            <label>
              🎨 Màu chữ:
              <input type="color" v-model="textColor" @input="updateActiveTextbox" />
            </label>
            <label>
              🎨 Background :
              <input type="color" v-model="bgColor" @input="updateActiveTextbox" />
              <input
                type="checkbox"
                v-model="isTransparent"
                @input="updateActiveTextbox"
              />
            </label>
            <label>
              Căn chỉnh :
              <input type="checkbox" v-model="bold" @change="updateActiveTextbox" />B
              <input type="checkbox" v-model="italic" @change="updateActiveTextbox" />I
              <input type="checkbox" v-model="underline" @change="updateActiveTextbox" />U
            </label>

            <label>
              🔠 Font:
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

            <label>
              🔡 Cỡ chữ:
              <input
                type="number"
                v-model.number="fontSize"
                min="10"
                max="120"
                @input="updateActiveTextbox"
              />
            </label>

            <label>
              🧭 Căn lề:
              <select v-model="textAlign" @change="updateActiveTextbox">
                <option value="left">Trái</option>
                <option value="center">Giữa</option>
                <option value="right">Phải</option>
                <option value="justify">Canh đều</option>
              </select>
            </label>

            <button @click="exportJSON">💾 Xuất JSON</button>
          </div>
        </div>
        <div
          class="tab-pane fade"
          id="profile-tab-pane"
          role="tabpanel"
          aria-labelledby="profile-tab"
          tabindex="0"
        >
          <button @click="startDrawingMode">
            {{ btnDraw ? "❌ Hủy chế độ vẽ" : "✏️ Bật chế độ vẽ" }}
          </button>
          <span>Chế độ vẽ </span>
          <select name="" v-model="drawingMode" id="drawing-mode">
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
          <label>
            Màu vẽ:
            <input type="color" v-model="drawingColor" />
          </label>
          <label>
            Độ dày:
            <input type="number" v-model="drawingLineWidth" min="1" max="50" />
          </label>
          <label>
            Đổ bóng:
            <input type="number" v-model="drawingShadowWidth" min="0" max="50" />
          </label>
          <label>
            Màu bóng:
            <input type="color" v-model="drawingShadowColor" />
          </label>
        </div>
        <div
          class="tab-pane fade"
          id="contact-tab-pane"
          role="tabpanel"
          aria-labelledby="contact-tab"
          tabindex="0"
        >
          ...
        </div>
        <div
          class="tab-pane fade"
          id="disabled-tab-pane"
          role="tabpanel"
          aria-labelledby="disabled-tab"
          tabindex="0"
        >
          ...
        </div>
      </div>
    </div>

    <!-- JSON output -->
    <pre v-if="exportedJson"><code>{{ exportedJson }}</code></pre>
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
function startDrawingMode() {
  canvas.isDrawingMode = !canvas.isDrawingMode;
  btnDraw.value = canvas.isDrawingMode;
  mode();
}

watch(
  [
    btnDraw,
    drawingMode,
    drawingColor,
    drawingLineWidth,
    drawingShadowWidth,
    drawingShadowColor,
  ],
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

  if (patternBrushMap[brushName]) {
    console.log("test ", patternBrushMap[brushName]);

    brush = patternBrushMap[brushName];
  } else if (fabric[`${brushName}Brush`]) {
    brush = new fabric[`${brushName}Brush`](canvas);
  } else {
    // Nếu không tìm được brush phù hợp thì không thay đổi brush hiện tại
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
  if (!patternBrushMap[texture]) {
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
      // Nếu đang chọn texture thì update lại
      if (drawingMode.value === "texture") {
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
.controls {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  align-items: center;
  margin-bottom: 1rem;
}
.controls label {
  display: flex;
  align-items: center;
  gap: 5px;
}
pre {
  background: #f4f4f4;
  padding: 10px;
  border-radius: 6px;
  max-height: 300px;
  overflow: auto;
}
</style>
