import  { createContext, useEffect, useState } from 'react';

export const UserContext = createContext();

const UserProvider = ({ children }) => {
  const [token, setToken] = useState(localStorage.getItem('token'));  
  const [userInfo, setUserInfo] = useState(JSON.parse(localStorage.getItem('user_info')));

  useEffect(() => {
    const storedToken = localStorage.getItem('token');
    const storedUserInfo = JSON.parse(localStorage.getItem('user_info'));
    setToken(storedToken);
    setUserInfo(storedUserInfo);
  }, []);

  const updateUserContext = () => {
    const storedToken = localStorage.getItem('token');
    const storedUserInfo = JSON.parse(localStorage.getItem('user_info'));
    setToken(storedToken);
    setUserInfo(storedUserInfo);
  };

  return (
    <UserContext.Provider value={{ token, userInfo, updateUserContext }}>
      {children}
    </UserContext.Provider>
  );
};

export default UserProvider;
