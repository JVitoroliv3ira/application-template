import UserAuthenticationRequestDTO from "../dtos/requests/user-authentication-request.dto";
import ApiResponseDTO from "../dtos/responses/api-response.dto";
import AuthenticatedUserResponseDTO from "../dtos/responses/authenticated-user-response.dto";
import { post } from "./base-http.service";

const userAuthenticationService = async (content: UserAuthenticationRequestDTO): Promise<ApiResponseDTO<AuthenticatedUserResponseDTO>> => {
  return await post<UserAuthenticationRequestDTO, ApiResponseDTO<AuthenticatedUserResponseDTO>>(
    'auth/login',
    content
  );
}

export default userAuthenticationService;
