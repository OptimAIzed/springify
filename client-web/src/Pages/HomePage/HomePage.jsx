import styles from "./HomePage.module.css";
import { useState } from "react";
import axios from "axios";
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
  const [image, setImage] = useState(null); 
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
    image: ""
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
    const baseUrl = "http://localhost:8888/api/projects/generate";
  
    const token = localStorage.getItem('token');
  
    if (!image) {
      alert("Please upload a UML class diagram image");
      return;
    }
  
    const multipartData = new FormData();
    Object.keys(formData).forEach(key => {
      multipartData.append(key, formData[key]);
    });
  
    if (dependencies && dependencies.length > 0) {
      multipartData.append('dependencies', dependencies.join(','));
    }
  
    multipartData.append('image', image);
  
    axios.post(baseUrl, multipartData, {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'multipart/form-data',
      },
      responseType: 'blob', 
    })
      .then(response => {
        const blob = new Blob([response.data], { type: 'application/zip' });
        const downloadUrl = URL.createObjectURL(blob);
  
        const link = document.createElement('a');
        link.href = downloadUrl;
        link.download = `${formData.baseDir}.zip`;
        document.body.appendChild(link);
        link.click();
  
        link.remove();
        URL.revokeObjectURL(downloadUrl);
      })
      .catch(error => {
        console.error('Error downloading the zip file:', error);
      });
  };
  


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
            <PhotoDrop setImage={setImage} image={image} />
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
