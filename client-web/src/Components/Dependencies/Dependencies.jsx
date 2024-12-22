import React, { useState } from "react";
import styles from "./Dependencies.module.css";

function Dependencies({ onAddDependency }) {
  const allDependencies = [
    {
      id: 1,
      name: "GraalVM Native Support",
      description:
        "Support for compiling Spring applications to native executables using the GraalVM native-image compiler.",
      category: "Developer Tools",
    },
    {
      id: 2,
      name: "Spring Boot DevTools",
      description:
        "Provides fast application restarts, LiveReload, and configurations for enhanced development experience.",
      category: "Developer Tools",
    },
    {
      id: 3,
      name: "Lombok",
      description:
        "Java annotation library which helps to reduce boilerplate code.",
      category: "Developer Tools",
    },
    {
      id: 4,
      name: "Spring Configuration Processor",
      description:
        "Generate metadata for developers to offer contextual help and 'code completion' when working with custom configuration keys.",
      category: "Configuration",
    },
    {
      id: 5,
      name: "Docker Compose Support",
      description:
        "Provides docker compose support for enhanced development experience.",
      category: "Deployment",
    },
    {
      id: 6,
      name: "Spring Modulith",
      description: "Support for building modular monolithic applications.",
      category: "Architecture",
    },
  ];

  const [searchTerm, setSearchTerm] = useState("");

  const filteredDependencies = allDependencies.filter((dep) =>
    dep.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className={styles.container}>
      <input
        type="text"
        placeholder="Web, Security, JPA, Actuator, Devtools..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        className={styles.searchBar}
      />

      <ul className={styles.list}>
        {filteredDependencies.map((dep) => (
          <li
            key={dep.id}
            className={styles.listItem}
            onClick={() => onAddDependency(dep)}
          >
            <strong>{dep.name}</strong>
            <p>{dep.description}</p>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Dependencies;
