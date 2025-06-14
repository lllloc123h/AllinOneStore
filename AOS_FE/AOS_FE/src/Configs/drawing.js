// Configs/drawing.js
import { fabric } from 'fabric'

export function setupDrawing(canvas) {
  const patternBrushes = {}

  const initPatternBrushes = () => {
    // hline
    patternBrushes.hline = new fabric.PatternBrush(canvas)
    patternBrushes.hline.getPatternSrc = function () {
      const c = document.createElement('canvas')
      c.width = c.height = 10
      const ctx = c.getContext('2d')
      ctx.strokeStyle = this.color
      ctx.lineWidth = 5
      ctx.moveTo(5, 0)
      ctx.lineTo(5, 10)
      ctx.stroke()
      return c
    }

    // vline
    patternBrushes.vline = new fabric.PatternBrush(canvas)
    patternBrushes.vline.getPatternSrc = function () {
      const c = document.createElement('canvas')
      c.width = c.height = 10
      const ctx = c.getContext('2d')
      ctx.strokeStyle = this.color
      ctx.lineWidth = 5
      ctx.moveTo(0, 5)
      ctx.lineTo(10, 5)
      ctx.stroke()
      return c
    }

    // square
    patternBrushes.square = new fabric.PatternBrush(canvas)
    patternBrushes.square.getPatternSrc = function () {
      const size = 10
      const c = document.createElement('canvas')
      c.width = c.height = size
      const ctx = c.getContext('2d')
      ctx.fillStyle = this.color
      ctx.fillRect(0, 0, size, size)
      return c
    }

    // texture
    const img = new Image()
    img.src = textureUrl
    patternBrushes.texture = new fabric.PatternBrush(canvas)
    patternBrushes.texture.source = img
  }

  const setBrush = (type = 'Pencil', options = {}) => {
    let brush
    if (patternBrushes[type]) {
      brush = patternBrushes[type]
    } else if (fabric[type + 'Brush']) {
      brush = new fabric[type + 'Brush'](canvas)
    } else {
      brush = new fabric.PencilBrush(canvas)
    }

    brush.color = options.color || '#000'
    brush.width = options.width || 10
    brush.shadow = new fabric.Shadow({
      blur: options.shadowBlur || 0,
      offsetX: options.shadowOffset || 0,
      offsetY: options.shadowOffset || 0,
      color: options.shadowColor || '#000',
      affectStroke: true,
    })

    canvas.freeDrawingBrush = brush
  }

  const toggleDrawing = () => {
    canvas.isDrawingMode = !canvas.isDrawingMode
  }

  initPatternBrushes()

  return {
    setBrush,
    toggleDrawing,
  }
}
