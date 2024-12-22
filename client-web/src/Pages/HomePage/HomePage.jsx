import styles from "./HomePage.module.css";
import Header from "../../Components/Header/Header";
import Footer from "../../Components/Footer/Footer";
import RadioForm from "../../Components/RadioForm/RadioForm";
import DependencyPage from '../../Pages/DependencyPage/DependencyPage';
import PhotoDrop from "../../Components/PhotoDrop/PhotoDrop";
import ProjectForm from "../../Components/ProjectForm/ProjectForm";

//components

function HomePage({ theme }) {
  return (
    <div className={styles.container}>
      <Header theme={theme} />
      <div className={styles.content}>
        <div className={styles.leftColumn}>
          <RadioForm />
          <ProjectForm />
        </div>
        <div className={styles.rightColumn}>
          <DependencyPage />
          <PhotoDrop />
        </div>
      </div>
      <Footer theme={theme} />
    </div>
  );
}

export default HomePage;
