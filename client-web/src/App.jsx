//import { useState } from 'react';
import styles from './App.module.css';
import ThemeToggle from './Components/ThemeToggle/ThemeToggle';

//Components
import HomePage from "./Pages/HomePage/HomePage";

function App() {
  return (
    <div className={styles.container}>
      <div className={styles.left}>
        left
      </div>
      <div className={styles.center}>
        <HomePage />
      </div>
      <div className={styles.right}>
      <ThemeToggle />
      </div>
    </div>
  );
}

export default App;
