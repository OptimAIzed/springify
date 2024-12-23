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

export const Signup = async (data) => {
    try {
        const response = await axios.post("http://localhost:8888/api/auth/signup", {
            "firstname": data["firstName"],
            "lastname": data["lastName"],
            "email": data["email"],
            "password": data["password"],
            "gender": data["gender"] == "male" ? 0 : 1
        });

        if (response.status === 201) {
            console.log("Signup successful:", response.data);
            return response.data;
        } else {
            throw new Error(`Signup failed with status: ${response.status}`);
        }
    } catch (error) {
        throw new Error(`Error: ${error.response ? error.response.data : error.message}`);
    }
}
