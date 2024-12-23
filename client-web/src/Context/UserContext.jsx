import { createContext, useEffect, useState } from 'react';

export const UserContext = createContext();

const UserProvider = ({ children }) => {
  const [token,setToken] = useState(null);

  useEffect(() => {
    setToken(localStorage.getItem('token'))
    console.log(localStorage.getItem('token'))
  },[])


  return (
    <UserContext.Provider value={{ token }}>
      {children}
    </UserContext.Provider>
  );
};

export default UserProvider;
