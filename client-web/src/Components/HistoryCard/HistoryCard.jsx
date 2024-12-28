import React from "react";
import styles from "./HistoryCard.module.css";
import StarBorderIcon from "@mui/icons-material/StarBorder";

function HistoryCard({ time, title, dependencies,img }) {
  return (
    <div className={styles.card}>
      <div className={styles.header}>
        <div className={styles.info}>
          <img
            className={styles.classDiagramImage}
            src={`data:image/png;base64,${img}`}
            alt="Class Diagram"
          />
          <span className={styles.time}>{time}</span>
          <span className={styles.title}>{title}</span>
        </div>
        <button className={styles.favoriteButton} aria-label="Add to favorites">
          <StarBorderIcon />
        </button>
      </div>
      <p className={styles.dependencies}>{dependencies}</p>
      
    </div>
  );
}

export default HistoryCard;
