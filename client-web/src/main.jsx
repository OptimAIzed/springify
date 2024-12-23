import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { createBrowserRouter, RouterProvider } from "react-router-dom";

// Pages
import App from './App.jsx'
import ErrorPage from "./pages/ErrorPage/ErrorPage.jsx";
import AuthPage from './Pages/AuthPage/AuthPage.jsx';
import UserProvider from './Context/UserContext.jsx';
import RegisterPage from './Pages/RegisterPage/RegisterPage.jsx';

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <ErrorPage />,
  },
  {
    path: "/login",
    element: <AuthPage />,
    errorElement: <ErrorPage />,
  },
  {
    path: "/register",
    element: <RegisterPage />,
    errorElement: <ErrorPage />
  }
]);

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <UserProvider>
      <RouterProvider router={router} />
    </UserProvider>
  </StrictMode>
);
