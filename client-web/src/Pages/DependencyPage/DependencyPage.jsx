import React, { useState, useEffect } from "react";
import Dependencies from "../../Components/Dependencies/Dependencies";
import styles from "./DependencyPage.module.css";

function DependencyPage() {
  const [selectedDependencies, setSelectedDependencies] = useState([]);
  const [isDependenciesVisible, setIsDependenciesVisible] = useState(false);

  const handleAddDependency = (dependency) => {
    if (!selectedDependencies.find((dep) => dep.id === dependency.id)) {
      setSelectedDependencies([...selectedDependencies, dependency]);
    }
  };

  const handleDeleteDependency = (id) => {
    setSelectedDependencies(selectedDependencies.filter((dep) => dep.id !== id));
  };

  const handleClickOutside = (e) => {
    if (!e.target.closest(`.${styles.dependenciesContainer}`)) {
      setIsDependenciesVisible(false);
    }
  };

  useEffect(() => {
    if (isDependenciesVisible) {
      document.addEventListener("mousedown", handleClickOutside);
    } else {
      document.removeEventListener("mousedown", handleClickOutside);
    }
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [isDependenciesVisible]);

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <h1 className={styles.title}>Dependencies</h1>
        <button
          className={styles.addButton}
          onClick={() => setIsDependenciesVisible(true)}
        >
          ADD DEPENDENCIES...
        </button>
      </div>

      <div className={styles.divider}></div>

      <div className={styles.selectedDependencies}>
        {selectedDependencies.length === 0 ? (
          <p className={styles.noDependency}>No dependency selected</p>
        ) : (
          <ul className={styles.selectedList}>
            {selectedDependencies.map((dep) => (
              <li key={dep.id} className={styles.selectedItem}>
                <div className={styles.itemContent}>
                  <strong>{dep.name}</strong>
                  <span className={styles.category}>{dep.category}</span>
                  <p>{dep.description}</p>
                </div>
                <button
                  className={styles.deleteButton}
                  onClick={() => handleDeleteDependency(dep.id)}
                >
                  ❌
                </button>
              </li>
            ))}
          </ul>
        )}
      </div>

      {isDependenciesVisible && (
        <div className={styles.overlay}>
          <div className={styles.dependenciesContainer}>
            <Dependencies onAddDependency={handleAddDependency} />
          </div>
        </div>
      )}
    </div>
  );
}

export default DependencyPage;
