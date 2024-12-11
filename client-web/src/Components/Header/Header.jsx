import styles from "./Header.module.css";

//Images
import LogoDark from '../../assets/images/logo.png';
import LogoLight from '../../assets/images/logo-white.png';

function Header({ theme }) {
  return (
    <>
      <div className={styles.container}>
        <img className={styles.logo} src={theme == 'dark' ? LogoLight : LogoDark} alt="Logo" />
      </div>
      <div className={styles.line}></div>
    </>
  )
}

export default Header;