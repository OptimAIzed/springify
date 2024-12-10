import React, { useState } from "react";
import DependencyCard from "../DependencyCard/DependencyCard"; 
import styles from "./Dependencies.module.css";

function Dependencies() {
  const allDependencies = [
    {
      id: 1,
      name: "GraalVM Native Support",
      description: "Support for compiling Spring applications to native executables using the GraalVM native-image compiler.",
      category: "Developer Tools",
    },
    {
      id: 2,
      name: "Spring Boot DevTools",
      description: "Provides fast application restarts, LiveReload, and configurations for enhanced development experience.",
      category: "Developer Tools",
    },
    {
      id: 3,
      name: "Lombok",
      description: "Java annotation library which helps to reduce boilerplate code.",
      category: "Developer Tools",
    },
    {
      id: 4,
      name: "Spring Configuration Processor",
      description: "Generate metadata for developers to offer contextual help and 'code completion' when working with custom configuration keys.",
      category: "Configuration",
    },
    {
      id: 5,
      name: "Docker Compose Support",
      description: "Provides docker compose support for enhanced development experience.",
      category: "Deployment",
    },
    {
      id: 6,
      name: "Spring Modulith",
      description: "Support for building modular monolithic applications.",
      category: "Architecture",
    },
  ];

  const [dependencies, setDependencies] = useState([]); 
  const [showAvailable, setShowAvailable] = useState(false); 
  const [searchTerm, setSearchTerm] = useState(""); 


  const filteredDependencies = allDependencies.filter((dep) =>
    dep.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  
  const handleAddDependency = (dependency) => {
    if (!dependencies.find((dep) => dep.id === dependency.id)) {
      setDependencies([...dependencies, dependency]);
      setShowAvailable(false); 
      setSearchTerm(""); 
    }
  };

  
  const handleDeleteDependency = (id) => {
    setDependencies(dependencies.filter((dep) => dep.id !== id));
  };

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <h2>Dependencies</h2>
        <button
          className={styles.addButton}
          onClick={() => setShowAvailable(!showAvailable)} 
        >
          {showAvailable ? "Cancel" : "ADD DEPENDENCIES... "}
        </button>
      </div>
      {showAvailable ? ( 
        <div className={styles.availableDependencies}>
          <input
            type="text"
            placeholder="Search dependencies..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className={styles.searchBar}
          />
          {filteredDependencies.length === 0 ? (
            <p>No dependencies found.</p>
          ) : (
            <ul className={styles.list}>
              {filteredDependencies.map((dep) => (
                <li
                     key={dep.id}
                     className={styles.listItem}
                    onClick={() => handleAddDependency(dep)} 
                >
                  <span>
                     <strong>{dep.name}</strong> - {dep.description}
                  </span>
                </li>
          ))}
            </ul>

          )}
        </div>
      ) : (
        <div className={styles.selectedDependencies}>
          {dependencies.length === 0 ? (
            <p className={styles.noDependency}>No dependency selected</p>
          ) : (
            <div className={styles.dependenciesList}>
              {dependencies.map((dep) => (
                <DependencyCard
                  key={dep.id}
                  id={dep.id}
                  name={dep.name}
                  description={dep.description}
                  onDelete={handleDeleteDependency}
                />
              ))}
            </div>
          )}
        </div>
      )}
    </div>
  );
}

export default Dependencies;
