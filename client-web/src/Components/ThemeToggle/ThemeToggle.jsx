import styles from "./ThemeToggle.module.css";

//icons
import LightModeIcon from '@mui/icons-material/LightMode';
import DarkModeIcon from '@mui/icons-material/DarkMode';

function ThemeToggle({ theme, toggle }) {
  const setLightTheme = () => {
    toggle(() => ("light"));
    document.documentElement.setAttribute("data-theme", "light");
  };

  const setDarkTheme = () => {
    toggle(() => ("dark"));
    document.documentElement.setAttribute("data-theme", "dark");
  };

  return (
    <div className={styles.container}>
      <div className={styles.holder}>
        <div className={styles.lightToggle} onClick={setLightTheme}>
          <LightModeIcon sx={{ color: 'white', fontSize: 25 }} /></div>
        <div className={styles.darkToggle} onClick={setDarkTheme}>
          <DarkModeIcon sx={{ color: 'black', fontSize: 25 }} />
        </div>
      </div>
    </div>
  );
}

export default ThemeToggle;
