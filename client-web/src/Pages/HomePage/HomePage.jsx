import styles from "./HomePage.module.css";
import { useState } from "react";

import Header from "../../Components/Header/Header";
import Footer from "../../Components/Footer/Footer";
import RadioForm from "../../Components/RadioForm/RadioForm";
import DependencyPage from '../../Pages/DependencyPage/DependencyPage';
import PhotoDrop from "../../Components/PhotoDrop/PhotoDrop";
import ProjectForm from "../../Components/ProjectForm/ProjectForm";
import { UserContext } from "../../Context/UserContext";
import { useContext, useEffect } from "react";
import { useNavigate } from "react-router";

function HomePage({ theme }) {
  const { token } = useContext(UserContext);
  const navigate = useNavigate()

  const [dependencies, setDependencies] = useState([]);
  const [formData, setFormData] = useState({
    groupId: "com.example",
    artifactId: "demo",
    name: "demo",
    description: "Demo project for Spring Boot",
    packageName: "com.example.demo",
    packaging: "jar",
    javaVersion: "17",
    type: "gradle-project",
    language: "java",
    bootVersion: "3.4.1",
    baseDir: "demo",
    dependencies: "",
  });

  const setField = (key, value) => {
    setFormData((prev) => ({
      ...prev,
      [key]: value,
    }));
  }

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const generate = () => {
    const baseUrl = "https://start.spring.io/starter.zip";
    const queryString = new URLSearchParams(formData).toString();
    const fullUrl = `${baseUrl}?${queryString}${dependencies.join(',')}`;

    const a = document.createElement("a");
    a.href = fullUrl;
    a.download = "spring-boot-project.zip";
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
  }

  useEffect(() => {
    if (token == null) {
      navigate('/login')
    }
  }, [])

  return (
    token != null ? (
      <div className={styles.container}>
        <Header theme={theme} />
        <div className={styles.content}>
          <div className={styles.leftColumn}>
            <RadioForm formData={formData} handleChange={handleChange} />
            <ProjectForm formData={formData} handleChange={handleChange} setField={setField} />
          </div>
          <div className={styles.rightColumn}>
            <DependencyPage dependencies={dependencies} setDependencies={setDependencies} />
            <PhotoDrop />
          </div>
        </div>
        <Footer theme={theme} onClick={generate} />
      </div>
    ) : (
      <div className={styles.errorMessage}>You must login</div>
    )
  );
}

export default HomePage;
