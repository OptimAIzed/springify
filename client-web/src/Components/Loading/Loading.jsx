import React, { useState } from "react";
import styles from "./Loading.module.css";


function Loading() {

  return (
    <div className={styles.overlay}>
      <div className={styles.container}>
        <div className={styles.header}>
        </div>
        <div className={styles.content}>
          <div className={styles.spinner}></div>
          <h2>Loading...</h2>
        </div>
      </div>
    </div>
  );
}

export default Loading;
