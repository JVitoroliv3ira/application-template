import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import RegisterPage from '../pages/authentication/register';
import { ToastContainer } from 'react-toastify';
import LoginPage from '../pages/authentication/login';
import MainPage from '../pages/app/main';
import AuthGuard from './guards/auth-guard';

const AppRouter = () => {
  return (
    <Router>
      <ToastContainer />
      <Routes>
        <Route path='/app/auth/register' element={<RegisterPage />} />
        <Route path='/app/auth/login' element={<LoginPage />} />
        <Route path="/app/main" element={
          <AuthGuard>
            <MainPage />
          </AuthGuard>
        } />
      </Routes>
    </Router>
  );
}

export default AppRouter;
