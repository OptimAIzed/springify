import axios from "axios";

export const GetProjects = async (id) => {
    try {
        const token = localStorage.getItem('token');
        const response = await axios.get(`http://localhost:8888/api/projects/user/${id}`,{
            headers: {
              Authorization: `Bearer ${token}`,
            }}
        );
        if (response.status === 200) {
            return response.data;
        } else {
            throw new Error(`projects failed with status: ${response.status}`);
        }
    } catch (error) {
        throw new Error(`Error: ${error.response ? error.response.data : error.message}`);
    }
}

export const DeleteAllProjects = async (id) => {
    try {
        const token = localStorage.getItem('token');
        const response = await axios.delete(`http://localhost:8888/api/projects/deleteAll/${id}`,{
            headers: {
              Authorization: `Bearer ${token}`,
            }}
        );
        if (response.status === 204) {
            return response.data;
        } else {
            throw new Error(`projects failed with status: ${response.status}`);
        }
    } catch (error) {
        throw new Error(`Error: ${error.response ? error.response.data : error.message}`);
    }
}