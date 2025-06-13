import { fabric } from 'fabric';

// ✅ Cập nhật textbox đang chọn
export function updateActiveTextbox(canvas, options) {
  const activeObj = canvas.getActiveObject();
  if (!activeObj || activeObj.type !== "textbox") return;

  const start = activeObj.selectionStart;
  const end = activeObj.selectionEnd;

  const hasSelection = start !== end;

  if (hasSelection) {
    for (let i = start; i < end; i++) {
      activeObj.setSelectionStyles({
        fill: options.fill,
        fontFamily: options.fontFamily,
        fontSize: options.fontSize,
        fontWeight: options.isBold ? 'bold' : '',
        fontStyle: options.isItalic ? 'italic' : '',
        underline: options.isUnderline,
        stroke: options.strokeColor,
        strokeWidth: options.strokeWidth,
        shadow: new fabric.Shadow({
          color: options.shadowColor,
          blur: options.shadowBlur,
          offsetX: 0,
          offsetY: 0
        }),
      }, i, i + 1);
    }
  } else {
    // Không có vùng chọn, áp dụng toàn bộ textbox
    activeObj.set({
      fill: options.fill,
      fontFamily: options.fontFamily,
      fontSize: options.fontSize,
      fontWeight: options.isBold ? 'bold' : 'normal',
      fontStyle: options.isItalic ? 'italic' : 'normal',
      underline: options.isUnderline,
      stroke: options.strokeColor,
      strokeWidth: options.strokeWidth,
      shadow: new fabric.Shadow({
        color: options.shadowColor,
        blur: options.shadowBlur,
        offsetX: 0,
        offsetY: 0
      }),
      lineHeight: options.lineHeight,
      charSpacing: options.charSpacing,
    });
  }

  canvas.requestRenderAll();
}


// ✅ Đồng bộ form từ textbox đang chọn
export function updateFormFromObject(canvas, formStateRefs) {
  const obj = canvas.getActiveObject();
  if (obj && obj.type === 'textbox') {
    formStateRefs.textColor.value = obj.fill || '#000000';
    formStateRefs.backgroundColor.value = obj.backgroundColor || 'transparent';
    formStateRefs.fontFamily.value = obj.fontFamily || 'Helvetica';
    formStateRefs.fontSize.value = obj.fontSize || 24;
    formStateRefs.textAlign.value = obj.textAlign || 'left';
    formStateRefs.isBold.value = obj.fontWeight === 'bold';
    formStateRefs.isItalic.value = obj.fontStyle === 'italic';
    formStateRefs.isUnderline.value = !!obj.underline;
    formStateRefs.strokeColor.value = obj.stroke || '#000000';
    formStateRefs.strokeWidth.value = obj.strokeWidth || 0;
    formStateRefs.shadowColor.value = obj.shadow?.color || '#000000';
    formStateRefs.shadowBlur.value = obj.shadow?.blur || 0;
    formStateRefs.lineHeight.value = obj.lineHeight || 1.2;
    formStateRefs.charSpacing.value = obj.charSpacing || 0;
  }
}

// ✅ Thêm textbox mới vào canvas
export function addTextboxToCanvas(canvas, options) {
  const textbox = new fabric.Textbox('Nhập văn bản', {
    left: 100,
    top: 100,
    width: 200,
    fill: options.fill || '#000000',
    backgroundColor: options.backgroundColor || 'transparent',
    fontFamily: options.fontFamily || 'Helvetica',
    fontSize: options.fontSize || 24,
    textAlign: options.textAlign || 'left',
    fontWeight: options.isBold ? 'bold' : 'normal',
    fontStyle: options.isItalic ? 'italic' : 'normal',
    underline: options.isUnderline || false,
    stroke: options.strokeColor || '#000000',
    strokeWidth: options.strokeWidth || 0,
    shadow: new fabric.Shadow({
      color: options.shadowColor || 'rgba(0,0,0,0)',
      blur: options.shadowBlur || 0,
      offsetX: 0,
      offsetY: 0
    }),
    lineHeight: options.lineHeight || 1.2,
    charSpacing: options.charSpacing || 0,
    editable: true
  });

  canvas.add(textbox);
  canvas.setActiveObject(textbox);
  canvas.renderAll();
}
