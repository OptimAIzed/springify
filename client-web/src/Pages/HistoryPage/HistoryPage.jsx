import React, { useState } from "react";
import styles from "./HistoryPage.module.css";
import HistoryCard from "../../Components/HistoryCard/HistoryCard";

function HistoryPage({ theme, closeAction }) {
  const [history, setHistory] = useState([
    {
      date: "Thursday, 7 November 2024",
      entries: [
        {
          time: "11:02",
          title: "Project Maven, Language Java, Spring Boot 3.3.5",
          dependencies:
            "Dependencies: Spring Web, Spring Data JPA, H2 Database, Lombok, Spring Boot DevTools",
        },
        {
          time: "11:01",
          title: "Project Maven, Language Java, Spring Boot 3.3.5",
          dependencies:
            "Dependencies: Spring Web, Spring Data JPA, H2 Database, Lombok, Spring Boot DevTools",
        },
      ],
    },
    {
      date: "Monday, 4 November 2024",
      entries: [
        {
          time: "11:45",
          title: "Project Maven, Language Java, Spring Boot 3.3.5",
          dependencies:
            "Dependencies: Spring Web, Spring Data JPA, MySQL Driver, Spring Security, Spring Boot DevTools",
        },
      ],
    },
  ]);

  const handleClear = () => {
    setHistory([]);
  };

  return (
    <div className={styles.overlay}>
      <div className={styles.container}>
        <div className={styles.header}>
          <h2>History</h2>
          <button className={styles.clearButton} onClick={handleClear}>
            CLEAR
          </button>
        </div>
        <div className={styles.content}>
          {history.map((section, index) => (
            <div key={index} className={styles.dateSection}>
              <p className={styles.date}>{section.date}</p>
              {section.entries.map((entry, entryIndex) => (
                <HistoryCard
                  key={entryIndex}
                  time={entry.time}
                  title={entry.title}
                  dependencies={entry.dependencies}
                />
              ))}
            </div>
          ))}
        </div>
        <hr className={styles.separator} />
        <div className={styles.closeButtonContainer}>

          <button className={styles.closeButton} onClick={() => closeAction(false)}>
            <span className={styles.closeText}>CLOSE</span> ESC
          </button>
        </div>
      </div>
    </div>
  );
}

export default HistoryPage;
