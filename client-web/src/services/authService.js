import axios from 'axios';

export const login = async (email, password) => {
    try {
        const response = await axios.post(`http://localhost:8888/api/auth/signin`, {
            email,
            password
        });
        console.log("this is the response : " + response)
        if (response.status === 200) {
            console.log("Login successful:", response.data);
            return response.data; 
        } else {
            throw new Error(`SIGNIN failed with status: ${response.status}`);
        }
    } catch (error) {
        console.log(error)
        throw new Error(`Error: ${error.response ? error.response.data : error.message}`);
    }
};

export const Signup = async (data) => {
    try {
        console.log(data)
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

