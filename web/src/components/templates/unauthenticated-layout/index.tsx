import { ReactNode } from "react";
import HeaderComponent from "../../header";

type UnauthenticatedLayoutProps = {
  children?: ReactNode;
};

const UnauthenticatedLayout = ({ children }: UnauthenticatedLayoutProps) => {
  return (
    <div className="flex flex-col max-w-full w-full h-screen bg-slate-300">
      <HeaderComponent>
        <a className="text-sm font-light" href="/app/auth/register">
          Cadastre-se
        </a>
        <a className="text-sm font-light" href="/app/auth/login">
          Entrar
        </a>
      </HeaderComponent>
      <main className="flex flex-col justify-center items-center w-full h-screen p-5">
        {children}
      </main>
    </div>
  );
}

export default UnauthenticatedLayout;
