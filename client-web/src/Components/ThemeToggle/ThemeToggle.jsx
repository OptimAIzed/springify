import { useState } from "react";
import styles from "./ThemeToggle.module.css";

function ThemeToggle() {
  const [theme, setTheme] = useState("light");

  const toggleTheme = () => {
    setTheme((prevTheme) => (prevTheme === "light" ? "dark" : "light"));
    document.documentElement.setAttribute("data-theme", theme === "light" ? "dark" : "light");
  };

  return (
    <div className={styles.container}>
      <button className={styles.toggleButton} onClick={toggleTheme}>
        {theme === "light" ? "🌞" : "🌜"}
      </button>
    </div>
  );
}

export default ThemeToggle;
