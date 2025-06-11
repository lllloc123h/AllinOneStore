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
          <button @click="startDrawingMode">➕ Vẽ</button>
          <span>Chế độ vẽ </span>
          <select name="" v-model="drawingMode" id="drawing-mode">
            <option value="Circle">Circle</option>
            <option value="Pencil">Pencil</option>
            <option value="Spray">Spray</option>
            <option value="Pattern">Pattern</option>
            <option value="hline">hline</option>
            <option value="vline">vline</option>
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
import { ref, onMounted, onBeforeUnmount, watch } from "vue";
import { fabric } from "fabric";
import komiImage from "../../assets/imgs/komi.jpg";

// Canvas
const canvasRef = ref();
let canvas;
let vLinePatternBrush,
  hLinePatternBrush,
  squarePatternBrush,
  diamondPatternBrush,
  texturePatternBrush;

// const $ = (id) => document.getElementById();

// Control values
const textColor = ref("#000000");
const fontFamily = ref("Helvetica");
const fontSize = ref(24);
const textAlign = ref("left");
const exportedJson = ref("");

const drawingMode = ref("Pencil");
const drawingColor = ref("#000000");
const drawingLineWidth = ref(5);
const drawingShadowWidth = ref(0);
const drawingShadowColor = ref("#000000");

function startDrawingMode() {
  canvas.isDrawingMode = !canvas.isDrawingMode;
  mode();
}
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
    hline: vLinePatternBrush,
    vline: hLinePatternBrush,
    square: squarePatternBrush,
    diamond: diamondPatternBrush,
    texture: texturePatternBrush,
  };

  canvas.freeDrawingBrush = patternBrushMap[brushName]
    ? patternBrushMap[brushName]
    : new fabric[`${brushName}Brush`](canvas);

  const brush = canvas.freeDrawingBrush;
  if (brush) {
    brush.color = drawingColor.value;
    brush.width = drawingLineWidth.value;
    brush.shadow = new fabric.Shadow({
      blur: drawingShadowWidth.value,
      offsetX: 0,
      offsetY: 0,
      affectStroke: true,
      color: drawingShadowColor.value,
    });
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

  // PatternBrushes
  if (fabric.PatternBrush) {
    // HORIZONTAL LINE (hline)
    hLinePatternBrush = new fabric.PatternBrush(canvas);
    hLinePatternBrush.getPatternSrc = function () {
      const patternCanvas = fabric.getEnv().document.createElement("canvas");
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
    vLinePatternBrush.getPatternSrc = function () {
      const patternCanvas = fabric.getEnv().document.createElement("canvas");
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
    squarePatternBrush.getPatternSrc = function () {
      const squareWidth = 10;
      const squareDistance = 2;
      const patternCanvas = fabric.getEnv().document.createElement("canvas");
      patternCanvas.width = patternCanvas.height = squareWidth + squareDistance;
      const ctx = patternCanvas.getContext("2d");
      ctx.fillStyle = this.color;
      ctx.fillRect(0, 0, squareWidth, squareWidth);
      return patternCanvas;
    };

    // DIAMOND
    diamondPatternBrush = new fabric.PatternBrush(canvas);
    diamondPatternBrush.getPatternSrc = function () {
      const squareWidth = 10;
      const squareDistance = 5;
      const rect = new fabric.Rect({
        width: squareWidth,
        height: squareWidth,
        angle: 45,
        fill: this.color,
      });
      const canvasWidth = rect.getBoundingRect().width;
      const patternCanvas = fabric.getEnv().document.createElement("canvas");
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
  canvas.on("selection:created", updateFormFromObject);
  canvas.on("selection:updated", updateFormFromObject);
  window.addEventListener("keydown", handleDeleteKey);
});

onBeforeUnmount(() => {
  window.removeEventListener("keydown", handleDeleteKey);
});

function handleDeleteKey(e) {
  if (e.key === "Delete" || e.key === "Backspace") {
    const activeObj = canvas.getActiveObject();
    if (activeObj) {
      // Nếu là nhóm object
      if (activeObj.type === "activeSelection") {
        activeObj.forEachObject((obj) => canvas.remove(obj));
      } else {
        canvas.remove(activeObj);
      }
      canvas.discardActiveObject().renderAll();
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
  });
  canvas.add(textbox).setActiveObject(textbox);
}

// Cập nhật textbox đang chọn
function updateActiveTextbox() {
  const obj = canvas.getActiveObject();
  if (obj && obj.type === "textbox") {
    obj.set({
      fill: textColor.value,
      fontFamily: fontFamily.value,
      fontSize: fontSize.value,
      textAlign: textAlign.value,
    });
    canvas.renderAll();
  }
}

// Khi chọn object thì cập nhật form control
function updateFormFromObject(e) {
  const obj = canvas.getActiveObject();
  if (obj && obj.type === "textbox") {
    textColor.value = obj.fill || "#000000";
    fontFamily.value = obj.fontFamily || "Helvetica";
    fontSize.value = obj.fontSize || 24;
    textAlign.value = obj.textAlign || "left";
  }
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
