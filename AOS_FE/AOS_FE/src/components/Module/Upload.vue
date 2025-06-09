<template>
  <div>
    <input type="file" multiple @change="uploadImages" />
    <div v-if="imageUrls.length">
      <h3>Uploaded Images:</h3>
      <div v-for="(url, index) in imageUrls" :key="index">
        <img :src="url" alt="Uploaded" style="max-width: 200px; margin: 10px" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { storage } from "../firebase"; // đã config bằng .env
import { ref as storageRef, uploadBytes, getDownloadURL } from "firebase/storage";

const imageUrls = ref([]);

const uploadImages = async (event) => {
  const files = event.target.files;
  if (!files.length) return;

  for (let file of files) {
    const filePath = `images/${Date.now()}_${file.name}`;
    const fileRef = storageRef(storage, filePath);

    try {
      await uploadBytes(fileRef, file);
      const url = await getDownloadURL(fileRef);
      imageUrls.value.push(url);
      console.log("Uploaded:", url);
    } catch (error) {
      console.error("Error uploading:", file.name, error);
    }
  }
};
</script>
