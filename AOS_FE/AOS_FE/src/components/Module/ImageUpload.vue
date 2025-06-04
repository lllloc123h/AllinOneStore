<template>
  <div class="upload-section">
    <label class="upload-box">
      <input type="file" @change="onFileChange" />
      <div class="upload-content">
        <div class="upload-icon">
          ⬆️ <!-- Replace with SVG or icon if needed -->
        </div>
        <span class="upload-text">Chọn hoặc kéo tập tin vào đây</span>
      </div>
    </label>
  </div>
</template>


<script>
export default {
  name: 'ImageUploader',
  data() {
    return {
      selectedFile: null,
      preview: null
    };
  },
  methods: {
    onFileChange(event) {
      const file = event.target.files[0];
      if (file && file.type.startsWith('image/')) {
        this.selectedFile = file;

        const reader = new FileReader();
        reader.onload = (e) => {
          this.preview = e.target.result;
        };
        reader.readAsDataURL(file);
      } else {
        this.selectedFile = null;
        this.preview = null;
        alert('Please select a valid image file.');
      }
    },
    async submitImage() {
      if (!this.selectedFile) return;

      const formData = new FormData();
      formData.append('image', this.selectedFile);

      try {
        const response = await fetch('/api/upload', {
          method: 'POST',
          body: formData
        });

        if (!response.ok) throw new Error('Upload failed');
        alert('Image uploaded successfully!');
      } catch (error) {
        alert('Error uploading image: ' + error.message);
      }
    }
  }
};
</script>
<style scoped>
.upload-section {
  font-family: Arial, sans-serif;
}

.upload-box {
  display: block;
  width: 100%;
  max-width: 360px;
  border: 1px dashed #d1d5db;
  background-color: #f9fafb;
  border-radius: 8px;
  padding: 12px 16px;
  cursor: pointer;
  transition: border-color 0.3s;
}

.upload-box:hover {
  border-color: #3b82f6;
}

.upload-box input[type="file"] {
  display: none;
}

.upload-content {
  display: flex;
  align-items: center;
  gap: 12px;
}

.upload-icon {
  width: 32px;
  height: 32px;
  background-color: #d1d5db;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
}

.upload-text {
  color: #111827;
  font-size: 14px;
  font-weight: 500;
}
</style>

