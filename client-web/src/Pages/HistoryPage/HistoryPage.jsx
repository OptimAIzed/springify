import React, { useState } from "react";
import styles from "./HistoryPage.module.css";
import HistoryCard from "../../Components/HistoryCard/HistoryCard";

function HistoryPage() {
  // Créer un état pour gérer la visibilité de la fenêtre d'historique
  const [isOpen, setIsOpen] = useState(true);

  const handleClose = () => {
    // Ferme la fenêtre en mettant à jour l'état
    setIsOpen(false);
  };

  if (!isOpen) return null; // Si la fenêtre est fermée, ne rien afficher

  return (
    <div className={styles.overlay}>
      <div className={styles.container}>
        <div className={styles.header}>
          <h2>History</h2>
          <button className={styles.clearButton}>CLEAR</button>
        </div>
        <div className={styles.content}>
          <div className={styles.dateSection}>
            <p className={styles.date}>Thursday, 7 November 2024</p>
            <HistoryCard
              time="11:02"
              title="Project Maven, Language Java, Spring Boot 3.3.5"
              dependencies="Dependencies: Spring Web, Spring Data JPA, H2 Database, Lombok, Spring Boot DevTools"
            />
            <HistoryCard
              time="11:01"
              title="Project Maven, Language Java, Spring Boot 3.3.5"
              dependencies="Dependencies: Spring Web, Spring Data JPA, H2 Database, Lombok, Spring Boot DevTools"
            />
          </div>
          <div className={styles.dateSection}>
            <p className={styles.date}>Monday, 4 November 2024</p>
            <HistoryCard
              time="11:45"
              title="Project Maven, Language Java, Spring Boot 3.3.5"
              dependencies="Dependencies: Spring Web, Spring Data JPA, MySQL Driver, Spring Security, Spring Boot DevTools"
            />
          </div>
        </div>
        
        <div className={styles.closeButtonContainer}>
          <button className={styles.closeButton} onClick={handleClose}>
            CLOSE ESC
          </button>
        </div>
      </div>
    </div>
  );
}

export default HistoryPage;
