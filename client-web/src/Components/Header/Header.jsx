import styles from "./Header.module.css";

//Images
import Logo from '../../assets/images/logo.png';

function Header() {
  return (
    <div className={styles.container}>
      <img className={styles.logo} src={Logo} alt="Logo" />
    </div>
  )
}

export default Header;