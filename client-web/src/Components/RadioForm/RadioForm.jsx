import { useState } from "react";
import styles from "./RadioForm.module.css";

function RadioForm() {
  const [projectType, setProjectType] = useState("Gradle - Groovy");
  const [language, setLanguage] = useState("Java");
  const [springBootVersion, setSpringBootVersion] = useState("3.4.1");

  const handleProjectTypeChange = (event) => {
    setProjectType(event.target.value);
  };

  const handleLanguageChange = (event) => {
    setLanguage(event.target.value);
  };

  const handleSpringBootVersionChange = (event) => {
    setSpringBootVersion(event.target.value);
  };

  return (
    <div className={styles.container}>
      <div className={styles.row}>
        {/* Project Options */}
        <div>
          <h4>Project</h4>
          <div className={styles.radioGroup}>
            <label className={styles.radioOption}>
              <input
                type="radio"
                name="projectType"
                value="Gradle - Groovy"
                checked={projectType === "Gradle - Groovy"}
                onChange={handleProjectTypeChange}
              />
              <span>Gradle - Groovy</span>
            </label>
            <label className={styles.radioOption}>
              <input
                type="radio"
                name="projectType"
                value="Gradle - Kotlin"
                checked={projectType === "Gradle - Kotlin"}
                onChange={handleProjectTypeChange}
              />
              <span>Gradle - Kotlin</span>
            </label>
            <label className={styles.radioOption}>
              <input
                type="radio"
                name="projectType"
                value="Maven"
                checked={projectType === "Maven"}
                onChange={handleProjectTypeChange}
              />
              <span>Maven</span>
            </label>
          </div>
        </div>

        {/* Language Options */}
        <div>
          <h4>Language</h4>
          <div className={styles.radioGroup}>
            <label className={styles.radioOption}>
              <input
                type="radio"
                name="language"
                value="Java"
                checked={language === "Java"}
                onChange={handleLanguageChange}
              />
              <span>Java</span>
            </label>
            <label className={styles.radioOption}>
              <input
                type="radio"
                name="language"
                value="Kotlin"
                checked={language === "Kotlin"}
                onChange={handleLanguageChange}
              />
              <span>Kotlin</span>
            </label>
            <label className={styles.radioOption}>
              <input
                type="radio"
                name="language"
                value="Groovy"
                checked={language === "Groovy"}
                onChange={handleLanguageChange}
              />
              <span>Groovy</span>
            </label>
          </div>
        </div>

        {/* Spring Boot Version Options */}
        <div>
          <h4>Spring Boot Version</h4>
          <div className={styles.radioGroup}>
            <label className={styles.radioOption}>
              <input
                type="radio"
                name="springBootVersion"
                value="3.4.1 (SNAPSHOT)"
                checked={springBootVersion === "3.4.1 (SNAPSHOT)"}
                onChange={handleSpringBootVersionChange}
              />
              <span>3.4.1 (SNAPSHOT)</span>
            </label>
            <label className={styles.radioOption}>
              <input
                type="radio"
                name="springBootVersion"
                value="3.4.1"
                checked={springBootVersion === "3.4.1"}
                onChange={handleSpringBootVersionChange}
              />
              <span>3.4.1</span>
            </label>
            <label className={styles.radioOption}>
              <input
                type="radio"
                name="springBootVersion"
                value="3.3.7 (SNAPSHOT)"
                checked={springBootVersion === "3.3.7 (SNAPSHOT)"}
                onChange={handleSpringBootVersionChange}
              />
              <span>3.3.7 (SNAPSHOT)</span>
            </label>
            <label className={styles.radioOption}>
              <input
                type="radio"
                name="springBootVersion"
                value="3.3.6"
                checked={springBootVersion === "3.3.6"}
                onChange={handleSpringBootVersionChange}
              />
              <span>3.3.6</span>
            </label>
          </div>
        </div>
      </div>
    </div>
  );
}

export default RadioForm;
