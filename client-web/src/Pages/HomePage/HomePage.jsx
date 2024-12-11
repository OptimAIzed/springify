import styles from "./HomePage.module.css";
import Header from "../../Components/Header/Header";
import Footer from "../../Components/Footer/Footer";
import RadioForm from "../../Components/RadioForm/RadioForm";
import PhotoDrop from "../../Components/PhotoDrop/PhotoDrop";
import ProjectForm from "../../Components/ProjectForm/ProjectForm";

//components

function HomePage() {
  return (
    <div className={styles.container}>
      <Header />
      <div className={styles.content}>
        <div className={styles.leftColumn}>
          <RadioForm />
          <ProjectForm />
        </div>
        <div className={styles.rightColumn}>
          <PhotoDrop />
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default HomePage;
