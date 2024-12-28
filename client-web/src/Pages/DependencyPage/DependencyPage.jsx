import styles from "./DependencyPage.module.css";
import React, { useState } from "react";
import axios from "axios";
import { allDependencies } from "../../utils/dependenciesList";

function DependencyPage({ image, setDependencies }) {
  const [selectedDependencies, setSelectedDependencies] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchDependencies = async () => {
    const baseUrl = "http://localhost:8888/api/projects/generate/dependencies";
    const token = localStorage.getItem('token');

    if (!image) {
      alert("Please upload a UML class diagram image");
      return;
    }

    setLoading(true);
    setError(null);

    const multipartData = new FormData();
    multipartData.append('image', image);

    try {
      const response = await axios.post(baseUrl, multipartData, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'multipart/form-data',
        },
        responseType: 'json',
      });
      const selectedDependencies = response.data;
      const filteredDependencies = selectedDependencies.filter(dep =>
        allDependencies.some(allDep => allDep.dependency === dep.dependency)
      );

      // Set the filtered dependencies
      setSelectedDependencies(filteredDependencies);
      setDependencies(filteredDependencies.map(dep => dep.dependency));
    } catch (err) {
      setError(err);
      console.error('Error fetching dependencies:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <h1 className={styles.title}>Dependencies</h1>
        <button
          className={styles.addButton}
          onClick={() => fetchDependencies()}
        >
          GENERATE DEPENDENCIES
        </button>
      </div>

      <div className={styles.divider}></div>

      <div className={styles.selectedDependencies}>
        {loading ? <p className={styles.noDependency}>Loading dependencies...</p> :
          selectedDependencies.length === 0 ? (
            <p className={styles.noDependency}>No dependencies available for this project.</p>
          ) : (
            <ul className={styles.selectedList}>
              {selectedDependencies.map((dep) => (
                <li key={dep.id} className={styles.selectedItem}>
                  <div className={styles.itemContent}>
                    <strong>{dep.name}</strong>
                    <span className={styles.category}>{dep.category}</span>
                    <p>{dep.description}</p>
                  </div>
                </li>
              ))}
            </ul>
          )}
      </div>
    </div>
  );
}

export default DependencyPage;
