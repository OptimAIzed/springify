import React from "react";
import Dependencies from "../../Components/Dependencies/Dependencies";
import styles from "./DependencyPage.module.css"; 

function DependencyPage() {
  return (
    <div className={styles.container}>
      <Dependencies />
    </div>
  );
}

export default DependencyPage;
