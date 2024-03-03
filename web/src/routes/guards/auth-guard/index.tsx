import { ReactNode } from "react";
import { getToken } from "../../../core/services/user-authentication.service";
import { Navigate } from "react-router-dom";

type AuthGuardProps = {
  children: ReactNode;
};

const AuthGuard = ({ children }: AuthGuardProps) => {
  const isAuthenticated = (): boolean => {
    const token =  getToken();
    return token !== null && token !== undefined && token.trim().length > 0;
  }

  if (!isAuthenticated()) {
    return <Navigate to="/app/auth/login" replace />;
  }

  return children;
}

export default AuthGuard;
