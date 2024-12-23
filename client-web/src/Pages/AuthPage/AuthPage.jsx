import { useNavigate } from "react-router";
import { UserContext } from "../../Context/UserContext";
import { login } from "../../services/authService";
import styles from "./AuthPage.module.css";
import { useContext, useState } from "react";
const AuthPage = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const { token } = useContext(UserContext);
    const navigate = useNavigate();


    const handleSubmit = async (e) => {
        try {
            e.preventDefault();
            console.log("this is the token  : " + token)
            const response =  await login(email, password);
            console.log(response)
            localStorage.setItem('token', response.access_token)
            navigate("/");
        } catch(error) {
            setError("Error logging in" + error);
        }
    };

    return (
        <div className={styles.authPage}>
            <form onSubmit={handleSubmit} className={styles.loginForm}>
                {error && <div className={styles.errorMessage}>{error}</div>}
                <div className={styles.formGroup}>
                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        id="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div className={styles.formGroup}>
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className={styles.submitButton}>Login</button>
                <a className={styles.alreadyLink} href="/register">Already a member ? </a>
            </form>
        </div>
    );
};

export default AuthPage;
