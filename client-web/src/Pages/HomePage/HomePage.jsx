import styles from "./HomePage.module.css";
import Header from "../../Components/Header/Header";
import Footer from "../../Components/Footer/Footer";
import RadioForm from "../../Components/RadioForm/RadioForm";
import PhotoDrop from "../../Components/PhotoDrop/PhotoDrop";
import ProjectForm from "../../Components/ProjectForm/ProjectForm";
import { UserContext } from "../../Context/UserContext";
import ErrorPage from "../ErrorPage/ErrorPage";
import { useContext } from "react";

//components

function HomePage({ theme }) {
  const {token} = useContext(UserContext)
  return (
     token != null ? (
      <div className={styles.container}>
      <Header theme={theme} />
      <div className={styles.content}>
        <div className={styles.leftColumn}>
          <RadioForm />
          <ProjectForm />
        </div>
        <div className={styles.rightColumn}>
          <PhotoDrop />
        </div>
      </div>
      <Footer theme={theme} />
    </div>
     ) : (
       <div>You must login</div>
     )
  );
}

export default HomePage;
