import { useState } from 'react';
import styles from './App.module.css';
import ThemeToggle from './Components/ThemeToggle/ThemeToggle';

//Components
import HomePage from "./Pages/HomePage/HomePage";
import HistoryPage from './Pages/HistoryPage/HistoryPage';

//Images
import HamburgerDark from "./assets/images/hamburger.png";
import HamburgerLight from "./assets/images/hamburger-white.png";

function App() {
  const [theme, setTheme] = useState("light");

  return (
    <div className={styles.container}>
      <div className={styles.left}>
        <div className={styles.content}>
          <img className={styles.burger} src={theme == "light" ? HamburgerDark : HamburgerLight} alt='hamburger icon' />
          <hr />
        </div>
      </div>
      <div className={styles.center}>
       
        <HistoryPage theme={theme} />
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
