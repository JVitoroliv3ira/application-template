const RegisterPage = () => {
  return (
    <div className="flex flex-col max-w-full w-full h-screen bg-slate-300">
      <header className="flex items-center justify-between w-full p-5 bg-slate-100">
        <a href="/app/main" className="text-lg">
          Application Template
        </a>
        <div className="space-x-4">
          <a className="text-sm font-light" href="/app/auth/register">
            Cadastre-se
          </a>
          <a className="text-sm font-light" href="/app/auth/login">
            Entrar
          </a>
        </div>
      </header>
    </div>
  );
}

export default RegisterPage;
