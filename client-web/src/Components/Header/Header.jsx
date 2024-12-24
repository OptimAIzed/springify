import styles from "./Header.module.css";

//Images
import LogoDark from '../../assets/images/logo.png';
import LogoLight from '../../assets/images/logo-white.png';
import { useContext } from "react";
import { UserContext } from "../../Context/UserContext";
import { useNavigate } from "react-router";

function Header({ theme }) {
  const { userInfo, updateUserContext } = useContext(UserContext);
  const navigate = useNavigate();

  function logoutButton() {
    localStorage.removeItem('token');
    localStorage.removeItem('user_info');

    updateUserContext();

    navigate('/login');
  }

  return (
    <>
      <div className={styles.container}>
        <img
          className={styles.logo}
          src={theme === 'dark' ? LogoLight : LogoDark}
          alt="Logo"
        />
        <div>
          <h1 className={styles.greetings}>
            Hi {userInfo?.firstname || "Guest"}!
          </h1>
          <button className={styles.button} onClick={logoutButton}>
            Logout
          </button>
        </div>
      </div>
      <div className={styles.line}></div>
    </>
  );
}

export default Header;
