
import React, { useState } from "react";
import styles from "./DependencyCard.module.css";

function DependencyCard({ id, name, version, description, author, onDelete }) {
  const [hovered, setHovered] = useState(false); 

  return (
    <div
      className={styles.container}
      onMouseEnter={() => setHovered(true)} 
      onMouseLeave={() => setHovered(false)} 
    >
      <h3 className={styles.title}>{name}</h3>
      <p className={styles.version}>{author}</p>
      <p className={styles.description}>{description}</p>
      {hovered && (
        <button
          className={styles.deleteButton}
          onClick={() => onDelete(id)} 
        >
          ❌
        </button>
      )}
    </div>
  );
}

export default DependencyCard;
