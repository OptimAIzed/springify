import { useState } from 'react';
import styles from './App.module.css';
import ThemeToggle from './Components/ThemeToggle/ThemeToggle';

//Components
import HomePage from "./Pages/HomePage/HomePage";
import Loading from "./Components/Loading/Loading";
import LeftPanel from './Components/LeftPanel/LeftPanel';

function App() {
  const [theme, setTheme] = useState("light");

  return (
    <div className={`${styles.container} ${theme == 'light' ? "light" : "dark"}`}>
      <div className={styles.left}>
        <div className={styles.content}>
          <LeftPanel theme={theme} />
        </div>
      </div>
      <div className={styles.center}>
        <HomePage theme={theme} />
      </div>
      <div className={styles.right}>
        <div className={styles.content}>
          <ThemeToggle theme={theme} toggle={setTheme} />
        </div>
      </div>
    </div>
  );
}

export default App;
