import { useState } from 'react';
import styles from './App.module.css';

//Components
import HomePage from "./Pages/HomePage/HomePage";
import Loading from "./Components/Loading/Loading";
function App() {
  return (
    <div className={styles.container}>
      <div className={styles.left}>
        left
      </div>
      <div className={styles.center}>
        {/*<HomePage />*/}
        <Loading/>
      </div >
      <div className={styles.right}>
        right
      </div >
    </div>
  );
}

export default App;
