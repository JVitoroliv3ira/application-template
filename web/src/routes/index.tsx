import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import RegisterPage from '../pages/authentication/register';
import { ToastContainer } from 'react-toastify';

const AppRouter = () => {
  return (
    <Router>
      <ToastContainer />
      <Routes>
        <Route path='/app/auth/register' element={<RegisterPage />} />
      </Routes>
    </Router>
  );
}

export default AppRouter;
