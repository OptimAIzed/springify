import { useState } from 'react';
import './App.css';

function App() {
  const [projectDetails, setProjectDetails] = useState({
    language: 'Java',
    framework: 'Spring Boot',
    dependencies: []
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProjectDetails({ ...projectDetails, [name]: value });
  };

  const handleAddDependency = () => {
    setProjectDetails({
      ...projectDetails,
      dependencies: [...projectDetails.dependencies, 'New Dependency']
    });
  };

  const handleGenerate = () => {
    alert('Project generated with details: ' + JSON.stringify(projectDetails));
  };

  return (
    <div className="app">
      <header className="header">
        <h1>Spring Project Generator</h1>
        <p>Create a Spring Boot project with ease</p>
      </header>

      <main className="container">
        <section className="form-section">
          <h2>Project Configuration</h2>

          <div className="form-group">
            <label htmlFor="language">Language</label>
            <select name="language" value={projectDetails.language} onChange={handleChange}>
              <option value="Java">Java</option>
              <option value="Kotlin">Kotlin</option>
              <option value="Groovy">Groovy</option>
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="framework">Framework</label>
            <select name="framework" value={projectDetails.framework} onChange={handleChange}>
              <option value="Spring Boot">Spring Boot</option>
              <option value="Spring MVC">Spring MVC</option>
            </select>
          </div>

          <button className="btn-add" onClick={handleAddDependency}>
            Add Dependency
          </button>

          <ul className="dependency-list">
            {projectDetails.dependencies.map((dep, index) => (
              <li key={index}>{dep}</li>
            ))}
          </ul>

          <button className="btn-generate" onClick={handleGenerate}>
            Generate Project
          </button>
        </section>
      </main>

      <footer className="footer">
        <p>© 2024 SpringifyUML. All Rights Reserved.</p>
      </footer>
    </div>
  );
}

export default App;
