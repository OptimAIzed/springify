import styles from "./Footer.module.css";

function Footer() {
  return (
    <div className={styles.container}>
      <button className={styles.button} onClick={() => alert("Button Clicked!")}>
        Generate
      </button>
    </div>
  )
}

export default Footer;