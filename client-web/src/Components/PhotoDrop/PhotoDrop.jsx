import { useState } from "react";
import styles from "./PhotoDrop.module.css";

function PhotoDrop() {
  const [image, setImage] = useState(null);

  const handleImageChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      setImage(URL.createObjectURL(file));
    }
  };

  return (
    <div className={styles.container}>
      <h3>Upload an Image</h3>
      <input
        type="file"
        accept="image/*"
        onChange={handleImageChange}
        className={styles.input}
      />
      {image && (
        <div className={styles.preview}>
          <img src={image} alt="Preview" className={styles.image} />
        </div>
      )}
    </div>
  );
}

export default PhotoDrop;
