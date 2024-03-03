import { ReactNode } from "react";
import HeaderComponent from "../../header";
import { removeEmail, removeToken } from "../../../core/services/user-authentication.service";
import { useNavigate } from "react-router-dom";

type AuthenticatedLayoutProps = {
  children?: ReactNode;
};

const AuthenticatedLayout = ({ children }: AuthenticatedLayoutProps) => {
  const navigate = useNavigate();

  const logout = (): void => {
    removeToken();
    removeEmail();
    navigate('/app/auth/login');
  }

  return (
    <div className="flex flex-col max-w-full w-full h-screen bg-slate-300">
      <HeaderComponent>
        <button
          className="bg-transparent text-sm font-light"
          onClick={logout}
        >
          Sair
        </button>
      </HeaderComponent>
      <main className="flex flex-col justify-center items-center w-full h-screen p-5">
        {children}
      </main>
    </div>
  );
}

export default AuthenticatedLayout;
