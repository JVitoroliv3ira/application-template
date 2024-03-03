import UserAuthenticationRequestDTO from "../dtos/requests/user-authentication-request.dto";
import ApiResponseDTO from "../dtos/responses/api-response.dto";
import AuthenticatedUserResponseDTO from "../dtos/responses/authenticated-user-response.dto";
import { get, insert, remove } from "../utils/local-storage-util";
import { post } from "./base-http.service";

const TOKEN_KEY = "AUTHENTICATION_USER_TOKEN";
const EMAIL_KEY = "AUTHENTICATION_USER_EMAIL";

const userAuthenticationService = async (content: UserAuthenticationRequestDTO): Promise<ApiResponseDTO<AuthenticatedUserResponseDTO>> => {
  return await post<UserAuthenticationRequestDTO, ApiResponseDTO<AuthenticatedUserResponseDTO>>(
    'auth/login',
    content
  );
}

const saveToken = (token: string): void => insert(TOKEN_KEY, token);
const saveEmail = (email: string): void => insert(EMAIL_KEY, email);
const getToken = (): string | null => get(TOKEN_KEY);
const getEmail = (): string | null => get(EMAIL_KEY);
const removeToken = (): void => remove(TOKEN_KEY);
const removeEmail = (): void => remove(EMAIL_KEY);

export default userAuthenticationService;
export { saveToken, saveEmail, getToken, getEmail, removeToken, removeEmail };
