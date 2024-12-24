import styles from "./ProjectForm.module.css";

function ProjectForm({ formData, handleChange, setField }) {
  return (
    <div className={styles.container}>
      <form className={styles.form}>
        {/* Project Metadata */}
        <h4>Project Metadata</h4>
        <div className={styles.formGroup}>
          <label>Group</label>
          <input
            type="text"
            name="groupId"
            value={formData.groupId}
            onChange={(event) => { handleChange(event); setField("packageName", `${event.target.value}.${formData.artifactId}`); }}
            className={styles.input}
          />
        </div>
        <div className={styles.formGroup}>
          <label>Artifact</label>
          <input
            type="text"
            name="artifactId"
            value={formData.artifactId}
            onChange={(event) => { handleChange(event); setField("name", event.target.value); setField("packageName", `${formData.groupId}.${event.target.value}`); setField("baseDir", event.target.value); }}
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
                value="jar"
                checked={formData.packaging === "jar"}
                onChange={handleChange}
              />
              Jar
            </label>
            <label>
              <input
                type="radio"
                name="packaging"
                value="war"
                checked={formData.packaging === "war"}
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
                value="23"
                checked={formData.javaVersion === "23"}
                onChange={handleChange}
              />
              23
            </label>
            <label>
              <input
                type="radio"
                name="javaVersion"
                value="21"
                checked={formData.javaVersion === "21"}
                onChange={handleChange}
              />
              21
            </label>
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
          </div>
        </div>
      </form>
    </div>
  );
}

export default ProjectForm;
