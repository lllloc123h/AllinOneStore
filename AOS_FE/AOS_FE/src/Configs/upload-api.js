import axios from "axios";

const upload = {
  async uploadListImage(images, folderName) {
    const results = [];
    
    for (const item of images) {
      const formData = new FormData();
      formData.append("file", item.file);
      formData.append("upload_preset", "upload_preset");
      formData.append("folder", folderName);
      
      try {
        const endpoint =
          item.type === "image"
            ? "https://api.cloudinary.com/v1_1/da2v8uqir/image/upload"
            : "https://api.cloudinary.com/v1_1/da2v8uqir/video/upload";
            
        const response = await axios.post(endpoint, formData);
        
        results.push({
          type: item.type,
          url: response.data.secure_url,
          public_id: response.data.public_id,
        });
      } catch (err) {
        console.error("Upload images failed:", err);
        throw new Error(`Upload failed for ${item.file.name}`);
      }
    }
    
    return results;
  },

  async uploadSingleImageOrVideo(file, folderName) {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("upload_preset", "upload_preset");
    formData.append("folder", folderName);
    console.log("Uploading file:", file);
    try {
 const endpoint =
         file.type.startsWith("image/")
            ? "https://api.cloudinary.com/v1_1/da2v8uqir/image/upload"
            : "https://api.cloudinary.com/v1_1/da2v8uqir/video/upload";
const response = await axios.post(endpoint, formData);
        console.log("Upload response:", response.data);
      return {
        url: response.data.secure_url,
        public_id: response.data.public_id,
      };
    } catch (err) {
      console.error("Upload single image failed:", err);
      throw new Error(`Upload failed for ${file.name}`);
    }
  },

  // Hàm load ảnh từ Cloudinary theo folder
  async loadImagesByFolder(folderName, maxResults = 50) {
    try {
      // Cloudinary Search API endpoint
      const endpoint = `https://api.cloudinary.com/v1_1/da2v8uqir/resources/search`;
      
      const searchParams = {
        expression: `folder:${folderName}/*`,
        resource_type: 'image',
        max_results: maxResults,
        sort_by: [['created_at', 'desc']], // Sắp xếp theo ngày tạo mới nhất
      };

      const response = await axios.post(endpoint, searchParams, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Basic ${btoa('API_KEY:API_SECRET')}` // Cần thay thế bằng API key thật
        }
      });

      return response.data.resources.map(resource => ({
        id: resource.public_id,
        name: resource.filename || resource.public_id.split('/').pop(),
        size: resource.bytes,
        url: resource.secure_url,
        cloudinaryUrl: resource.secure_url,
        publicId: resource.public_id,
        width: resource.width,
        height: resource.height,
        format: resource.format,
        createdAt: resource.created_at,
        isUrl: false,
        uploading: false,
      }));
    } catch (err) {
      console.error("Load images from Cloudinary failed:", err);
      throw new Error("Không thể tải danh sách ảnh từ Cloudinary");
    }
  },

  // Hàm load ảnh từ backend API (nếu ảnh được lưu trong database)
  async loadImagesByProductId(productId) {
    try {
      const response = await axios.get(`/api/products/${productId}/images`);
      
      return response.data.map(image => ({
        id: image.id,
        name: image.name || image.url.split('/').pop(),
        size: image.size || 0,
        url: image.url,
        cloudinaryUrl: image.cloudinary_url || image.url,
        publicId: image.public_id,
        isUrl: !image.public_id, // Nếu không có publicId thì là URL external
        uploading: false,
        createdAt: image.created_at,
      }));
    } catch (err) {
      console.error("Load images from backend failed:", err);
      throw new Error("Không thể tải danh sách ảnh từ server");
    }
  }
};

export default { upload };