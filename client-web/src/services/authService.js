import axios from 'axios';

export const login = async (email, password) => {
    try {
        const response = await axios.post(`http://localhost:8888/api/auth/signin`, {
            email,
            password
        });
        
        if (response.status === 200) {
            console.log("Login successful:", response.data);
            return response.data; 
        } else {
            console.error("Login failed with status:", response.status);
            return null;
        }
    } catch (error) {
        if (error.response) {
            console.error("Error response:", error.response.data);
        } else if (error.request) {
            console.error("Error request:", error.request);
        } else {
            console.error("Error:", error.message);
        }

        return null;
    }
};

export const Signup = async (email, password) => {
    try {
        // Make POST request to signup
        const response = await axios.post(`${import.meta.env.VITE_BACKEND_URL_AUTH}/api/auth/signup`, {
            "firstname": "ouabiba",
            "lastname": "hamza",
            "email": email,
            "password": password,
            "gender": "false"
        });
        
        // Assuming the response has a data object with authentication token or user details
        if (response.status === 200) {
            console.log("Signup successful:", response.data);
            return response.data; // Return user data or token
        } else {
            console.error("Signup failed with status:", response.status);
            return null;
        }
    } catch (error) {
        // Handle errors (e.g., network issues, 400/500 responses)
        if (error.response) {
            // Server responded with a status other than 2xx
            console.error("Error response:", error.response.data);
        } else if (error.request) {
            // Request was made but no response received
            console.error("Error request:", error.request);
        } else {
            // Something else happened
            console.error("Error:", error.message);
        }

        return null;
    }
}
