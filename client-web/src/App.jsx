import { useState } from 'react';
import styles from './App.module.css';
import ThemeToggle from './Components/ThemeToggle/ThemeToggle';

//Components
import HomePage from "./Pages/HomePage/HomePage";
import HistoryPage from './Pages/HistoryPage/HistoryPage';

//Images
import LeftPanel from './Components/LeftPanel/LeftPanel';

function App() {
  const [theme, setTheme] = useState("light");
  const [history, setHistory] = useState(false);
  return (
    <div className={`${styles.container} ${theme == 'light' ? "light" : "dark"}`}>
      <div className={styles.left}>
        <div className={styles.content}>
          <LeftPanel theme={theme} history={history} setHistory={setHistory} />
        </div>
      </div>
      <div className={styles.center}>
        <HomePage theme={theme} />
        {history && <HistoryPage theme={theme} closeAction={setHistory} />}
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
