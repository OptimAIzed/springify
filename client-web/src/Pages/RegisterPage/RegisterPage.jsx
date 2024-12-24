import React, { useContext, useEffect, useState } from 'react';
import styles from './RegisterPage.module.css';
import { useNavigate } from 'react-router';
import { Signup } from '../../services/authService';
import { UserContext } from '../../Context/UserContext';

const RegisterPage = () => {
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        confirmPassword: '',
        gender: ''
    });
    const { updateUserContext, token } = useContext(UserContext);
    const navigate = useNavigate()
    const [error,setError] = useState(null)
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        try {
            e.preventDefault();
            if(formData.password !== formData.confirmPassword) {
                alert("Passwords do not match");
                return;
            }

            const response = await Signup(formData)
            const userInfo = {
                firstname: response.user_info.firstname,
                lastname: response.user_info.lastname, 
            };

            localStorage.setItem('user_info', JSON.stringify(userInfo));
            localStorage.setItem('token', response.access_token)
            updateUserContext()
            navigate('/')
        } catch(error) {
            setError("Signup failed")
        }         
    };

    useEffect(() => {
        if(token != null) {
          navigate('/')
        }
    },[])
    return (
        <div className={styles.container}>
            <div className={styles.registerCard}>
                <h2 className={styles.registerTitle}>Register</h2>
                <form onSubmit={handleSubmit}>
                    {error &&  <div className={styles.errorMessage}>{error}</div>}
                    <div>
                        <label>First Name:</label>
                        <input
                            className={styles.inputField}
                            type="text"
                            name="firstName"
                            value={formData.firstName}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div>
                        <label>Last Name:</label>
                        <input
                            className={styles.inputField}
                            type="text"
                            name="lastName"
                            value={formData.lastName}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div>
                        <label>Email:</label>
                        <input
                            className={styles.inputField}
                            type="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div>
                        <label>Password:</label>
                        <input
                            className={styles.inputField}
                            type="password"
                            name="password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div>
                        <label>Confirm Password:</label>
                        <input
                            className={styles.inputField}
                            type="password"
                            name="confirmPassword"
                            value={formData.confirmPassword}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div>
                        <label>Gender:</label>
                        <select
                            className={styles.selectField}
                            name="gender"
                            value={formData.gender}
                            onChange={handleChange}
                            required
                        >
                            <option value="">Select Gender</option>
                            <option value="male">Male</option>
                            <option value="female">Female</option>
                        </select>
                    </div>
                    <button type="submit" className={styles.submitBtn}>Register</button>
                </form>
            </div>
        </div>
    );
};

export default RegisterPage;
