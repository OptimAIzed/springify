import { useRouteError, useNavigate } from "react-router-dom";
import styles from './ErrorPage.module.css';

export default function ErrorPage() {
  const error = useRouteError();
  const navigate = useNavigate();

  const handleGoBack = () => {
    navigate("/");
  };

  return (
    <div className={styles.errorContainer}>
      <h1 className={styles.errorCode}>Oops! 🚀</h1>
      <p className={styles.errorMessage}>Sorry, a <i>{error.statusText || error.message}</i> error has occurred.</p>
      <button className={styles.errorButton} onClick={handleGoBack}>Go Back</button>
    </div>
  );
}