import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import RegisterPage from '../pages/authentication/register';

const AppRouter = () => {
  return (
    <Router>
      <Routes>
        <Route path='/app/auth/register' element={<RegisterPage />} />
      </Routes>
    </Router>
  );
}

export default AppRouter;
