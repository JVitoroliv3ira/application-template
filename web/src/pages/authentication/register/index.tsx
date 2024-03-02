import HeaderComponent from "../../../components/header";

const RegisterPage = () => {
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
    </div>
  );
}

export default RegisterPage;
