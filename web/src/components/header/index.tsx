import { ReactNode } from 'react';

type HeaderComponentProps = {
  children?: ReactNode;
};

const HeaderComponent = ({ children }: HeaderComponentProps) => {
  return (
    <header className="flex items-center justify-between w-full p-5 bg-slate-100">
      <a href="/app/main" className="text-lg">
        Application Template
      </a>
      <div className="space-x-4">
        {children}
      </div>
    </header>
  );
}

export default HeaderComponent;
