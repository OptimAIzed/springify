import React, { useState } from "react";
import styles from "./Loading.module.css";
import 'bootstrap-icons/font/bootstrap-icons.css'; // Assurez-vous que Bootstrap Icons est bien importé

function Loading() {
  const [loading, setLoading] = useState(true); // Utilisation de l'état pour contrôler la visibilité de la page de chargement

  const closeLoadingPage = () => {
    setLoading(false); // Fermer la page de chargement en changeant l'état
    console.log("Page de chargement fermée");
  };

  if (!loading) return null; // Si loading est false, la page de chargement n'est plus affichée

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
