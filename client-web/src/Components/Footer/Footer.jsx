import styles from "./Footer.module.css";

function Footer({ theme }) {
  return (
    <div className={styles.container} style={{ backgroundColor: theme == "dark" ? "#262a2d" : "#ecf2f2" }}>
      <button className={`${styles.button} ${theme == "light" ? "light" : "dark"}`} onClick={() => alert("Button Clicked!")}>
        GENERATE
      </button>
    </div>
  )
}

export default Footer;