import { useState } from 'react';
import styles from './App.module.css';

//Components
import HomePage from "./Pages/HomePage/HomePage";
import HistoryPage from './Pages/HistoryPage/HistoryPage';

function App() {
  return (
    <div className={styles.container}>
      <div className={styles.left}>
        left
      </div>
      <div className={styles.center}>
        {/*<HomePage />*/}
        <HistoryPage />
      </div >
      <div className={styles.right}>
        right
      </div >
    </div>
  );
}

export default App;
