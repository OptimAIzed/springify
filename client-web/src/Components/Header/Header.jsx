import styles from "./Header.module.css";

function Header() {
  return (
    <div className={styles.container}>
      <img src="logopath/logo.png" alt="Logo" className={styles.logo} />
    </div>
  )
}

export default Header;