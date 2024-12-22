import { login } from "../../services/authService";
import styles from "./AuthPage.module.css";
import { useState } from "react";
//components
const AuthPage = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        const response = await login(email, password);
        if (response) {
            console.log("Login successful:", response);
        } else {
            console.error("Login failed");
        }
    };

    return (
        <div className={styles.authPage}>
            <form onSubmit={handleSubmit} className={styles.loginForm}>
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
            </form>
        </div>
    );
};

export default AuthPage;