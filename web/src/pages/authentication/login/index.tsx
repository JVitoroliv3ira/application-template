import { Field, Form, Formik } from "formik";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { z } from "zod";

import UnauthenticatedLayout from "../../../components/templates/unauthenticated-layout";

import UserAuthenticationRequestDTO, { validationSchema } from "../../../core/dtos/requests/user-authentication-request.dto";
import ApiResponseDTO from "../../../core/dtos/responses/api-response.dto";
import AuthenticatedUserResponseDTO from "../../../core/dtos/responses/authenticated-user-response.dto";

import userAuthenticationService, { saveEmail, saveToken } from "../../../core/services/user-authentication.service";

import loadingIcon from '../../../global/assets/dots.svg';

const LoginPage = () => {
  const [loading, setLoading] = useState<boolean>(false);
  const navigate = useNavigate();
  const initialValues = {
    email: '',
    password: ''
  } as UserAuthenticationRequestDTO;

  const validateRequest = (request: UserAuthenticationRequestDTO) => {
    try {
      validationSchema.parse(request);
      return null;
    } catch (e) {
      if (e instanceof z.ZodError) {
        return e.errors.map(err => err.message);
      }
      return ["Erro de validação desconhecido."];
    }
  }

  const handleServiceResponse = (response: ApiResponseDTO<AuthenticatedUserResponseDTO>) => {
    if (response.hasErrors) {
      response.errors.forEach(error => toast.error(error));
      return;
    }
    saveEmail(response.content.email);
    saveToken(response.content.token);
    navigate('/app/main');
  }

  const handleSubmit = async (request: UserAuthenticationRequestDTO) => {
    setLoading(true);
    const validationErrors = validateRequest(request);
    if (validationErrors) {
      validationErrors.forEach(error => toast.error(error));
      setLoading(false);
      return;
    }
  
    try {
      const response = await userAuthenticationService(request);
      handleServiceResponse(response);
    } catch (e) {
      toast.error("Ocorreu um erro desconhecido durante a autenticação.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <UnauthenticatedLayout>
      <Formik initialValues={initialValues} onSubmit={handleSubmit}>
        <Form className="bg-slate-100 max-w-sm w-full shadow-md rounded px-8 pt-6 pb-8 mb-4">
          <div className="mb-4">
            <label
              className="block text-gray-700 text-sm font-bold mb-2"
              htmlFor="email"
            >
              Email
            </label>
            <Field
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              id="email"
              name="email"
              type="email"
              placeholder="Informe o seu email"
            />
          </div>
          <div className="mb-4">
            <label
              className="block text-gray-700 text-sm font-bold mb-2"
              htmlFor="email"
            >
              Senha
            </label>
            <Field
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              id="password"
              name="password"
              type="password"
              placeholder="Informe a sua senha"
            />
          </div>
          <div className="mb-4">
            <a className="text-sm font-light" href="/app/auth/register">
              Ainda não possui uma conta?
            </a>
          </div>
          <div className="w-full mb-4">
            <button
              className={`flex justify-center items-center w-full rounded text-white bg-slate-950 px-3 py-2 ${loading ? 'opacity-50 cursor-not-allowed' : ''}`}
              disabled={loading}
              type="submit"
            >
              {!loading ? <span>Entrar</span> : <img src={loadingIcon} alt="Carregando" />}
            </button>
          </div>
        </Form>
      </Formik>  
    </UnauthenticatedLayout>
  );
}

export default LoginPage;
