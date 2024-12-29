import React, { useEffect, useState } from "react";
import styles from "./HistoryPage.module.css";
import HistoryCard from "../../Components/HistoryCard/HistoryCard";
import { DeleteAllProjects, GetProjects } from "../../services/projectService";
import { groupByDate } from "../../utils/Helper";

function HistoryPage({ theme, closeAction }) {
  const [loading, setLoading] = useState(false);
  const [history, setHistory] = useState([]);

  const handleClear = () => {
    const id = localStorage.getItem("user_info")
      ? JSON.parse(localStorage.getItem("user_info")).id
      : null;

    if(!id) return

     DeleteAllProjects(id).then(() => {
         setHistory([]);
     }).catch((error) => {
      console.error("Error deleting projects:", error);
     })
  };

  useEffect(() => {
    setLoading(true);
    const id = localStorage.getItem("user_info")
      ? JSON.parse(localStorage.getItem("user_info")).id
      : null;
    
    if(!id) return
    GetProjects(id)
      .then((response) => {
        const transformedHistory = groupByDate(response); 
        setHistory(transformedHistory);
      })
      .catch((error) => {
        console.error("Error fetching projects:", error);
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);
  

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
         {loading ? (
          <p>Loading projects...</p>
          ) : history.length === 0 ? (
            <p>No projects found</p>
          ) : (
            history.map((section, index) => (
              <div key={index} className={styles.dateSection}>
                <p className={styles.date}>{section.date}</p>
                {section.entries.map((entry, entryIndex) => (
                  <HistoryCard
                    key={entryIndex}
                    time={entry.time}
                    title={entry.title}
                    dependencies={entry.dependencies}
                    img={entry.img}
                  />
                ))}
              </div>
            ))
          )}

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
