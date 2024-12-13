import React from "react";
import styles from "./HistoryCard.module.css";
import StarBorderIcon from "@mui/icons-material/StarBorder";


function HistoryCard({ time, title, dependencies }) {
  return (
    <div className={styles.card}>
      <div className={styles.header}>
        <span className={styles.time}>{time}</span>
        <button className={styles.favoriteButton} aria-label="Add to favorites">
          <StarBorderIcon />
        </button>
      </div>
      <div className={styles.body}>
        <p className={styles.title}>{title}</p>
        <p className={styles.dependencies}>{dependencies}</p>
      </div>
    </div>
  );
}

export default HistoryCard;
