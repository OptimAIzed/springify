import { useState } from 'react';
import styles from './App.module.css';

//Components
import HomePage from "./Pages/HomePage/HomePage";
import DependencyPage from './Pages/DependencyPage/DependencyPage';

function App() {
  return (
      
      <div className={styles.center}>
        <DependencyPage />
      </div >
  
  );
}

export default App;
