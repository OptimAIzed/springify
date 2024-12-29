import styles from "./PhotoDrop.module.css";
import { useState } from "react";

function PhotoDrop({ setImage }) {
  const [preview, setPreview] = useState(null);

  const handleImageChange = (event) => {
    const file = event.target.files[0];

    if (file) {
      const maxSize = 64 * 1024; // 64KB
      if (file.size > maxSize) {
        alert("File size exceeds 64KB. Please upload a smaller file.");
        return;
      }

      const validTypes = ["image/jpeg", "image/png", "image/jpg"];
      if (!validTypes.includes(file.type)) {
        alert("Invalid file type. Please upload a JPEG or PNG image.");
        return;
      }

      setPreview(URL.createObjectURL(file));
      setImage(file);
    }
  };

  return (
    <div className={styles.container}>
      <h3>Upload a UML class diagram image</h3>
      <input
        type="file"
        accept="image/*"
        onChange={handleImageChange}
        className={styles.input}
      />
      {preview && (
        <div className={styles.preview}>
          <img src={preview} alt="Preview" className={styles.image} />
        </div>
      )}
    </div>
  );
}

export default PhotoDrop;
