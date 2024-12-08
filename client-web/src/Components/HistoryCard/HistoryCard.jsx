import React from "react";
import styles from "./HistoryCard.module.css";
import 'bootstrap-icons/font/bootstrap-icons.css';


function HistoryCard({ time, title, dependencies }) {
  return (
    <div className={styles.card}>
      <div className={styles.header}>
        <span className={styles.time}>{time}</span>
        <button className={styles.favoriteButton}><i className="bi bi-star"></i></button>
      </div>
      <div className={styles.body}>
        <p className={styles.title}>{title}</p>
        <p className={styles.dependencies}>{dependencies}</p>
      </div>
    </div>
  );
}

export default HistoryCard;

