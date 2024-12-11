import { useState } from "react";
import styles from "./ProjectForm.module.css";

function ProjectForm() {
  const [formData, setFormData] = useState({
    group: "com.example",
    artifact: "demo",
    name: "demo",
    description: "Demo project for Spring Boot",
    packageName: "com.example.demo",
    packaging: "Jar",
    javaVersion: "17",
  });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  return (
    <div className={styles.container}>
      <form className={styles.form}>
        {/* Project Metadata */}
        <h4>Project Metadata</h4>
        <div className={styles.formGroup}>
          <label>Group</label>
          <input
            type="text"
            name="group"
            value={formData.group}
            onChange={handleChange}
            className={styles.input}
          />
        </div>
        <div className={styles.formGroup}>
          <label>Artifact</label>
          <input
            type="text"
            name="artifact"
            value={formData.artifact}
            onChange={handleChange}
            className={styles.input}
          />
        </div>
        <div className={styles.formGroup}>
          <label>Name</label>
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            className={styles.input}
          />
        </div>
        <div className={styles.formGroup}>
          <label>Description</label>
          <input
            type="text"
            name="description"
            value={formData.description}
            onChange={handleChange}
            className={styles.input}
          />
        </div>
        <div className={styles.formGroup}>
          <label>Package Name</label>
          <input
            type="text"
            name="packageName"
            value={formData.packageName}
            onChange={handleChange}
            className={styles.input}
          />
        </div>

        {/* Packaging */}
        <div className={styles.formGroup}>
          <label>Packaging</label>
          <div className={styles.radioGroup}>
            <label>
              <input
                type="radio"
                name="packaging"
                value="Jar"
                checked={formData.packaging === "Jar"}
                onChange={handleChange}
              />
              Jar
            </label>
            <label>
              <input
                type="radio"
                name="packaging"
                value="War"
                checked={formData.packaging === "War"}
                onChange={handleChange}
              />
              War
            </label>
          </div>
        </div>

        {/* Java Version */}
        <div className={styles.formGroup}>
          <label>Java version</label>
          <div className={styles.radioGroup}>
            <label>
              <input
                type="radio"
                name="javaVersion"
                value="17"
                checked={formData.javaVersion === "17"}
                onChange={handleChange}
              />
              17
            </label>
            <label>
              <input
                type="radio"
                name="javaVersion"
                value="11"
                checked={formData.javaVersion === "11"}
                onChange={handleChange}
              />
              11
            </label>
            <label>
              <input
                type="radio"
                name="javaVersion"
                value="8"
                checked={formData.javaVersion === "8"}
                onChange={handleChange}
              />
              8
            </label>
          </div>
        </div>

        {/* Submit Button 
        <button type="submit" className={styles.submitButton}>
          Generate Project
        </button>*/}
      </form>
    </div>
  );
}

export default ProjectForm;
