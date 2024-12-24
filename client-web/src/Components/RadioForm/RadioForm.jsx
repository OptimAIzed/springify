import { useState } from "react";
import styles from "./RadioForm.module.css";

function RadioForm({ formData, handleChange }) {
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
                name="type"
                value="gradle-project"
                checked={formData.type === "gradle-project"}
                onChange={handleChange}
              />
              <span>Gradle - Groovy</span>
            </label>
            <label className={styles.radioOption}>
              <input
                type="radio"
                name="type"
                value="gradle-project-kotlin"
                checked={formData.type === "gradle-project-kotlin"}
                onChange={handleChange}
              />
              <span>Gradle - Kotlin</span>
            </label>
            <label className={styles.radioOption}>
              <input
                type="radio"
                name="type"
                value="maven-project"
                checked={formData.type === "maven-project"}
                onChange={handleChange}
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
                value="java"
                checked={formData.language === "java"}
                onChange={handleChange}
              />
              <span>Java</span>
            </label>
            <label className={styles.radioOption}>
              <input
                type="radio"
                name="language"
                value="kotlin"
                checked={formData.language === "kotlin"}
                onChange={handleChange}
              />
              <span>Kotlin</span>
            </label>
            <label className={styles.radioOption}>
              <input
                type="radio"
                name="language"
                value="groovy"
                checked={formData.language === "groovy"}
                onChange={handleChange}
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
                name="bootVersion"
                value="3.4.2 (SNAPSHOT)"
                checked={formData.bootVersion === "3.4.2-SNAPSHOT"}
                onChange={handleChange}
              />
              <span>3.4.2 (SNAPSHOT)</span>
            </label>
            <label className={styles.radioOption}>
              <input
                type="radio"
                name="bootVersion"
                value="3.4.1"
                checked={formData.bootVersion === "3.4.1"}
                onChange={handleChange}
              />
              <span>3.4.1</span>
            </label>
            <label className={styles.radioOption}>
              <input
                type="radio"
                name="bootVersion"
                value="3.3.8 (SNAPSHOT)"
                checked={formData.bootVersion === "3.3.8-SNAPSHOT"}
                onChange={handleChange}
              />
              <span>3.3.8 (SNAPSHOT)</span>
            </label>
            <label className={styles.radioOption}>
              <input
                type="radio"
                name="bootVersion"
                value="3.3.7"
                checked={formData.bootVersion === "3.3.7"}
                onChange={handleChange}
              />
              <span>3.3.7</span>
            </label>
          </div>
        </div>
      </div>
    </div>
  );
}

export default RadioForm;
